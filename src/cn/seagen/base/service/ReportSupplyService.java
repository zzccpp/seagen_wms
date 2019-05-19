package cn.seagen.base.service;

import java.util.List;

import cn.seagen.base.vo.RequestBase;
import cn.seagen.base.vo.ScanSupply;
import cn.seagen.base.vo.SixSpike;

public interface ReportSupplyService {
	/**
	 * 获取导入台报表数据
	 * @param requestBase
	 * @return
	 */
	public Object querySupply(RequestBase requestBase);
	/**
	 * 根据时间查找导入台统计
	 * #供件总量  #有效时间  count(REPORT_DATE)/60 h  #供件效率=供件总量/有效操作时间  #峰值  连续一分钟内最大值
	 * @param stime
	 * @param etime
	 * @return
	 */
	public List<ScanSupply> queryScanSupply(RequestBase requestBase);
	/**
	 * 获取某个导入台连续60分钟峰值数据
	 * @param stime 开始时间
	 * @param etime 结束时间
	 * @param supplyNo 导入台号
	 * @param sixSpikeNum 峰值
	 * @return
	 */
	public List<SixSpike> getSixSpikeData(String stime,String etime,int supplyNo,int sixSpikeNum,int layer);
	
	/**
	 * 导出Excel：导入台统计
	 * @param requestBase
	 * @param request
	 * @return
	 */
	public byte[] exportSupply(RequestBase requestBase);
	
	/**
	 * 导出Excel：导入台峰值统计
	 * @param requestBase
	 * @param request
	 * @return
	 */
	public byte[] exportSpikeSupply(RequestBase requestBase);
}
