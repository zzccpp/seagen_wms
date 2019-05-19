package cn.seagen.base.dao;

import java.util.List;
import java.util.Map;


/** 分钟量统计查询、导出等 */
public interface ReportMinuteDao {
	
	/**
	 * 查询分钟量统计
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> queryMinute(String sql);
	
}
