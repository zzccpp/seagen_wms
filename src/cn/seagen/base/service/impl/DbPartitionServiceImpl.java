package cn.seagen.base.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.seagen.base.bean.ScanSortingExitsBean;
import cn.seagen.base.dao.DbPartitionDao;
import cn.seagen.base.service.DbPartitionService;
import cn.seagen.base.utils.SqlUtils;
@Service
public class DbPartitionServiceImpl implements DbPartitionService{
	private Logger logger = LogManager.getLogger(DbPartitionService.class.getName());
	@Resource
	private DbPartitionDao dbPartitionDaoImpl;

	public DbPartitionServiceImpl() {
		super();
	}

	@Override
	public List<ScanSortingExitsBean> findTableList() {
		List<ScanSortingExitsBean> list = dbPartitionDaoImpl.findTableList();  
		logger.info("获取扫描、分拣分表记录表中数据：共<"+list.size()+">条");
		return list;
	}

	@Override
	public boolean createTablePartition(String month,int type) {
		List<String> list = SqlUtils.getCreateTablePartitionSql(month, type);
		if(list == null)
			return false;
		for(String sql : list){
			if(dbPartitionDaoImpl.createTablePartition(sql)){
				logger.info("创建分表成功："+sql);
				ScanSortingExitsBean scanSortingExitsBean = SqlUtils.getScanSortingExitsTableName(sql, type);
				if(scanSortingExitsBean != null){
					//判断触发器是否存在，存在则删除
					dbPartitionDaoImpl.findTriggerExits(SqlUtils.findTriggerExitsSql(scanSortingExitsBean.getTable_name()));
					if(dbPartitionDaoImpl.createTrigger(SqlUtils.getCreateTriggerSql(scanSortingExitsBean.getTable_name(), type))) {
						logger.info("创建触发器成功：");
					} else {
						logger.error("创建触发器失败：");
					}
					scanSortingExitsBean.setMonth(month);
					scanSortingExitsBean.setTable_type(type);
					if(dbPartitionDaoImpl.insertScanSortingExitsTable(scanSortingExitsBean)){
						logger.info("插入scan_sorting_exits_tables成功："+scanSortingExitsBean.toString());
						return true;
					}
					logger.error("插入scan_sorting_exits_tables失败："+scanSortingExitsBean.toString());
				}
			} else {
				logger.error("创建分表失败："+sql);
			}
		}
		
		return true;
	}
	
	@Override
	public List<ScanSortingExitsBean> findTableExits(String month, int type) {
		return dbPartitionDaoImpl.findTableExits(month, type);
	}

	@Override
	public boolean createTablePartitionByName(String tableName,int type) {
		String sql = SqlUtils.getCreateTablePartitionSqlByTableName(tableName, type);
		if(dbPartitionDaoImpl.createTablePartition(sql)){
			logger.info("创建分表成功："+sql);
			//判断触发器是否存在，存在则删除
			dbPartitionDaoImpl.findTriggerExits(SqlUtils.findTriggerExitsSql(tableName));
			
			if(dbPartitionDaoImpl.createTrigger(SqlUtils.getCreateTriggerSql(tableName, type))) {
				logger.info("创建触发器成功：");
			} else {
				logger.error("创建触发器失败：");
			}
			ScanSortingExitsBean scanSortingExitsBean = SqlUtils.getScanSortingExitsTableName(sql, type);
			if(scanSortingExitsBean != null){
				scanSortingExitsBean.setMonth(SqlUtils.getMonthFromTableName(tableName));
				scanSortingExitsBean.setTable_type(type);
				if(dbPartitionDaoImpl.insertScanSortingExitsTable(scanSortingExitsBean)){
					logger.info("插入scan_sorting_exits_tables成功："+scanSortingExitsBean.toString());
					return true;
				}
				logger.error("插入scan_sorting_exits_tables失败："+scanSortingExitsBean.toString());
			}
		}
		return false;
	}

	@Override
	public boolean findTableIsExits(String tableName) {
		return dbPartitionDaoImpl.findTableIsExits(tableName);
	}

	@Override
	public boolean insertScanSortingExitsTable(
			ScanSortingExitsBean scanSortingExitsBean) {
		if(dbPartitionDaoImpl.insertScanSortingExitsTable(scanSortingExitsBean)){
			logger.info("插入scan_sorting_exits_tables成功："+scanSortingExitsBean.toString());
			return true;
		}
		logger.error("插入scan_sorting_exits_tables失败："+scanSortingExitsBean.toString());
		return false;
	}

	@Override
	public boolean deletePartitionTables(List<String> tableNames) {
		for(String tableName : tableNames){
			if(dbPartitionDaoImpl.deletePartitionTables(tableName)){
				logger.info("删除分表成功："+tableName);
				if(dbPartitionDaoImpl.deleteScanSortingExitsTable(tableName)){
					logger.info("删除分表记录成功："+tableName);
				} else {
					logger.error("删除分表记录失败："+tableName);
				}
			} else {
				logger.error("删除分表失败："+tableName);
			}
		}
		return true;
	}

	@Override
	public boolean deleteScanSortingExitsTable(String tableName) {
		return dbPartitionDaoImpl.deleteScanSortingExitsTable(tableName);
	}

	@Override
	public boolean deletePartitionTables(String tableName) {
		if(dbPartitionDaoImpl.deletePartitionTables(tableName)){
			//判断触发器是否存在，存在则删除
			dbPartitionDaoImpl.findTriggerExits(SqlUtils.findTriggerExitsSql(tableName));
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<String> findPartitionTables() {
		return dbPartitionDaoImpl.findPartitionTables();
	}
}
