package cn.seagen.base.dao;

import java.util.List;
import java.util.Map;

import cn.seagen.base.bean.SortschemeDetail;

public interface SortSchemeDetailRunDao {
	
	
	/**
	 * 删除当前运行分拣方案明细表数据
	 * @return
	 */
	public boolean deleteSortschemeDetailRun();
	
	/**
	 * 当前使用分拣方案入库
	 * @param list
	 * @return
	 */
	public boolean insertSortSchemeDetailRun(List<SortschemeDetail> list);
	
	/**
	 * 查询当前使用分拣方案列表
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> findSortSchemeDetailList(String sql);
	
	/**
	 * 根据sql查询分拣方案记录条数
	 * @param sql
	 * @return
	 */
	public int findSortSchemeDetailListNum(String sql);
}
