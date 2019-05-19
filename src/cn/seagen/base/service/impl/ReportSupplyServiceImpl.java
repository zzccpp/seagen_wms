package cn.seagen.base.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.seagen.base.constant.EquipmentConstant;
import cn.seagen.base.dao.ReportSupplyDao;
import cn.seagen.base.enums.ReportType;
import cn.seagen.base.excel.PoiCell;
import cn.seagen.base.excel.PoiData;
import cn.seagen.base.excel.PoiExcel;
import cn.seagen.base.service.ReportSupplyService;
import cn.seagen.base.utils.JUtils;
import cn.seagen.base.utils.ReportUtils;
import cn.seagen.base.vo.EasyuiGridSumData;
import cn.seagen.base.vo.Reportfun;
import cn.seagen.base.vo.RequestBase;
import cn.seagen.base.vo.ScanSupply;
import cn.seagen.base.vo.SixSpike;
@Service
public class ReportSupplyServiceImpl implements ReportSupplyService,Serializable{
	private static final long serialVersionUID = 8373862207189858329L;
	private static Logger logger = LogManager.getLogger(ReportSupplyServiceImpl.class.getName());
	@Resource
	private ReportSupplyDao reportSupplyDaoImpl;
	private static String[] titles = new String[] { "分拣总量", "正常量", "异常量","无读量","无方案","多条码", //
		"无数据",  "拦截", "错分", "最大循环","迷失" };
	private static String[] fields = new String[] { "all_sum", "success_sum", "err_sum","no_reade", //
		"no_chute","more_data", "no_data", "cancel_sum", "err_chute", "max_cycles","lost_data" };
	private final static  int FROZEN_FIELDS_LEN = 4;//固定列数
	@Override
	public Object querySupply(RequestBase requestBase) {
		logger.info("querysupply:startTm=" + requestBase.getDate() + ",endTm=" //
				+ requestBase.getEnd_date() + ",type=" + requestBase.getType());
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
		int len = FROZEN_FIELDS_LEN;
		if(requestBase.getMore() != 0) {
			len = fields.length;
		}
		// 不固定标题
		List<Object> columnsList = new ArrayList<Object>();
		List<Object> columns_row1 = new ArrayList<Object>();
		List<Object> columns_row2 = new ArrayList<Object>();
		// 根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)来设置不固定子标题
		if (type == ReportType.REP_TIME.getValue()) {
			//根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)来设置固定子标题
			frozencolumn = Reportfun.getColumn("导入台", "supply_id", true);
			columns_row1.add(Reportfun.getColumn(first_name, 1, len));
			for(int i = 0;i < len;i++){
				columns_row2.add(Reportfun.getColumn(titles[i], fields[i], true));
			}
		}
		if (type == ReportType.REP_DAY.getValue() || type == ReportType.REP_MONTH.getValue() //
				|| type == ReportType.REP_YEAR.getValue()) {
			//根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)来设置固定子标题
			frozencolumn = Reportfun.getColumn(first_name, "supply_id", true);
			for (int i = 1; i <= nums; i++) {
				// 一天24小时
				String titlename = ReportUtils.getReportHead(type, i-1);
				String fieldname = String.format("%2d", i).replace(" ", "0");
				columns_row1.add(Reportfun.getColumn(titlename, 1, len));
				for(int j = 0;j < len;j++){
					columns_row2.add(Reportfun.getColumn(titles[j], fields[j]+fieldname, true));
				}
			}
		}
		columnsList.add(columns_row1);
		columnsList.add(columns_row2);
		frozenColumnsList.add(frozencolumn);
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		if(requestBase.getLayer() == 0){
			requestBase.setLayer(1);
			dataList = reportSupplyDaoImpl.querySupply(requestBase);
			requestBase.setLayer(2);
			dataList.addAll(reportSupplyDaoImpl.querySupply(requestBase));
		}else{
			dataList = reportSupplyDaoImpl.querySupply(requestBase);
		}
			
		// 求和数据
		List<Map<String, Object>> footerList = new ArrayList<Map<String, Object>>();
		Map<String, Object> footerdata = new HashMap<String, Object>();
		footerdata.put("supply_id", "合计");
		for (Iterator<Map<String, Object>> iterator = dataList.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			for (String key : map.keySet()) {
				if (key.toLowerCase().equals("supply_id".toLowerCase())) {
					continue;
				}
				int value = Integer.parseInt(map.get(key).toString());
				footerdata.put(key, (footerdata.get(key) == null) ? value : ((Integer) footerdata.get(key) + value));
			}
		}
		footerList.add(footerdata);
		
		easyuiGridSumData.setData(dataList);
		easyuiGridSumData.setColumns(columnsList);
		easyuiGridSumData.setFrozenColumns(frozenColumnsList);
		easyuiGridSumData.setFooter(footerdata);
		
		return easyuiGridSumData;
	}
	@Override
	public List<ScanSupply> queryScanSupply(RequestBase requestBase) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		List<ScanSupply> list = new ArrayList<ScanSupply>();
		if(requestBase.getLayer() == 0){
			requestBase.setLayer(1);
			dataList = reportSupplyDaoImpl.queryScanSupply(requestBase);
			list = ConvertSpikeData(dataList,1);
			requestBase.setLayer(2);
			list.addAll(ConvertSpikeData(reportSupplyDaoImpl.queryScanSupply(requestBase),2));
		}else{
			dataList = reportSupplyDaoImpl.queryScanSupply(requestBase);
			list = ConvertSpikeData(dataList,requestBase.getLayer());
		}
		
		return list;
	}
	
	@Override
	public List<SixSpike> getSixSpikeData(String stime, String etime,
			int supplyNo,int sixSpikeNum,int layer) {
		
		List<Map<String, Object>> dataList = reportSupplyDaoImpl.getSixSpikeTime(stime, etime, supplyNo, sixSpikeNum,layer);
		
		List<SixSpike> spikeList = new ArrayList<SixSpike>();
		int len = dataList.size();
		String sdate= "";
		String edate= "";
		if(len < 1)
			return spikeList;
		for (int i = 0; i < len;) {
			sdate = dataList.get(i).get("stime").toString();
			edate = dataList.get(i).get("etime").toString();
			break;
		}
		spikeList = initSpikeList(sdate,edate);
		List<Map<String, Object>>  listMap = reportSupplyDaoImpl.getSixSpikeData(sdate, edate, supplyNo,layer);
		int size = listMap.size();
		len = spikeList.size();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < len; j++) {
				if(listMap.get(i).get("report_date").equals(spikeList.get(j).getReportDate())){
					spikeList.get(j).setScanNum(JUtils.strToInt(listMap.get(i).get("all_sum").toString()));
					break;
				}
			}
		}
		
		return spikeList;
	}
	
	/**
	 * 初始化list，不足的补0
	 * @param stime
	 * @param etime
	 * @return
	 */
	private List<SixSpike> initSpikeList(String stime,String etime){
		SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm");
		List<SixSpike> list = new ArrayList<SixSpike>();
		try {
			//设置开始时间
			Date sdate = sdf_date.parse(stime);
			Calendar ca = Calendar.getInstance();
			ca.setTime(sdate);
			for (int i = 0; i < 60; i++) {
				list.add(i,new SixSpike(sdf_time.format(ca.getTime()),0));
				ca.add( Calendar.MINUTE,1);
			}
			list.add(new SixSpike(sdf_time.format(ca.getTime()),0));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 按导入台初始化List集合容量，并排好序
	 * list index=supplyNum-1
	 * @return
	 */
	private List<ScanSupply> initList(int layerId){
		int num = 0;
		if(layerId == 1)
			num = EquipmentConstant.firsrLayerSupplyNum;
		else 
			num = EquipmentConstant.secondLayerSupplyNum;
		List<ScanSupply> list= new ArrayList<ScanSupply>(num);//初始化容量
		for (int i = 1; i <= num; i++) {
			list.add(i-1, new ScanSupply(layerId+"-"+i, 0, 0, 0, "0.00", "0"));
		}
		return list;
	}
	
	@Override
	public byte[] exportSupply(RequestBase requestBase) {
	
		logger.info("exportSupply:startTm=" + requestBase.getDate() +",endTm=" + //
				requestBase.getEnd_date() + ",type=" + requestBase.getType());
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
		PoiCell pcTitle = new PoiCell("导入台统计查询");
		poiData.setTitle(pcTitle);
		// Query
		PoiCell pcQuery = new PoiCell(first_name);
		poiData.setQuery(pcQuery);
		// Head
		List<List<PoiCell>> heads = new ArrayList<List<PoiCell>>();
		if (type == ReportType.REP_TIME.getValue()) {
			List<PoiCell> pcHead1 = new ArrayList<PoiCell>();
			pcHead1.add(new PoiCell("导入台"));
			for (int i = 0; i < len; i++)
				pcHead1.add(new PoiCell(titles[i]));

			heads.add(pcHead1);
		} else {// (此处是提供相对于以左上1,1格子为参照的精准的CELL位置)
			List<PoiCell> pcHead1 = new ArrayList<PoiCell>();
			List<PoiCell> pcHead2 = new ArrayList<PoiCell>();

			pcHead1.add(new PoiCell(1, 2, 1, 1, "导入台"));
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
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		if(requestBase.getLayer() == 0){
			requestBase.setLayer(1);
			dataList = reportSupplyDaoImpl.querySupply(requestBase);
			requestBase.setLayer(2);
			dataList.addAll(reportSupplyDaoImpl.querySupply(requestBase));
		}else{
			dataList = reportSupplyDaoImpl.querySupply(requestBase);
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
				pcdata.add(new PoiCell(map.get("SUPPLY_ID").toString()));
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
				pcdata.add(new PoiCell(map.get("SUPPLY_ID").toString()));
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
		totaldata.put("SUPPLY_ID", "合计");
		//
		List<List<PoiCell>> footers = new ArrayList<List<PoiCell>>();
		List<PoiCell> footerdata = new ArrayList<PoiCell>();
		/*for (int i = 0; i < datas.get(0).size(); i++) {
			footerdata.add(new PoiCell(""));
		}
		*/
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
	@Override
	public byte[] exportSpikeSupply(RequestBase requestBase) {
		List<ScanSupply> list = ReportUtils.getDealData(queryScanSupply(requestBase));
		PoiData poiData = new PoiData();
		// 设置标题
		PoiCell pcTitle = new PoiCell("导入台供件统计报表");
		poiData.setTitle(pcTitle);
		// Query
		PoiCell pcQuery = new PoiCell(requestBase.getDate()+"至"+requestBase.getEnd_date());
		poiData.setQuery(pcQuery);
		//设置标题
		List<List<PoiCell>> headList = new LinkedList<List<PoiCell>>();
		List<PoiCell> lineData = new LinkedList<PoiCell>();
		PoiCell cell = null;
		String [] heads = {"导入台号" ,"峰值(1分钟内)","峰值(连续60分钟)","供件总量 ","有效操作时间(小时)","供件效率(件/小时)"};
		for (int i = 0; i < heads.length; i++) {
			cell = new PoiCell(i, 1, i, 1, heads[i]);
			lineData.add(cell);
		}
		headList.add(lineData);
		poiData.setHeads(headList);
		//设置数据
		List<List<PoiCell>> allData = new LinkedList<List<PoiCell>>();
		for (int i = 0;i<list.size();i++) {
			lineData = new LinkedList<PoiCell>();
			cell = new PoiCell(i, 1, 1, 1, list.get(i).getSupplyNo());
			lineData.add(cell);
			cell = new PoiCell(i, 1, 2, 1, (list.get(i).getSpikeNum()+""));
			lineData.add(cell);
			cell = new PoiCell(i, 1, 3, 1, (list.get(i).getSixSpikeNum()+""));
			lineData.add(cell);
			cell = new PoiCell(i, 1, 4, 1, (list.get(i).getAllCount()+""));
			lineData.add(cell);
			cell = new PoiCell(i, 1, 5, 1, list.get(i).getValidTime());
			lineData.add(cell);
			cell = new PoiCell(i, 1, 6, 1, list.get(i).getEfficiency());
			lineData.add(cell);
			allData.add(lineData);
		}
		poiData.setDatas(allData);
		PoiExcel pe = new PoiExcel();
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
	
	private List<ScanSupply> ConvertSpikeData(List<Map<String, Object>> dataList,int layerId){
		List<ScanSupply> list = initList(layerId);
		int len = list.size();
		ScanSupply scanSupply;
		int size = dataList.size();
		for (int i = 0; i < size; i++) {
			scanSupply = new ScanSupply();
			scanSupply.setSupplyNo(JUtils.strToInt(dataList.get(i).get("layer_id").toString())+"-"+dataList.get(i).get("supply_id").toString());
			scanSupply.setSpikeNum(JUtils.strToInt(dataList.get(i).get("spike_num").toString()));
			scanSupply.setSixSpikeNum(JUtils.strToInt(dataList.get(i).get("six_spike").toString()));
			scanSupply.setAllCount(JUtils.strToInt(dataList.get(i).get("all_sum").toString()));
			scanSupply.setValid_time(JUtils.strToInt(dataList.get(i).get("valid_time").toString()));//此时单位m
			if(JUtils.strToInt(dataList.get(i).get("supply_id").toString())> len) break;//防止中途导入台参数变少,而实际出现大于数量的导入上件记录导致的越界
			list.set(JUtils.strToInt(dataList.get(i).get("supply_id").toString())-1,scanSupply);//按导入台替换信息                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
		}
		return list;
	}
}
