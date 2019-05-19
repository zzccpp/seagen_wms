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
import cn.seagen.base.dao.ReportCarDao;
import cn.seagen.base.enums.ReportType;
import cn.seagen.base.excel.PoiCell;
import cn.seagen.base.excel.PoiData;
import cn.seagen.base.excel.PoiExcel;
import cn.seagen.base.service.ReportCarService;
import cn.seagen.base.utils.ReportUtils;
import cn.seagen.base.vo.EasyuiGridSumData;
import cn.seagen.base.vo.Reportfun;
import cn.seagen.base.vo.RequestBase;

@Service
public class ReportCarServiceImpl implements ReportCarService {

	private static Logger logger = LogManager.getLogger(ReportCarService.class.getName());

	@Resource
	private ReportCarDao reportCarDaoImpl;

	private static String[] titles = new String[] { "分拣总量", "正常量", "异常量", "无读量", "无方案", "多条码", "无数据", "拦截", "错分",
			"最大循环", "迷失" };
	private static String[] fields = new String[] { "sorting_count", "success_sum", "err_sum", "no_reade", "no_chute",
			"more_data", "no_data", "cancel_sum", "err_chute", "max_cycles", "lost_data" };
	private final static int FROZEN_FIELDS_LEN = 4;// 固定列数

	@Override
	public Object querycar(RequestBase requestBase) {

		logger.info("querycar:startTm=" + requestBase.getDate() + ",endTm=" + requestBase.getEnd_date() 
				+ ",type=" + requestBase.getType());
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
		Map<String, Object> frozencolumn = new HashMap<String, Object>();

		int len = FROZEN_FIELDS_LEN;
		if (requestBase.getMore() != 0) {
			len = fields.length;
		}
		// 不固定标题
		List<Object> columnsList = new ArrayList<Object>();
		List<Object> columns_row1 = new ArrayList<Object>();
		List<Object> columns_row2 = new ArrayList<Object>();
		// 根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)来设置不固定子标题
		if (type == ReportType.REP_TIME.getValue()) {
			// 根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)来设置固定子标题
			frozencolumn = Reportfun.getColumn("小车号", "car_id", true);
			columns_row1.add(Reportfun.getColumn(first_name, 1, len));
			for (int i = 0; i < len; i++) {
				columns_row2.add(Reportfun.getColumn(titles[i], fields[i], true));
			}
		}
		if (type == ReportType.REP_DAY.getValue() || type == ReportType.REP_MONTH.getValue() //
				|| type == ReportType.REP_YEAR.getValue()) {
			// 根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)来设置固定子标题
			frozencolumn = Reportfun.getColumn(first_name, "car_id", true);
			for (int i = 1; i <= nums; i++) {
				// 一天24小时
				String titlename = ReportUtils.getReportHead(type, i - 1);
				String fieldname = String.format("%2d", i).replace(" ", "0");
				columns_row1.add(Reportfun.getColumn(titlename, 1, len));
				for (int j = 0; j < len; j++) {
					columns_row2.add(Reportfun.getColumn(titles[j], fields[j] + fieldname, true));
				}
			}
		}
		columnsList.add(columns_row1);
		columnsList.add(columns_row2);
		frozenColumnsList.add(frozencolumn);
		
		int layerId = requestBase.getLayer();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		
		if(layerId ==0 ){
			List<Map<String, Object>> dataList1 = reportCarDaoImpl.queryCar(requestBase,1);
			List<Map<String, Object>> dataList2 = reportCarDaoImpl.queryCar(requestBase,2);
			dataList.addAll(dataList1);
			dataList.addAll(dataList2);
		}else{
			dataList = reportCarDaoImpl.queryCar(requestBase,layerId);
		}
		
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		
		int carNumFlag=0;
		int layer =1;
		// 求和数据
		List<Map<String, Object>> footerList = new ArrayList<Map<String, Object>>();
		Map<String, Object> footerdata = new HashMap<String, Object>();
		footerdata.put("car_id", "合计");
		for (Iterator<Map<String, Object>> iterator = dataList.iterator(); iterator.hasNext();) {
			carNumFlag++;
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			for (String key : map.keySet()) {
				if (key.toLowerCase().equals("car_id".toLowerCase())) {
					if(layerId == 0){
						if(carNumFlag>EquipmentConstant.firsrLayer){
							layer=2;
						}
						map.put("car_id", layer+"-"+map.get("car_id"));
					}else{
						map.put("car_id", layerId+"-"+map.get("car_id"));
					}
					data.add(map);
					continue;
				}
				if (key.toLowerCase().equals("layer_id".toLowerCase())) {
					continue;
				}
				int value = Integer.parseInt(map.get(key).toString());
				footerdata.put(key, (footerdata.get(key) == null) ? value : ((Integer) footerdata.get(key) + value));
			}
		}
		footerList.add(footerdata);

		easyuiGridSumData.setData(data);
		easyuiGridSumData.setColumns(columnsList);
		easyuiGridSumData.setFrozenColumns(frozenColumnsList);
		easyuiGridSumData.setFooter(footerdata);
		return easyuiGridSumData;
	}

	@Override
	public byte[] exportCar(RequestBase requestBase) {
		logger.info("exportCar:startTm=" + requestBase.getDate() +",endTm=" + requestBase.getEnd_date() //
				+ ",type=" + requestBase.getType());
		PoiData poiData = new PoiData();

		// 根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)
		int type = requestBase.getType();
		// 存在多级标题时，第一行标题个数
		int nums = ReportUtils.getReportNums(type, requestBase.getDate());
		// 固定标题第一行
		String first_name = ReportUtils.getReportFirstName(type, requestBase);

		int len = FROZEN_FIELDS_LEN;
		if(requestBase.getMore() != 0) {
			len = fields.length;
		}
		// Title
		PoiCell pcTitle = new PoiCell("小车统计查询");
		poiData.setTitle(pcTitle);
		// Query
		PoiCell pcQuery = new PoiCell(first_name);
		poiData.setQuery(pcQuery);
		// Head
		List<List<PoiCell>> heads = new ArrayList<List<PoiCell>>();
		if (type == ReportType.REP_TIME.getValue()) {
			List<PoiCell> pcHead1 = new ArrayList<PoiCell>();
			pcHead1.add(new PoiCell("小车号"));
			for (int i = 0; i < len; i++)
				pcHead1.add(new PoiCell(titles[i]));

			heads.add(pcHead1);
		} else {// (此处是提供相对于以左上1,1格子为参照的精准的CELL位置)
			List<PoiCell> pcHead1 = new ArrayList<PoiCell>();
			List<PoiCell> pcHead2 = new ArrayList<PoiCell>();

			pcHead1.add(new PoiCell(1, 2, 1, 1, "小车号"));
			for (int i = 0; i < nums; i++) {
				String titlename = ReportUtils.getReportHead(type, i);
				pcHead1.add(new PoiCell(1, 1, 2 + i * len, len, titlename));
				for (int j = 0; j < len; j++)
					pcHead2.add(new PoiCell(2, 1, 2 + i * len + j, 1, titles[j]));
			}
			heads.add(pcHead1);
			heads.add(pcHead2);
		}
		poiData.setHeads(heads);
		
		// Data
		List<List<PoiCell>> datas = new ArrayList<List<PoiCell>>();
		
		int layerId = requestBase.getLayer();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		
		if(layerId ==0 ){
			List<Map<String, Object>> dataList1 = reportCarDaoImpl.queryCar(requestBase,1);
			List<Map<String, Object>> dataList2 = reportCarDaoImpl.queryCar(requestBase,2);
			dataList.addAll(dataList1);
			dataList.addAll(dataList2);
		}else{
			dataList = reportCarDaoImpl.queryCar(requestBase,requestBase.getLayer());
		}
		
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		
		int carNumFlag=0;
		int layer =1;
		for (Iterator<Map<String, Object>> iterator = dataList.iterator(); iterator.hasNext();) {
			carNumFlag++;
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			for (String key : map.keySet()) {
				if (key.toLowerCase().equals("car_id".toLowerCase())) {
					if(layerId == 0){
						if(carNumFlag>EquipmentConstant.firsrLayer){
							layer=2;
						}
						map.put("car_id", layer+"-"+map.get("car_id"));
					}else{
						map.put("car_id", layerId+"-"+map.get("car_id"));
					}
					data.add(map);
					continue;
				}
				if (key.toLowerCase().equals("layer_id".toLowerCase())) {
					continue;
				}
			}
		}
		// 求和数据
		Map<String, Object> totaldata = new HashMap<String, Object>();
		int total = 0;
		Object ob = null;
		if (type == ReportType.REP_TIME.getValue()) {
			Map<String, Object> map = null;
			for (int i = 0; i < data.size(); i++) {
				map = dataList.get(i);// 拿到一行数据
				List<PoiCell> pcdata = new ArrayList<PoiCell>();
				pcdata.add(new PoiCell(map.get("car_id").toString()));
				for (int j = 0; j < len; j++) {
					pcdata.add(new PoiCell(map.get(fields[j]).toString()));
					// 以下是统计
					ob = map.get(fields[j]);
					total = (Integer) (totaldata.containsKey(fields[j]) ? ((Integer)totaldata.get(fields[j]) + Integer.parseInt(ob.toString())) : Integer.parseInt(ob.toString()));
					totaldata.put(fields[j], total); //
				}
				datas.add(pcdata);
			}
		} else {
			Map<String, Object> map = null;
			for (int i = 0; i < dataList.size(); i++) {
				map = dataList.get(i);// 拿到一行数据
				List<PoiCell> pcdata = new ArrayList<PoiCell>();
				pcdata.add(new PoiCell(map.get("car_id").toString()));
				for (int j = 0; j < nums; j++) {
					String valuename = String.format("%2d", j + 1).replace(" ", "0");
					for (int m = 0; m < len; m++) {
						pcdata.add(new PoiCell(map.get(fields[m] + valuename).toString()));
						// 以下是统计
						ob = map.get(fields[m] + valuename);
						total = (Integer) (totaldata.containsKey(fields[m] + valuename) ? ((Integer)totaldata.get(fields[m] + valuename) + Integer.parseInt(ob.toString())) : Integer.parseInt(ob.toString()));
						totaldata.put(fields[m] + valuename, total); // 
					}
				}
				datas.add(pcdata);
			}
		}
		poiData.setDatas(datas);
		totaldata.put("car_id", "合计");
		//
		List<List<PoiCell>> footers = new ArrayList<List<PoiCell>>();
		List<PoiCell> footerdata = new ArrayList<PoiCell>();
		footerdata.add(new PoiCell("合计"));
		if(type == ReportType.REP_TIME.getValue()) {
			for (int m = 0; m < len; m++) {
				footerdata.add(new PoiCell(totaldata.containsKey(fields[m]) ? totaldata.get(fields[m]).toString():"0"));
			}
		}
		for (int j = 1; j <= nums; j++) {
			String valuename = String.format("%2d", j).replace(" ", "0");
			for (int m = 0; m < len; m++) {
				footerdata.add(new PoiCell(totaldata.containsKey(fields[m]+valuename)?totaldata.get(fields[m]+valuename).toString():"0"));
			}
		}
		footers.add(footerdata);
		poiData.setFooters(footers);
		//
		PoiExcel pe = new PoiExcel();
		pe.setSplitPos(5, 2); // 冻结行列
		pe.setDataAndExcute(poiData);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			pe.getWorkbook().write(out);
			return out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			ReportUtils.CloseOpStream(out);
		}
	}

}
