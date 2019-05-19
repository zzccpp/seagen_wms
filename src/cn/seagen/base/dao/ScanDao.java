package cn.seagen.base.dao;

import java.util.List;
import java.util.Map;

import cn.seagen.base.bean.Waybill;

public interface ScanDao {
	/**
	 * 将扫描记录添加到本地数据库，不管是否扫描到
	 * @param waybill
	 * @param tab 分表名称
	 * @return
	 */
	public boolean insertScanInfo(Waybill waybill,String tab);
	
	/**
	 * 根据运单号获取(所有)扫描表中的部分字段信息【错分辅助查询】
	 * @param packageCode 运单号
	 * @return 无数据返回null 有数据返回list集合
	 */
	public List<Map<String, Object>> findScanInfos(String packageCode);
	
	/**
	 * 获取导入台前后的扫描记录
	 * @param supplyId 导入台
	 * @param scanTime 扫描时间
	 * @return 同导入台扫描前后的上的记录
	 */
	public List<Map<String, Object>> findScanBeforeOrAfter(int supplyId,String scanTime);
	
	/**
	 * 根据sql查询扫描记录列表
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> findScanList(String sql);
	
	/**
	 * 根据sql查询扫描记录条数
	 * @param sql
	 * @return
	 */
	public int findScanListNum(String sql);
}
