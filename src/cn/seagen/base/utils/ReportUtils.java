package cn.seagen.base.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import cn.seagen.base.constant.EquipmentConstant;
import cn.seagen.base.enums.ReportType;
import cn.seagen.base.vo.RequestBase;
import cn.seagen.base.vo.ScanSupply;

public class ReportUtils {

	/**
	 * 根据统计类型以及统计时间，获取第一列标题名称
	 * 针对批次量统计
	 * 0近一周、1近一月2近三个月3近半年(半年的考虑数据量暂不开放)
	 * @param type
	 * @param requestBase
	 * @return
	 */
	public static String getReportFirstName(int type) {
		String date = DateUtils.getNow();//当前时间
		String name = "";
		// 查询近一周:从当前时间开始，统计过去7天的数据
		if (type == ReportType.REP_TIME.getValue())
			name = "近一周";
		// 查询近一月:从当前时间开始，统计过去30天的数据
		if (type == ReportType.REP_DAY.getValue()) 
			name = "近一个月";
		// 查询近三个月:从当前时间开始，统计过去90天的数据
		if (type == ReportType.REP_MONTH.getValue())
			name = "近三个月";
		// 查询近半年:从当前时间开始，统计过去180天的数据
		if (type == ReportType.REP_YEAR.getValue())
			name = "近半年";
		name = name+"_"+date.substring(0, 10);
		return name;
	}
	
	/**
	 * 根据统计类型以及统计时间，获取第一列标题名称
	 * 
	 * @param type
	 * @param requestBase
	 * @return
	 */
	public static String getReportFirstName(int type, RequestBase requestBase) {
		String name = "";
		String date = requestBase.getDate();
		String end_date = requestBase.getEnd_date();
		// 查询时间段：第一列名称则为时间段
		if (type == ReportType.REP_TIME.getValue()) {
			name = date.substring(0, 16) + " 至 " + end_date.substring(0, 16);
		}
		// 查询天：第一列名称则为需要从该天零点开始，以结束时间未结束
		if (type == ReportType.REP_DAY.getValue()) {
			name = DateUtils.formatDateTime("yyyy年MM月dd日", JUtils.strToDate(date));
		}
		// 查询月：第一列名称则为需要从该月零点开始，以结束时间未结束
		if (type == ReportType.REP_MONTH.getValue()) {
			name = DateUtils.formatDateTime("yyyy年MM月", JUtils.strToDate(date));
		}
		// 查询年：第一列名称则为需要从该年零点开始，以结束时间未结束
		if (type == ReportType.REP_YEAR.getValue()) {
			name = DateUtils.formatDateTime("yyyy年", JUtils.strToDate(date));
		}
		int layer = requestBase.getLayer();
		//全层级
		if(layer == 0){
			
		}
		//层级1
		if(layer == 1){
			name = name + "_层级01";
		}
		//层级2
		if(layer == 2){
			name = name + "_层级02";
		}
		return name;
	}

	/**
	 * 根据统计类型以及统计时间，获取第一行标题名称 klq
	 * 
	 * @param type
	 * @param date
	 * @return
	 */
	public static String getReportNameType(int type, String date) {
		String name = "";
		if (type == ReportType.REP_TIME.getValue()) {

		}
		if (type == ReportType.REP_DAY.getValue()) {
			name = "点";
		}
		if (type == ReportType.REP_MONTH.getValue()) {
			name = "日";
		}
		if (type == ReportType.REP_YEAR.getValue()) {
			name = "月";
		}
		return name;
	}

	public static String getReportHead(int type, int index) {
		String name = "";
		if (type == ReportType.REP_DAY.getValue()) {
			name = String.format("%2d", index).replace(" ", "0") + " - " //
					+ String.format("%2d", index + 1).replace(" ", "0") + "点";
		}
		if (type == ReportType.REP_MONTH.getValue()) {
			name = String.format("%2d", index + 1).replace(" ", "0") + "日";

		}
		if (type == ReportType.REP_YEAR.getValue()) {
			name = String.format("%2d", index + 1).replace(" ", "0") + "月";
		}
		if (type == ReportType.REP_MINUTE.getValue()) {
			name = String.format("%2d", index).replace(" ", "0") + " - " //
					+ String.format("%2d", index + 1).replace(" ", "0") + "分";
		}
		return name;
	}

	/**
	 * 根据统计类型以及统计时间，获取第一行标题个数 klq
	 * 
	 * @param type
	 * @param date
	 * @return
	 */
	public static int getReportNums(int type, String date) {
		int num = 0;
		if (type == ReportType.REP_TIME.getValue()) {
			
		}
		if (type == ReportType.REP_DAY.getValue()) {
			num = 24;// 一天24小时
		}
		if (type == ReportType.REP_MONTH.getValue()) {
			num = JUtils.getDaysOfMonth(date); // 取当前的天数
		}
		if (type == ReportType.REP_YEAR.getValue()) {
			num = 12;// 一年12个月
		}
		return num;
	}

	public static void CloseOpStream(ByteArrayOutputStream out) {
		if (out != null) {
			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 导出Excel表格
	 * @param title
	 * @param response
	 * @param bytes
	 */
	public static void doResponse(String title, HttpServletResponse response, byte[] bytes) {
		try {
			// 设置响应头,设置response参数，可以打开下载页面
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(title.getBytes(), "iso-8859-1") + ".xls");
			response.setContentLength(bytes.length);
			response.getOutputStream().write(bytes);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getReportHeadMinute(String index) {
		String head1 = index.substring(0, 2);
		String head2 = head1;
		int minute = Integer.parseInt(index.substring(2, 4));
		if(minute == 59)
			head2 = ""+(Integer.parseInt(head1)+1);
		String name = head1+":"+String.format("%2d", minute).replace(" ", "0") + " - " //
					+ head2+":"+String.format("%2d", minute + 1).replace(" ", "0");
		return name;
	}
	
	/**
	 * 
	 * @param type 1 数据库中where条件下比较时间的格式;2 数据库中select条件下时间格式;3数据库中group by条件下的时间格式;
	 * @param timeType 
	 * @return
	 */
	public static String getTimeFormat(int type,int timeType){
		String str = "";
		if(type == 1){
			switch (timeType) {
			case 1:
				return "%Y%m%d";
			case 2:
				return "%Y%m";
			case 3:
				return "%Y";
			default:
				return "%Y%m%d";
			}
		} 
		
		if(type == 2){
			switch (timeType) {
			case 1:
				return "%H";
			case 2:
				return "%d";
			case 3:
				return "%m";
			default:
				return "%H";
			}
		}
		
		if(type == 3){
			switch (timeType) {
			case 1:
				return "%Y%m%d %H";
			case 2:
				return "%Y%m%d";
			case 3:
				return "%Y%m";
			default:
				return "%Y%m%d";
			}
		}
		return str;
	}
	
	/**
	 * 获取查询列数
	 * @param type 0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)
	 * @param report_date_v 查询的开始时间
	 * @return
	 */
	public static int getColNum(int type,String report_date_v){
		switch (type) {
		case 1:
			return 24;
		case 2:
			return JUtils.getDaysOfMonth(report_date_v);
		case 3:
			return 12;
		default:
			return 24;
		}
	}
	
	/**
	 * 对 字符串进行补0操作
	 * @param str
	 * @param n
	 * @return
	 */
	public static String formatStr(String str, int n){
		String temp = (str == null) ? "" : str;
		int len = temp.length();
		while (len < n) {
			temp = "0" + temp;
			len++;
		}
		return temp;
	}
	
	/**
	 * 组装分钟量统计时：时间段统计中的分钟
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static List<String> formatMinuteList(String beginTime,String endTime){
		List<String> dataList = new ArrayList<String>();
		Calendar begin = Calendar.getInstance();
		begin.setTime(SqlUtils.formatStrToDate(beginTime, "yyyy-MM-dd HH:mm"));
		Calendar end = Calendar.getInstance();
		end.setTime(SqlUtils.formatStrToDate(endTime, "yyyy-MM-dd HH:mm"));
		int nums = (int) ((end.getTimeInMillis()-begin.getTimeInMillis())/60000);
		String tt = "";
		for(int i = 0;i < nums+1;i++){
			tt = DateUtils.formatDateTime("yyyyMMdd HHmm", begin.getTime());
			begin.add(Calendar.MINUTE, 1);
			dataList.add(tt.substring(9, 13));
		}
		return dataList;
	}
	
	/**
	 * 
	 * @param i_more
	 * @return
	 */
	public static List<Map<String, Object>> formatCarDataMap(int i_more){
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = null;
		for(int i = 1;i <= EquipmentConstant.carNum;i++){
			map = new HashMap<String, Object>();
			map.put("car_id", i);
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
	
	/**
	 * 获取数据，并处理封装好.
	 * @param stime
	 * @param etime
	 * @return 
	 */
	public static List<ScanSupply> getDealData(List<ScanSupply> list){
		DecimalFormat df_time = new DecimalFormat("0.00");
		ScanSupply scanSupply = new ScanSupply();
		scanSupply.setSupplyNo("合计");
		for (ScanSupply info : list) {
			info.setSupplyNo(info.getSupplyNo()+"导入台");
			info.setValidTime(df_time.format(info.getValid_time()/60f));//供件有效时间单位 h
			if(info.getValid_time()>0){
				info.setEfficiency(""+info.getAllCount()/info.getValid_time()*60);//供件效率
			}else{
				info.setEfficiency("0");
			}
			//合计累加
			scanSupply.setAllCount(scanSupply.getAllCount()+info.getAllCount());
			scanSupply.setSpikeNum(scanSupply.getSpikeNum()+info.getSpikeNum());
			scanSupply.setSixSpikeNum(scanSupply.getSixSpikeNum()+info.getSixSpikeNum());
			scanSupply.setValid_time(scanSupply.getValid_time()+info.getValid_time());
		}
		scanSupply.setValidTime(df_time.format(scanSupply.getValid_time()/60f));//供件有效时间单位 h
		if(scanSupply.getValid_time()>0){
			scanSupply.setEfficiency(""+scanSupply.getAllCount()/scanSupply.getValid_time()*60);//供件效率
		}else{
			scanSupply.setEfficiency("0");
		}
		list.add(scanSupply);
		return list;
	}
	
	public static void main(String[] args){
//		String sql = formatSumSql("2018-01-22", "2018-01-22 12:00:00", 1, 1);
//		System.err.println(sql);
//		String batchsql = formatBatchSql(0,1,1,10);
//		System.err.println(batchsql);
//		System.out.println(formatMinuteDropTempSql());
//		System.out.println(formatMinuteCreateTempSql());
//		String[] sqls = formatMinuteInsertTempSql("2018-01-23 09:00:01", "2018-01-23 09:10:59");
//		for(int i = 0;i < sqls.length;i++){
//			System.out.println(sqls[i]);
//		}
//		String mSql = formatMinuteSql("2018-01-23 09:00:01", "2018-01-23 09:10:59", 0, 1);
//		System.out.println(mSql);
		
//		List<Map<String, Object>> dataList = formatMinuteDataMap("2018-01-23 09:00:01", "2018-01-23 09:10:59",0);
//		for (Iterator<Map<String, Object>> iterator = dataList.iterator(); iterator.hasNext();) {
//			Map<String, Object> map = (Map<String, Object>) iterator.next();
//			if(map.containsKey("date_val"))
//				System.out.println(map.get("date_val"));
//		}
		
//		int type = 0;
//		String scanSql = formatScanSqlList("2018-01-26 11:11:00", "2018-01-26 11:12:00", type);
//		System.out.println(scanSql);
//		type = 1;
//		scanSql = formatScanSqlList("2018-01-26 11:11:00", "2018-01-26 11:12:00", type);
//		System.out.println(scanSql);
//		type = 2;
//		scanSql = formatScanSqlList("2018-01-26 11:11:00", "2018-01-26 11:12:00", type);
//		System.out.println(scanSql);
//		type = 3;
//		scanSql = formatScanSqlList("2018-01-26 11:11:00", "2018-01-26 11:12:00", type);
//		System.out.println(scanSql);
	}
}
