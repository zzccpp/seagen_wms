package cn.seagen.base.service;


/** 数据库数据备份、删除操作实现 */
public interface DbDataBackUpService {
	
	/**
	 * 获取数据库表中可以删除的数量
	 * 
	 * @param sql
	 * @return 可删除的数量
	 */
	public int findCanDeleteCount(String sql);
	
	/**
	 * 获取数据库表中总的数据量
	 * 
	 * @param sql
	 * @return 表中总的数据量
	 */
	public int findTableDataCount(String sql);
	
	/**
	 * 删除数据
	 * @param sql
	 * @return
	 */
	public boolean deleteTableData(String sql);
	
}
