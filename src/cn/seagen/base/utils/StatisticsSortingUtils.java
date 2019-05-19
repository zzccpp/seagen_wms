package cn.seagen.base.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.seagen.base.bean.ReportBoxBean;
import cn.seagen.base.bean.ReportSortingBean;
import cn.seagen.base.bean.ReportSupplyBean;
import cn.seagen.base.bean.StatisticsBoxBean;
import cn.seagen.base.bean.StatisticsProgressBean;
import cn.seagen.base.bean.StatisticsSortBean;
import cn.seagen.base.constant.StatisticsConstant;
import cn.seagen.base.enums.SortStatus;

/**
 *统计类工具类:汇总、批次、封包统计
 */
public class StatisticsSortingUtils {
	/** 分拣类统计需要的字段*/
	private static String statistics_sorting_keys = "f_recno,batch_id,sorting_time,unix_timestamp(sorting_time_date) as sorting_time_long,car_id,"
			+ "chute_id,supply_id,scan_id,layer_id,sorting_status";
	/** 封包量统计需要的字段*/
	private static String statistics_box_keys = "f_recno,print_time,print_time_long,batch_id,chute_id";

	/** 查询统计进度需要的字段*/
	private static String statistics_progress_keys = "f_recno,st_type,statistics_name,statistics_table,current_progress";
	
	/** 插入统计进度需要的字段*/
	private static String statistics_insert_progress_keys = "st_type,statistics_name,statistics_table,current_progress";
	
	
	/** 插入汇总、批次量统计表需要的字段*/
	private static String report_sorting_insert_keys = "report_date,sum_name,sum_type,begin_time,end_time,supply_sum,scan_sum,"
			+ "layer_sum,box_sum,all_sum,success_sum,err_sum,no_chute,more_data,no_reade,no_data,cancel_sum,err_chute,"
			+ "max_cycles,lost_data,layer0,layer1,layer2,scan0,scan1,scan2,scan3,scan4,scan5,scan6,scan7,"
			+ "scan8,scan9,scan10,scan11,scan12,scan13,scan14,scan15,scan16,supply0,supply1,supply2,supply3,"
			+ "supply4,supply5,supply6,supply7,supply8,supply9,supply10,supply11,supply12,supply13,supply14,"
			+ "supply15,supply16,supply17,supply18,supply19,supply20,supply21,supply22,supply23,supply24,noread0,"
			+ "noread1,noread2,noread3,noread4,noread5,noread6,noread7,noread8,noread9,noread10,noread11,noread12,"
			+ "noread13,noread14,noread15,noread16,noread17,noread18,noread19,noread20,noread21,noread22,noread23,"
			+ "noread24";
	
	/** 插入封包量统计表中的字段*/
	private static String report_box_insert_keys = "report_date,sum_name,sum_type,box_sum";

	/**
	 * 组装分拣类统计时查询分表表中数据
	 * @param f_recno
	 * @param counts
	 * @param tableName 表名
	 * @return
	 */
	public static String formatFindSortList(Long f_recno,int counts,String tableName){
		String sql = "select "+ statistics_sorting_keys + " from "+tableName+" where f_recno > " + f_recno 
				+ " and create_time < date_add(now(),interval -60 second) limit 0," + counts + ";";
		return sql;
	}
	
	/**
	 * 组装封包类统计时查询box_temp表中数据
	 * @param f_recno
	 * @param counts
	 * @return
	 */
	public static String formatFindBoxList(Long f_recno,int counts){
		String sql = "select "+ statistics_box_keys + " from box_temp where f_recno > " + f_recno 
				+ " and create_time < date_add(now(),interval -60 second) limit 0," + counts + ";";
		return sql;
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
	 * 将统计类数据，按批次或者时间、统计方式（0批次、1小时）分组
	 * @param listStatisticsSortBeans
	 * @param type 统计方式（0批次、1小时）
	 * @return 
	 */
	public static Map<String, ReportSortingBean> dealSortingStatisticsSqlLists(List<StatisticsSortBean> listStatisticsSortBeans,int type) {
		//key：report_date+sum_type统计方式（0批次、1小时），value：是批次、汇总量统计的实体类
		Map<String,ReportSortingBean> map = new HashMap<String, ReportSortingBean>();
		//初始化bean
		ReportSortingBean reportSortingBean = null;
		//遍历list
		for(StatisticsSortBean bean : listStatisticsSortBeans){
			//组装map中的key值
			String key = "";
			if(type == 0){
				key= type+"-"+bean.getBatch_id();
			} else {
				//时间戳有秒转换为分钟
				int report_time_long = bean.getSorting_time_long()/60;
				//组装map中的key值
				key= type+"-"+report_time_long;
			}
			if(map.containsKey(key)){
				//map中存在该类型、该时间的记录则只做数据的加操作
				reportSortingBean = map.get(key);
				reportSortingBean.setBegin_time(formatReportSortBeginTime(reportSortingBean.getBegin_time(), bean.getSorting_time()));
				reportSortingBean.setEnd_time(formatReportSortEndTime(reportSortingBean.getEnd_time(), bean.getSorting_time()));
				//分拣总量
				reportSortingBean.setAll_sum(reportSortingBean.getAll_sum()+1);
				//层级总量
				reportSortingBean.setLayer_sum(reportSortingBean.getLayer_sum()+1);
				//导入台总量
				reportSortingBean.setSupply_sum(reportSortingBean.getSupply_sum()+1);
				//龙门架总量
				reportSortingBean.setScan_sum(reportSortingBean.getScan_sum()+1);
				//判断是否是异常状态的数据，是则增加异常统计数量
				if(bean.getSorting_status() != 0)
					reportSortingBean.setErr_sum(reportSortingBean.getErr_sum()+1);
				dealSortingStatus(reportSortingBean, bean.getSorting_status());
				dealSortingLayerNum(reportSortingBean, bean.getLayer_id());
				dealSortingScanNum(reportSortingBean, bean.getScan_id());
				dealSortingSupplyNum(reportSortingBean, bean.getSupply_id());
				if(bean.getSorting_status() == SortStatus.s_NoRead.getValue()){
					dealSortingSupplyNoreadNum(reportSortingBean, bean.getSupply_id());
				}
			} else {
				//map中不存在该类型、该时间的记录则做map的put操作
				reportSortingBean = new ReportSortingBean();
				reportSortingBean.setBegin_time( bean.getSorting_time());
				reportSortingBean.setEnd_time(bean.getSorting_time());
				//统计方式
				reportSortingBean.setSum_type(type);
				//统计名称
				reportSortingBean.setSum_name((type==0?"B":"S")+SqlUtils.formatDateToStr(bean.getSorting_time(), "")
						.replace("-", "").replace(" ", "").replace(":", ""));
				//分拣总量
				reportSortingBean.setAll_sum(1);
				//层级总量
				reportSortingBean.setLayer_sum(1);
				//导入台总量
				reportSortingBean.setSupply_sum(1);
				//龙门架总量
				reportSortingBean.setScan_sum(1);
				//判断是否是异常状态的数据，是则增加异常统计数量
				if(bean.getSorting_status() != 0)
					reportSortingBean.setErr_sum(1);
				reportSortingBean.setReport_date(SqlUtils.formatDateToStr(bean.getSorting_time(), StatisticsConstant.YYYYMMDDHHMM));
				dealSortingStatus(reportSortingBean, bean.getSorting_status());
				dealSortingLayerNum(reportSortingBean, bean.getLayer_id());
				dealSortingScanNum(reportSortingBean, bean.getScan_id());
				dealSortingSupplyNum(reportSortingBean, bean.getSupply_id());
				if(bean.getSorting_status() == SortStatus.s_NoRead.getValue()){
					dealSortingSupplyNoreadNum(reportSortingBean, bean.getSupply_id());
				}
				map.put(key, reportSortingBean);
			}
		}
		return map;
	}
	
	/**
	 * 将统计类数据，按分钟、统计模式分组
	 * @param listStatisticsBoxBeans
	 * @param sum_type 0，批次；1，小时
	 * @return 
	 */
	public static Map<String, ReportBoxBean> dealBoxStatisticsSqlLists(List<StatisticsBoxBean> listStatisticsBoxBeans,int sum_type) {
		//key：统计模式+“-”+时间戳（秒）与60的商，即转换为分钟，value：是封包量统计的实体类
		Map<String,ReportBoxBean> map = new HashMap<String, ReportBoxBean>();
		//初始化bean
		ReportBoxBean reportBoxBean = null;
		//遍历list
		for(StatisticsBoxBean bean : listStatisticsBoxBeans){
			//时间戳有秒转换为分钟
			int report_time_long = bean.getPrint_time_long()/60;
			//组装map中的key值
			String key = sum_type+"-"+report_time_long;
			if(map.containsKey(key)){
				//map中存在该分钟、该模式的记录则只做数据的加操作
				reportBoxBean = map.get(key);
				//封包
				reportBoxBean.setBox_sum(reportBoxBean.getBox_sum()+1);
			} else {
				//map中不存在该分钟、该模式的记录则做map的put操作
				reportBoxBean = new ReportBoxBean();
				reportBoxBean.setSum_name((sum_type == 0 ?"B":"S")+bean.getPrint_time().replace("-", "").replace(" ", "").replace(":", ""));
				reportBoxBean.setSum_type(sum_type);
				//封包
				reportBoxBean.setBox_sum(1);
				reportBoxBean.setReport_date(SqlUtils.formatDateToStr(bean.getPrint_time(), StatisticsConstant.YYYYMMDDHHMM));
				map.put(key, reportBoxBean);
			}
		}
		return map;
	}
	
	/**
	 * 按状态处理个状态的数量
	 * @param reportSortingBean
	 * @param status
	 */
	public static void dealSortingStatus(ReportSortingBean reportSortingBean,int status) {
		switch (status) {
		case 0:
			reportSortingBean.setSuccess_sum(reportSortingBean.getSuccess_sum()+1);
			break;
		case 1:
			reportSortingBean.setNo_chute(reportSortingBean.getNo_chute()+1);
			break;
		case 2:
			reportSortingBean.setNo_data(reportSortingBean.getNo_data()+1);
			break;
		case 3:
			reportSortingBean.setNo_reade(reportSortingBean.getNo_reade()+1);
			break;
		case 4:
			reportSortingBean.setCancel_sum(reportSortingBean.getCancel_sum()+1);
			break;
		case 5:
			reportSortingBean.setMax_cycles(reportSortingBean.getMax_cycles()+1);
			break;
		case 6:
			reportSortingBean.setErr_chute(reportSortingBean.getErr_chute()+1);
			break;
		case 7:
			reportSortingBean.setMore_data(reportSortingBean.getMore_data()+1);
			break;
		case 8:
			reportSortingBean.setLost_data(reportSortingBean.getLost_data()+1);
			break;
		default:
			break;
		}
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
	 * 按层级id给层级添加分拣数量
	 * @param bean
	 * @param layer_id
	 */
	public static void dealSortingLayerNum(ReportSortingBean reportSortingBean,int layer_id) {
		switch (layer_id) {
		case 0:
			reportSortingBean.setLayer0(reportSortingBean.getLayer0()+1);
			break;
		case 1:
			reportSortingBean.setLayer1(reportSortingBean.getLayer1()+1);
			break;
		case 2:
			reportSortingBean.setLayer2(reportSortingBean.getLayer2()+1);
			break;
		default:
			reportSortingBean.setLayer0(reportSortingBean.getLayer0()+1);
			break;
		}
	}
	
	/**
	 * 按扫描仪id给扫描仪添加分拣数量
	 * @param reportSortingBean
	 * @param scan_id
	 */
	public static void dealSortingScanNum(ReportSortingBean reportSortingBean,int scan_id){
		switch (scan_id) {
		case 0:
			reportSortingBean.setScan0(reportSortingBean.getScan0()+1);
			break;
		case 1:
			reportSortingBean.setScan1(reportSortingBean.getScan1()+1);
			break;
		case 2:
			reportSortingBean.setScan2(reportSortingBean.getScan2()+1);
			break;
		case 3:
			reportSortingBean.setScan3(reportSortingBean.getScan3()+1);
			break;
		case 4:
			reportSortingBean.setScan4(reportSortingBean.getScan4()+1);
			break;
		case 5:
			reportSortingBean.setScan5(reportSortingBean.getScan5()+1);
			break;
		case 6:
			reportSortingBean.setScan6(reportSortingBean.getScan6()+1);
			break;
		case 7:
			reportSortingBean.setScan7(reportSortingBean.getScan7()+1);
			break;
		case 8:
			reportSortingBean.setScan8(reportSortingBean.getScan8()+1);
			break;
		case 9:
			reportSortingBean.setScan9(reportSortingBean.getScan9()+1);
			break;
		case 10:
			reportSortingBean.setScan10(reportSortingBean.getScan10()+1);
			break;
		case 11:
			reportSortingBean.setScan11(reportSortingBean.getScan11()+1);
			break;
		case 12:
			reportSortingBean.setScan12(reportSortingBean.getScan12()+1);
			break;
		case 13:
			reportSortingBean.setScan13(reportSortingBean.getScan13()+1);
			break;
		case 14:
			reportSortingBean.setScan14(reportSortingBean.getScan14()+1);
			break;
		case 15:
			reportSortingBean.setScan15(reportSortingBean.getScan15()+1);
			break;
		case 16:
			reportSortingBean.setScan16(reportSortingBean.getScan16()+1);
			break;
		default:
			reportSortingBean.setScan0(reportSortingBean.getScan0()+1);
			break;
		}
	}	
	/**
	 * 根据导入台id给导入台添加分拣量
	 * @param reportSortingBean
	 * @param supply_id
	 */
	public static void dealSortingSupplyNum(ReportSortingBean reportSortingBean,int supply_id){
		switch (supply_id) {
		case 0:
			reportSortingBean.setSupply0(reportSortingBean.getSupply0()+1);
			break;
		case 1:
			reportSortingBean.setSupply1(reportSortingBean.getSupply1()+1);
			break;
		case 2:
			reportSortingBean.setSupply2(reportSortingBean.getSupply2()+1);
			break;
		case 3:
			reportSortingBean.setSupply3(reportSortingBean.getSupply3()+1);
			break;
		case 4:
			reportSortingBean.setSupply4(reportSortingBean.getSupply4()+1);
			break;
		case 5:
			reportSortingBean.setSupply5(reportSortingBean.getSupply5()+1);
			break;
		case 6:
			reportSortingBean.setSupply6(reportSortingBean.getSupply6()+1);
			break;
		case 7:
			reportSortingBean.setSupply7(reportSortingBean.getSupply7()+1);
			break;
		case 8:
			reportSortingBean.setSupply8(reportSortingBean.getSupply8()+1);
			break;
		case 9:
			reportSortingBean.setSupply9(reportSortingBean.getSupply9()+1);
			break;
		case 10:
			reportSortingBean.setSupply10(reportSortingBean.getSupply10()+1);
			break;
		case 11:
			reportSortingBean.setSupply11(reportSortingBean.getSupply11()+1);
			break;
		case 12:
			reportSortingBean.setSupply12(reportSortingBean.getSupply12()+1);
			break;
		case 13:
			reportSortingBean.setSupply13(reportSortingBean.getSupply13()+1);
			break;
		case 14:
			reportSortingBean.setSupply14(reportSortingBean.getSupply14()+1);
			break;
		case 15:
			reportSortingBean.setSupply15(reportSortingBean.getSupply15()+1);
			break;
		case 16:
			reportSortingBean.setSupply16(reportSortingBean.getSupply16()+1);
			break;
		case 17:
			reportSortingBean.setSupply17(reportSortingBean.getSupply17()+1);
			break;
		case 18:
			reportSortingBean.setSupply18(reportSortingBean.getSupply18()+1);
			break;
		case 19:
			reportSortingBean.setSupply19(reportSortingBean.getSupply19()+1);
			break;
		case 20:
			reportSortingBean.setSupply20(reportSortingBean.getSupply20()+1);
			break;
		case 21:
			reportSortingBean.setSupply21(reportSortingBean.getSupply21()+1);
			break;
		case 22:
			reportSortingBean.setSupply22(reportSortingBean.getSupply22()+1);
			break;
		case 23:
			reportSortingBean.setSupply23(reportSortingBean.getSupply23()+1);
			break;
		case 24:
			reportSortingBean.setSupply24(reportSortingBean.getSupply24()+1);
			break;
		default:
			reportSortingBean.setSupply0(reportSortingBean.getSupply0()+1);
			break;
		}
	}
	
	/**
	 * 根据导入台id给导入台添加无读量
	 * @param reportSortingBean
	 * @param supply_id
	 */
	public static void dealSortingSupplyNoreadNum(ReportSortingBean reportSortingBean,int supply_id){
		switch (supply_id) {
		case 0:
			reportSortingBean.setNoread0(reportSortingBean.getNoread0()+1);
			break;
		case 1:
			reportSortingBean.setNoread1(reportSortingBean.getNoread1()+1);
			break;
		case 2:
			reportSortingBean.setNoread2(reportSortingBean.getNoread2()+1);
			break;
		case 3:
			reportSortingBean.setNoread3(reportSortingBean.getNoread3()+1);
			break;
		case 4:
			reportSortingBean.setNoread4(reportSortingBean.getNoread4()+1);
			break;
		case 5:
			reportSortingBean.setNoread5(reportSortingBean.getNoread5()+1);
			break;
		case 6:
			reportSortingBean.setNoread6(reportSortingBean.getNoread6()+1);
			break;
		case 7:
			reportSortingBean.setNoread7(reportSortingBean.getNoread7()+1);
			break;
		case 8:
			reportSortingBean.setNoread8(reportSortingBean.getNoread8()+1);
			break;
		case 9:
			reportSortingBean.setNoread9(reportSortingBean.getNoread9()+1);
			break;
		case 10:
			reportSortingBean.setNoread10(reportSortingBean.getNoread10()+1);
			break;
		case 11:
			reportSortingBean.setNoread11(reportSortingBean.getNoread11()+1);
			break;
		case 12:
			reportSortingBean.setNoread12(reportSortingBean.getNoread12()+1);
			break;
		case 13:
			reportSortingBean.setNoread13(reportSortingBean.getNoread13()+1);
			break;
		case 14:
			reportSortingBean.setNoread14(reportSortingBean.getNoread14()+1);
			break;
		case 15:
			reportSortingBean.setNoread15(reportSortingBean.getNoread15()+1);
			break;
		case 16:
			reportSortingBean.setNoread16(reportSortingBean.getNoread16()+1);
			break;
		case 17:
			reportSortingBean.setNoread17(reportSortingBean.getNoread17()+1);
			break;
		case 18:
			reportSortingBean.setNoread18(reportSortingBean.getNoread18()+1);
			break;
		case 19:
			reportSortingBean.setNoread19(reportSortingBean.getNoread19()+1);
			break;
		case 20:
			reportSortingBean.setNoread20(reportSortingBean.getNoread20()+1);
			break;
		case 21:
			reportSortingBean.setNoread21(reportSortingBean.getNoread21()+1);
			break;
		case 22:
			reportSortingBean.setNoread22(reportSortingBean.getNoread22()+1);
			break;
		case 23:
			reportSortingBean.setNoread23(reportSortingBean.getNoread23()+1);
			break;
		case 24:
			reportSortingBean.setNoread24(reportSortingBean.getNoread24()+1);
			break;
		default:
			reportSortingBean.setNoread0(reportSortingBean.getNoread0()+1);
			break;
		}
	}
	
	
	/**
	 * 将实体类组装成sql语句
	 * @param bean
	 * @param insert_or_update 0,insert;1,update
	 * @return
	 */
	public static String formatReportSortingSql(ReportSortingBean bean,int insert_or_update){
		StringBuilder builder = new StringBuilder();
		if(insert_or_update == 0){
			builder.append("insert into report_sorting ("+report_sorting_insert_keys+") values(");
			builder.append("'").append(bean.getReport_date()).append("',");
			builder.append("'").append(bean.getSum_name()).append("',");
			builder.append("").append(bean.getSum_type()).append(",");
			builder.append("'").append(bean.getBegin_time()).append("',");
			builder.append("'").append(bean.getEnd_time()).append("',");
			builder.append(bean.getSupply_sum()).append(",");
			builder.append(bean.getScan_sum()).append(",");
			builder.append(bean.getLayer_sum()).append(",");
			builder.append(bean.getBox_sum()).append(",");
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
			builder.append(bean.getLayer0()).append(",");
			builder.append(bean.getLayer1()).append(",");
			builder.append(bean.getLayer2()).append(",");
			builder.append(bean.getScan0()).append(",");
			builder.append(bean.getScan1()).append(",");
			builder.append(bean.getScan2()).append(",");
			builder.append(bean.getScan3()).append(",");
			builder.append(bean.getScan4()).append(",");
			builder.append(bean.getScan5()).append(",");
			builder.append(bean.getScan6()).append(",");
			builder.append(bean.getScan7()).append(",");
			builder.append(bean.getScan8()).append(",");
			builder.append(bean.getScan9()).append(",");
			builder.append(bean.getScan10()).append(",");
			builder.append(bean.getScan11()).append(",");
			builder.append(bean.getScan12()).append(",");
			builder.append(bean.getScan13()).append(",");
			builder.append(bean.getScan14()).append(",");
			builder.append(bean.getScan15()).append(",");
			builder.append(bean.getScan16()).append(",");
			builder.append(bean.getSupply0()).append(",");
			builder.append(bean.getSupply1()).append(",");
			builder.append(bean.getSupply2()).append(",");
			builder.append(bean.getSupply3()).append(",");
			builder.append(bean.getSupply4()).append(",");
			builder.append(bean.getSupply5()).append(",");
			builder.append(bean.getSupply6()).append(",");
			builder.append(bean.getSupply7()).append(",");
			builder.append(bean.getSupply8()).append(",");
			builder.append(bean.getSupply9()).append(",");
			builder.append(bean.getSupply10()).append(",");
			builder.append(bean.getSupply11()).append(",");
			builder.append(bean.getSupply12()).append(",");
			builder.append(bean.getSupply13()).append(",");
			builder.append(bean.getSupply14()).append(",");
			builder.append(bean.getSupply15()).append(",");
			builder.append(bean.getSupply16()).append(",");
			builder.append(bean.getSupply17()).append(",");
			builder.append(bean.getSupply18()).append(",");
			builder.append(bean.getSupply19()).append(",");
			builder.append(bean.getSupply20()).append(",");
			builder.append(bean.getSupply21()).append(",");
			builder.append(bean.getSupply22()).append(",");
			builder.append(bean.getSupply23()).append(",");
			builder.append(bean.getSupply24()).append(",");
			builder.append(bean.getNoread0()).append(",");
			builder.append(bean.getNoread1()).append(",");
			builder.append(bean.getNoread2()).append(",");
			builder.append(bean.getNoread3()).append(",");
			builder.append(bean.getNoread4()).append(",");
			builder.append(bean.getNoread5()).append(",");
			builder.append(bean.getNoread6()).append(",");
			builder.append(bean.getNoread7()).append(",");
			builder.append(bean.getNoread8()).append(",");
			builder.append(bean.getNoread9()).append(",");
			builder.append(bean.getNoread10()).append(",");
			builder.append(bean.getNoread11()).append(",");
			builder.append(bean.getNoread12()).append(",");
			builder.append(bean.getNoread13()).append(",");
			builder.append(bean.getNoread14()).append(",");
			builder.append(bean.getNoread15()).append(",");
			builder.append(bean.getNoread16()).append(",");
			builder.append(bean.getNoread17()).append(",");
			builder.append(bean.getNoread18()).append(",");
			builder.append(bean.getNoread19()).append(",");
			builder.append(bean.getNoread20()).append(",");
			builder.append(bean.getNoread21()).append(",");
			builder.append(bean.getNoread22()).append(",");
			builder.append(bean.getNoread23()).append(",");
			builder.append(bean.getNoread24()).append(");");
		} else {
			builder.append("update report_sorting set ");
//			builder.append("report_date='").append(bean.getReport_date()).append("'");
//			builder.append(",sum_name=").append("'").append(bean.getSum_name()).append("'");
//			builder.append(",sum_type=").append(bean.getSum_type());
			builder.append("begin_time=").append("'").append(bean.getBegin_time()).append("'");
			builder.append(",end_time=").append("'").append(bean.getEnd_time()).append("'");
			builder.append(",supply_sum=").append(bean.getSupply_sum());
			builder.append(",scan_sum=").append(bean.getScan_sum());
			builder.append(",layer_sum=").append(bean.getLayer_sum());
			builder.append(",box_sum=").append(bean.getBox_sum());
			builder.append(",all_sum=").append(bean.getAll_sum());
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
			builder.append(",layer0=").append(bean.getLayer0());
			builder.append(",layer1=").append(bean.getLayer1());
			builder.append(",layer2=").append(bean.getLayer2());
			builder.append(",scan0=").append(bean.getScan0());
			builder.append(",scan1=").append(bean.getScan1());
			builder.append(",scan2=").append(bean.getScan2());
			builder.append(",scan3=").append(bean.getScan3());
			builder.append(",scan4=").append(bean.getScan4());
			builder.append(",scan5=").append(bean.getScan5());
			builder.append(",scan6=").append(bean.getScan6());
			builder.append(",scan7=").append(bean.getScan7());
			builder.append(",scan8=").append(bean.getScan8());
			builder.append(",scan9=").append(bean.getScan9());
			builder.append(",scan10=").append(bean.getScan10());
			builder.append(",scan11=").append(bean.getScan11());
			builder.append(",scan12=").append(bean.getScan12());
			builder.append(",scan13=").append(bean.getScan13());
			builder.append(",scan14=").append(bean.getScan14());
			builder.append(",scan15=").append(bean.getScan15());
			builder.append(",scan16=").append(bean.getScan16());
			builder.append(",supply0=").append(bean.getSupply0());
			builder.append(",supply1=").append(bean.getSupply1());
			builder.append(",supply2=").append(bean.getSupply2());
			builder.append(",supply3=").append(bean.getSupply3());
			builder.append(",supply4=").append(bean.getSupply4());
			builder.append(",supply5=").append(bean.getSupply5());
			builder.append(",supply6=").append(bean.getSupply6());
			builder.append(",supply7=").append(bean.getSupply7());
			builder.append(",supply8=").append(bean.getSupply8());
			builder.append(",supply9=").append(bean.getSupply9());
			builder.append(",supply10=").append(bean.getSupply10());
			builder.append(",supply11=").append(bean.getSupply11());
			builder.append(",supply12=").append(bean.getSupply12());
			builder.append(",supply13=").append(bean.getSupply13());
			builder.append(",supply14=").append(bean.getSupply14());
			builder.append(",supply15=").append(bean.getSupply15());
			builder.append(",supply16=").append(bean.getSupply16());
			builder.append(",supply17=").append(bean.getSupply17());
			builder.append(",supply18=").append(bean.getSupply18());
			builder.append(",supply19=").append(bean.getSupply19());
			builder.append(",supply20=").append(bean.getSupply20());
			builder.append(",supply21=").append(bean.getSupply21());
			builder.append(",supply22=").append(bean.getSupply22());
			builder.append(",supply23=").append(bean.getSupply23());
			builder.append(",supply24=").append(bean.getSupply24());
			builder.append(",noread0=").append(bean.getNoread0());
			builder.append(",noread1=").append(bean.getNoread1());
			builder.append(",noread2=").append(bean.getNoread2());
			builder.append(",noread3=").append(bean.getNoread3());
			builder.append(",noread4=").append(bean.getNoread4());
			builder.append(",noread5=").append(bean.getNoread5());
			builder.append(",noread6=").append(bean.getNoread6());
			builder.append(",noread7=").append(bean.getNoread7());
			builder.append(",noread8=").append(bean.getNoread8());
			builder.append(",noread9=").append(bean.getNoread9());
			builder.append(",noread10=").append(bean.getNoread10());
			builder.append(",noread11=").append(bean.getNoread11());
			builder.append(",noread12=").append(bean.getNoread12());
			builder.append(",noread13=").append(bean.getNoread13());
			builder.append(",noread14=").append(bean.getNoread14());
			builder.append(",noread15=").append(bean.getNoread15());
			builder.append(",noread16=").append(bean.getNoread16());
			builder.append(",noread17=").append(bean.getNoread17());
			builder.append(",noread18=").append(bean.getNoread18());
			builder.append(",noread19=").append(bean.getNoread19());
			builder.append(",noread20=").append(bean.getNoread20());
			builder.append(",noread21=").append(bean.getNoread21());
			builder.append(",noread22=").append(bean.getNoread22());
			builder.append(",noread23=").append(bean.getNoread23());
			builder.append(",noread24=").append(bean.getNoread24());
			builder.append(" where f_recno=").append(bean.getF_recno()).append(";");
		}
		
		return builder.toString();
	}
	
	/**
	 * 将实体类组装成sql语句
	 * @param bean
	 * @param insert_or_update 0,insert;1,update
	 * @return
	 */
	public static String formatReportBoxSql(ReportBoxBean bean,int insert_or_update){
		StringBuilder builder = new StringBuilder();
		if(insert_or_update == 0){
			builder.append("insert into report_sorting("+report_box_insert_keys+") values(");
			builder.append("'").append(bean.getReport_date()).append("','");
			builder.append(bean.getSum_name()).append("',");
			builder.append(bean.getSum_type()).append(",");
			builder.append(bean.getBox_sum()).append(");");
		} else {
			builder.append("update report_sorting set ");
			builder.append("report_date='").append(bean.getReport_date()).append("'");
			builder.append(", sum_name='").append(bean.getSum_name()).append("'");
			builder.append(", sum_type=").append(bean.getSum_type());
			builder.append(", box_sum=").append(bean.getBox_sum());
			builder.append(" where f_recno=").append(bean.getF_recno()).append(";");
		}
		
		return builder.toString();
	}
	
	/**
	 * 判断汇总、批次统计时的起始时间
	 * @param begin_time 当前时间
	 * @param db_begin_time 数据库中时间
	 * @return
	 */
	public static String formatReportSortBeginTime(String begin_time,String db_begin_time){
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(fmt.parse(begin_time).getTime() < fmt.parse(db_begin_time).getTime()) {
				return begin_time;
			} else {
				return db_begin_time;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return begin_time;
	}
	
	/**
	 * 判断汇总、批次统计时的结束时间
	 * @param end_time 当前时间
	 * @param db_end_time 数据库中时间
	 * @return
	 */
	public static String formatReportSortEndTime(String end_time,String db_end_time){
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(fmt.parse(end_time).getTime() > fmt.parse(db_end_time).getTime()) {
				return end_time;
			} else {
				return db_end_time;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return end_time;
	}
	
}
