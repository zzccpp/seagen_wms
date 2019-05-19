package cn.seagen.base.service;

import cn.seagen.base.vo.RequestBase;

/** 批次量统计数据层 */
public interface ReportBatchService {
	
	/**
	 * 批次量统计查询
	 * @param requestBase
	 * @return
	 */
	public Object queryBatch(RequestBase requestBase);

	/**
	 * Excel导出：批次量统计
	 */
	public byte[] exportBatch(RequestBase requestBase);
	
	/**
	 * 获取批次量统计显示字段
	 * @param requestBase
	 * @return
	 */
	public Object getBatchColumns(RequestBase requestBase);
	
}
