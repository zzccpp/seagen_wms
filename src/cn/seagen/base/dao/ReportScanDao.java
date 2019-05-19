package cn.seagen.base.dao;

import java.util.List;
import java.util.Map;


/** 扫描量统计查询、导出等 */
public interface ReportScanDao {
	
	/**
	 * 查询扫描量统计
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> queryScan(String sql);
	
}
