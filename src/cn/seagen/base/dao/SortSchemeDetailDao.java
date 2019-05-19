package cn.seagen.base.dao;

import java.util.List;

import cn.seagen.base.bean.SortschemeDetail;

public interface SortSchemeDetailDao {
	
	/**
	 * 删除本地分拣方案明细表数据
	 * @return
	 */
	public boolean deleteSortschemeDetail();
	
	/**
	 * 新增分拣方案明细
	 * @param sortSchemeDetail
	 * @return
	 */
	public boolean insertSortschemeDetail(SortschemeDetail sortSchemeDetail);
	
	/**
	 * 新增分拣方案明细
	 * @param DbSortSchemeDetailList
	 * @return
	 */
	public  boolean insertSortschemeDetailList(List<SortschemeDetail> DbSortSchemeDetailList);
	
	/**
	 * 获取当前分拣线要使用的分拣明细数据
	 * @param scheme_id
	 * @return
	 */
	public List<SortschemeDetail> getSortschemeDetail(String scheme_id);
}
