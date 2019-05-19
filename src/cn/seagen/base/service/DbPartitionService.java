package cn.seagen.base.service;

import java.util.List;

import cn.seagen.base.bean.ScanSortingExitsBean;


public interface DbPartitionService {
	
	/**
	 * 获取扫描、分拣分表记录表数据
	 * @return 无数据返回：null；有数据则返回实体类的list集合；
	 */
	public List<ScanSortingExitsBean> findTableList();

	/**
	 * 创建分表
	 * @param month 201801
	 * @param type 0，扫描表；1，分拣表；2，待定；
	 * @return 成功则返回true；失败返回false；
	 */
	public boolean createTablePartition(String month,int type);
	
	/**
	 * 判断分表是否存在
	 * @param month 201801
	 * @param type 0，扫描表；1，分拣表；2，待定；
	 * @return List<ScanSortingExitsBean>
	 */
	public List<ScanSortingExitsBean> findTableExits(String month,int type);
	
	/**
	 * 创建分表:根据表名创建
	 * @param tableName
	 * @param type 0，扫描表；1，分拣表；2，待定；
	 * @return 成功则返回true；失败返回false；
	 */
	public boolean createTablePartitionByName(String tableName,int type);
	
	/**
	 * 根据表名，判断表是否存在
	 * @param tableName
	 * @return
	 */
	public boolean findTableIsExits(String tableName);
	
	/**
	 * 创建分表之后，插入一条数据到scan_sorting_exits_tables中
	 * @param sql
	 * @param type
	 * @return
	 */
	public boolean insertScanSortingExitsTable(ScanSortingExitsBean scanSortingExitsBean);
	
	/**
	 * 删除分表:根据表名
	 * @param tableNames
	 * @return
	 */
	public boolean deletePartitionTables(List<String> tableNames);
	
	/**
	 * 删除分表:根据表名
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
}
