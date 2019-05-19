package cn.seagen.base.dao;

import java.util.List;
import java.util.Map;

import cn.seagen.base.bean.Waybill;

public interface WaybillDao {
	
	
	/**
	 * 添加原始运单数据
	 * @param list
	 * @return
	 */
	public boolean insertWaybill(List<Waybill> list);
	
	/**
	 * 查询原始运单列表
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> findWaybillList(String sql);
	
	/**
	 * 查询原始运单条数
	 * @param sql
	 * @return
	 */
	public int findWaybillListNum(String sql);
	

	/**
	 * 查询最新分拣记录,用于分析最新的运单操作轨迹
	 * @param waybillCode
	 * @return
	 */
	public Waybill findWaybillTrace(String waybillCode);
	
}
