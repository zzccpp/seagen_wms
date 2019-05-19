package cn.seagen.base.service;

import java.util.List;
import java.util.Map;

import cn.seagen.base.bean.Waybill;

public interface ScanService {
	/**
	 * 将扫描记录添加到本地数据库，不管是否扫描到
	 * @param waybill
	 * @return
	 */
	public boolean insertScanInfo(Waybill waybill);
	
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
	 * 根据条码查询扫描记录列表
	 * @param packagecode
	 * @param pages 页码
	 * @param rows 每页显示数量
	 * @return
	 */
	public Object findScanList(String packagecode,int pages,int rows);
}
