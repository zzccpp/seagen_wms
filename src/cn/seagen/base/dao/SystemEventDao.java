package cn.seagen.base.dao;

import java.util.List;
import java.util.Map;

import cn.seagen.base.bean.SystemEvent;

/**
 * 线体事件日志,主要是线体通过MQ传过来的事件
 * @author zcp
 *
 */
public interface SystemEventDao {

	/**
	 * 根据事件ID,查询相应的事件
	 * @param fRecno 事件类型ID
	 * @return 存在返回实体对象, 不存在返回null
	 */
	public SystemEvent findFlagEventById(int fRecno);
	
	/**
	 * 添加线体事件
	 * @param systemEvent 事件实体
	 * @return 成功 true ,失败 false
	 */
	public boolean insertSystemEvent(SystemEvent systemEvent);
	
	/**
	 * 查询系统事件列表
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> findSystemEventList(String sql);
	
	/**
	 * 根据sql查询系统事件记录条数
	 * @param sql
	 * @return
	 */
	public int findSystemEventListNum(String sql);
}
