package cn.seagen.base.dao;

import java.util.List;
import java.util.Map;


/** 汇总量统计查询、导出等 */
public interface ReportSumDao {
	
	/**
	 * 查询汇总量统计
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> querySum(String sql);
}
