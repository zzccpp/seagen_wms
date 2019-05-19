package cn.seagen.base.dao;

import java.util.List;
import java.util.Map;

import cn.seagen.base.bean.Waybill;


/** 分拣相关操作，如入库、查询等 */
public interface SortedDao {
	
	/**
	 * 分拣数据入库
	 * @param sql
	 * true 成功，false 失败；
	 */
	public boolean insertSorted(String sql);
	
	/**
	 * 分拣数据入库
	 * @param waybill
	 * @param tab 分表名称
	 * true 成功，false 失败；
	 */
	public boolean insertSorted(Waybill waybill,String tab);
	
	/**
	 * 根据运单号获取(所有)分拣表中的部分字段信息【错分辅助查询】
	 * @param packageCode 运单号
	 * @return 无数据返回null 有数据返回list集合
	 */
	public List<Map<String, Object>> findSortedInfos(String packageCode);
	/**
	 * 根据当前小车,查询前一个小车与后一小车在扫描时间相近的运单分拣信息,查询(所有)分拣表中的部分字段信息【错分辅助查询】
	 * @param carId 小车号
	 * @param scanTime 扫描时间
	 * @return 无数据返回null 有数据返回list集合
	 */
	public List<Map<String, Object>> findCarBeforeOrAfter(int carId,String scanTime);
	
	/**
	 * 根据sql查询分拣记录列表
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> findSortedList(String sql);
	
	/**
	 * 根据sql查询分拣记录条数
	 * @param sql
	 * @return
	 */
	public int findSortedListNum(String sql);
	
	/**
	 * 获取该格口未建包分拣数据
	 * @param chuteId 格口编号
	 * @return 返回list集合
	 */
	public List<Waybill> getUnJbSortedList(int chuteId);
	
	/**
	 * 更新建包分拣数据
	 * @param list
	 * @param chuteId
	 * @return
	 */
	public boolean updateJbSortedList(String[] sqls);
	
	/**
	 *  取批次ID
	 * @param isNew 是否取新批次
	 * @return
	 */
	public int getBatchId(boolean isNew);
}
