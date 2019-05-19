package cn.seagen.base.dao;

import java.util.List;
import java.util.Map;

import cn.seagen.base.vo.RequestBase;

/** 小车量统计查询、导出等 */
public interface ReportCarDao {
	
	/**
	 * 查询 小车量统计
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> queryCar(RequestBase requestBase,int layerId);
}
