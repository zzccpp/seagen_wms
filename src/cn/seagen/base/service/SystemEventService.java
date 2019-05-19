package cn.seagen.base.service;

import cn.seagen.base.mq.dto.MQEvent;
import cn.seagen.base.vo.RequestBase;

/**
 * 线体事件处理服务接口
 * @author zcp
 */
public interface SystemEventService {

	/**
	 * 添加线体事件
	 * @param mQEvent 线体事件
	 * @return 成功 true ,失败 false
	 */
	public boolean insertSystemEvent(MQEvent mQEvent);
	
	public Object findSystemEventList(int page, int rows, int sort,String startTime, String endTime,
			int eventType);
	
	
	/**
	 * Excel导出：分拣量统计
	 */
	public byte[] exportEvent(RequestBase requestBase,int sort);
	
}
