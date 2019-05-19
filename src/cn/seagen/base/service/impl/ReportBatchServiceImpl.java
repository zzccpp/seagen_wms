package cn.seagen.base.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.seagen.base.constant.EquipmentConstant;
import cn.seagen.base.dao.ReportBatchDao;
import cn.seagen.base.excel.PoiCell;
import cn.seagen.base.excel.PoiData;
import cn.seagen.base.excel.PoiExcel;
import cn.seagen.base.service.ReportBatchService;
import cn.seagen.base.utils.ReportUtils;
import cn.seagen.base.vo.EasyuiGridData;
import cn.seagen.base.vo.EasyuiGridSumData;
import cn.seagen.base.vo.Reportfun;
import cn.seagen.base.vo.RequestBase;
@Service
public class ReportBatchServiceImpl implements ReportBatchService{
	private static Logger logger = LogManager.getLogger(ReportBatchServiceImpl.class);

	@Resource
	private ReportBatchDao reportBatchDaoImpl;
	
	public ReportBatchServiceImpl() {
		super();
	}
	
	private static String[] f_titles = new String[] { "批次ID","开机时间", "关机时间"};
	private static String[] f_fields = new String[] {"sum_name","batch_begin","batch_end"};
	
	private static String[] titles = new String[] { "吞吐量","正常分拣", "异常分拣","正常分拣率","正常分拣率（包含无读）","工作时间(H)",//
		"每小时分拣量(件/H)","无读","无方案","多条码","无数据","拦截","错分","最大循环圈数","迷失","封包量"};
	private static String[] fields = new String[] { "all_sum","success_sum","err_sum",//
		"persent_read","persent_noread","work_time","supply_houre","no_reade","no_chute","more_data",//
		"no_data","cancel_sum","err_chute","max_cycles","lost_data","box_sum"};
	private final static  int FROZEN_FIELDS_LEN = 8;//固定列数

	@Override
	public Object queryBatch(RequestBase requestBase) {
		logger.info("queryBatch:page=" + requestBase.getPage() + ",row="//
				+ requestBase.getRows()+ ",type=" + requestBase.getType());
		EasyuiGridData easyuiGridData = new EasyuiGridData();
		// 固定标题
		List<Object> frozenColumnsList = new ArrayList<Object>();
		
		for(int i = 0;i < f_titles.length;i++){
			if(i == 0){
				frozenColumnsList.add(Reportfun.getColumn(f_titles[i], f_fields[i],140));
			} else {
				frozenColumnsList.add(Reportfun.getColumn(f_titles[i], f_fields[i],100));
			}
		}
		// 不固定标题
		List<Object> columnsList = new ArrayList<Object>();
		// 不固定标题的子标题
		int scan_num = 0;
		int supply_num = 0;
		int len = FROZEN_FIELDS_LEN;
		if(requestBase.getMore() != 0) {
			len = fields.length;
			scan_num = EquipmentConstant.scanNum; // 取当前的龙门架扫描仪个数
			supply_num = EquipmentConstant.supplyNum; // 取当前的导入台个数
		}
		for(int i = 0;i < len;i++){
			if(i == 3 || i == 4){
				columnsList.add(Reportfun.getColumn(titles[i], fields[i], "format_two_decimal",true,140));
			} else {
				columnsList.add(Reportfun.getColumn(titles[i], fields[i]));
			}
		}
		
		for (int i = 1; i <= scan_num; i++) {
			String titlename = String.format("扫描架" + String.format("%2d", i).replace(" ", "0"));
			columnsList.add(Reportfun.getColumn(titlename, "scan" + i));
		}
		for (int i = 1; i <= supply_num; i++) {
			String titlename = String.format("导入台" + String.format("%2d", i).replace(" ", "0"));
			columnsList.add(Reportfun.getColumn(titlename, "supply" + i));
			columnsList.add(Reportfun.getColumn(titlename+"无读", "noread" + i));
		}
		//组装查询批次统计数量的sql语句
		String numSql = formatBatchNum(requestBase.getType());
		int totals = reportBatchDaoImpl.findBatchNum(numSql);
		if(totals <= 0){
			easyuiGridData.setRows(new ArrayList<Map<String,Object>>());
			easyuiGridData.setTotal(0);
			return easyuiGridData;
		}
		//组装查询语句
		String sql = formatBatchSql(requestBase.getType(), requestBase.getMore(), requestBase.getPage(), requestBase.getRows());
		List<Map<String, Object>> dataList = reportBatchDaoImpl.queryBatch(sql);
		
		easyuiGridData.setRows(dataList);
		easyuiGridData.setTotal(totals);
		
		return easyuiGridData;
	}

	@Override
	public byte[] exportBatch(RequestBase requestBase) {
		logger.info("exportBatch:page=" + requestBase.getPage() + ",row="//
				+ requestBase.getRows()+ ",type=" + requestBase.getType());
		PoiData poiData = new PoiData();

		// 根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)
		int type = requestBase.getType();
		// 固定标题第一行
		String first_name = ReportUtils.getReportFirstName(type);

		// Title
		PoiCell pcTitle = new PoiCell("格口统计查询");
		poiData.setTitle(pcTitle);
		// Query
		PoiCell pcQuery = new PoiCell(first_name);
		poiData.setQuery(pcQuery);
		// Head
		List<List<PoiCell>> heads = new ArrayList<List<PoiCell>>();
		
		// 不固定标题的子标题
		int scan_num = 0;
		int supply_num = 0;
		int len = 8;
		if(requestBase.getMore() != 0) {
			len = fields.length;
			scan_num = EquipmentConstant.scanNum; // 取当前的龙门架扫描仪个数
			supply_num = EquipmentConstant.supplyNum; // 取当前的导入台个数
		}
		List<PoiCell> pcHead1 = new ArrayList<PoiCell>();
		for (int i = 0; i < f_titles.length; i++)
			pcHead1.add(new PoiCell(f_titles[i]));
		for (int i = 0; i < len; i++)
			pcHead1.add(new PoiCell(titles[i]));
		
		for (int i = 1; i <= scan_num; i++) {
			String titlename = String.format("扫描架" + String.format("%2d", i).replace(" ", "0"));
			pcHead1.add(new PoiCell(titlename));
		}		
		for (int i = 1; i <= supply_num; i++) {
			String titlename = String.format("导入台" + String.format("%2d", i).replace(" ", "0"));
			pcHead1.add(new PoiCell(titlename));
			pcHead1.add(new PoiCell(titlename+"无读"));
		}

		heads.add(pcHead1);
		poiData.setHeads(heads);

		// Data
		List<List<PoiCell>> datas = new ArrayList<List<PoiCell>>();
		
		//组装查询语句
		String sql = formatBatchSql(requestBase.getType(), requestBase.getMore(), requestBase.getPage(), requestBase.getRows());
		List<Map<String, Object>> dataList = reportBatchDaoImpl.queryBatch(sql);
		
				
		Map<String, Object> map = null;
		for (int i = 0; i < dataList.size(); i++) {
			map = dataList.get(i);// 拿到一行数据
			List<PoiCell> pcdata = new ArrayList<PoiCell>();
			for (int j = 0; j < f_fields.length; j++) {
				pcdata.add(new PoiCell(map.get(f_fields[j]).toString()));
			}
			for (int j = 0; j < len; j++) {
				pcdata.add(new PoiCell(map.get(fields[j]).toString()));
			}
			for (int j = 1; j <= scan_num; j++) {
				String titlename = "scan" + j;
				pcdata.add(new PoiCell(map.get(titlename).toString()));
			}
			for (int j = 1; j <= supply_num; j++) {
				String titlename = "supply" + j;
				pcdata.add(new PoiCell(map.get(titlename).toString()));
				pcdata.add(new PoiCell(map.get("noread"+j).toString()));
			}
			datas.add(pcdata);
		}
		poiData.setDatas(datas);

		PoiExcel pe = new PoiExcel();
		pe.setSplitPos(5, 4); // 冻结行列
		pe.setDataAndExcute(poiData);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			pe.getWorkbook().write(out);
			return out.toByteArray();
		} catch (Exception e) {
			logger.error("批次量统计导出异常："+e.getMessage(), e);
			return null;
		} finally {
			ReportUtils.CloseOpStream(out);
		}
	}

	@Override
	public Object getBatchColumns(RequestBase requestBase) {
		EasyuiGridSumData easyuiGridSumData = new EasyuiGridSumData();
		// 固定标题
		List<Object> frozenColumnsList = new ArrayList<Object>();
		for(int i = 0;i < f_titles.length;i++){
			if(i == 0){
				frozenColumnsList.add(Reportfun.getColumn(f_titles[i], f_fields[i],140));
			} else {
				frozenColumnsList.add(Reportfun.getColumn(f_titles[i], f_fields[i],100));
			}
		}
		// 不固定标题
		List<Object> columnsList = new ArrayList<Object>();
		// 不固定标题的子标题
		int scan_num = 0;
		int supply_num = 0;
		int len = FROZEN_FIELDS_LEN;
		if(requestBase.getMore() != 0) {
			len = fields.length;
			scan_num = EquipmentConstant.scanNum; // 取当前的龙门架扫描仪个数
			supply_num = EquipmentConstant.supplyNum; // 取当前的导入台个数
		}

		for(int i = 0;i < len;i++){
			if(i == 3 || i == 4){
				columnsList.add(Reportfun.getColumn(titles[i], fields[i], "format_two_decimal",true,140));
			} else {
				columnsList.add(Reportfun.getColumn(titles[i], fields[i]));
			}
		}
		
		for (int i = 1; i <= scan_num; i++) {
			String titlename = String.format("扫描架" + String.format("%2d", i).replace(" ", "0"));
			columnsList.add(Reportfun.getColumn(titlename, "scan" + i));
		}
		for (int i = 1; i <= supply_num; i++) {
			String titlename = String.format("导入台" + String.format("%2d", i).replace(" ", "0"));
			columnsList.add(Reportfun.getColumn(titlename, "supply" + i));
			columnsList.add(Reportfun.getColumn(titlename+"无读", "noread" + i));
		}
		easyuiGridSumData.setColumns(columnsList);
		easyuiGridSumData.setFrozenColumns(frozenColumnsList);
		return easyuiGridSumData;
	}

	/**
	 * 组装获取批次统计数量的sql语句
	 * @param type 0近一周、1近一月2近三个月3近半年(半年的考虑数据量暂不开放)
	 * @return
	 */
	public static String formatBatchNum(int type){
		
		String sql = "select count(*) as o_count from report_sorting where sum_type = 0 "
				+ "and date_format(begin_time, '%Y-%m-%d') >= date_format(";
		String date = "";
		switch (type) {
		case 0:
			date = "date_add(now(), interval -7 day)";
			break;
		case 1:
			date = "date_add(curdate(), interval -1 month)";
			break;
		case 2:
			date = "date_add(curdate(), interval -3 month)";
			break;
		case 3:
			date = "date_add(curdate(), interval -6 month)";
			break;
		default:
			date = "date_add(now(), interval -7 day)";
			break;
		}
		sql = sql + date + ", '%Y-%m-%d')";
		return sql;
	}
	
	/**
	 * 格式化批次量统计的sql
	 * @param i_type 0近一周、1近一月2近三个月3近半年(半年的考虑数据量暂不开放)
	 * @param i_more 是否显示更多信息0不显示，非0显示
	 * @param i_page 查询第几页
	 * @param i_rows 每页显示多少条数据
	 * @return
	 */
	public static String formatBatchSql(int i_type,int i_more,int i_page,int i_rows){
		int v_start_index = (i_page - 1) * i_rows;
		if (v_start_index < 0) {
			v_start_index = 0;
		}

		//判断请求的数据类型 3近半年(半年的考虑数据量暂不开放)
		String report_date = "";
		if(i_type==1){
			report_date = "date_add(curdate(), interval -1 month)";
		} else if(i_type==2) {
			report_date = "date_add(now(), interval -3 month)";
		//elseif (i_type=3) then
		//	String report_date = date_add(curdate(), interval -6 month);
		} else {
			report_date = "date_add(now(), interval -7 day)";
		}
		StringBuilder builder = new StringBuilder();
		if (i_more != 0) {
			builder.append("select sum_name, date_format(begin_time, '%m-%d %H:%i') as batch_begin,");
			builder.append("	date_format(end_time, '%m-%d %H:%i') as batch_end,");
			builder.append("	all_sum, success_sum, err_sum,");
			builder.append("	(case when all_sum > 0 then format((success_sum *100) /all_sum,3) else 0 end) as persent_read,");
			builder.append("	(case when all_sum > 0 then format(((success_sum + no_reade) *100) /all_sum,3) else 0 end) as persent_noread,");
			builder.append("	format(timestampdiff(second, begin_time, end_time) / 3600.0, 2) as work_time,");
			builder.append("	(case when format(timestampdiff(second, begin_time, end_time) / 3600.0, 2) > 0 then truncate(");
			builder.append("		all_sum / format(timestampdiff(second, begin_time, end_time) / 3600.0, 2), 0) else 0 end) as supply_houre,");
			builder.append("	no_chute, more_data, no_reade, no_data, cancel_sum, err_chute, max_cycles, lost_data, box_sum,");
			builder.append("	scan1, scan2, scan3, scan4, scan5, scan6, scan7, scan8, scan9, scan10,");
			builder.append("	scan11, scan12, scan13, scan14, scan15, scan16,");
			builder.append("	supply1, supply2, supply3, supply4, supply5, supply6, supply7, supply8,");
			builder.append("	supply9, supply10, supply11, supply12, supply13, supply14, supply15, supply16,");
			builder.append("	supply17, supply18, supply19, supply20, supply21, supply22, supply23, supply24,");
			builder.append("	noread1, noread2, noread3, noread4, noread5, noread6, noread7, noread8,");
			builder.append("	noread9, noread10, noread11, noread12, noread13, noread14, noread15, noread16,");
			builder.append("	noread17, noread18, noread19, noread20, noread21, noread22, noread23, noread24 ");
			builder.append("from report_sorting ");
			builder.append("where date_format(begin_time, '%Y-%m-%d') >= date_format("+report_date+", '%Y-%m-%d')");
			builder.append("	and sum_type = 0 ");
			builder.append("order by f_recno desc limit "+v_start_index+", "+i_rows+";");
		}else{
			builder.append("select sum_name, date_format(begin_time, '%m-%d %H:%i') as batch_begin,");
			builder.append("	date_format(end_time, '%m-%d %H:%i') as batch_end,");
			builder.append("	all_sum, success_sum, err_sum, no_reade,");
			builder.append("	(case when all_sum > 0 then format((success_sum *100) /all_sum,3) else 0 end) as persent_read,");
			builder.append("	(case when all_sum > 0 then format(((success_sum + no_reade) *100) /all_sum,3) else 0 end) as persent_noread,");
			builder.append("	format(timestampdiff(second, begin_time, end_time) / 3600.0, 2) as work_time,");
			builder.append("	(case when format(timestampdiff(second, begin_time, end_time) / 3600.0, 2) > 0 then truncate(");
			builder.append("		all_sum / format(timestampdiff(second, begin_time, end_time) / 3600.0, 2), 0) else 0 end) as supply_houre ");
			builder.append("from report_sorting ");
			builder.append("where date_format(begin_time, '%Y-%m-%d') >= date_format("+report_date+", '%Y-%m-%d')");
			builder.append("	and sum_type = 0 ");
			builder.append("order by f_recno desc limit "+v_start_index+", "+i_rows+";");
		}
		return builder.toString();
	}
}
