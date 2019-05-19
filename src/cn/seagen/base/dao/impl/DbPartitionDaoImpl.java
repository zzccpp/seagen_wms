package cn.seagen.base.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.seagen.base.bean.ScanSortingExitsBean;
import cn.seagen.base.constant.SystemConstant;
import cn.seagen.base.dao.DbPartitionDao;
@Component
public class DbPartitionDaoImpl implements DbPartitionDao{
	private Logger logger = LogManager.getLogger(DbPartitionDaoImpl.class.getName());
	@Resource
	private JdbcTemplate template;
	
	@Override
	public List<ScanSortingExitsBean> findTableList() {
		String sql = "select f_recno,table_name,month,table_type from scan_sorting_exits_tables;";
		List<ScanSortingExitsBean> list = new ArrayList<ScanSortingExitsBean>();
		try {
			list = template.query(sql, new BeanPropertyRowMapper<ScanSortingExitsBean>(ScanSortingExitsBean.class)); 
		} catch (Exception e) {
			logger.error("findTableList 异常:" + e.getMessage(), e);
		}
		return list;
	}

	@Override
	public boolean createTablePartition(String sql) {
		boolean result = false;
		try {
			template.execute(sql);
			result = true;
		} catch (Exception e) {
			logger.error("createTablePartition 异常:" + e.getMessage(), e);
			result = false;
			throw new RuntimeException("创建分表失败："+sql);
		}
		return result;
	}

	@Override
	public boolean insertScanSortingExitsTable(ScanSortingExitsBean scanSortingExitsBean) {
		boolean result = false;
		int res = 0;
		String sql = "insert into scan_sorting_exits_tables(table_name,month,table_type) values(?,?,?);";
		try {
			res = template.update(sql,scanSortingExitsBean.getTable_name(),scanSortingExitsBean.getMonth(),scanSortingExitsBean.getTable_type());
			if (res > 0)
				result = true;
		} catch (Exception e) {
			logger.error("insertScanSortingExitsTable 异常："+e.getMessage(),e);
			result = false;
			throw new RuntimeException("插入scan_sorting_exits_tables失败："+sql);
		} 
		return result;
	}

	@Override
	public List<ScanSortingExitsBean> findTableExits(String month, int type) {
		String sql = "select f_recno,table_name,month,table_type from scan_sorting_exits_tables where table_type = ? and month = ?";
		List<ScanSortingExitsBean> list = new ArrayList<ScanSortingExitsBean>();
		try {
			list = template.query(sql, new BeanPropertyRowMapper<ScanSortingExitsBean>(ScanSortingExitsBean.class),type,month);  
		} catch (Exception e) {
			logger.error("findTableExits 异常："+e.getMessage(),e);
		} 
		return list;
	}

	@Override
	public boolean findTableIsExits(String tableName) {
		String sql = "show tables like ?";
		try {
			List<String> list= template.queryForList(sql, String.class, tableName);
			if(list == null || list.size() <= 0){
				return false;
			}else{
				return true;
			}
		} catch (Exception e) {
			logger.error("findTableIsExits 异常："+e.getMessage(),e);
		} 
		return false;
	}

	@Override
	public boolean deletePartitionTables(String tableName) {
		boolean result = false;
		String sql = "drop table if exists "+tableName+";";
		try {
			template.execute(sql);
			result = true;
		} catch (Exception e) {
			logger.error("deletePartitionTables:" + e.getMessage(), e);
			result = false;
			throw new RuntimeException("删除deletePartitionTables失败："+sql);
		}
		return result;
	}

	@Override
	public boolean deleteScanSortingExitsTable(String tableName) {
		String sql = "delete from scan_sorting_exits_tables where table_name = '"+tableName+"';";
		try {
			if(template.update(sql) > 0){
				return true;
			}
		} catch (Exception e) {
			logger.error("deleteScanSortingExitsTable:" + e.getMessage(), e);
			throw new RuntimeException("删除deleteScanSortingExitsTable失败："+sql);
		}
		return false;
	}

	@Override
	public List<String> findPartitionTables() {
		try {
			String sql = "select table_name from information_schema.tables where table_schema='"+SystemConstant.DB_NAME+"' "
					+ "and table_type='base table' AND TABLE_NAME LIKE '%s%_0%';";
			List<String> list = template.queryForList(sql, String.class);
			return list;
		} catch (Exception e) {
			logger.error("findPartitionTables 异常:" + e.getMessage(), e);
			return null;
		}
	}

	@Override
	public boolean createTrigger(String sql) {
		boolean result = false;
		try {
			template.execute(sql);
			result =  true;
		} catch (Exception e) {
			logger.error("新增触发器异常:" + e.getMessage(), e);
			result = false;
			throw new RuntimeException("新增触发器失败："+sql);
		} 
		return result;
	}

	@Override
	public boolean findTriggerExits(String sql) {
		try {
			template.execute(sql);
		} catch (Exception e) {
			logger.error("findTriggerExits 异常:" + e.getMessage(), e);
			return false;
		}
		return true;
	}
}
