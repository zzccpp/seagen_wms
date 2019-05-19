package cn.seagen.base.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.seagen.base.constant.EquipmentConstant;
import cn.seagen.base.dao.ReportScanDao;
import cn.seagen.base.enums.ReportType;
import cn.seagen.base.excel.PoiCell;
import cn.seagen.base.excel.PoiData;
import cn.seagen.base.excel.PoiExcel;
import cn.seagen.base.service.ReportScanService;
import cn.seagen.base.utils.DateUtils;
import cn.seagen.base.utils.ReportUtils;
import cn.seagen.base.vo.EasyuiGridSumData;
import cn.seagen.base.vo.Reportfun;
import cn.seagen.base.vo.RequestBase;
@Service
public class ReportScanServiceImpl implements ReportScanService{
	private static Logger logger = LogManager.getLogger(ReportScanServiceImpl.class);
	
	private static String[] titles = new String[] { "扫描总量", "正常量", "多条码","无读量", "正常占比(%)"};
	private static String[] fields = new String[] { "all_sum", "success_sum", "more_data","no_reade","persent"};
	
	@Resource
	private ReportScanDao reportScanDaoImpl;
	
	public ReportScanServiceImpl() {
		super();
	}

	@Override
	public Object queryScan(RequestBase requestBase) {
		logger.info("queryScan:startTm=" + requestBase.getDate() + //
				",endTm=" + requestBase.getEnd_date() + ",type=" + requestBase.getType());
		EasyuiGridSumData easyuiGridSumData = new EasyuiGridSumData();
		// 根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)
		int type = requestBase.getType();
		// 存在多级标题时，第一行标题个数
		int nums = ReportUtils.getReportNums(type, requestBase.getDate());
		// 固定标题第一行
		String first_name = ReportUtils.getReportFirstName(type, requestBase);
		// 固定标题
		List<Object> frozenColumnsList = new ArrayList<Object>();
		// 固定标题的子标题
		Map<String, Object> frozencolumn = new HashMap<String,Object>();
		
		// 不固定标题
		List<Object> columnsList = new ArrayList<Object>();
		// 根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)来设置不固定子标题
		if (type == ReportType.REP_TIME.getValue()) {
			//根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)来设置固定子标题
			frozencolumn = Reportfun.getColumn("龙门架号", "scan_id");
			for(int i = 0;i < titles.length;i++){
				if(i == titles.length-1){
					columnsList.add(Reportfun.getColumn(titles[i], fields[i], "format_two_decimal"));
				} else {
					columnsList.add(Reportfun.getColumn(titles[i], fields[i]));
				}
			}
			frozenColumnsList.add(frozencolumn);
		}
		if (type == ReportType.REP_DAY.getValue() || type == ReportType.REP_MONTH.getValue()//
				|| type == ReportType.REP_YEAR.getValue()) {
			//根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)来设置固定子标题
			frozencolumn = Reportfun.getColumn(first_name, "count_point");
			frozenColumnsList.add(frozencolumn);
			frozenColumnsList.add(Reportfun.getColumn("数据类型", "count_type",100));
//			columnsList.add(Reportfun.getColumn("数据类型", "count_type"));
			int scanNum = 0;
			if(requestBase.getLayer() == 1){
				scanNum = EquipmentConstant.firsrLayerScanNum;
				for(int j = 0;j < scanNum;j++){
					columnsList.add(Reportfun.getColumn("1-"+ReportUtils.formatStr(""+(j+1), 3)+"龙门架", "scan_"+(j+1)+"_1"));
				}
			}else if(requestBase.getLayer() == 2){
				scanNum = EquipmentConstant.secondLayerScanNum;
				for(int j = 0;j < scanNum;j++){
					columnsList.add(Reportfun.getColumn("2-"+ReportUtils.formatStr(""+(j+1), 3)+"龙门架", "scan_"+(j+1)+"_2"));
				}
			}else{
				scanNum = EquipmentConstant.firsrLayerScanNum;
				for(int j = 0;j < scanNum;j++){
					columnsList.add(Reportfun.getColumn("1-"+ReportUtils.formatStr(""+(j+1), 3)+"龙门架", "scan_"+(j+1)+"_1"));
				}
				scanNum = EquipmentConstant.secondLayerScanNum;
				for(int j = 0;j < scanNum;j++){
					columnsList.add(Reportfun.getColumn("2-"+ReportUtils.formatStr(""+(j+1), 3)+"龙门架", "scan_"+(j+1)+"_2"));
				}
			}
		}

		//组装sql语句查询扫描量统计数据
		String sql = "";
		List<Map<String, Object>> dataList  = new ArrayList<Map<String,Object>>();
		if(requestBase.getLayer() == 0){
			sql = formatScanSqlList(requestBase.getDate(), requestBase.getEnd_date(), requestBase.getType(),1);
			dataList = reportScanDaoImpl.queryScan(sql);
			sql = formatScanSqlList(requestBase.getDate(), requestBase.getEnd_date(), requestBase.getType(),2);
			dataList.addAll(reportScanDaoImpl.queryScan(sql));
		}else{
			sql = formatScanSqlList(requestBase.getDate(), requestBase.getEnd_date(), requestBase.getType(),requestBase.getLayer());
			dataList = reportScanDaoImpl.queryScan(sql);
		}
		List<Map<String, Object>> ddList = new ArrayList<Map<String,Object>>();
		if(type != ReportType.REP_TIME.getValue()){
			//模拟该统计类型的数据集合
			List<Map<String, Object>> dataListS = formatScanDataMap(nums,requestBase.getLayer());
			//模拟该统计类型的数据总行数
			List<String> mapList =formatMinuteList(nums,requestBase.getLayer());
			for (Iterator<Map<String, Object>> iterator = dataList.iterator(); iterator.hasNext();) {
				Map<String, Object> map = (Map<String, Object>) iterator.next();
				String key = map.get("date_val")+"_"+map.get("scan_id");
				for (int i = 0; i < mapList.size(); i++) {
					if(mapList.get(i).equals(key)){
						//判断key一致，则直接更新构造的dataListS
						dataListS.set(i, map);
						break;
					}
				}
			}
			//龙门架数量
			int scanNum = 0;
			if(requestBase.getLayer() == 1){
				scanNum = EquipmentConstant.firsrLayerScanNum;
			}else if(requestBase.getLayer() == 2){
				scanNum = EquipmentConstant.secondLayerScanNum;
			}else{
				scanNum = EquipmentConstant.scanNum;
			}
			//当前页面需要显示的总行数是nums*scanNum=rows，故在组装数据时需要完成对rows的赋值
			//数据库查询出来的数据，通过上方对dataList的遍历，将值赋给新构造的dataListS中（rows条数据），共：（2+scanNum）列数据
			//每列数据如下mm.put中的key值
			int ro = 0;
			for(int j = 1;j <= nums;j++){
				String titlename = ReportUtils.getReportHead(type, j-1);
				for(int h = 0;h < fields.length;h++){
					//如：{scan_1=788784, scan_13=788539, scan_14=789078, scan_15=788767, scan_16=788328, 
					//	scan_10=786455, scan_11=790142, scan_12=787959, count_type=扫描总量, count_point=01月, 
					//	scan_2=787917, scan_3=788074, scan_4=787591, scan_5=786967, scan_6=789347, 
					//	scan_7=788470, scan_8=787590, scan_9=788720}
					Map<String, Object> mm = new HashMap<String, Object>();
					String key = fields[h];
					//遍历获取单行数据
					for(int i = 1;i <= scanNum;i++){
						ro = (j-1)*scanNum+(i-1);
						Map<String, Object> map = dataListS.get(ro);
						mm.put("scan_"+map.get("scan_id"), map.get(key));
					}
					mm.put("count_type", titles[h]);
					mm.put("count_point", titlename);
					ddList.add(mm);
				}
			}
		} 
		
		if (type == ReportType.REP_TIME.getValue()) {
			easyuiGridSumData.setData(dataList);
		} else {
			easyuiGridSumData.setData(ddList);
		}
		
		easyuiGridSumData.setFrozenColumns(frozenColumnsList);
		easyuiGridSumData.setColumns(columnsList);
		
		return easyuiGridSumData;
	}

	@Override
	public byte[] exportScan(RequestBase requestBase) {
		logger.info("exportScan:startTm=" + requestBase.getDate() +",endTm="//
				+ requestBase.getEnd_date() + ",type=" + requestBase.getType());
		PoiData poiData = new PoiData();

		// 根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)
		int type = requestBase.getType();
		// 存在多级标题时，第一行标题个数
		int nums = ReportUtils.getReportNums(type, requestBase.getDate());
		// 固定标题第一行
		String first_name = ReportUtils.getReportFirstName(type, requestBase);

		// Title
		PoiCell pcTitle = new PoiCell("扫描统计查询");
		poiData.setTitle(pcTitle);
		// Query
		PoiCell pcQuery = new PoiCell(first_name);
		poiData.setQuery(pcQuery);
		// Head
		List<List<PoiCell>> heads = new ArrayList<List<PoiCell>>();
		if (type == ReportType.REP_TIME.getValue()) {
			List<PoiCell> pcHead1 = new ArrayList<PoiCell>();
			pcHead1.add(new PoiCell("龙门架号"));
			for (int i = 0; i < titles.length; i++)
				pcHead1.add(new PoiCell(titles[i]));

			heads.add(pcHead1);
		} else {// (此处是提供相对于以左上1,1格子为参照的精准的CELL位置)
			List<PoiCell> pcHead1 = new ArrayList<PoiCell>();
			pcHead1.add(new PoiCell(1, 1, 1, 1, "统计时间"));
			pcHead1.add(new PoiCell(1, 1, 2, 1, "数据类型"));
//			for(int j = 0;j < EquipmentConstant.scanNum;j++){
//				pcHead1.add(new PoiCell(1, 1, 2+(j+1), 1, ReportUtils.formatStr(""+(j+1), 3)+"龙门架"));
//			}
			int scanNum = 0;
			if(requestBase.getLayer() == 1){
				scanNum = EquipmentConstant.firsrLayerScanNum;
				for(int j = 0;j < scanNum;j++){
					pcHead1.add(new PoiCell(1, 1, 2+(j+1), 1, "1-"+ReportUtils.formatStr(""+(j+1), 3)+"龙门架"));
				}
			}else if(requestBase.getLayer() == 2){
				scanNum = EquipmentConstant.secondLayerScanNum;
				for(int j = 0;j < scanNum;j++){
					pcHead1.add(new PoiCell(1, 1, 2+(j+1), 1, "2-"+ReportUtils.formatStr(""+(j+1), 3)+"龙门架"));
				}
			}else{
				scanNum = EquipmentConstant.firsrLayerScanNum;
				for(int j = 0;j < scanNum;j++){
					pcHead1.add(new PoiCell(1, 1, 2+(j+1), 1, "1-"+ReportUtils.formatStr(""+(j+1), 3)+"龙门架"));
				}
				scanNum = EquipmentConstant.secondLayerScanNum;
				for(int j = 0;j < scanNum;j++){
					pcHead1.add(new PoiCell(1, 1, EquipmentConstant.firsrLayerScanNum+2+(j+1), 1, "2-"+ReportUtils.formatStr(""+(j+1), 3)+"龙门架"));
				}
			}
			heads.add(pcHead1);
		}
		poiData.setHeads(heads);

		// Data
		List<List<PoiCell>> datas = new ArrayList<List<PoiCell>>();
		//组装sql语句查询扫描量统计数据
		String sql = "";
		List<Map<String, Object>> dataList  = new ArrayList<Map<String,Object>>();
		if(requestBase.getLayer() == 0){
			sql = formatScanSqlList(requestBase.getDate(), requestBase.getEnd_date(), requestBase.getType(),1);
			dataList = reportScanDaoImpl.queryScan(sql);
			sql = formatScanSqlList(requestBase.getDate(), requestBase.getEnd_date(), requestBase.getType(),2);
			dataList.addAll(reportScanDaoImpl.queryScan(sql));
		}else{
			sql = formatScanSqlList(requestBase.getDate(), requestBase.getEnd_date(), requestBase.getType(),requestBase.getLayer());
			dataList = reportScanDaoImpl.queryScan(sql);
		}
		
		//龙门架数量
		int scanNum = 0;
		if(requestBase.getLayer() == 1){
			scanNum = EquipmentConstant.firsrLayerScanNum;
		}else if(requestBase.getLayer() == 2){
			scanNum = EquipmentConstant.secondLayerScanNum;
		}else{
			scanNum = EquipmentConstant.scanNum;
		}
		
		List<Map<String, Object>> ddList = new ArrayList<Map<String,Object>>();
		if(type != ReportType.REP_TIME.getValue()){
			//模拟该统计类型的数据集合
			List<Map<String, Object>> dataListS = formatScanDataMap(nums,requestBase.getLayer());
			//模拟该统计类型的数据总行数
			List<String> mapList =formatMinuteList(nums,requestBase.getLayer());
			for (Iterator<Map<String, Object>> iterator = dataList.iterator(); iterator.hasNext();) {
				Map<String, Object> map = (Map<String, Object>) iterator.next();
				String key = map.get("date_val")+"_"+map.get("scan_id");
				for (int i = 0; i < mapList.size(); i++) {
					if(mapList.get(i).equals(key)){
						//判断key一致，则直接更新构造的dataListS
						dataListS.set(i, map);
						break;
					}
				}
			}
			//当前页面需要显示的总行数是nums*scanNum=rows，故在组装数据时需要完成对rows的赋值
			//数据库查询出来的数据，通过上方对dataList的遍历，将值赋给新构造的dataListS中（rows条数据），共：（2+scanNum）列数据
			//每列数据如下mm.put中的key值
			int ro = 0;
			for(int j = 1;j <= nums;j++){
				String titlename = ReportUtils.getReportHead(type, j-1);
				for(int h = 0;h < fields.length;h++){
					//如：{scan_1=788784, scan_13=788539, scan_14=789078, scan_15=788767, scan_16=788328, 
					//	scan_10=786455, scan_11=790142, scan_12=787959, count_type=扫描总量, count_point=01月, 
					//	scan_2=787917, scan_3=788074, scan_4=787591, scan_5=786967, scan_6=789347, 
					//	scan_7=788470, scan_8=787590, scan_9=788720}
					Map<String, Object> mm = new HashMap<String, Object>();
					String key = fields[h];
					//遍历获取单行数据
					for(int i = 1;i <= scanNum;i++){
						ro = (j-1)*scanNum+(i-1);
						Map<String, Object> map = dataListS.get(ro);
						mm.put("scan_"+map.get("scan_id"), map.get(key));
					}
					mm.put("count_type", titles[h]);
					mm.put("count_point", titlename);
					ddList.add(mm);
				}
			}
		}
		// 求和数据
		Map<String, Object> totaldata = new HashMap<String, Object>();
		int total = 0;
		Object ob = null;
		if (type == ReportType.REP_TIME.getValue()) {
			Map<String, Object> map = null;
			for (int i = 0; i < dataList.size(); i++) {
				map = dataList.get(i);// 拿到一行数据
				List<PoiCell> pcdata = new ArrayList<PoiCell>();
				pcdata.add(new PoiCell(map.get("scan_id").toString()));
				for (int j = 0; j < fields.length; j++) {
					pcdata.add(new PoiCell(map.get(fields[j]).toString()));
					// 以下是统计
					ob = map.get(fields[j]);
					if(fields[j].equals("persent")) {
						totaldata.put(fields[j], (Double) (totaldata.containsKey(fields[j]) ? ((Double)totaldata.get(fields[j])
								+ Double.parseDouble(ob.toString())) : Double.parseDouble(ob.toString()))); // 
						
					}else{
						total = (Integer) (totaldata.containsKey(fields[j]) ? ((Integer)totaldata.get(fields[j]) 
								+ Integer.parseInt(ob.toString())) : Integer.parseInt(ob.toString()));
						totaldata.put(fields[j], total); //
					}
					
				}
				datas.add(pcdata);
			}
		} else {
			Map<String, Object> map = null;
			for (int i = 0; i < ddList.size(); i++) {
				map = ddList.get(i);// 拿到一行数据
				List<PoiCell> pcdata = new ArrayList<PoiCell>();
				pcdata.add(new PoiCell(map.get("count_point").toString()));
				pcdata.add(new PoiCell(map.get("count_type").toString()));
				if(requestBase.getLayer() == 1){
					scanNum = EquipmentConstant.firsrLayerScanNum;
					for (int j = 0; j < scanNum; j++) {
						String valuename = ""+(j + 1);
						pcdata.add(new PoiCell(map.get("scan_" + valuename+"_1").toString()));
					}
				}else if(requestBase.getLayer() == 2){
					scanNum = EquipmentConstant.secondLayerScanNum;
					for (int j = 0; j < scanNum; j++) {
						String valuename = ""+(j + 1);
						pcdata.add(new PoiCell(map.get("scan_" + valuename+"_2").toString()));
					}
				}else{
					scanNum = EquipmentConstant.firsrLayerScanNum;
					for (int j = 0; j < scanNum; j++) {
						String valuename = ""+(j + 1);
						pcdata.add(new PoiCell(map.get("scan_" + valuename+"_1").toString()));
					}
					scanNum = EquipmentConstant.secondLayerScanNum;
					for (int j = 0; j < scanNum; j++) {
						String valuename = ""+(j + 1);
						pcdata.add(new PoiCell(map.get("scan_" + valuename+"_2").toString()));
					}
				}
				datas.add(pcdata);
			}
		}
		poiData.setDatas(datas);
		//
		PoiExcel pe = new PoiExcel();
		if (type == ReportType.REP_TIME.getValue()) {
			pe.setSplitPos(4, 2); // 冻结行列
		} else {
			pe.setSplitPos(4, 3); // 冻结行列
		}
		pe.setDataAndExcute(poiData);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			pe.getWorkbook().write(out);
			return out.toByteArray();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			ReportUtils.CloseOpStream(out);
		}
	}
	
	/**
	 * 组装扫描量统计时的sql语句
	 * @param startTime 开始时间
	 * @param endTime 结束时间（时间段统计时）
	 * @param type 0 时间段;1 天;2 月;3 年;
	 * @param layer_id 层级id
	 * @return
	 */
	public static String formatScanSqlList(String startTime,String endTime,int type,int layer_id){
		StringBuilder e_sql = new StringBuilder();
		String r_sql = "";
		int scanNum  = 0;
		if(layer_id == 1){
			scanNum = EquipmentConstant.firsrLayerScanNum;
		}else if(layer_id == 2){
			scanNum = EquipmentConstant.secondLayerScanNum;
		}
		if (type==0){
			//REPORT表中明细段----------------------------
			r_sql = "(select scan_id,layer_id, sum(all_sum) as all_sum, sum(success_sum) as success_sum , sum(more_data) as more_data, sum(no_reade) "
					+ "as no_reade from report_scan where report_date between date_format('"+startTime+"', '%Y-%m-%d %H:%i') "
					+ "and date_format('"+endTime+"', '%Y-%m-%d %H:%i') and layer_id = "+layer_id+" and scan_id > 0 and scan_id <= "+scanNum+" group by scan_id order by scan_id) A ";

			//输出SQL-------------------------------------
			e_sql.append("select concat('").append(layer_id).append("','-',lpad(date_val, 3, '0'), '龙门架') as scan_id \n");
			e_sql.append(" , layer_id \n");
			e_sql.append(" , ifnull(sum(all_sum), 0) as all_sum \n");
			e_sql.append(" , ifnull(sum(success_sum), 0) as success_sum \n");
			e_sql.append(" , ifnull(sum(more_data), 0) as more_data \n");
			e_sql.append(" , ifnull(sum(no_reade), 0) as no_reade \n");
			e_sql.append(" , case when ifnull(sum(all_sum),0) > 0 then format(sum(success_sum)/sum(all_sum)*100, 2) else 0.00 end as persent \n");

			e_sql.append("  from flag_date left join "+r_sql+" on convert(date_val, signed) = convert(scan_id, signed) "
					+ "where convert(date_val, signed) <= "+scanNum+" group by date_val order by scan_id; \n");

		} else {
			e_sql.append(" select date_val,A.scan_id,A.layer_id,sum(A.all_sum) as all_sum,sum(A.success_sum) as success_sum, \n");
			e_sql.append(" sum(A.more_data) as more_data,sum(A.no_reade) as no_reade, \n");
			e_sql.append(" case when ifnull(sum(all_sum), 0) > 0 then format(sum(success_sum)/sum(all_sum)*100, 2) else 0.00 end as persent");
			e_sql.append(" from(	select	concat(scan_id,'_',layer_id) as scan_id,layer_id,sum(all_sum) as all_sum,date_format(report_date, '"+ReportUtils.getTimeFormat(2, type)+"') as date_val, \n");
			e_sql.append(" date_format(report_date, '"+ReportUtils.getTimeFormat(3, type)+"') as report_date,sum(success_sum) as success_sum, \n");
			e_sql.append(" sum(more_data) as more_data,sum(no_reade) as no_reade \n");
			e_sql.append(" from report_scan where \n");
			e_sql.append(" report_date > '"+DateUtils.formatReportStartTime(startTime,type)+"' and report_date < '"+DateUtils.formatReportEndTime(startTime,type)+"' \n");
			e_sql.append(" and layer_id = "+layer_id).append(" and scan_id > 0 and scan_id <= "+scanNum+" group by report_date,scan_id) A \n");
			e_sql.append(" group by report_date,scan_id; \n");
		}
		return e_sql.toString();
	}
	
	/**
	 * 组装扫描量统计时：统计涉及到的行数（时间点+扫描仪id做key值）
	 * @param i_num 天：24，月：月的天数；年：12
	 * @param layer_id 层级id
	 * @return
	 */
	public static List<String> formatMinuteList(int i_num,int layer_id){
		int scanNum = 0;
		if(layer_id == 1){
			scanNum = EquipmentConstant.firsrLayerScanNum;
		}else if(layer_id == 2){
			scanNum = EquipmentConstant.secondLayerScanNum;
		}
		List<String> dataList = new ArrayList<String>();
		for(int i = 0;i < i_num;i++){
			if(layer_id != 0){
				for(int j = 1;j <= scanNum;j++){
					//按天查询时，起始值是0
					dataList.add(ReportUtils.formatStr(""+i, 2)+"_"+j+"_"+layer_id);
				}
			}
			if(layer_id == 0){
				scanNum = EquipmentConstant.firsrLayerScanNum;
				for(int j = 1;j <= scanNum;j++){
					//按天查询时，起始值是0
					dataList.add(ReportUtils.formatStr(""+i, 2)+"_"+j+"_"+1);
				}
				scanNum = EquipmentConstant.secondLayerScanNum;
				for(int j = 1;j <= scanNum;j++){
					//按天查询时，起始值是0
					dataList.add(ReportUtils.formatStr(""+i, 2)+"_"+j+"_"+2);
				}
			}
		}
		return dataList;
	}
	
	/**
	 * 组装扫描量统计时：统计涉及到的数据列表
	 * @param i_num 天：24，月：月的天数；年：12
	 * @param layer_id 层级id
	 * @return
	 */
	public static List<Map<String, Object>> formatScanDataMap(int i_num,int layer_id){
		int scanNum = 0;
		if(layer_id == 1){
			scanNum = EquipmentConstant.firsrLayerScanNum;
		}else if(layer_id == 2){
			scanNum = EquipmentConstant.secondLayerScanNum;
		}
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>(i_num*scanNum);
		Map<String, Object> map = null;
		
		for(int i = 0;i < i_num;i++){
			if(layer_id != 0){
				if(layer_id == 1){
					scanNum = EquipmentConstant.firsrLayerScanNum;
				}else if(layer_id == 2){
					scanNum = EquipmentConstant.secondLayerScanNum;
				}
				for(int j = 1;j <= scanNum;j++){
					map = new HashMap<String, Object>();
					//按天查询时，起始值是0
					map.put("date_val", ReportUtils.formatStr(""+i, 2));
					map.put("scan_id", j+"_"+layer_id);
					map.put("layer_id", layer_id);
					map.put("all_sum", 0);
					map.put("success_sum", 0);
					map.put("more_data", 0);
					map.put("no_reade", 0);
					map.put("persent", 0);
					dataList.add(map);
				}
			}
			if(layer_id == 0){
				scanNum = EquipmentConstant.firsrLayerScanNum;
				for(int j = 1;j <= scanNum;j++){
					map = new HashMap<String, Object>();
					//按天查询时，起始值是0
					map.put("date_val", ReportUtils.formatStr(""+i, 2));
					map.put("scan_id", j+"_"+1);
					map.put("layer_id", 1);
					map.put("all_sum", 0);
					map.put("success_sum", 0);
					map.put("more_data", 0);
					map.put("no_reade", 0);
					map.put("persent", 0);
					dataList.add(map);
				}
				scanNum = EquipmentConstant.secondLayerScanNum;
				for(int j = 1;j <= scanNum;j++){
					map = new HashMap<String, Object>();
					//按天查询时，起始值是0
					map.put("date_val", ReportUtils.formatStr(""+i, 2));
					map.put("scan_id", j+"_"+2);
					map.put("layer_id", 2);
					map.put("all_sum", 0);
					map.put("success_sum", 0);
					map.put("more_data", 0);
					map.put("no_reade", 0);
					map.put("persent", 0);
					dataList.add(map);
				}
			}
		}
		return dataList;
	}

}
