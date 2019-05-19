package cn.seagen.base.timertasks;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.seagen.base.bean.ScanSortingExitsBean;
import cn.seagen.base.constant.DbPartitionConstant;
import cn.seagen.base.service.DbPartitionService;
import cn.seagen.base.utils.DateUtils;
import cn.seagen.base.utils.SqlUtils;

@Service
public class TaskDbPartition {
	
	@Resource
	private DbPartitionService dbPartitionServiceImpl;
	
	private static Logger logger = LogManager.getLogger(TaskDbPartition.class);
	@Scheduled(initialDelay=20*1000,fixedDelay = 5*60*1000)   //服务启动后20s执行，然后执行后5分钟在执行
	public void timerTask(){
		logger.info("开始执行数据库定时任务：判断业务表是否需要分表、删除分表.");
		//开始时间
		long begintime = System.currentTimeMillis();
		//当前月
		String month = DateUtils.findYearMonth(1);
		//业务类型 0，扫描；1，分拣
		int type = 0;
		//判断是否存在分表，以及判断是否存在分表创建失败的情况
		//数据库查询当月扫描的分表情况：与配置的分表数量进行判断，判断是否存在创建失败的分表，失败则重新创建
		dealPartitionTalbes(month, type);
		type = 1;
		//数据库查询当月分拣的分表情况：与配置的分表数量进行判断，判断是否存在创建失败的分表，失败则重新创建
		dealPartitionTalbes(month, type);
		//获取当前的天
		//当前天
		int days = DateUtils.findDay();
		//判断是否需要新建下个月的分表
		if(days > 26){
			//每个月月末26号时判断是否需要创建下个月的分表
			String nextMonth = DateUtils.findYearMonth(2);
			type = 0;
			//判断是否存在分表，以及判断是否存在分表创建失败的情况
			//数据库查询下个月扫描的分表情况：与配置的分表数量进行判断，判断是否存在创建失败的分表，失败则重新创建
			dealPartitionTalbes(nextMonth, type);
			type = 1;
			//数据库查询当月分拣的分表情况：与配置的分表数量进行判断，判断是否存在创建失败的分表，失败则重新创建
			dealPartitionTalbes(nextMonth, type);
			
			//判断是否需要删除上上个月的分表
			String lastMonth = DateUtils.findYearMonth(-1);
			deletePartitionTables(lastMonth);
		}
		
		//判断数据库中实际存在分表（可能因修改电脑时间导致多创建的分表），
		//可能分表记录表中无记录，但实际存在，判断该分表属于过去还是未来，删除过去（与当前月相差3个月以上），保留未来
		//获取数据库中所有分表的sql语句：select table_name from information_schema.tables where table_schema='seagen_wms' 
		// and table_type='base table' AND TABLE_NAME LIKE '%s%_0%'
		//获取数据库中所有分表表名，通过对年月的格式转化，判断是否存在“历史”数据（可能因修改电脑时间导致多创建的分表，与当前时间相差较久）
		//当月份相差（过去三个月以上）则删除该分表；未来的分表不作处理
		List<String> listTables = dbPartitionServiceImpl.findPartitionTables();
		List<String> listOldTables = new ArrayList<String>();
		if(listTables != null && listTables.size() > 0){
			for(String tableName : listTables){
				String mon = SqlUtils.getMonthFromTableName(tableName);
				try{
					//获取分表中月份与当前月的差
					int num = Integer.parseInt(month) - Integer.parseInt(mon);
					//大于三个月时（即已经过去四个月），将删除该分表
					if(num > 3) {
						listOldTables.add(tableName);
					}
				} catch(Exception e){
					logger.error("检查是否存在过去较久分表时出错",e);
				}
			}
		}
		//删除过去较久的分表
		if(listOldTables != null && listOldTables.size() > 0){
			for(String tableName : listOldTables){
				//先删除实际的分表
				if(!dbPartitionServiceImpl.deletePartitionTables(tableName)){
					logger.error("删除过去较久分表失败："+tableName);
				} else {
					logger.info("删除过去较久分表成功："+tableName);
					//再删除分表记录表中的记录
					if(!dbPartitionServiceImpl.deleteScanSortingExitsTable(tableName)){
						logger.error("删除过去较久分表记录失败："+tableName);
					} else {
						logger.info("删除过去较久分表记录成功："+tableName);
					}
				}
			}
		}
		//获取存在的分表集合
		listTables = dbPartitionServiceImpl.findPartitionTables();
		List<String> nowTables = new ArrayList<String>();
		for(String tb:listTables){
			if(tb.startsWith("scan_"))
				nowTables.add(tb);
		}
		//记录存在的扫描类分表
		DbPartitionConstant.scanPartitionList = new ArrayList<String>(nowTables);
		nowTables = new ArrayList<String>();
		for(String tb:listTables){
			if(tb.startsWith("sorting_"))
				nowTables.add(tb);
		}
		//记录存在的分拣类分表
		DbPartitionConstant.sortingPartitionList = new ArrayList<String>(nowTables);
		long runtime = System.currentTimeMillis() - begintime;
		logger.info("结束执行数据库定时任务：判断业务表是否需要分表、删除分表.共耗时：("+runtime+")");
	}
	/**
	 * 创建分表、添加分表记录到记录表中，判断是否需要添加，判断添加的分表数量是否正常
	 * @param month
	 * @param type 业务类型 0，扫描；1，分拣
	 */
	public void dealPartitionTalbes(String month,int type){
		//双重验证
		//1,判断业务分表记录表中的分表数量是否正常
		List<ScanSortingExitsBean> listScanSortingExitsBeans = dbPartitionServiceImpl.findTableExits(month, type);
		if(listScanSortingExitsBeans != null && listScanSortingExitsBeans.size() != DbPartitionConstant.PARTITION_TALBE_NUM){
			logger.info(month+"月分表异常，正常数量为："+DbPartitionConstant.PARTITION_TALBE_NUM+",实际数量为："+listScanSortingExitsBeans.size());
			List<String> notExitsTables = SqlUtils.findNotExitsPartitionTable(listScanSortingExitsBeans, month, type);
			for(String name : notExitsTables){
				//判断实际是否存在该名称的分表，存在则将该表名插入到scan_sorting_exits_tables中，否则新建该分表，同时插入数据到scan_sorting_exits_tables中
				if(dbPartitionServiceImpl.findTableIsExits(name)){
					ScanSortingExitsBean scanSortingExitsBean = new ScanSortingExitsBean();
					scanSortingExitsBean.setTable_name(name);
					scanSortingExitsBean.setMonth(SqlUtils.getMonthFromTableName(name));
					scanSortingExitsBean.setTable_type(type);
					dbPartitionServiceImpl.insertScanSortingExitsTable(scanSortingExitsBean);
				} else {
					//新建分表，同时插入数据到scan_sorting_exits_tables中
					dbPartitionServiceImpl.createTablePartitionByName(name, type);
				}
			}
		}
		//2,判断业务分表实际情况是否正常
		//先获取月份对应的分表名称，然后根据名称去数据库中查询，判断是否存在该名称的分表，不存在则新建该分表，同时插入数据到scan_sorting_exits_tables中
		List<String> tables = SqlUtils.getCreateTablePartitionNames(month, type);
		for(String name : tables){
			if(!dbPartitionServiceImpl.findTableIsExits(name))
				dbPartitionServiceImpl.createTablePartitionByName(name, type);
		}
		logger.info("业务分表正常："+month+",type="+type);
	}
	
	/**
	 * 删除业务分表
	 * @param month 201801
	 */
	public void deletePartitionTables(String month) {
		//业务类型 0，扫描；1，分拣
		int type = 0;
		List<String> scanTables = SqlUtils.getCreateTablePartitionNames(month, type);
		List<String> scanDelTables = new ArrayList<String>();
		for(String tableName : scanTables) {
			//先判断该分表是否实际存在，存在则删除
			if(dbPartitionServiceImpl.findTableIsExits(tableName)){
				scanDelTables.add(tableName);
			}
		}
		for(String tableName : scanDelTables){
			//先删除实际的分表
			if(!dbPartitionServiceImpl.deletePartitionTables(tableName)){
				logger.error("删除分表失败："+tableName);
			} else {
				logger.info("删除分表成功："+tableName);
				//再删除分表记录表中的记录
				if(!dbPartitionServiceImpl.deleteScanSortingExitsTable(tableName)){
					logger.error("删除分表记录失败："+tableName);
				} else {
					logger.info("删除分表记录成功："+tableName);
				}
			}
		}
		type = 1;
		List<String> sortTables = SqlUtils.getCreateTablePartitionNames(month, type);
		List<String> sortDelTables = new ArrayList<String>();
		for(String tableName : sortTables) {
			//先判断该分表是否实际存在，存在则删除
			if(dbPartitionServiceImpl.findTableIsExits(tableName)){
				sortDelTables.add(tableName);
			}
		}
		for(String tableName : sortDelTables){
			//先删除实际的分表
			if(!dbPartitionServiceImpl.deletePartitionTables(tableName)){
				logger.error("删除分表失败："+tableName);
			} else {
				logger.info("删除分表成功："+tableName);
				//再删除分表记录表中的记录
				if(!dbPartitionServiceImpl.deleteScanSortingExitsTable(tableName)){
					logger.error("删除分表记录失败："+tableName);
				} else {
					logger.info("删除分表记录成功："+tableName);
				}
			}
		}
	}
	
}
