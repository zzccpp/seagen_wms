package cn.seagen.base.service;

import java.util.List;
import java.util.Map;

import cn.seagen.base.bean.Waybill;


public interface SortedService {

	/**
	 * 分拣数据入库
	 * @param waybill
	 */
	public boolean insertSorted(Waybill waybill);
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
	 * 根据条码查询分拣记录列表
	 * @param packagecode
	 * @param boxcode
	 * @param pages 页码
	 * @param rows 每页显示数量
	 * @return
	 */
	public Object findSortedList(String packagecode,String boxcode,int pages,int rows);
	
	/**
	 * 根据条码查询分拣记录列表
	 * @param date_start
	 * @param date_end
	 * @param update_flag 0,未上传；1，已上传；
	 * @param pages 页码
	 * @param rows 每页显示数量
	 * @return
	 */
	public Object findSortDetailList(String date_start,String date_end,int update_flag,int pages,int rows);
	
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
	public boolean updateJbSortedList(List<Waybill> list,int chuteId);
	
	/**
	 *  取批次ID
	 * @param isNew 是否取新批次
	 * @return
	 */
	public int getBatchId(boolean isNew);
}
