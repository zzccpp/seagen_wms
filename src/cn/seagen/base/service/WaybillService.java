package cn.seagen.base.service;

import java.util.List;
import cn.seagen.base.bean.Waybill;

public interface WaybillService {
	
	/**
	 * 添加原始运单数据
	 * @param sortschemeDetail
	 * @return
	 */
	public boolean insertWaybill(List<Waybill> list);
	
	/**
	 * 根据单号查询原始运单
	 * pages 页码
	 * @param rows 每页显示数量
	 * @param waybill_code
	 * @return
	 */
	public Object findWaybillList(int page,int rows,String waybill_code);
	
	/**
	 * 查询最新分拣记录,用于分析最新的运单操作轨迹
	 * @param waybillCode
	 * @return
	 */
	public Waybill findWaybillTrace(String waybillCode);
}
