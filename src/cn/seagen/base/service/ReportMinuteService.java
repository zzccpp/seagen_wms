package cn.seagen.base.service;

import cn.seagen.base.vo.RequestBase;

/** 分钟量统计数据层 */
public interface ReportMinuteService {
	
	/**
	 * 分钟量统计查询
	 * @param requestBase
	 * @return
	 */
	public Object queryMinute(RequestBase requestBase);

	/**
	 * Excel导出：分钟量统计
	 */
	public byte[] exportMinute(RequestBase requestBase);
	
}
