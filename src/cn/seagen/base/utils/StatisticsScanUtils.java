package cn.seagen.base.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.seagen.base.bean.ReportScanBean;
import cn.seagen.base.bean.StatisticsScanBean;
import cn.seagen.base.constant.StatisticsConstant;

/**
 *统计类工具类
 */
public class StatisticsScanUtils {
	/** 扫描量统计需要的字段*/
	private static String statistics_scan_keys = "f_recno,date_format(scan_time,'%Y-%m-%d %H:%i:%s') as scan_time,unix_timestamp(date_format(scan_time,'%Y-%m-%d %H:%i:%s')) as scan_time_long,scan_id,layer_id,scan_status";
	
	/** 插入扫描量统计表需要的字段*/
	private static String report_scan_insert_keys = "report_date,scan_id,layer_id,all_sum,success_sum,more_data,no_reade";
	
	/**
	 * 组装扫描仪统计时查询scan_temp表中数据
	 * @param f_recno
	 * @param counts
	 * @param tableName 表名
	 * @return
	 */
	public static String formatFindScanList(Long f_recno,int counts,String tableName){
		String sql = "select "+ statistics_scan_keys + " from "+tableName+" where f_recno > " + f_recno 
				+ " and create_time < date_add(now(),interval -60 second) limit 0," + counts + ";";
		return sql;
	}
	
	/**
	 * 将统计类数据，按分钟、龙门架id分组
	 * @param listStatisticsScanBeans
	 * @return 
	 */
	public static Map<String, ReportScanBean> dealScanStatisticsSqlLists(List<StatisticsScanBean> listStatisticsScanBeans) {
		//key：龙门架id+“-”+时间戳（秒）与60的商，即转换为分钟，value：是分钟量统计的实体类
		Map<String,ReportScanBean> map = new HashMap<String, ReportScanBean>();
		//初始化bean
		ReportScanBean reportScanBean = null;
		//遍历list
		for(StatisticsScanBean bean : listStatisticsScanBeans){
			//时间戳有秒转换为分钟
			int report_time_long = bean.getScan_time_long()/60;
			//组装map中的key值
			String key = bean.getScan_id()+"-"+bean.getLayer_id()+"-"+report_time_long;
			if(map.containsKey(key)){
				//map中存在该分钟、该层级的记录则只做数据的加操作
				reportScanBean = map.get(key);
				//分拣总量
				reportScanBean.setAll_sum(reportScanBean.getAll_sum()+1);
				dealScanStatus(reportScanBean, bean.getScan_status());
			} else {
				//map中不存在该分钟、该层级的记录则做map的put操作
				reportScanBean = new ReportScanBean();
				//龙门架id
				reportScanBean.setScan_id(bean.getScan_id());
				//层级id
				reportScanBean.setLayer_id(bean.getLayer_id());
				//分拣总量
				reportScanBean.setAll_sum(1);
				reportScanBean.setReport_date(SqlUtils.formatDateToStr(bean.getScan_time(), StatisticsConstant.YYYYMMDDHHMM));
				dealScanStatus(reportScanBean, bean.getScan_status());
				map.put(key, reportScanBean);
			}
		}
		return map;
	}
	
	/**
	 * 按状态处理个状态的数量
	 * @param reportScanBean
	 * @param status 0，正常；3，无读；7，多条码
	 */
	public static void dealScanStatus(ReportScanBean reportScanBean,int status) {
		switch (status) {
		case 0:
			reportScanBean.setSuccess_sum(reportScanBean.getSuccess_sum()+1);
			break;
		case 3:
			reportScanBean.setNo_reade(reportScanBean.getNo_reade()+1);
			break;
		case 7:
			reportScanBean.setMore_data(reportScanBean.getMore_data()+1);
			break;
		default:
			break;
		}
	}
		
	/**
	 * 将实体类组装成sql语句
	 * @param bean
	 * @param insert_or_update 0,insert;1,update
	 * @return
	 */
	public static String formatReportScanSql(ReportScanBean bean,int insert_or_update){
		StringBuilder builder = new StringBuilder();
		if(insert_or_update == 0){
			builder.append("insert into report_scan("+report_scan_insert_keys+") values(");
			builder.append("'").append(bean.getReport_date()).append("',");
			builder.append(bean.getScan_id()).append(",");
			builder.append(bean.getLayer_id()).append(",");
			builder.append(bean.getAll_sum()).append(",");
			builder.append(bean.getSuccess_sum()).append(",");
			builder.append(bean.getMore_data()).append(",");
			builder.append(bean.getNo_reade()).append(");");
		} else {
			builder.append("update report_scan set ");
			builder.append("report_date='").append(bean.getReport_date()).append("'");
			builder.append(", scan_id=").append(bean.getScan_id());
			builder.append(", layer_id=").append(bean.getLayer_id());
			builder.append(", all_sum=").append(bean.getAll_sum());
			builder.append(", success_sum=").append(bean.getSuccess_sum());
			builder.append(", no_reade=").append(bean.getNo_reade());
			builder.append(", more_data=").append(bean.getMore_data());
			builder.append(" where f_recno=").append(bean.getF_recno()).append(";");
		}
		
		return builder.toString();
	}
}
