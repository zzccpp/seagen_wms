package cn.seagen.base.dao;

import java.util.List;
import java.util.Map;


/** 批次量统计查询、导出等 */
public interface ReportBatchDao {
	
	/**
	 * 批次量统计查询
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> queryBatch(String sql);
	
	/**
	 * 获取批次统计的数量
	 * @param sql
	 * @return
	 */
	public int findBatchNum(String sql);
}
