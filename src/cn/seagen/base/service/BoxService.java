package cn.seagen.base.service;

import cn.seagen.base.bean.BoxBean;

public interface BoxService {
	/**
	 * 获取未使用的箱号
	 *  当type=1时，以sitecode为参数条件； 当type为2按类型box_type为参数条件；
	 *  默认无条件按未使用的顺序取
	 * @param type 参数类型
	 * @param val 参数值
	 * @return 箱号
	 */
	public BoxBean getUnusedBox(int type, String val);
	
	/**
	 * 更新箱号状态（用于锁定箱号）
	 * @param boxId
	 * @return
	 */
	public boolean updateBoxStatus(long boxId);
	
	/**
	 * 更新箱号信息
	 * @param box
	 * @return
	 */
	public boolean updateBox(BoxBean box);
	
	/**
	 * 建包箱号信息插入箱号统计表
	 * @param box
	 * @return
	 */
	public boolean insertBoxTemp(BoxBean box);
}
