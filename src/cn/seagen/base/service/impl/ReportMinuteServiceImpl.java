package cn.seagen.base.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.seagen.base.dao.ReportMinuteDao;
import cn.seagen.base.enums.ReportType;
import cn.seagen.base.excel.PoiCell;
import cn.seagen.base.excel.PoiData;
import cn.seagen.base.excel.PoiExcel;
import cn.seagen.base.service.ReportMinuteService;
import cn.seagen.base.utils.DateUtils;
import cn.seagen.base.utils.ReportUtils;
import cn.seagen.base.utils.SqlUtils;
import cn.seagen.base.vo.EasyuiGridSumData;
import cn.seagen.base.vo.Reportfun;
import cn.seagen.base.vo.RequestBase;
@Service
public class ReportMinuteServiceImpl implements ReportMinuteService{
	private static Logger logger = LogManager.getLogger(ReportMinuteServiceImpl.class);
	
	private static String[] titles = new String[] { "分拣总量", "正常量", "异常量","无读量",  "无方案", "多条码",//
		"无数据", "拦截", "错分", "最大循环","迷失" };
	private static String[] fields = new String[] { "sorting_count", "success_sum", "err_sum", // 
		"no_reade", "no_chute", "more_data", "no_data", "cancel_sum", "err_chute", "max_cycles", "lost_data" };
	private final static  int FROZEN_FIELDS_LEN = 4;//固定列数
	@Resource
	private ReportMinuteDao reportMinuteDaoImpl;
	
	public ReportMinuteServiceImpl() {
		super();
	}

	@Override
	public Object queryMinute(RequestBase requestBase) {
		logger.info("queryMinute:day=" + requestBase.getDate());
		EasyuiGridSumData easyuiGridSumData = new EasyuiGridSumData();
		// 存在多级标题时，第一行标题个数
		int nums = 24;
		int type = requestBase.getType();
		// 固定标题第一行
		String first_name = ReportUtils.getReportFirstName(type, requestBase);
		
		// 固定标题
		List<Object> frozenColumnsList = new ArrayList<Object>();
		// 固定标题的子标题
		List<Object> frozenColumns_row1 = new ArrayList<Object>();
		
		int len = FROZEN_FIELDS_LEN;
		if(requestBase.getMore() != 0) {
			len = fields.length;
		}
		// 不固定标题
		List<Object> columnsList = new ArrayList<Object>();
		List<Object> columns_row1 = new ArrayList<Object>();
		List<Object> columns_row2 = new ArrayList<Object>();
		
		if(type == ReportType.REP_TIME.getValue()){
			//按时间段统计
			frozenColumns_row1.add(Reportfun.getColumn("分钟", "date_val",100));
			columns_row1.add(Reportfun.getColumn(first_name, 1, len));
			for(int j = 0;j < len;j++){
				columns_row2.add(Reportfun.getColumn(titles[j], fields[j], true));
			}
			frozenColumnsList.add(frozenColumns_row1);
		} else {
			List<Object> frozenColumns_row2 = new ArrayList<Object>();
			//按天统计
			frozenColumns_row1.add(Reportfun.getColumn(first_name, 1, 1));
			frozenColumns_row2.add(Reportfun.getColumn("分钟", "date_val",100));
			for (int i = 1; i <= nums; i++) {
				// 一天24小时
				String titlename = ReportUtils.getReportHead(type, i-1);;
				columns_row1.add(Reportfun.getColumn(titlename, 1, len));
				String fieldname = String.format("%2d", i).replace(" ", "0");
				for(int j = 0;j < len;j++){
					columns_row2.add(Reportfun.getColumn(titles[j], fields[j]+fieldname, true));
				}
			}
			frozenColumnsList.add(frozenColumns_row1);
			frozenColumnsList.add(frozenColumns_row2);
		}
		columnsList.add(columns_row1);
		columnsList.add(columns_row2);
		
		//组装查询分钟量统计的sql语句
		String sql = formatMinuteSql(requestBase.getDate(), requestBase.getEnd_date(), type,requestBase.getLayer(), requestBase.getMore());
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		dataList = reportMinuteDaoImpl.queryMinute(sql);
		if(type == ReportType.REP_TIME.getValue()){
			//当统计方式为时间段时，数据库查询出的数据：只有存在分拣记录，若无分拣记录则不存在记录，故需要组装时间段内的分钟
			//获取分钟量统计
			//dataListS中的分钟顺序与minuteList中的顺序一致；
			List<Map<String, Object>> dataListS = formatMinuteDataMap(requestBase.getDate(), requestBase.getEnd_date(), requestBase.getMore());
			//list中至存放分钟：0910表示9点10分；
			List<String> minuteList = ReportUtils.formatMinuteList(requestBase.getDate(), requestBase.getEnd_date());
			for (Iterator<Map<String, Object>> iterator = dataList.iterator(); iterator.hasNext();) {
				Map<String, Object> map = (Map<String, Object>) iterator.next();
				for (int i = 0; i < minuteList.size(); i++) {
					if(map.get("date_val").equals(minuteList.get(i))){
						//判断分钟一致，则直接更新构造的dataListS
						dataListS.set(i, map);
						break;
					}
				}
			}
			//将更新过的数据赋值给dataList
			dataList = new ArrayList<Map<String,Object>>(dataListS);
		}
		// 求和数据
//		List<Map<String, Object>> footerList = new ArrayList<Map<String, Object>>();
		Map<String, Object> footerdata = new HashMap<String, Object>();
		footerdata.put("date_val", "合计");
		for (Iterator<Map<String, Object>> iterator = dataList.iterator(); iterator.hasNext();) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			for (String key : map.keySet()) {
				if (key.equals("date_val")) {
					if(type == ReportType.REP_TIME.getValue()){
						String nm = ReportUtils.getReportHeadMinute(map.get(key).toString());
						map.put(key, nm);
					} else {
						String nm = ReportUtils.getReportHead(4, Integer.parseInt(map.get(key).toString())-1);
						map.put(key, nm);
					}
					continue;
				}
				int value = Integer.parseInt(map.get(key).toString());
				footerdata.put(key, (footerdata.get(key) == null) ? value : ((Integer) footerdata.get(key) + value));
			}
		}

//		footerList.add(footerdata);
		//写数据到jsp界面
		easyuiGridSumData.setData(dataList);
		easyuiGridSumData.setColumns(columnsList);
		easyuiGridSumData.setFrozenColumns(frozenColumnsList);
		easyuiGridSumData.setFooter(footerdata);

		return easyuiGridSumData;
	}

	@Override
	public byte[] exportMinute(RequestBase requestBase) {
		logger.info("exportMinute:day=" + requestBase.getDate());
		PoiData poiData = new PoiData();
		// 存在多级标题时，第一行标题个数
		int nums = 24;
		int type = requestBase.getType();
		// 固定标题第一行
		String first_name = ReportUtils.getReportFirstName(type, requestBase);

		// Title
		PoiCell pcTitle = new PoiCell("分钟统计查询");
		poiData.setTitle(pcTitle);
		// Query
		PoiCell pcQuery = new PoiCell(first_name);
		poiData.setQuery(pcQuery);
		// Head
		List<List<PoiCell>> heads = new ArrayList<List<PoiCell>>();
		List<PoiCell> pcHead1 = new ArrayList<PoiCell>();
		List<PoiCell> pcHead2 = new ArrayList<PoiCell>();
		int len = FROZEN_FIELDS_LEN;
		if(requestBase.getMore() != 0) {
			len = fields.length;
		}
		
		if(type == ReportType.REP_TIME.getValue()){
			pcHead1.add(new PoiCell(1, 2, 1 , 1, "分钟"));
			pcHead1.add(new PoiCell(1, 1, 2, len, first_name));
			for (int j = 0; j < len; j++)
				pcHead2.add(new PoiCell(2, 1, 2 + j, 1, titles[j]));
		} else {
			pcHead1.add(new PoiCell(1, 1, 1, 1, first_name));
			pcHead2.add(new PoiCell(2, 1, 1 , 1, "分钟"));
			for (int i = 0; i < nums; i++) {
				String titlename = String.format("%2d", i).replace(" ", "0") + " - " //
						+ String.format("%2d", i+1).replace(" ", "0") + "点";
				pcHead1.add(new PoiCell(1, 1, 2 + i * len, len, titlename));
				for (int j = 0; j < len; j++)
					pcHead2.add(new PoiCell(2, 1, 2 + i * len + j, 1, titles[j]));
			}
		}
		
		heads.add(pcHead1);
		heads.add(pcHead2);
		poiData.setHeads(heads);

		// Data
		List<List<PoiCell>> datas = new ArrayList<List<PoiCell>>();
		//组装查询分钟量统计的sql语句
		String sql = formatMinuteSql(requestBase.getDate(), requestBase.getEnd_date(), type,requestBase.getLayer(), requestBase.getMore());
		List<Map<String, Object>> dataList = reportMinuteDaoImpl.queryMinute(sql);
		if(type == ReportType.REP_TIME.getValue()){
			//当统计方式为时间段时，数据库查询出的数据：只有存在分拣记录，若无分拣记录则不存在记录，故需要组装时间段内的分钟
			//获取分钟量统计
			//dataListS中的分钟顺序与minuteList中的顺序一致；
			List<Map<String, Object>> dataListS = formatMinuteDataMap(requestBase.getDate(), requestBase.getEnd_date(), requestBase.getMore());
			//list中至存放分钟：0910表示9点10分；
			List<String> minuteList = ReportUtils.formatMinuteList(requestBase.getDate(), requestBase.getEnd_date());
			for (Iterator<Map<String, Object>> iterator = dataList.iterator(); iterator.hasNext();) {
				Map<String, Object> map = (Map<String, Object>) iterator.next();
				for (int i = 0; i < minuteList.size(); i++) {
					if(map.get("date_val").equals(minuteList.get(i)))
						//判断分钟一致，则直接更新构造的dataListS
						dataListS.set(i, map);
				}
			}
			//将更新过的数据赋值给dataList
			dataList = new ArrayList<Map<String,Object>>(dataListS);
		}
		Map<String, Object> map = null;
		for (int i = 0; i < dataList.size(); i++) {
			map = dataList.get(i);// 拿到一行数据
			List<PoiCell> pcdata = new ArrayList<PoiCell>();
			String nm = "";
			if(type == ReportType.REP_TIME.getValue()){
				nm = ReportUtils.getReportHeadMinute(map.get("date_val").toString());
			} else {
				nm = ReportUtils.getReportHead(4, Integer.parseInt(map.get("date_val").toString())-1);
			}
			pcdata.add(new PoiCell(nm));
			if(type == ReportType.REP_TIME.getValue()){
				for (int m = 0; m < len; m++) {
					pcdata.add(new PoiCell(map.get(fields[m]).toString()));
				}
			} else {
				for (int j = 0; j < nums; j++) {
					String valuename = String.format("%2d", j + 1).replace(" ", "0");
					for (int m = 0; m < len; m++) {
						pcdata.add(new PoiCell(map.get(fields[m] + valuename).toString()));
					}
				}
			}
			datas.add(pcdata);
		}
		poiData.setDatas(datas);
		
		Map<String, Object> totaldata = new HashMap<String, Object>();
		totaldata.put("date_val", "合计");
		for (Iterator<Map<String, Object>> iterator = dataList.iterator(); iterator.hasNext();) {
			Map<String, Object> map1 = (Map<String, Object>) iterator.next();
			for (String key : map1.keySet()) {
				if (key.equals("date_val"))
					continue;
				int value = Integer.parseInt(map1.get(key).toString());
				totaldata.put(key, (totaldata.get(key) == null) //
						? value : ((Integer) totaldata.get(key) + value));
			}
		}

		//
		List<List<PoiCell>> footers = new ArrayList<List<PoiCell>>();
		List<PoiCell> footerdata = new ArrayList<PoiCell>();
		footerdata.add(new PoiCell(totaldata.get("date_val").toString()));
		if(type == ReportType.REP_TIME.getValue()){
			for (int m = 0; m < len; m++) {
				footerdata.add(new PoiCell(totaldata.containsKey(fields[m])//
						?totaldata.get(fields[m]).toString():"0"));
			}
		} else {
			for (int j = 1; j <= nums; j++) {
				String valuename = String.format("%2d", j).replace(" ", "0");
				for (int m = 0; m < len; m++) {
					footerdata.add(new PoiCell(totaldata.containsKey(fields[m]+valuename)//
							?totaldata.get(fields[m]+valuename).toString():"0"));
				}
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

	
	/**
	 * 分钟量时间段统计的sql语句
	 * @param startTime 开始时间
	 * @param endTime 结束时间（时间段统计时）
	 * @param type 0 时间段;1 天;2 月（暂不开放）;
	 * @param layer_id 层级：0，全部；1，层级1，2层级2，
	 * @param i_more 是否显示更多信息0不显示，非0显示
	 * @return
	 */
	public static String formatMinuteSql(String startTime,String endTime,int type,int layer_id,int i_more){
		//时间段
		if(type == 0){
			StringBuilder builder = new StringBuilder();
			//是否显示更多
			if(i_more == 0){
				builder.append("select date_format(report_date,'%H%i') as date_val, sum(sorting_count) as sorting_count, sum(success_sum) as success_sum,\n");
				builder.append(" sum(no_reade) as no_reade, sum(err_sum) as err_sum \n");
				builder.append(" from report_minute \n");
				builder.append(" where report_date between '"+startTime+"' and '"+endTime+"' \n");
				if(layer_id != 0)
					builder.append(" and layer_id="+layer_id+" \n");
				builder.append(" group by report_date; \n");
			} else {
				builder.append(" select date_format(report_date,'%H%i') as date_val, sum(sorting_count) as sorting_count, sum(success_sum) as success_sum,\n");
				builder.append(" sum(no_reade) as no_reade, sum(err_sum) as err_sum, sum(no_chute) as no_chute,\n");
				builder.append(" sum(more_data) as more_data, sum(no_data) as no_data, sum(cancel_sum) as cancel_sum,\n");
				builder.append(" sum(err_chute) as err_chute, sum(max_cycles) as max_cycles,sum(lost_data) as lost_data \n");
				builder.append(" from report_minute \n");
				builder.append(" where report_date between '"+startTime+"' and '"+endTime+"' \n");
				if(layer_id != 0)
					builder.append(" and layer_id="+layer_id+" \n");
				builder.append(" group by report_date; \n");
			}
			return builder.toString();
		} 
		//按天
		if(type == 1){
			//REPORT表中明细段----------------------------
			String r_sql = " (select sum(sorting_count) as sorting_count,date_format(report_date, '%H') as report_houre, date_format(report_date, '%i') as report_minute,\n"
					+ "sum(success_sum) as success_sum, sum(no_reade) as no_reade,\n"; 
			if(i_more !=0 ) {
				r_sql = r_sql + " sum(no_chute) as no_chute, sum(more_data) as more_data, sum(no_data) as no_data,\n"
						+"sum(cancel_sum) as cancel_sum, sum(err_chute) as err_chute, sum(max_cycles) as max_cycles, sum(lost_data) as lost_data,\n";
			}

			r_sql = r_sql + "	sum(err_sum) as err_sum from report_minute where date_format(report_date, '%Y-%m-%d') = date_format('"+startTime
					+"', '%Y-%m-%d') ";
			if(layer_id != 0)
				r_sql = r_sql +" and layer_id="+layer_id+" \n";
			r_sql = r_sql + " group by report_houre, report_minute) A \n";

			//转换SQL-------------------------------------
			String t_sql = " (	select report_minute ";

			int d_sql = 1;
			StringBuilder builder = new StringBuilder();
			while(d_sql <= 24){
				//24个小时
				String d_sql2s = ReportUtils.formatStr(""+d_sql, 2); //前补0，补齐2位
				builder.append(", sum(case when convert(date_val, signed) = "+d_sql+" then sorting_count else 0 end) as sorting_count"+d_sql2s+" \n");
				builder.append(", sum(case when convert(date_val, signed) = "+d_sql+" then success_sum else 0 end) as success_sum"+d_sql2s+" \n");
				builder.append(", sum(case when convert(date_val, signed) = "+d_sql+" then no_reade else 0 end) as no_reade"+d_sql2s+" \n");
				builder.append(", sum(case when convert(date_val, signed) = "+d_sql+" then err_sum else 0 end) as err_sum"+d_sql2s+" \n");
				//是否显示更多
				if(i_more !=0 ) {
					builder.append(", sum(case when convert(date_val, signed) = "+d_sql+" then no_chute else 0 end) as no_chute"+d_sql2s+" \n");
					builder.append(", sum(case when convert(date_val, signed) = "+d_sql+" then more_data else 0 end) as more_data"+d_sql2s+" \n");
					builder.append(", sum(case when convert(date_val, signed) = "+d_sql+" then no_data else 0 end) as no_data"+d_sql2s+" \n");
					builder.append(", sum(case when convert(date_val, signed) = "+d_sql+" then cancel_sum else 0 end) as cancel_sum"+d_sql2s+" \n");
					builder.append(", sum(case when convert(date_val, signed) = "+d_sql+" then err_chute else 0 end) as err_chute"+d_sql2s+" \n");
					builder.append(", sum(case when convert(date_val, signed) = "+d_sql+" then max_cycles else 0 end) as max_cycles"+d_sql2s+" \n");
					builder.append(", sum(case when convert(date_val, signed) = "+d_sql+" then lost_data else 0 end) as lost_data"+d_sql2s+" \n");
				}
				d_sql = d_sql + 1;
			}

			t_sql = t_sql + builder.toString()+" from flag_date left join "+r_sql+" on convert(flag_date.date_val, signed) = report_houre + 1 "
					+ "where report_houre is not null group by report_minute) B \n";

			//输出SQL-------------------------------------
			String e_sql = " select date_val ";

			//时间条件段
			d_sql = 1;
			StringBuilder stringBuilder = new StringBuilder();
			while(d_sql <= 24){
				String d_sql2s = ReportUtils.formatStr(""+d_sql, 2); //前补0，补齐2位
				stringBuilder.append(", ifnull(sum(sorting_count"+d_sql2s+"), 0) as sorting_count"+d_sql2s+" \n");
				stringBuilder.append(", ifnull(sum(success_sum"+d_sql2s+"), 0) as success_sum"+d_sql2s+" \n");
				stringBuilder.append(", ifnull(sum(no_reade"+d_sql2s+"), 0) as no_reade"+d_sql2s+" \n");
				stringBuilder.append(", ifnull(sum(err_sum"+d_sql2s+"), 0) as err_sum"+d_sql2s+" \n");
				//是否显示更多
				if(i_more !=0 ) {
					stringBuilder.append(", ifnull(sum(no_chute"+d_sql2s+"), 0) as no_chute"+d_sql2s+" \n");
					stringBuilder.append(", ifnull(sum(more_data"+d_sql2s+"), 0) as more_data"+d_sql2s+" \n");
					stringBuilder.append(", ifnull(sum(no_data"+d_sql2s+"), 0) as no_data"+d_sql2s+" \n");
					stringBuilder.append(", ifnull(sum(cancel_sum"+d_sql2s+"), 0) as cancel_sum"+d_sql2s+" \n");
					stringBuilder.append(", ifnull(sum(err_chute"+d_sql2s+"), 0) as err_chute"+d_sql2s+" \n");
					stringBuilder.append(", ifnull(sum(max_cycles"+d_sql2s+"), 0) as max_cycles"+d_sql2s+" \n");
					stringBuilder.append(", ifnull(sum(lost_data"+d_sql2s+"), 0) as lost_data"+d_sql2s+" \n");
				}
				d_sql = d_sql + 1;
			}

			e_sql = e_sql + stringBuilder.toString() + " from flag_date left join "+t_sql+" on convert(date_val, signed) = report_minute + 1 \n "
					+ " where convert(date_val, signed) <= 60 group by date_val order by date_val; \n";
			return e_sql;
		}
		return "";
	}
	
	/**
	 * 组装分钟量统计时：时间段统计中的分钟
	 * @param beginTime
	 * @param endTime
	 * @param i_more 0不显示全部；1显示全部
	 * @return
	 */
	public static List<Map<String, Object>> formatMinuteDataMap(String beginTime,String endTime,int i_more){
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		
		Calendar begin = Calendar.getInstance();
		begin.setTime(SqlUtils.formatStrToDate(beginTime, "yyyy-MM-dd HH:mm"));
		Calendar end = Calendar.getInstance();
		end.setTime(SqlUtils.formatStrToDate(endTime, "yyyy-MM-dd HH:mm"));
		int nums = (int) ((end.getTimeInMillis()-begin.getTimeInMillis())/60000);
		String tt = "";
		Map<String, Object> map = null;
		for(int i = 0;i < nums+1;i++){
			map = new HashMap<String, Object>();
			tt = DateUtils.formatDateTime("yyyyMMdd HHmm", begin.getTime());
			begin.add(Calendar.MINUTE, 1);
			map.put("date_val", tt.substring(9, 13));
			map.put("sorting_count", 0);
			map.put("success_sum", 0);
			map.put("no_reade", 0);
			map.put("err_sum", 0);
			if(i_more != 0){
				map.put("no_chute", 0);
				map.put("more_data", 0);
				map.put("no_data", 0);
				map.put("cancel_sum", 0);
				map.put("err_chute", 0);
				map.put("max_cycles", 0);
				map.put("lost_data", 0);
			}
			dataList.add(map);
		}
		return dataList;
	}
}
