package cn.seagen.base.utils;

import cn.seagen.base.bean.Waybill;
/**
 * 分拣相关工具类
 *
 */
public class SortingUtils {
	/** 存放插入字段*/
	private static String colums = "batch_id, sort_mode, sort_source, waybill_id, waybill_num, waybill_code, waybill_site_code,"
			+ " package_code, waybill_status, waybill_time, site_code, site_name, car_id, chute_id, scan_id, supply_id, layer_id,"
			+ " car_num, chute_num, scan_num, supply_num, layer_num, package_weight, package_length, package_width, package_height, "
			+ "supply_type, supply_time, scan_cycle, scan_status, scan_time, sorting_status, sorting_time, re_mark, update_flag,"
			+ " update_time, receive_time";
	
	/**
	 * 组装分拣入库sql语句
	 * @param waybill
	 * @return
	 */
	public static String formatInsertSortSql(Waybill waybill) {
		String sql = "INSERT INTO sorting (" + colums + ") values(";
		StringBuilder builder = new StringBuilder();
		builder.append("");
		//batch_id批次ID,
	    builder.append(waybill.getBatch_id()).append(",");
	    //sort_mode
	    builder.append(waybill.getSort_mode()).append(",");
	    //sort_source
	    builder.append(waybill.getSort_source()).append(",");  
	    //waybill_id运单跟踪ID
	    builder.append(waybill.getWaybill_id()).append(",");  
	    //waybill_num快件分拣过程唯一编号GUID
	    builder.append(waybill.getWaybill_num()).append(",");  
	    //waybill_code运单号
	    builder.append(waybill.getWaybill_code()).append(","); 
	    //waybill_site_code运单表中目的地代码
	    builder.append(waybill.getWaybill_site_code()).append(",");
	    //package_code包裹号
	    builder.append(waybill.getPackage_code()).append(",");
	    //waybill_status运单状态(0正常运单,1运单取消,2运单地址更改,3无数据)
	    builder.append(waybill.getWaybill_status()).append(","); 
	    //waybill_time运单生成时间
	    builder.append(waybill.getWaybill_time()).append(","); 
	    //site_code目的地代码
	    builder.append(waybill.getSite_code()).append(",");
	    //site_name目的地名称
	    builder.append(waybill.getSite_name()).append(",");
	    //car_id小车编号
	    builder.append(waybill.getCar_id()).append(",");
	    //chute_id滑槽口号
	    builder.append(waybill.getChute_id()).append(",");
	    //scan_id扫描仪编号
	    builder.append(waybill.getScan_id()).append(",");
	    //supply_id供件台编号导入号台
	    builder.append(waybill.getSupply_id()).append(",");
	    //layer_id层级id
	    builder.append(waybill.getLayer_id()).append(",");
	    //car_num小车编号
	    builder.append(waybill.getCar_num()).append(",");
	    //chute_num滑槽口编号
	    builder.append(waybill.getChute_num()).append(",");
	    //scan_num扫描仪编号
	    builder.append(waybill.getScan_num()).append(",");
	    //supply_num供件台编号
	    builder.append(waybill.getSupply_num()).append(",");
	    //layer_num层级编号
	    builder.append(waybill.getLayer_num()).append(",");
	    //package_weight重量
	    builder.append(waybill.getPackage_weight()).append(",");
	    //package_length长度
	    builder.append(waybill.getPackage_length()).append(",");
	    //package_width宽度
	    builder.append(waybill.getPackage_width()).append(",");
	    //package_height高度
	    builder.append(waybill.getPackage_height()).append(",");
	    //supply_type供件方式暂定1
	    builder.append(waybill.getSupply_type()).append(",");
	    //supply_time供件时间
	    builder.append(waybill.getSupply_time()).append(",");
	    //scan_cycle扫描圈数
	    builder.append(waybill.getScan_cycle()).append(",");
	    //scan_status扫描状态(0正常扫描,1无分拣方案,2无数据,3无读,4取消,5最大圈数,6格口错,7多条码,8迷失,255未知)
	    builder.append(waybill.getScan_status()).append(",");
	    //scan_time扫描时间
	    builder.append(waybill.getScan_time()).append(","); 
	    //sorting_status分拣状态(0正常扫描,1无分拣方案,2无数据,3无读,4取消,5最大圈数,6格口错,7多条码,8迷失,255未知)
	    builder.append(waybill.getSorting_status()).append(",");
	    //sorting_time分拣时间
	    builder.append(waybill.getSorting_time()).append(",");
	    //re_mark备注
	    builder.append(waybill.getRe_mark()).append(",");
	    //update_flag更新标识0：未更新1：已更新2不需要更新
	    builder.append(waybill.getUpdate_flag()).append(",");
	    //update_time更新时间
	    builder.append(DateUtils.getNow_W()).append(",");
	    //receive_time消息接收时间
	    builder.append(waybill.getReceive_time());
	    
	    sql = sql + builder.toString()+");";
	    
		return sql;
	}
	
}
