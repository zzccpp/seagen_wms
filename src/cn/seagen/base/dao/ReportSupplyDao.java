package cn.seagen.base.dao;

import java.util.List;
import java.util.Map;

import cn.seagen.base.vo.RequestBase;


public interface ReportSupplyDao {
	/**
	 * 获取导入台报表数据
	 * @param requestBase
	 * @return
	 */
	public List<Map<String, Object>> querySupply(RequestBase requestBase);
	/**
	 * 根据时间查找导入台统计
	 * #供件总量  #有效时间  count(REPORT_DATE)/60 h  #供件效率=供件总量/有效操作时间  #峰值  连续一分钟内最大值
	 * @param stime
	 * @param etime
	 * @return
	 */
	public List<Map<String, Object>> queryScanSupply(RequestBase requestBase);
	/**
	 * 获取某段时间，某个导入台连续60分钟峰值的截止时间.
	 * @param stime
	 * @param etime
	 * @param supplyNo
	 * @param sixSpikeNum
	 * @return
	 */
	public List<Map<String,Object>> getSixSpikeTime(String stime,String etime,int supplyNo,int sixSpikeNum,int layer);
	/**
	 * 获取某个导入台连续60分钟峰值数据
	 * @param stime
	 * @param etime
	 * @param supplyNo
	 * @return
	 */
	public List<Map<String, Object>> getSixSpikeData(String stime,String etime,int supplyNo,int layer);
}
