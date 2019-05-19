package cn.seagen.base.dao;

import java.util.List;

import cn.seagen.base.bean.ScanSortingExitsBean;

/** 业务数据表分表相关操作，如新建分表、删除分表等 */
public interface DbPartitionDao {
	
	/**
	 * 获取扫描、分拣分表记录表数据
	 * @return 无数据返回：null；有数据则返回实体类的list集合；
	 */
	public List<ScanSortingExitsBean> findTableList();

	/**
	 * 创建分表
	 * @param sql 
	 * @return 成功则返回true；失败返回false；
	 */
	public boolean createTablePartition(String sql);
	
	/**
	 * 创建分表之后，插入一条数据到scan_sorting_exits_tables中
	 * @param sql
	 * @param type
	 * @return
	 */
	public boolean insertScanSortingExitsTable(ScanSortingExitsBean scanSortingExitsBean);
	
	/**
	 * 判断分表是否存在
	 * @param month 201801
	 * @param type 0，扫描表；1，分拣表；2，待定；
	 * @return List<ScanSortingExitsBean>
	 */
	public List<ScanSortingExitsBean> findTableExits(String month,int type);
	
	
	/**
	 * 根据表名，判断表是否存在
	 * @param tableName
	 * @return
	 */
	public boolean findTableIsExits(String tableName);
	
	
	/**
	 * 删除分表
	 * @param tableName
	 * @return
	 */
	public boolean deletePartitionTables(String tableName);
	
	
	/**
	 * 删除scan_sorting_exits_tables中记录
	 * @param tableName
	 * @return
	 */
	public boolean deleteScanSortingExitsTable(String tableName);
	
	/**
	 * 获取数据库中所有分表名称
	 * @return
	 */
	public List<String> findPartitionTables();
	
	/**
	 * 创建触发器
	 * @param sql
	 * @return
	 */
	public boolean createTrigger(String sql);
	
	/**
	 * 判断触发器是否存在，存在则删除
	 * @param sql
	 * @return
	 */
	public boolean findTriggerExits(String sql);
}
