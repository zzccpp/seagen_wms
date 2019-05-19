package cn.seagen.base.dao;

import cn.seagen.base.bean.Sortscheme;


public interface SortSchemeDao {
	
	/**
	 * 添加分拣主题方案
	 * @param sortscheme
	 * @return
	 */
	public boolean insertSortscheme(Sortscheme sortscheme); 
	
	/**
	 * 只有一套分拣主题时
	 * 获取当前生效的分拣主题方案
	 * @return
	 */
	public Sortscheme getSortscheme();
	
	
	/**
	 * 有多套分拣主题可切换时调用此方法
	 * 获取当前生效的分拣主题方案
	 * 使用channel_id字段 ==1：启用
	 * @return
	 */
	public Sortscheme getSortschemeChange();
	
	/**
	 * 删除分拣主题方案表数据
	 * @return
	 */
	public boolean deleteSortscheme();
}
