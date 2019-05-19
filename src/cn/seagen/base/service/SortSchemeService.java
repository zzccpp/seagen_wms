package cn.seagen.base.service;

import cn.seagen.base.bean.Sortscheme;
import cn.seagen.base.bean.SortschemeDetail;

public interface SortSchemeService {
	
	/**
	 * 添加分拣主题方案
	 * @param sortscheme
	 * @return
	 */
	public boolean insertSortscheme(Sortscheme sortscheme); 
	
	/**
	 * 获取当前生效的分拣主题方案
	 * @return
	 */
	public Sortscheme getSortscheme();
	
	/**
	 * 删除分拣主题方案表数据
	 * @return
	 */
	public boolean deleteSortscheme();
	
	
	
	
	/**
	 * 分拣方案详情入库
	 * @param sortschemeDetail
	 * @return
	 */
	public boolean insertSortSchemeInfo(SortschemeDetail sortschemeDetail);
	
	/**
	 * 当前运行分拣方案
	 * pages 页码
	 * @param rows 每页显示数量
	 * @param chute_no
	 * @param site_code
	 * @param box_site_name
	 * @return
	 */
	public Object findRunSortSchemeList(int page,int rows,int chute_no,String site_code,String box_site_name);


}
