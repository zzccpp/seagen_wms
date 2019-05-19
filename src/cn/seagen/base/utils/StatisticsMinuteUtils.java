package cn.seagen.base.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.seagen.base.bean.ReportMinuteBean;
import cn.seagen.base.bean.StatisticsSortBean;
import cn.seagen.base.constant.StatisticsConstant;

/**
 *统计类工具类
 */
public class StatisticsMinuteUtils {
	
	/** 插入分钟量统计表需要的字段*/
	private static String report_minute_insert_keys = "report_date, layer_id, sorting_count, success_sum, no_reade, "
			+ "err_sum, no_chute, more_data, no_data, cancel_sum, err_chute, max_cycles, lost_data";
	
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
			return "select count(*) as num from "+tableName+" where report_date='"+report_date+"' and scan_id="+scan_id+";";
		case 5:
		case 8:
		case 9:
		case 10:
			if(sum_type == 0){
				return "select count(*) as num from "+tableName+" where sum_type = 0 order by f_recno desc limit 1;";
			} else {
				return "select count(*) as num from "+tableName+" where report_date='"+report_date+"' and sum_type="
						+sum_type+" order by f_recno desc limit 1;";
			}
		case 6:
			return "select count(*) as num from "+tableName+" where report_date='"+report_date+"' and layer_id="+layer_id
					+" and supply_id="+supply_id+";";
		default:
			return "";
		}
	}
	
	/**
	 * 将统计类数据，按分钟、层级id分组
	 * @param listStatisticsSortBeans
	 * @return 
	 */
	public static Map<String, ReportMinuteBean> dealMinuteStatisticsSqlLists(List<StatisticsSortBean> listStatisticsSortBeans) {
		//key：层级id+“-”+时间戳（秒）与60的商，即转换为分钟，value：是分钟量统计的实体类
		Map<String,ReportMinuteBean> map = new HashMap<String, ReportMinuteBean>();
		//初始化bean
		ReportMinuteBean reportMinuteBean = null;
		//遍历list
		for(StatisticsSortBean bean : listStatisticsSortBeans){
			//时间戳有秒转换为分钟
			int report_time_long = bean.getSorting_time_long()/60;
			//组装map中的key值
			String key = bean.getLayer_id()+"-"+report_time_long;
			if(map.containsKey(key)){
				//map中存在该分钟、该层级的记录则只做数据的加操作
				reportMinuteBean = map.get(key);
				//分拣总量
				reportMinuteBean.setSorting_count(reportMinuteBean.getSorting_count()+1);
				//判断是否是异常状态的数据，是则增加异常统计数量
				if(bean.getSorting_status() != 0)
					reportMinuteBean.setErr_sum(reportMinuteBean.getErr_sum()+1);
				dealMinuteStatus(reportMinuteBean, bean.getSorting_status());
			} else {
				//map中不存在该分钟、该层级的记录则做map的put操作
				reportMinuteBean = new ReportMinuteBean();
				reportMinuteBean.setLayer_id(bean.getLayer_id());
				//分拣总量
				reportMinuteBean.setSorting_count(1);
				//判断是否是异常状态的数据，是则增加异常统计数量
				if(bean.getSorting_status() != 0)
					reportMinuteBean.setErr_sum(1);
				reportMinuteBean.setReport_date(SqlUtils.formatDateToStr(bean.getSorting_time(), StatisticsConstant.YYYYMMDDHHMM));
				dealMinuteStatus(reportMinuteBean, bean.getSorting_status());
				map.put(key, reportMinuteBean);
			}
		}
		return map;
	}
	
	/**
	 * 按状态处理个状态的数量
	 * @param reportMinuteBean
	 * @param status
	 */
	public static void dealMinuteStatus(ReportMinuteBean reportMinuteBean,int status) {
		switch (status) {
		case 0:
			reportMinuteBean.setSuccess_sum(reportMinuteBean.getSuccess_sum()+1);
			break;
		case 1:
			reportMinuteBean.setNo_chute(reportMinuteBean.getNo_chute()+1);
			break;
		case 2:
			reportMinuteBean.setNo_data(reportMinuteBean.getNo_data()+1);
			break;
		case 3:
			reportMinuteBean.setNo_reade(reportMinuteBean.getNo_reade()+1);
			break;
		case 4:
			reportMinuteBean.setCancel_sum(reportMinuteBean.getCancel_sum()+1);
			break;
		case 5:
			reportMinuteBean.setMax_cycles(reportMinuteBean.getMax_cycles()+1);
			break;
		case 6:
			reportMinuteBean.setErr_chute(reportMinuteBean.getErr_chute()+1);
			break;
		case 7:
			reportMinuteBean.setMore_data(reportMinuteBean.getMore_data()+1);
			break;
		case 8:
			reportMinuteBean.setLost_data(reportMinuteBean.getLost_data()+1);
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
	public static String formatReportMinuteSql(ReportMinuteBean bean,int insert_or_update){
		StringBuilder builder = new StringBuilder();
		if(insert_or_update == 0){
			builder.append("insert into report_minute("+report_minute_insert_keys+") values(");
			builder.append("'").append(bean.getReport_date()).append("',");
			builder.append(bean.getLayer_id()).append(",");
			builder.append(bean.getSorting_count()).append(",");
			builder.append(bean.getSuccess_sum()).append(",");
			builder.append(bean.getNo_reade()).append(",");
			builder.append(bean.getErr_sum()).append(",");
			builder.append(bean.getNo_chute()).append(",");
			builder.append(bean.getMore_data()).append(",");
			builder.append(bean.getNo_data()).append(",");
			builder.append(bean.getCancel_sum()).append(",");
			builder.append(bean.getErr_chute()).append(",");
			builder.append(bean.getMax_cycles()).append(",");
			builder.append(bean.getLost_data()).append(");");
		} else {
			builder.append("update report_minute set ");
			builder.append("report_date='").append(bean.getReport_date()).append("'");
			builder.append(", layer_id=").append(bean.getLayer_id());
			builder.append(", sorting_count=").append(bean.getSorting_count());
			builder.append(", success_sum=").append(bean.getSuccess_sum());
			builder.append(", no_reade=").append(bean.getNo_reade());
			builder.append(", err_sum=").append(bean.getErr_sum());
			builder.append(", no_chute=").append(bean.getNo_chute());
			builder.append(", more_data=").append(bean.getMore_data());
			builder.append(", no_data=").append(bean.getNo_data());
			builder.append(", cancel_sum=").append(bean.getCancel_sum());
			builder.append(", err_chute=").append(bean.getErr_chute());
			builder.append(", max_cycles=").append(bean.getMax_cycles());
			builder.append(", lost_data=").append(bean.getLost_data());
			builder.append(" where f_recno=").append(bean.getF_recno()).append(";");
		}
		
		return builder.toString();
	}
		
}
