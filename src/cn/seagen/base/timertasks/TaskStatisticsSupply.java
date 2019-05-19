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

import cn.seagen.base.bean.ReportSupplyBean;
import cn.seagen.base.bean.StatisticsProgressBean;
import cn.seagen.base.bean.StatisticsSortBean;
import cn.seagen.base.constant.StatisticsConstant;
import cn.seagen.base.enums.StatisticsType;
import cn.seagen.base.service.DbPartitionService;
import cn.seagen.base.service.StatisticsService;
import cn.seagen.base.utils.SqlUtils;
import cn.seagen.base.utils.StatisticsSupplyUtils;
import cn.seagen.base.utils.StatisticsUtils;
/**
 * 
 *导入台统计定时任务
 */
@Service
public class TaskStatisticsSupply {

	@Resource
	private StatisticsService statisticsServiceImpl;
	@Resource
	private DbPartitionService dbPartitionServiceImpl;
	
	private static Logger logger = LogManager.getLogger(TaskStatisticsSupply.class);
	
	@Scheduled(initialDelay=2*60*1000,fixedDelay = 5*60*1000)   //服务启动后2分钟执行，然后执行后5分钟在执行
	public void timerTask(){
		logger.info("开始执行统计类定时任务：tableName="+StatisticsType.STATISTICS_SUPPLY.getMessage()
				+",type="+StatisticsType.STATISTICS_SUPPLY.getValue());
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
			StatisticsProgressBean statisticsProgressBean = statisticsServiceImpl.getStatisticsProgressBean(StatisticsType.STATISTICS_SUPPLY.getValue(),table);
			if(statisticsProgressBean != null && statisticsProgressBean.getCurrent_progress() != 0)
				progress = statisticsProgressBean.getCurrent_progress();
			//执行导入台统计：从sorting_temp表中查询数据，插入到report_supply表中
			//6，导入台；
			doSortStatistics(progress,StatisticsConstant.STATISTICS_MAX_COUNT, StatisticsType.STATISTICS_SUPPLY.getValue(),table);
		}
		//结束时间
		long runtime = System.currentTimeMillis() - begintime;
		logger.info("结束执行统计类定时任务："+StatisticsConstant.logKey[StatisticsType.STATISTICS_SUPPLY.getValue()]
				+",tableName="+StatisticsType.STATISTICS_SUPPLY.getMessage()
				+",type="+StatisticsType.STATISTICS_SUPPLY.getValue()+".共耗时：("+runtime+")");
	}
	
	/**
	 * sorting相关的统计
	 * @param f_recno
	 * @param count 统计数量
	 * @param type 0：小车量；1，格口；2，分钟；4，站点；5，汇总；6，导入台；9，批次统计；
	 * @param table 统计表名
	 */
	public boolean doSortStatistics(long f_recno,int count,int type,String table){
		//目前分拣类统计只做：分钟、汇总、导入台、批次 统计
		List<StatisticsSortBean> list = statisticsServiceImpl.findSortList(f_recno, count,table);
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
		//key：导入台id+“-”+层级id+“-”+时间戳（秒）与60的商（即转换为分钟），value：是导入台量统计的实体类
		Map<String,ReportSupplyBean> map = StatisticsSupplyUtils.dealSupplyStatisticsSqlLists(list);
		List<String> listSqls = new ArrayList<String>();
		//遍历map，获取统计完成的数据实体类
		Set<Entry<String, ReportSupplyBean>> entrySet=map.entrySet();  
		for(Entry<String, ReportSupplyBean> entry:entrySet){  
			ReportSupplyBean bean =entry.getValue();  
		    //0,insert;1,update
		    int insert_or_update = 0;
		    //组装查询是否存在的sql语句
		    String sqls = StatisticsUtils.formatIfExitsReportTable(StatisticsType.STATISTICS_SUPPLY.getMessage(), 
		    		bean.getReport_date(), bean.getLayer_id(), bean.getSupply_id(), 0, 0, StatisticsType.STATISTICS_SUPPLY.getValue());
		    //判断数据库中是否存在该时间段、类型的统计
		    if(statisticsServiceImpl.findIfExitsInReportTable(sqls)){
		    	//组装查询存在的sql语句
				String sql = StatisticsUtils.formatFindReportSql(null, SqlUtils.formatDateToStr(bean.getReport_date(), 
						StatisticsConstant.YYYYMMDDHHMM), bean.getLayer_id(), bean.getSupply_id(), 0, 0, StatisticsType.STATISTICS_SUPPLY.getValue());
		    	//存在，则查询出存在的记录，做和操作；
				ReportSupplyBean reportSupplyBean = statisticsServiceImpl.findReportSupplyBean(sql);
		    	bean.setF_recno(reportSupplyBean.getF_recno());
		    	bean.setLayer_id(reportSupplyBean.getLayer_id());
		    	bean.setAll_sum(bean.getAll_sum()+reportSupplyBean.getAll_sum());
		    	bean.setSuccess_sum(bean.getSuccess_sum()+reportSupplyBean.getSuccess_sum());
		    	bean.setErr_sum(bean.getErr_sum()+reportSupplyBean.getErr_sum());
		    	bean.setNo_chute(bean.getNo_chute()+reportSupplyBean.getNo_chute());
		    	bean.setNo_data(bean.getNo_data()+reportSupplyBean.getNo_data());
		    	bean.setNo_reade(bean.getNo_reade()+reportSupplyBean.getNo_reade());
		    	bean.setCancel_sum(bean.getCancel_sum()+reportSupplyBean.getCancel_sum());
		    	bean.setMax_cycles(bean.getMax_cycles()+reportSupplyBean.getMax_cycles());
		    	bean.setErr_chute(bean.getErr_chute()+reportSupplyBean.getErr_chute());
		    	bean.setMore_data(bean.getMore_data()+reportSupplyBean.getMore_data());
		    	bean.setLost_data(bean.getLost_data()+reportSupplyBean.getLost_data());
		    	insert_or_update = 1;//更新操作
		    }else{
		    	//连续60分钟峰值(不包含当前分钟)，整合sql语句时，查询数据库获取当前分钟之前60分钟内的生产数量做峰值
			    int six_spike = statisticsServiceImpl.findReportSixSpike(StatisticsSupplyUtils.formatReportSixSpikeSql(bean.getReport_date(),bean.getSupply_id(),bean.getLayer_id(), type));
			    bean.setSix_spike(six_spike);
		    }
		    
		    //实体类 转换成sql语句，进行更新、插入操作
		    String sql = StatisticsSupplyUtils.formatReportSupplySql(bean,insert_or_update);
		    listSqls.add(sql);
		} 
		//判断list是否为空
		if(listSqls != null && listSqls.size() > 0){
			//插入统计表中
			statisticsServiceImpl.insertReportTable(listSqls);
			//初始化统计进度表实例
			StatisticsProgressBean bean = new StatisticsProgressBean();
			bean.setCurrent_progress(f_recno);//统计进度
			bean.setStatistics_name(StatisticsType.STATISTICS_SUPPLY.getMessage());//统计存储的表
			bean.setStatistics_table(table);//统计查询的表
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
			logger.info("本次统计需要数据库操作的记录条数为0,故不需要下一步操作,统计类型：导入台量统计");
		}
		return false;
	}
}