package cn.seagen.base.timertasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.seagen.base.bean.ReportBoxBean;
import cn.seagen.base.bean.ReportSortingBean;
import cn.seagen.base.bean.StatisticsBoxBean;
import cn.seagen.base.bean.StatisticsProgressBean;
import cn.seagen.base.bean.StatisticsSortBean;
import cn.seagen.base.constant.StatisticsConstant;
import cn.seagen.base.enums.StatisticsTempType;
import cn.seagen.base.enums.StatisticsType;
import cn.seagen.base.service.DbPartitionService;
import cn.seagen.base.service.StatisticsService;
import cn.seagen.base.utils.SqlUtils;
import cn.seagen.base.utils.StatisticsSortingUtils;
import cn.seagen.base.utils.StatisticsUtils;
/**
 * 
 *汇总量统计定时任务：汇总、汇总封包统计
 */
@Service
public class TaskStatisticsSorting {

	@Resource
	private StatisticsService statisticsServiceImpl;
	@Resource
	private DbPartitionService dbPartitionServiceImpl;
	
	private static Logger logger = LogManager.getLogger(TaskStatisticsSorting.class);
	
	@Scheduled(initialDelay=2*60*1000,fixedDelay = 5*60*1000)   //服务启动后2分钟执行，然后执行后5分钟在执行
	public void timerTask(){
		//汇总
		doStatisticsWork(StatisticsType.STATISTICS_SORTING.getMessage(), StatisticsType.STATISTICS_SORTING.getValue());
		//汇总封包
		doStatisticsWork(StatisticsType.STATISTICS_SORTING_BOX.getMessage(), StatisticsType.STATISTICS_SORTING_BOX.getValue());
	}
	
	/**
	 * 执行具体的统计工作:从sorting_temp（分拣数据）、box_temp（封包数据）表中查询数据插入到：report_sorting表中；
	 * @param tableName 
	 * @param type  5，汇总；8，汇总封包；
	 */
	public void doStatisticsWork(String tableName,int type){
		logger.info("开始执行统计定时任务：tableName="+tableName+",type="+type);
		//开始时间
		long begintime = System.currentTimeMillis();
		//存在的分表集合
		List<String> listTables = dbPartitionServiceImpl.findPartitionTables();
		List<String> sortingTabls = new ArrayList<String>();
		for(String tb:listTables){
			if(tb.startsWith("sorting_"))
				sortingTabls.add(tb);
		}
		for(String table:sortingTabls){
			//获取当前类型的统计进度
			long progress = 0;
			StatisticsProgressBean statisticsProgressBean = statisticsServiceImpl.getStatisticsProgressBean(type,table);
			if(statisticsProgressBean != null && statisticsProgressBean.getCurrent_progress() > 0)
				progress = statisticsProgressBean.getCurrent_progress();
		
			//根据统计的不通类型，细分为：分拣类、扫描类、封包类
			if(type == 5){
				//5，汇总；
				doSortStatistics(progress,StatisticsConstant.STATISTICS_MAX_COUNT, type,table);
			}
			//获取封包数据的统计进度
			statisticsProgressBean = statisticsServiceImpl.getStatisticsProgressBean(type,StatisticsTempType.STATISTICS_TEMP_BOX.getMessage());
			if(statisticsProgressBean != null && statisticsProgressBean.getCurrent_progress() > 0)
				progress = statisticsProgressBean.getCurrent_progress();
			if(type == 8){
				//8，汇总封包；
				doBoxStatistics(progress,StatisticsConstant.STATISTICS_MAX_COUNT, type);
			}
		}
		//结束时间
		long runtime = System.currentTimeMillis() - begintime;
		logger.info("结束执行统计定时任务："+StatisticsConstant.logKey[type]+",tableName="+tableName
				+",type="+type+".共耗时：("+runtime+")");
	}
	
	/**
	 * sorting相关的统计
	 * @param f_recno
	 * @param count 统计数量
	 * @param type 0：小车量；1，格口；2，分钟；4，站点；5，汇总；6，导入台；9，批次统计；
	 * @param statistics_table 统计表名
	 */
	public boolean doSortStatistics(long f_recno,int count,int type,String statistics_table){
		//目前分拣类统计只做：分钟、汇总、导入台、批次 统计
		List<StatisticsSortBean> list = statisticsServiceImpl.findSortList(f_recno, count,statistics_table);
		if(list == null || list.size() == 0) {
			logger.info("分拣类统计：数量为0，type="+type);
			return false;
		}
		logger.info("分拣类统计：数量为:"+list.size()+"，type="+type);
		//判断进度记录表中是更新还是新增
		boolean isUpdate = true;
		if(f_recno == 0)
			isUpdate = false;
		//获取查询出来的最大主键（查询默认按主键增序排列）
		f_recno = list.get(list.size()-1).getF_recno();
		//统计模式 0,批次；1，小时
		int sum_type = 1;
		//key：批次id或者时间、统计模式（0，批次；1，小时）；value：是分拣量统计的实体类
		Map<String,ReportSortingBean> map = StatisticsSortingUtils.dealSortingStatisticsSqlLists(list,sum_type);
		List<String> listSqls = new ArrayList<String>();
		//遍历map，获取统计完成的数据实体类
		Set<Entry<String, ReportSortingBean>> entrySet=map.entrySet();  
		for(Entry<String, ReportSortingBean> entry:entrySet){  
			ReportSortingBean bean =entry.getValue();  
		    //0,insert;1,update
		    int insert_or_update = 0;
		    //组装查询是否存在的sql语句
		    String sqls = StatisticsUtils.formatIfExitsReportTable(StatisticsType.STATISTICS_SORTING.getMessage(), 
		    		bean.getReport_date(), 0, 0, 0,sum_type, type);
		    //判断数据库中是否存在该时间段、类型的统计
		    if(statisticsServiceImpl.findIfExitsInReportTable(sqls)){
		    	//存在，则查询出存在的记录，做和操作；
		    	//组装查询存在的sql语句
		    	String sql = StatisticsUtils.formatFindReportSql(null, bean.getReport_date(), 0, 0, 0, sum_type, 
		    			StatisticsType.STATISTICS_SORTING.getValue());
		    	ReportSortingBean reportSortingBean = statisticsServiceImpl.findReportSortingBean(sql);
//		    	logger.error("更新操作前："+reportSortingBean.toString()+" \n,更新操作中："+bean.toString()+",消息列数："+list.size());
		    	
		    	bean.setF_recno(reportSortingBean.getF_recno());
		    	bean.setBegin_time(StatisticsSortingUtils.formatReportSortBeginTime(bean.getBegin_time(), reportSortingBean.getBegin_time()));
		    	bean.setEnd_time(StatisticsSortingUtils.formatReportSortEndTime(bean.getEnd_time(), reportSortingBean.getEnd_time()));
		    	bean.setSupply_sum(bean.getSupply_sum()+reportSortingBean.getSupply_sum());
		    	bean.setScan_sum(bean.getScan_sum()+reportSortingBean.getScan_sum());
		    	bean.setLayer_sum(bean.getLayer_sum()+reportSortingBean.getLayer_sum());
		    	bean.setBox_sum(bean.getBox_sum()+reportSortingBean.getBox_sum());
		    	bean.setAll_sum(bean.getAll_sum()+reportSortingBean.getAll_sum());
		    	bean.setSuccess_sum(bean.getSuccess_sum()+reportSortingBean.getSuccess_sum());
		    	bean.setErr_sum(bean.getErr_sum()+reportSortingBean.getErr_sum());
		    	bean.setNo_chute(bean.getNo_chute()+reportSortingBean.getNo_chute());
		    	bean.setNo_data(bean.getNo_data()+reportSortingBean.getNo_data());
		    	bean.setNo_reade(bean.getNo_reade()+reportSortingBean.getNo_reade());
		    	bean.setCancel_sum(bean.getCancel_sum()+reportSortingBean.getCancel_sum());
		    	bean.setMax_cycles(bean.getMax_cycles()+reportSortingBean.getMax_cycles());
		    	bean.setErr_chute(bean.getErr_chute()+reportSortingBean.getErr_chute());
		    	bean.setMore_data(bean.getMore_data()+reportSortingBean.getMore_data());
		    	bean.setLost_data(bean.getLost_data()+reportSortingBean.getLost_data());
		    	bean.setLayer0(bean.getLayer0()+reportSortingBean.getLayer0());
		    	bean.setLayer1(bean.getLayer1()+reportSortingBean.getLayer1());
		    	bean.setLayer2(bean.getLayer2()+reportSortingBean.getLayer2());
		    	bean.setScan0(bean.getScan0()+reportSortingBean.getScan0());
		    	bean.setScan1(bean.getScan1()+reportSortingBean.getScan1());
		    	bean.setScan2(bean.getScan2()+reportSortingBean.getScan2());
		    	bean.setScan3(bean.getScan3()+reportSortingBean.getScan3());
		    	bean.setScan4(bean.getScan4()+reportSortingBean.getScan4());
		    	bean.setScan5(bean.getScan5()+reportSortingBean.getScan5());
		    	bean.setScan6(bean.getScan6()+reportSortingBean.getScan6());
		    	bean.setScan7(bean.getScan7()+reportSortingBean.getScan7());
		    	bean.setScan8(bean.getScan8()+reportSortingBean.getScan8());
		    	bean.setScan9(bean.getScan9()+reportSortingBean.getScan9());
		    	bean.setScan10(bean.getScan10()+reportSortingBean.getScan10());
		    	bean.setScan11(bean.getScan11()+reportSortingBean.getScan11());
		    	bean.setScan12(bean.getScan12()+reportSortingBean.getScan12());
		    	bean.setScan13(bean.getScan13()+reportSortingBean.getScan13());
		    	bean.setScan14(bean.getScan14()+reportSortingBean.getScan14());
		    	bean.setScan15(bean.getScan15()+reportSortingBean.getScan15());
		    	bean.setScan16(bean.getScan16()+reportSortingBean.getScan16());
		    	bean.setSupply0(bean.getSupply0()+reportSortingBean.getSupply0());
		    	bean.setSupply1(bean.getSupply1()+reportSortingBean.getSupply1());
		    	bean.setSupply2(bean.getSupply2()+reportSortingBean.getSupply2());
		    	bean.setSupply3(bean.getSupply3()+reportSortingBean.getSupply3());
		    	bean.setSupply4(bean.getSupply4()+reportSortingBean.getSupply4());
		    	bean.setSupply5(bean.getSupply5()+reportSortingBean.getSupply5());
		    	bean.setSupply6(bean.getSupply6()+reportSortingBean.getSupply6());
		    	bean.setSupply7(bean.getSupply7()+reportSortingBean.getSupply7());
		    	bean.setSupply8(bean.getSupply8()+reportSortingBean.getSupply8());
		    	bean.setSupply9(bean.getSupply9()+reportSortingBean.getSupply9());
		    	bean.setSupply10(bean.getSupply10()+reportSortingBean.getSupply10());
		    	bean.setSupply11(bean.getSupply11()+reportSortingBean.getSupply11());
		    	bean.setSupply12(bean.getSupply12()+reportSortingBean.getSupply12());
		    	bean.setSupply13(bean.getSupply13()+reportSortingBean.getSupply13());
		    	bean.setSupply14(bean.getSupply14()+reportSortingBean.getSupply14());
		    	bean.setSupply15(bean.getSupply15()+reportSortingBean.getSupply15());
		    	bean.setSupply16(bean.getSupply16()+reportSortingBean.getSupply16());
		    	bean.setSupply17(bean.getSupply17()+reportSortingBean.getSupply17());
		    	bean.setSupply18(bean.getSupply18()+reportSortingBean.getSupply18());
		    	bean.setSupply19(bean.getSupply19()+reportSortingBean.getSupply19());
		    	bean.setSupply20(bean.getSupply20()+reportSortingBean.getSupply20());
		    	bean.setSupply21(bean.getSupply21()+reportSortingBean.getSupply21());
		    	bean.setSupply22(bean.getSupply22()+reportSortingBean.getSupply22());
		    	bean.setSupply23(bean.getSupply23()+reportSortingBean.getSupply23());
		    	bean.setSupply24(bean.getSupply24()+reportSortingBean.getSupply24());
		    	bean.setSupply0(bean.getNoread0()+reportSortingBean.getNoread0());
		    	bean.setNoread1(bean.getNoread1()+reportSortingBean.getNoread1());
		    	bean.setNoread2(bean.getNoread2()+reportSortingBean.getNoread2());
		    	bean.setNoread3(bean.getNoread3()+reportSortingBean.getNoread3());
		    	bean.setNoread4(bean.getNoread4()+reportSortingBean.getNoread4());
		    	bean.setNoread5(bean.getNoread5()+reportSortingBean.getNoread5());
		    	bean.setNoread6(bean.getNoread6()+reportSortingBean.getNoread6());
		    	bean.setNoread7(bean.getNoread7()+reportSortingBean.getNoread7());
		    	bean.setNoread8(bean.getNoread8()+reportSortingBean.getNoread8());
		    	bean.setNoread9(bean.getNoread9()+reportSortingBean.getNoread9());
		    	bean.setNoread10(bean.getNoread10()+reportSortingBean.getNoread10());
		    	bean.setNoread11(bean.getNoread11()+reportSortingBean.getNoread11());
		    	bean.setNoread12(bean.getNoread12()+reportSortingBean.getNoread12());
		    	bean.setNoread13(bean.getNoread13()+reportSortingBean.getNoread13());
		    	bean.setNoread14(bean.getNoread14()+reportSortingBean.getNoread14());
		    	bean.setNoread15(bean.getNoread15()+reportSortingBean.getNoread15());
		    	bean.setNoread16(bean.getNoread16()+reportSortingBean.getNoread16());
		    	bean.setNoread17(bean.getNoread17()+reportSortingBean.getNoread17());
		    	bean.setNoread18(bean.getNoread18()+reportSortingBean.getNoread18());
		    	bean.setNoread19(bean.getNoread19()+reportSortingBean.getNoread19());
		    	bean.setNoread20(bean.getNoread20()+reportSortingBean.getNoread20());
		    	bean.setNoread21(bean.getNoread21()+reportSortingBean.getNoread21());
		    	bean.setNoread22(bean.getNoread22()+reportSortingBean.getNoread22());
		    	bean.setNoread23(bean.getNoread23()+reportSortingBean.getNoread23());
		    	bean.setNoread24(bean.getNoread24()+reportSortingBean.getNoread24());
//		    	logger.error("更新操作后："+bean.toString());
		    	insert_or_update = 1;//更新操作
		    }
		    //实体类 转换成sql语句，进行更新、插入操作
		    String sql = StatisticsSortingUtils.formatReportSortingSql(bean,insert_or_update);
		    listSqls.add(sql);
		} 
		//判断list是否为空
		if(listSqls != null && listSqls.size() > 0){
			//插入统计表中
			statisticsServiceImpl.insertReportTable(listSqls);
			//初始化统计进度表实例
			StatisticsProgressBean bean = new StatisticsProgressBean();
			bean.setCurrent_progress(f_recno);//统计进度
			//汇总量统计
			if(type == StatisticsType.STATISTICS_SORTING.getValue()){
				bean.setStatistics_name(StatisticsType.STATISTICS_SORTING.getMessage());//统计存储的表
			}
			//批次量统计
			if(type == StatisticsType.STATISTICS_BATCH.getValue()){
				bean.setStatistics_name(StatisticsType.STATISTICS_BATCH.getMessage());//统计存储的表
			}
			bean.setStatistics_table(statistics_table);//统计查询的表
			bean.setSt_type(type);//统计类型 0：小车量；1，格口；2，分钟；3，扫描；4，站点；5，汇总；  6，导入台；7，格口封包；8，汇总封包；9，批次统计；10，批次封包；
			//统计入库完成，添加进度记录到进度记录表中
			if(!isUpdate){
				//判断是否是第一次统计，则新增，否则更新
				String sql = StatisticsUtils.formatInsertStatisticsProSql(bean);
				if(!statisticsServiceImpl.insertStatisticsProgress(sql))
					logger.error("插入统计进度记录表失败："+sql);
			} else {
				String sql = StatisticsUtils.formatUpdateStatisticsProSql(bean);
				if(!statisticsServiceImpl.updateStatisticsProgress(sql))
					logger.error("更新统计进度记录表失败："+sql);
			}
		} else {
			logger.info("本次统计需要数据库操作的记录条数为0,故不需要下一步操作,统计类型：汇总、批次量统计,统计方式(0,批次;1,汇总)："+sum_type);
		}
		return false;
	}
	
	/**
	 * box相关的统计
	 * @param f_recno
	 * @param count 统计数量
	 * @param type 8，汇总封包；10，批次封包；
	 */
	public boolean doBoxStatistics(long f_recno,int count,int type){
		//目前封包类统计只做：汇总封包、批次封包 统计
		List<StatisticsBoxBean> list = statisticsServiceImpl.findBoxList(f_recno, count);
		if(list == null || list.size() == 0) {
			logger.info("封包类统计：数量为0，type="+type);
			return false;
		}
		logger.info("封包类统计：数量为:"+list.size()+"，type="+type);
		//判断进度记录表中是更新还是新增
		boolean isUpdate = true;
		if(f_recno == 0)
			isUpdate = false;
		//获取查询出来的最大主键（查询默认按主键增序排列）
		f_recno = list.get(list.size()-1).getF_recno();
		//统计模式 0,批次；1，小时
		int sum_type = 1;
		//key：龙门架id+“-”+层级id+“-”+时间戳（秒）与60的商，即转换为分钟，value：是扫描量统计的实体类
		Map<String,ReportBoxBean> map = StatisticsSortingUtils.dealBoxStatisticsSqlLists(list,sum_type);
		List<String> listSqls = new ArrayList<String>();
		//遍历map，获取统计完成的数据实体类
		Set<Entry<String, ReportBoxBean>> entrySet=map.entrySet();  
		for(Entry<String, ReportBoxBean> entry:entrySet){  
			ReportBoxBean bean =entry.getValue();  
		    //0,insert;1,update
		    int insert_or_update = 0;
		    //组装查询是否存在的sql语句
		    String sqls = StatisticsUtils.formatIfExitsReportTable(StatisticsType.STATISTICS_SORTING_BOX.getMessage(), 
		    		bean.getReport_date(), 0, 0, 0, sum_type,type);
		    //判断数据库中是否存在该时间段、类型的统计
		    if(statisticsServiceImpl.findIfExitsInReportTable(sqls)){
		    	//组装查询存在的sql语句
				String sql = StatisticsUtils.formatFindReportSql(null, SqlUtils.formatDateToStr(bean.getReport_date(), 
						StatisticsConstant.YYYYMMDDHHMM), 0, 0, 0, sum_type, type);
		    	//存在，则查询出存在的记录，做和操作；
				ReportSortingBean reportSortingBean = statisticsServiceImpl.findReportSortingBean(sql);
		    	bean.setF_recno(reportSortingBean.getF_recno());
		    	bean.setBox_sum(bean.getBox_sum()+reportSortingBean.getBox_sum());
		    	insert_or_update = 1;//更新操作
		    }
		    //实体类 转换成sql语句，进行更新、插入操作
		    String sql = StatisticsSortingUtils.formatReportBoxSql(bean,insert_or_update);
		    listSqls.add(sql);
		} 
		//判断list是否为空
		if(listSqls != null && listSqls.size() > 0){
			//插入统计表中
			statisticsServiceImpl.insertReportTable(listSqls);
			//初始化统计进度表实例
			StatisticsProgressBean bean = new StatisticsProgressBean();
			bean.setCurrent_progress(f_recno);//统计进度
			bean.setStatistics_name(StatisticsType.STATISTICS_BATCH_BOX.getMessage());//统计存储的表
			bean.setStatistics_table(StatisticsTempType.STATISTICS_TEMP_BOX.getMessage());//统计查询的表
			bean.setSt_type(type);//统计类型 0：小车量；1，格口；2，分钟；3，扫描；4，站点；5，汇总；  6，导入台；7，格口封包；8，汇总封包；9，批次统计；10，批次封包；
			//统计入库完成，添加进度记录到进度记录表中
			if(!isUpdate){
				//判断是否是第一次统计，则新增，否则更新
				String sql = StatisticsUtils.formatInsertStatisticsProSql(bean);
				if(!statisticsServiceImpl.insertStatisticsProgress(sql))
					logger.error("插入统计进度记录表失败："+sql);
			} else {
				String sql = StatisticsUtils.formatUpdateStatisticsProSql(bean);
				if(!statisticsServiceImpl.updateStatisticsProgress(sql))
					logger.error("更新统计进度记录表失败："+sql);
			}
		} else {
			logger.info("本次统计需要数据库操作的记录条数为0,故不需要下一步操作,统计类型：封包量统计,统计方式(0,批次;1,汇总)："+sum_type);
		}
		return true;
	}
}