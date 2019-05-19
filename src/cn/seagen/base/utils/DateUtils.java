package cn.seagen.base.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 *日期类方法
 */
public class DateUtils {
	/** 格式化时间：yyyy-MM-dd HH:mm:ss */
	public static SimpleDateFormat YYYYMMDDHHMMSS= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 获取年月：本月，下个月，上个月
	 * @param mon 1本月，2下个月，0上个月，-1上上个月
	 * @return 返回：201801
	 */
	public static String findYearMonth(int mon) {
        int year;
        int month;
        String date;
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + mon;
        date = year + "" + (month<10 ? "0" + month : month);
        return date;
    }
	
	/**
	 * 获取当前天数
	 * @return 返回：201801
	 */
	public static int findDay() {
        int day;
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        return day;
    }
	
	/** 获取时间字符:2017-03-08 10:26:58 */
	public static String getNow() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/** 获取时间字符[3位毫秒]:2017-03-08 10:26:58.235 */
	public static String getNow_W() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
	}
	
	// 日期格式化
	public static String formatDateTime(String format, Date date) {
		date = (date == null) ? (new Date()) : date;
		format = StringUtils.isEmpty(format) ? "yyyyMMdd" : format;
		return (new SimpleDateFormat(format)).format(date);
	}
	
	/**
	 * 查询开始时间转化long型
	 * @param endDate
	 * @param type
	 * @return
	 * @throws ParseException
	 */
	public static long formatStartDateToLong(String strDate,int type) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = null;
		long time = 0l;
		if(type==0){//时间段
			dt = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(strDate);
			//继续转换得到秒数的long型
			time = dt.getTime()/1000;
		}
		
		if (type == 1) {//天
			String str = strDate.substring(0,10)+" 00:00:00";
			dt = sdf.parse(str);
			//继续转换得到秒数的long型
			time = dt.getTime()/1000;
		}

		if (type == 2) {//月
			String str = strDate.substring(0,7)+"-01 00:00:00";
			dt = sdf.parse(str);
			//继续转换得到秒数的long型
			time = dt.getTime()/1000;
		}

		if (type == 3) {//年
			String str = strDate.substring(0,4)+"-01-01 00:00:00";
			dt = sdf.parse(str);
			//继续转换得到秒数的long型
			time = dt.getTime()/1000;
		}	
		
		return time;
	}
	
	/**
	 * 查询结束时间转化long型
	 * @param endDate
	 * @param type
	 * @return
	 * @throws ParseException
	 */
	public static long formatEndDateToLong(String endDate, int type) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = null;
		long time = 0l;
		if (type == 0) {// 时间段
			dt = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endDate);
			time = dt.getTime() / 1000;
		}

		if (type == 1) {// 天
			String str = endDate.substring(0, 10) + " 23:59:59";
			dt = sdf.parse(str);
			time = dt.getTime() / 1000;
		}

		if (type == 2) {// 月
			int year = Integer.parseInt(endDate.substring(0, 4));
			int month = Integer.parseInt(endDate.substring(5, 7));
			int day = leapYear(year, month);
			String str = endDate.substring(0, 8) + day + " 23:59:59";
			dt = sdf.parse(str);
			time = dt.getTime() / 1000;
		}

		if (type == 3) {// 年
			String str = endDate.substring(0, 4) + "-12-01 23:59:59";
			dt = sdf.parse(str);
			time = dt.getTime() / 1000;
		}

		return time;
	}

	/**
	 * 月份最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static int leapYear(int year, int month) {
		int day = 0;
		switch (month) {
		case 1:
			day = 31;
			break;
		case 3:
			day = 31;
			break;
		case 5:
			day = 31;
			break;
		case 7:
			day = 31;
			break;
		case 8:
			day = 31;
			break;
		case 10:
			day = 31;
			break;
		case 12:
			day = 31;
			break;
		case 2:
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
				day = 29;
			} else {
				day = 28;
			}
			break;
		case 4:
			day = 30;
			break;
		case 6:
			day = 30;
			break;
		case 9:
			day = 30;
			break;
		case 11:
			day = 30;
			break;
		default:
			break;
		}
		return day;
	}
	
	/**
	 * 格式化时间
	 * @param dateStr
	 * @return
	 */
	public static Date formatStrToDate(String dateStr,String format) {
		try {
			if (StringUtils.isEmpty(dateStr))
				return new Date();
			DateFormat fmt = null;
			if (StringUtils.isEmpty(format)) {
				fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			} else {
				fmt = new SimpleDateFormat(format);
			}
			return fmt.parse(dateStr);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	/**
	 * 格式化统计类时间格式：根据统计的不同类型，获取相关时间的初始值，例如：按天查询，则返回该天的：****-**-** 00:00:00
	 * @param time 
	 * @param type 0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)
	 * @return
	 */
	public static String formatReportStartTime(String time,int type){
		Calendar begin = Calendar.getInstance();
		String time_type = "";
		if(type == 0){
			time_type = "yyyy-MM-dd HH";
		}
		if(type == 1){
			time_type = "yyyy-MM-dd";
		}
		if(type == 2){
			time_type = "yyyy-MM";
		}
		if(type == 3){
			time_type = "yyyy";
		}
		begin.setTime(formatStrToDate(time, time_type));
		String tt = DateUtils.formatDateTime(time_type, begin.getTime());
		return tt;
	}
	
	/**
	 * 格式化统计类时间格式：根据统计的不同类型，获取相关时间的结束值，例如：按天查询，则返回该天的：****-**-(**+1) 00:00:00
	 * @param time 
	 * @param type 0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)
	 * @return
	 */
	public static String formatReportEndTime(String time,int type){
		Calendar begin = Calendar.getInstance();
		String time_type = "";
		if(type == 0){
			time_type = "yyyy-MM-dd HH";
			begin.setTime(formatStrToDate(time, time_type));
			begin.add(Calendar.HOUR_OF_DAY, 1);
		}
		if(type == 1){
			time_type = "yyyy-MM-dd";
			begin.setTime(formatStrToDate(time, time_type));
			begin.add(Calendar.DAY_OF_MONTH, 1);
		}
		if(type == 2){
			time_type = "yyyy-MM";
			begin.setTime(formatStrToDate(time, time_type));
			begin.add(Calendar.MONTH, 1);
		}
		if(type == 3){
			time_type = "yyyy";
			begin.setTime(formatStrToDate(time, time_type));
			begin.add(Calendar.YEAR, 1);
		}
		String tt = DateUtils.formatDateTime(time_type, begin.getTime());
		return tt;
	}
	
	/**
	 * 获取新批次
	 * @return
	 */
	public static String getNewBatch(){
		return "B" + getNow().replace("-", "").replace(" ", "").replace(":", "");
	}
}
