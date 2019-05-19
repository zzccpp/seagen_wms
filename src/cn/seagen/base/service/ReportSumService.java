package cn.seagen.base.service;

import cn.seagen.base.vo.RequestBase;

/** 汇总量统计数据层 */
public interface ReportSumService {
	
	/**
	 * 汇总量统计查询
	 * @param requestBase
	 * @return
	 */
	public Object querySum(RequestBase requestBase);

	/**
	 * Excel导出：分拣量统计
	 */
	public byte[] exportSum(RequestBase requestBase);
	
}
