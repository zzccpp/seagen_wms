package cn.seagen.base.service;

import cn.seagen.base.vo.RequestBase;

/** 小车量统计数据层 */
public interface ReportCarService {
	
	/**
	 *  小车量统计查询
	 * @param requestBase
	 * @return
	 */
	public Object querycar(RequestBase requestBase);

	/**
	 * Excel导出：分拣量统计
	 */
	public byte[] exportCar(RequestBase requestBase);
	
}
