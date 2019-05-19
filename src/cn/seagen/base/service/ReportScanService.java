package cn.seagen.base.service;

import cn.seagen.base.vo.RequestBase;

/** 扫描量统计数据层 */
public interface ReportScanService {
	
	/**
	 * 扫描量统计查询
	 * @param requestBase
	 * @return
	 */
	public Object queryScan(RequestBase requestBase);

	/**
	 * Excel导出：扫描量统计
	 */
	public byte[] exportScan(RequestBase requestBase);
	
}
