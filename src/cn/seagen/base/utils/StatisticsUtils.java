package cn.seagen.base.utils;

import cn.seagen.base.bean.StatisticsProgressBean;

/**
 *统计类工具类
 */
public class StatisticsUtils {

	/** 查询统计进度需要的字段*/
	private static String statistics_progress_keys = "f_recno,st_type,statistics_name,statistics_table,current_progress";
	
	/** 插入统计进度需要的字段*/
	private static String statistics_insert_progress_keys = "st_type,statistics_name,statistics_table,current_progress";
	
	/** 分钟量统计表中的字段*/
	private static String report_minute_keys = "f_recno, report_date, layer_id, sorting_count, success_sum,"
			+ " no_reade, err_sum, no_chute, more_data, no_data, cancel_sum, err_chute, max_cycles, lost_data";
	
	/** 扫描量统计表中的字段*/
	private static String report_scan_keys = "f_recno,report_date,scan_id,all_sum,success_sum,more_data,no_reade";
	
	/** 汇总、批次量统计表中的字段*/
	private static String report_sorting_keys = "f_recno,report_date,sum_name,sum_type,begin_time,end_time,supply_sum,scan_sum,"
			+ "layer_sum,all_sum,success_sum,err_sum,no_chute,more_data,no_reade,no_data,cancel_sum,err_chute,"
			+ "max_cycles,lost_data,box_sum,layer0,layer1,layer2,scan0,scan1,scan2,scan3,scan4,scan5,scan6,scan7,"
			+ "scan8,scan9,scan10,scan11,scan12,scan13,scan14,scan15,scan16,supply0,supply1,supply2,supply3,"
			+ "supply4,supply5,supply6,supply7,supply8,supply9,supply10,supply11,supply12,supply13,supply14,"
			+ "supply15,supply16,supply17,supply18,supply19,supply20,supply21,supply22,supply23,supply24,noread0,"
			+ "noread1,noread2,noread3,noread4,noread5,noread6,noread7,noread8,noread9,noread10,noread11,noread12,"
			+ "noread13,noread14,noread15,noread16,noread17,noread18,noread19,noread20,noread21,noread22,noread23,"
			+ "noread24";
	
	/** 导入台量统计表中的字段*/
	private static String report_supply_keys = "f_recno,report_date,supply_id,layer_id,all_sum,success_sum,err_sum,"
			+ "no_chute,more_data,no_reade,no_data,cancel_sum,err_chute,max_cycles,lost_data,six_spike";
	
	/** 封包量统计表中的字段*/
	private static String report_box_keys = "f_recno,report_date,sum_name,sum_type,box_sum";
	
	/**
	 * 根据类型返回获取各表中可以统计的数据数量
	 * @param f_recno 
	 * @param type 0：小车量；1，格口；2，分钟；3，扫描；4，站点；5，汇总；
	 * 6，导入台；7，格口封包；8，汇总封包；9，批次统计；10，批次封包；
	 * @return
	 */
	public static String findStatisticsCount(long f_recno,int type){
		switch (type) {
		case 0:
		case 1:
		case 2:
		case 4:
		case 5:
		case 6:
		case 9:
			return "select count(f_recno) as statistics_count from sorting_temp where f_recno > " + f_recno 
					+ " and create_time < date_add(now(),interval -60 second)";
		case 3:
			return "select count(f_recno) as statistics_count from scan_temp where f_recno > " + f_recno 
					+ " and create_time < date_add(now(),interval -60 second)";
		case 7:
		case 8:
		case 10:
			return "select count(f_recno) as statistics_count from box_temp where f_recno > " + f_recno 
					+ " and create_time < date_add(now(),interval -60 second)";
		default:
			return "";
		}
	}
	

	
	/**
	 * 组装判断统计记录是否存在的sql语句
	 * @param tableName
	 * @param report_date 记录时间
	 * @param layer_id 层级id
	 * @param supply_id 导入台id
	 * @param scan_id 龙门架id
	 * @param sum_type 统计方式0为批次1为小时
	 * @param type 0：小车量；1，格口；2，分钟；3，扫描；4，站点；5，汇总；
	 * 6，导入台；7，格口封包；8，汇总封包；9，批次统计；10，批次封包；
	 * @return
	 */
	public static String formatIfExitsReportTable(String tableName,String report_date,int layer_id,int supply_id,int scan_id,int sum_type,int type) {
		switch (type) {
		case 2:
			return "select count(*) as num from "+tableName+" where report_date='"+report_date+"' and layer_id="+layer_id+";";
		case 3:
			return "select count(*) as num from "+tableName+" where report_date='"+report_date+"' and scan_id="+scan_id+" and layer_id = "+layer_id+";";
		case 5:
		case 8:
		case 9:
		case 10:
			if(sum_type == 0){
				return "select count(*) as num from "+tableName+" where sum_type = 0 ;";
			} else {
				return "select count(*) as num from "+tableName+" where report_date='"+report_date+"' and sum_type="
						+sum_type+" ;";
			}
		case 6:
			return "select count(*) as num from "+tableName+" where report_date='"+report_date+"' and layer_id = "+layer_id+" and supply_id="+supply_id+";";
		default:
			return "";
		}
	}
	
	/**
	 * 组装查询统计记录的sql语句
	 * @param tableName
	 * @param report_date  统计为：批次、汇总时，表示统计名称；
	 * @param batch_id 批次id
	 * @param layer_id 层级id
	 * @param supply_id 导入台id
	 * @param scan_id 龙门架id
	 * @param sum_type 统计方式0为批次1为小时
	 * @param type 2,分钟；3，扫描；5，汇总；6，导入台；9，批次；8，汇总封包；10，批次封包
	 * @return
	 */
	public static String formatFindReportSql(String tableName,String report_date,int layer_id,int supply_id,int scan_id,int sum_type,int type){
		switch (type) {
		case 2:
			return "select "+report_minute_keys+" from report_minute where report_date='"+report_date+"' and layer_id="+layer_id+";";
		case 3:
			return "select "+report_scan_keys+" from report_scan where report_date='"+report_date+"' and scan_id = " +scan_id+" and layer_id="+layer_id+";";
		case 5:
		case 9:
			if(sum_type == 0){
				return "select "+report_sorting_keys+" from report_sorting where sum_type = 0 order by f_recno desc limit 1;";
			} else {
				return "select "+report_sorting_keys+" from report_sorting where report_date='"+report_date
						+"' and sum_type = 1 order by f_recno desc limit 1;";
			}
		case 6:
			return "select "+report_supply_keys+" from report_supply where report_date='"+report_date+"' and layer_id= "+layer_id+" and supply_id = "
				+supply_id+" order by f_recno desc limit 1;";
		case 8:
		case 10:
			if(sum_type == 0){
				return "select "+report_box_keys+" from report_sorting where sum_type = 0 order by f_recno desc limit 1;";
			} else {
				return "select "+report_box_keys+" from report_sorting where report_date='"+report_date
						+"' and sum_type = 1 order by f_recno desc limit 1;";
			}
		default:
			return "";
		}
	}
	
	/**
	 * 根据实体类组装sql语句
	 * @param bean
	 * @return
	 */
	public static String formatInsertStatisticsProSql(StatisticsProgressBean bean){
		StringBuilder builder = new StringBuilder();
		builder.append("insert into report_statistics_pro ("+statistics_insert_progress_keys+") values (");
		builder.append(bean.getSt_type()).append(",'");
		builder.append(bean.getStatistics_name()).append("','");
		builder.append(bean.getStatistics_table()).append("',");
		builder.append(bean.getCurrent_progress()).append(");");
		return builder.toString();
	}
	
	/**
	 * 根据类型获取统计进度表中的单条记录
	 * @param type 0：小车量；1，格口；2，分钟；3，扫描；4，站点；5，汇总；
	 * 6，导入台；7，格口封包；8，汇总封包；9，批次统计；10，批次封包；
	 * @return
	 */
	public static String formatFindStatisticsProgressBeanSql(int type){
		String sql = "select " + statistics_progress_keys + " from report_statistics_pro where st_type="+type+";";
		return sql;
	}
	
	/**
	 * 根据类型获取统计进度表中的单条记录
	 * @param type 0：小车量；1，格口；2，分钟；3，扫描；4，站点；5，汇总；
	 * 6，导入台；7，格口封包；8，汇总封包；9，批次统计；10，批次封包；
	 * @param statistics_table 统计表名
	 * @return
	 */
	public static String formatFindStatisticsProgressBeanSql(int type,String statistics_table){
		String sql = "select " + statistics_progress_keys + " from report_statistics_pro where st_type="+type+" and statistics_table ='"+statistics_table+"';";
		return sql;
	}
	
	/**
	 * 根据类型更新统计记录表中旧的数据
	 * @param type 0：小车量；1，格口；2，分钟；3，扫描；4，站点；5，汇总；
	 * 6，导入台；7，格口封包；8，汇总封包；9，批次统计；10，批次封包；
	 * @return
	 */
	public static String formatUpdateStatisticsProSql(StatisticsProgressBean bean){
		String sql = "update report_statistics_pro set current_progress = "+bean.getCurrent_progress()
				+" where st_type="+bean.getSt_type()+" and statistics_table='"+bean.getStatistics_table()+"';";
		return sql;
	}
	/**
	 * 根据类型删除统计记录表中旧的数据，只保留最新的一条
	 * @param type 0：小车量；1，格口；2，分钟；3，扫描；4，站点；5，汇总；
	 * 6，导入台；7，格口封包；8，汇总封包；9，批次统计；10，批次封包；
	 * @return
	 */
	public static String formatDeleteStatisticsProSql(int type){
		String sql = "delete rsp from report_statistics_pro rsp,(select f_recno from report_statistics_pro  WHERE st_type="+type //
				+ " order by f_recno desc limit 1) tmp where rsp.f_recno < tmp.f_recno and rsp.st_type="+type+";";
		return sql;
	}
	
}
