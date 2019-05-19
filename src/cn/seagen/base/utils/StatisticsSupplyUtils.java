package cn.seagen.base.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.seagen.base.bean.ReportSupplyBean;
import cn.seagen.base.bean.StatisticsSortBean;
import cn.seagen.base.constant.StatisticsConstant;

/**
 *统计类工具类
 */
public class StatisticsSupplyUtils {
	
	/** 插入导入台量统计表需要的字段*/
	private static String report_supply_insert_keys = "report_date,supply_id,layer_id,all_sum,success_sum,err_sum,"
			+ "no_chute,more_data,no_reade,no_data,cancel_sum,err_chute,max_cycles,lost_data,six_spike";
	
	/**
	 * 将统计类数据，按分钟、层级id、导入台id分组
	 * @param listStatisticsSortBeans
	 * @return 
	 */
	public static Map<String, ReportSupplyBean> dealSupplyStatisticsSqlLists(List<StatisticsSortBean> listStatisticsSortBeans) {
		//key：导入台id+“-”+层级id+“-”+时间戳（秒）与60的商（即转换为分钟），value：是导入台量统计的实体类
		Map<String,ReportSupplyBean> map = new HashMap<String, ReportSupplyBean>();
		//初始化bean
		ReportSupplyBean reportSupplyBean = null;
		//遍历list
		for(StatisticsSortBean bean : listStatisticsSortBeans){
			//时间戳有秒转换为分钟
			int report_time_long = bean.getSorting_time_long()/60;
			//组装map中的key值
			String key = bean.getSupply_id()+"-"+bean.getLayer_id()+"-"+report_time_long;
			if(map.containsKey(key)){
				//map中存在该分钟、该层级、该导入台的记录则只做数据的加操作
				reportSupplyBean = map.get(key);
				//分拣总量
				reportSupplyBean.setAll_sum(reportSupplyBean.getAll_sum()+1);
				//判断是否是异常状态的数据，是则增加异常统计数量
				if(bean.getSorting_status() != 0)
					reportSupplyBean.setErr_sum(reportSupplyBean.getErr_sum()+1);
				dealSupplyStatus(reportSupplyBean, bean.getSorting_status());
			} else {
				//map中不存在该分钟、该层级、该导入台的记录则做map的put操作
				reportSupplyBean = new ReportSupplyBean();
				//导入台id
				reportSupplyBean.setSupply_id(bean.getSupply_id());
				//层级id
				reportSupplyBean.setLayer_id(bean.getLayer_id());
				//分拣总量
				reportSupplyBean.setAll_sum(1);
				//连续60分钟峰值(不包含当前分钟)，整合sql语句时，查询数据库获取当前分钟之前60分钟内的生产数量做峰值
				reportSupplyBean.setSix_spike(0);
				//判断是否是异常状态的数据，是则增加异常统计数量
				if(bean.getSorting_status() != 0)
					reportSupplyBean.setErr_sum(1);
				reportSupplyBean.setReport_date(SqlUtils.formatDateToStr(bean.getSorting_time(), StatisticsConstant.YYYYMMDDHHMM));
				dealSupplyStatus(reportSupplyBean, bean.getSorting_status());
				map.put(key, reportSupplyBean);
			}
		}
		return map;
	}
	
	/**
	 * 按状态处理个状态的数量
	 * @param reportSupplyBean
	 * @param status
	 */
	public static void dealSupplyStatus(ReportSupplyBean reportSupplyBean,int status) {
		switch (status) {
		case 0:
			reportSupplyBean.setSuccess_sum(reportSupplyBean.getSuccess_sum()+1);
			break;
		case 1:
			reportSupplyBean.setNo_chute(reportSupplyBean.getNo_chute()+1);
			break;
		case 2:
			reportSupplyBean.setNo_data(reportSupplyBean.getNo_data()+1);
			break;
		case 3:
			reportSupplyBean.setNo_reade(reportSupplyBean.getNo_reade()+1);
			break;
		case 4:
			reportSupplyBean.setCancel_sum(reportSupplyBean.getCancel_sum()+1);
			break;
		case 5:
			reportSupplyBean.setMax_cycles(reportSupplyBean.getMax_cycles()+1);
			break;
		case 6:
			reportSupplyBean.setErr_chute(reportSupplyBean.getErr_chute()+1);
			break;
		case 7:
			reportSupplyBean.setMore_data(reportSupplyBean.getMore_data()+1);
			break;
		case 8:
			reportSupplyBean.setLost_data(reportSupplyBean.getLost_data()+1);
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
	public static String formatReportSupplySql(ReportSupplyBean bean,int insert_or_update){
		StringBuilder builder = new StringBuilder();
		if(insert_or_update == 0){
			builder.append("insert into report_supply("+report_supply_insert_keys+") values(");
			builder.append("'").append(bean.getReport_date()).append("',");
			builder.append(bean.getSupply_id()).append(",");
			builder.append(bean.getLayer_id()).append(",");
			builder.append(bean.getAll_sum()).append(",");
			builder.append(bean.getSuccess_sum()).append(",");
			builder.append(bean.getErr_sum()).append(",");
			builder.append(bean.getNo_chute()).append(",");
			builder.append(bean.getMore_data()).append(",");
			builder.append(bean.getNo_reade()).append(",");
			builder.append(bean.getNo_data()).append(",");
			builder.append(bean.getCancel_sum()).append(",");
			builder.append(bean.getErr_chute()).append(",");
			builder.append(bean.getMax_cycles()).append(",");
			builder.append(bean.getLost_data()).append(",");
			builder.append(bean.getSix_spike()).append(");");
		} else {
			builder.append("update report_supply set ");
			builder.append("report_date='").append(bean.getReport_date()).append("'");
			builder.append(", supply_id=").append(bean.getSupply_id());
			builder.append(", layer_id=").append(bean.getLayer_id());
			builder.append(", all_sum=").append(bean.getAll_sum());
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
			//builder.append(", six_spike=").append(bean.getSix_spike());
			builder.append(" where f_recno=").append(bean.getF_recno()).append(";");
		}
		
		return builder.toString();
	}
	
	/**
	 * 根据时间、格式获取相关统计的连续60分钟的生产峰值
	 * @param time 2018-01-12 11:01:00
	 * @param type 0：小车量；1，格口；2，分钟；3，扫描；4，站点；5，汇总；
	 * 6，导入台；7，格口封包；8，汇总封包；9，批次统计；10，批次封包；
	 * @return
	 */
	public static String formatReportSixSpikeSql(String time,int supply_id,int layerId,int type){
		switch (type) {
		case 6:
			return "select sum(all_sum) as six_spike from report_supply where report_date >= "
					+ "date_add('"+time+"',interval - 60 minute) and layer_id="+layerId+" and report_date < '"+time+"' and supply_id = "+supply_id+";";
		default:
			break;
		}
		return "";
	}
}
