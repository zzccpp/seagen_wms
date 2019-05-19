package cn.seagen.base.constant;

public class StatisticsConstant {

	/** 每次统计查询的数量 */
	public final static int STATISTICS_MAX_COUNT = 10000;
	/** 时间格式：yyyy-MM-dd HH:mm*/
	public final static String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
	/** 时间格式：yyyy-MM-dd HH:mm:ss*/
	public final static String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	/** 时间格式：yyyy-MM-dd HH:mm:ss.SSS*/
	public final static String YYYYMMDDHHMMSSM = "yyyy-MM-dd HH:mm:ss.SSS";
	/** 时间格式：yyyy-MM-dd HH*/
	public final static String YYYYMMDDHH = "yyyy-MM-dd HH";
	/** 时间格式：yyyy-MM-dd*/
	public final static String YYYYMMDD = "yyyy-MM-dd";
	
	/** 统计输出关键字*/
	public final static String[] logKey = {"小车量统计完成","格口量统计完成","分钟量统计完成","扫描量统计完成","站点量统计完成"
		,"汇总量统计完成","导入台量统计完成","格口封包量统计完成","汇总封包量统计完成","批次量统计完成","批次封包量统计完成"};
}
