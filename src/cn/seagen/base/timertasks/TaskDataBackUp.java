package cn.seagen.base.timertasks;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.seagen.base.constant.BackUpConstant;
import cn.seagen.base.constant.DbDataBackUpConstant;
import cn.seagen.base.service.DbDataBackUpService;
import cn.seagen.base.service.StatisticsService;
import cn.seagen.base.utils.SqlUtils;

import com.alibaba.druid.util.StringUtils;
/**
 *数据备份实现类
 */
@Service
public class TaskDataBackUp {
	
	private static Logger logger = LogManager.getLogger(TaskDataBackUp.class);
	
	@Resource
	private DbDataBackUpService dbDataBackUpServiceImpl;
	@Resource
	private StatisticsService statisticsServiceImpl;
	
	@Scheduled(initialDelay=1*60*1000,fixedDelay = 1*60*1000)   //服务启动后60秒钟执行，然后执行后1分钟在执行
	public void timerTask(){
		logger.info("开始执行数据备份定时任务");
		//开始时间
		long begintime = System.currentTimeMillis();
		
		//业务实现
		doDataBackUpWork();
		
		//结束时间
		long runtime = System.currentTimeMillis() - begintime;
		logger.info("结束执行数据备份定时任务，共耗时：("+runtime+")");
	}
	
	/**
	 * 备份业务实现
	 */
	public void doDataBackUpWork() {
		//box_temp 箱号缩略表
		doDataBackUp("box_temp", BackUpConstant.backupMainData, DbDataBackUpConstant.DB_BACK_DEL_COUNT, 0);
		//scan_temp 扫描缩略表
		doDataBackUp("scan_temp", BackUpConstant.backupMainData, DbDataBackUpConstant.DB_BACK_DEL_COUNT, 1);
		//sorting_temp 分拣缩略表
		doDataBackUp("sorting_temp", BackUpConstant.backupMainData, DbDataBackUpConstant.DB_BACK_DEL_COUNT, 2);
		//box 箱号表
		doDataBackUp("box", BackUpConstant.backupGenralData, DbDataBackUpConstant.DB_BACK_DEL_COUNT, 3);
		//waybill 原始运单表
		doDataBackUp("waybill", BackUpConstant.backupGenralData, DbDataBackUpConstant.DB_BACK_DEL_COUNT, 3);
		//printer_data 打印信息表
		doDataBackUp("printer_data", BackUpConstant.backupGenralData, DbDataBackUpConstant.DB_BACK_DEL_COUNT, 3);
		//system_event 系统事件表
		doDataBackUp("system_event", BackUpConstant.backupGenralData, DbDataBackUpConstant.DB_BACK_DEL_COUNT, 3);
		//system_errlog 系统错误日志表
		doDataBackUp("system_errlog", BackUpConstant.backupGenralData, DbDataBackUpConstant.DB_BACK_DEL_COUNT, 3);
		//system_log 系统日志表
		doDataBackUp("system_log", BackUpConstant.backupGenralData, DbDataBackUpConstant.DB_BACK_DEL_COUNT, 3);
		// 报表留默认1年,这里一个月统计算为31天，为了简单处理，格口、小车的统计采用直接查询，数据存储box_temp/scan_temp/sorting_temp
		//test
//		//report_minute 分钟量统计表
//		doDataBackUp("report_minute", BackUpConstant.backupGenralData, DbDataBackUpConstant.DB_BACK_DEL_COUNT, 3);
//		//report_scan 扫描量统计表
//		doDataBackUp("report_scan", BackUpConstant.backupGenralData, DbDataBackUpConstant.DB_BACK_DEL_COUNT, 3);
//		//report_sorting 分拣量统计表：批次、小时统计
//		doDataBackUp("report_sorting", BackUpConstant.backupGenralData, DbDataBackUpConstant.DB_BACK_DEL_COUNT, 3);
//		//report_supply 导入台量统计表
//		doDataBackUp("report_supply", BackUpConstant.backupGenralData, DbDataBackUpConstant.DB_BACK_DEL_COUNT, 3);
		//report_minute 分钟量统计表
		doDataBackUp("report_minute", BackUpConstant.backupReport*31, DbDataBackUpConstant.DB_BACK_DEL_COUNT, 3);
		//report_scan 扫描量统计表
		doDataBackUp("report_scan", BackUpConstant.backupReport*31, DbDataBackUpConstant.DB_BACK_DEL_COUNT, 3);
		//report_sorting 分拣量统计表：批次、小时统计
		doDataBackUp("report_sorting", BackUpConstant.backupReport*31, DbDataBackUpConstant.DB_BACK_DEL_COUNT, 3);
		//report_supply 导入台量统计表
		doDataBackUp("report_supply", BackUpConstant.backupReport*31, DbDataBackUpConstant.DB_BACK_DEL_COUNT, 3);
	}
	
	/**
	 * 根据表名、保留天数，进行数据的删除
	 * @param tableName 表名
	 * @param holdday 数据保留天数
	 * @param delCount 删除数量
	 * @param type  类型：0，box_temp;1,scan_temp;2,sorting_temp;
	 * 			3,box,waybill,printer_data,system_event,system_errlog,
	 * 			system_log,report_minute,report_scan,report_sorting,report_supply
	 * @return
	 */
	public boolean doDataBackUp(String tableName,int holdday,int delCount,int type){
		logger.info("开始执行<"+tableName+">数据备份定时任务");
		//开始时间
		long begintime = System.currentTimeMillis();
		
//		//组装查询表中 总的数据量的sql语句
//		String sql = SqlUtils.formatFindTableDataCount(tableName);
//		if(StringUtils.isEmpty(sql)){
//			logger.info("doDataBackUp组装查询表中 总的数据量的sql语句失败,终止执行备份删除操作！");
//			return false;
//		}
//		//判断表中数据量大小（最低保留数量，可配置）
//		int tableDataCount = dbDataBackUpServiceImpl.findTableDataCount(sql);
//		if(tableDataCount == 0){
//			logger.info("doDataBackUp数据库中该表："+tableName+"总的数据量为0,终止执行备份删除操作！"+sql);
//			return false;
//		}
//		//判断表中存在的数据量是否够删除
//		if(tableDataCount < delCount) {
//			logger.info("doDataBackUp数据库中该表："+tableName+"总的数据量为<"+tableDataCount+">，小于实际需要删除的数量<"+delCount+">,终止执行备份删除操作！");
//			return false;
//		}
		String sql = "";
		//获取保存在静态map中该表的可删除数量
		int canDeleteCount = 0;
		//判断是否存在该表的记录
		if(DbDataBackUpConstant.dataBackCanDelCount.containsKey(tableName))
			canDeleteCount = DbDataBackUpConstant.dataBackCanDelCount.get(tableName);
		//判断可删除数量是否大于每次备份的数量限制，否则需要重新获取
		if(canDeleteCount < delCount){
			//组装查询可删除数量的sql语句
			sql = SqlUtils.formatFindCanDeleteCount(tableName, holdday, type);
			if(StringUtils.isEmpty(sql)){
				logger.info("doDataBackUp组装获取可删除数量的sql语句失败,终止执行备份删除操作！");
				return false;
			}
			
			//判断是否存在数据可以删除，获取可删除数量
			canDeleteCount = dbDataBackUpServiceImpl.findCanDeleteCount(sql);
			if(canDeleteCount == 0){
				logger.info("doDataBackUp数据库中该表："+tableName+"可删除数量为0,终止执行备份删除操作！"+sql);
				return false;
			}
			
			//判断表中存在的数据量是否够删除
			if(canDeleteCount <= delCount) {
				logger.info("doDataBackUp数据库中该表："+tableName+"可删除的数量为<"+canDeleteCount+">，小于实际需要删除的数量<"+delCount+">,终止执行备份删除操作！");
				return false;
			}
		}		
		
		//将表中可删除的数量保存在map中，下次备份时直接取值，在于每次备份的数量限制进行判断，小于则重新获取，大于则直接进行删除
		//执行删除前赋值一次，避免删除失败现象
		DbDataBackUpConstant.dataBackCanDelCount.put(tableName, canDeleteCount);
		
		//组装删除语句
		sql = SqlUtils.formatDeleteTableData(tableName, delCount);
		if(StringUtils.isEmpty(sql)){
			logger.info("doDataBackUp组装删除表中数据的sql语句失败,终止执行备份删除操作！");
			return false;
		}
		
		//执行删除操作
		if(!dbDataBackUpServiceImpl.deleteTableData(sql)){
			logger.info("doDataBackUp删除表中数据失败,终止执行备份删除操作！"+sql);
			return false;
		}
		//将表中可删除的数量保存在map中，下次备份时直接取值，在于每次备份的数量限制进行判断，小于则重新获取，大于则直接进行删除
		DbDataBackUpConstant.dataBackCanDelCount.put(tableName, canDeleteCount-delCount);
		
		logger.info("doDataBackUp删除数据表<"+tableName+">中【"+delCount+"】条数据成功。");
		//结束时间
		long runtime = System.currentTimeMillis() - begintime;
		logger.info("结束执行<"+tableName+">数据备份定时任务，共耗时：("+runtime+")");
		return false;
	}
	
}