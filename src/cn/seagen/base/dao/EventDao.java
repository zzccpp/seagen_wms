package cn.seagen.base.dao;

import cn.seagen.base.bean.SystemEvent;

public interface EventDao {
	/**
	 * 
	 * @param event
	 * @return
	 */
	public boolean insertEventInfo(SystemEvent event);
}
