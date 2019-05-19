package cn.seagen.base.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.seagen.base.constant.DbPartitionConstant;
import cn.seagen.base.enums.EventType;

public class QueryUtils {

	/** 存放扫描数据字段 */
	public static String scan_colums = "f_recno,batch_id, sort_source, waybill_id, waybill_num,  "
			+ "package_code, waybill_status, waybill_time, serialno, site_code, site_name, car_id, "
			+ "chute_id, scan_id, supply_id, layer_id, package_weight, package_length, package_width, "
			+ "package_height, supply_time, scan_cycle, scan_status, scan_time, re_mark, update_flag, update_time, receive_time";
	/** 存放分拣数据字段 */
	public static String sorted_colums = "f_recno,batch_id, sort_source, waybill_id, waybill_num, "
			+ "package_code, waybill_status, waybill_time, site_code, site_name, car_id, chute_id, "
			+ "scan_id, supply_id, layer_id, package_weight, package_length, package_width, "
			+ "package_height, supply_time, scan_cycle, scan_status, scan_time, sorting_status, "
			+ "sorting_time, re_mark, update_flag, update_time,box_code,jb_time,jb_status,jb_update_flag,jb_update_time, receive_time";

	/**
	 * 根据包裹号查询扫描记录列表
	 * 
	 * @param packagecode
	 *            包裹条码
	 * @param pages
	 *            页码
	 * @param rows
	 *            每页显示数量
	 * @return
	 */
	public static String formatQueryScanSql(String packagecode, int pages, int rows) {
		pages = (pages < 0) ? 1 : pages;
		rows = (rows < 0) ? 30 : rows;
		List<String> list = new ArrayList<String>();
		for (Iterator<String> iterator = DbPartitionConstant.scanPartitionList.iterator(); iterator
				.hasNext();) {
			String tablename = (String) iterator.next();
			list.add("select " + scan_colums + " from " + tablename + " where package_code='"
					+ packagecode + "') as " + tablename + " \n");
		}
		StringBuilder builder = new StringBuilder();
		builder.append("select * from (");
		if (list.size() == 0)
			return null;
		for (int i = 0; i < list.size(); i++) {
			builder.append("select * from( \n");
			builder.append(list.get(i));
			if (i < list.size() - 1)
				builder.append(" union all \n");
		}
		builder.append(") as D order by receive_time desc limit " + ((pages - 1) * rows) + "," + rows + ";");
		return builder.toString();
	}

	/**
	 * 根据包裹号查询扫描记录数量
	 * 
	 * @param packagecode
	 *            包裹条码
	 * @return
	 */
	public static String formatQueryScanNumSql(String packagecode) {
		List<String> list = new ArrayList<String>();
		for (Iterator<String> iterator = DbPartitionConstant.scanPartitionList.iterator(); iterator
				.hasNext();) {
			String tablename = (String) iterator.next();
			list.add("select count(*) as num from " + tablename + " where package_code='"
					+ packagecode + "') as " + tablename + " \n");
		}
		StringBuilder builder = new StringBuilder();
		builder.append("select sum(num) from (");
		if (list.size() == 0)
			return null;
		for (int i = 0; i < list.size(); i++) {
			builder.append("select num from( \n");
			builder.append(list.get(i));
			if (i < list.size() - 1)
				builder.append(" union all \n");
		}
		builder.append(") as D ;");
		return builder.toString();
	}

	/**
	 * 根据包裹号查询扫描记录列表
	 * 
	 * @param packagecode
	 *            包裹条码
	 * @param boxcode
	 *            封包袋号
	 * @param pages
	 *            页码
	 * @param rows
	 *            每页显示数量
	 * @return
	 */
	public static String formatQuerySortedSql(String packagecode, String boxcode, int pages,
			int rows) {
		pages = (pages < 0) ? 1 : pages;
		rows = (rows < 0) ? 30 : rows;
		List<String> list = new ArrayList<String>();
		for (Iterator<String> iterator = DbPartitionConstant.sortingPartitionList.iterator(); iterator
				.hasNext();) {
			String tablename = (String) iterator.next();
			if (!StringUtils.isEmpty(packagecode) && !StringUtils.isEmpty(boxcode)) {
				list.add("select " + sorted_colums + " from " + tablename + " where package_code='"
						+ packagecode + "' and box_code='" + boxcode
						+ "' ) as " + tablename + " \n");
			} else {
				if (!StringUtils.isEmpty(packagecode))
					list.add("select " + sorted_colums + " from " + tablename
							+ " where package_code='" + packagecode
							+ "') as " + tablename + " \n");
				if (!StringUtils.isEmpty(boxcode))
					list.add("select " + sorted_colums + " from " + tablename + " where box_code='"
							+ boxcode + "') as " + tablename + " \n");
			}
		}
		StringBuilder builder = new StringBuilder();
		builder.append("select * from (");
		if (list.size() == 0)
			return null;
		for (int i = 0; i < list.size(); i++) {
			builder.append("select * from( \n");
			builder.append(list.get(i));
			if (i < list.size() - 1)
				builder.append(" union all \n");
		}
		builder.append(") as D order by receive_time desc limit " + ((pages - 1) * rows) + "," + rows + ";");
		return builder.toString();
	}

	/**
	 * 根据包裹号查询分拣记录数量
	 * 
	 * @param packagecode
	 *            包裹条码
	 * @param boxcode
	 *            封包袋号
	 * @return
	 */
	public static String formatQuerySoretedNumSql(String packagecode, String boxcode) {
		List<String> list = new ArrayList<String>();
		for (Iterator<String> iterator = DbPartitionConstant.sortingPartitionList.iterator(); iterator
				.hasNext();) {
			String tablename = (String) iterator.next();
			if (!StringUtils.isEmpty(packagecode) && !StringUtils.isEmpty(boxcode)) {
				list.add("select count(*) as num from " + tablename + " where package_code='"
						+ packagecode + "' and box_code='" + boxcode + "') as " + tablename + " \n");
			} else {
				if (!StringUtils.isEmpty(packagecode))
					list.add("select count(*) as num from " + tablename + " where package_code='"
							+ packagecode + "') as " + tablename + " \n");
				if (!StringUtils.isEmpty(boxcode))
					list.add("select count(*) as num from " + tablename + " where box_code='"
							+ boxcode + "') as " + tablename + " \n");
			}
		}
		StringBuilder builder = new StringBuilder();
		builder.append("select sum(num) from (");
		if (list.size() == 0)
			return null;
		for (int i = 0; i < list.size(); i++) {
			builder.append("select num from( \n");
			builder.append(list.get(i));
			if (i < list.size() - 1)
				builder.append(" union all \n");
		}
		builder.append(") as D;");
		return builder.toString();
	}

	/**
	 * 根据时间段、上传状态查询分拣明细记录（去重）
	 * 
	 * @param date_start
	 *            起始时间
	 * @param date_end
	 *            结束时间
	 * @param update_flag
	 *            数据上传状态：0，未上传；1，已上传；
	 * @param pages
	 *            页码
	 * @param rows
	 *            每页显示数量
	 * @return
	 */
	public static String formatQuerySortDetailSql(String date_start, String date_end,
			int update_flag, int pages, int rows) {
		List<String> list = new ArrayList<String>();
		for (Iterator<String> iterator = DbPartitionConstant.sortingPartitionList.iterator(); iterator
				.hasNext();) {
			String tablename = (String) iterator.next();
			if (!StringUtils.isEmpty(date_start) && !StringUtils.isEmpty(date_end)) {
				String sql = "(select * from(select " + sorted_colums + " from " + tablename
						+ " where sorting_time_date between '" + date_start + "' and '" + date_end
						+ "' and update_flag = " + update_flag
						+ " and package_code <> 'noread' order by sorting_time desc ) c"
						+ " group by package_code order by sorting_time_date desc) union (select "
						+ sorted_colums + " from " + tablename
						+ " where sorting_time_date between '" + date_start + "' and '" + date_end
						+ "' and update_flag = " + update_flag + " "
						+ " and package_code = 'noread' order by sorting_time_date desc)";
				list.add(sql);
			} else {
				return null;
			}
		}
		StringBuilder builder = new StringBuilder();
		builder.append("select * from (");
		if (list.size() == 0)
			return null;
		for (int i = 0; i < list.size(); i++) {
			builder.append(list.get(i));
			if (i < list.size() - 1)
				builder.append(" union \n");
		}
		builder.append(") as D limit " + ((pages - 1) * rows) + "," + rows + ";");
		return builder.toString();
	}

	/**
	 * 根据时间段、上传状态查询分拣明细记录数量（去重）
	 * 
	 * @param date_start
	 *            起始时间
	 * @param date_end
	 *            结束时间
	 * @param update_flag
	 *            数据上传状态：0，未上传；1，已上传；
	 * @return
	 */
	public static String formatQuerySoretDetailNumSql(String date_start, String date_end,
			int update_flag) {
		List<String> list = new ArrayList<String>();
		for (Iterator<String> iterator = DbPartitionConstant.sortingPartitionList.iterator(); iterator
				.hasNext();) {
			String tablename = (String) iterator.next();
			if (!StringUtils.isEmpty(date_start) && !StringUtils.isEmpty(date_end)) {
				String sql = "(select count(package_code) as num from " + tablename
						+ " where sorting_time_date between '" + date_start + "' and '" + date_end
						+ "' and update_flag = " + update_flag
						+ " and package_code <> 'noread') union (select count(*) as num from "
						+ tablename + " where sorting_time_date between '" + date_start + "' and '"
						+ date_end + "' and update_flag = " + update_flag
						+ " and package_code = 'noread')";
				list.add(sql);
			} else {
				return null;
			}
		}
		StringBuilder builder = new StringBuilder();
		builder.append("select sum(num) from (");
		if (list.size() == 0)
			return null;
		for (int i = 0; i < list.size(); i++) {
			builder.append(list.get(i));
			if (i < list.size() - 1)
				builder.append(" union \n");
		}
		builder.append(") as D;");
		return builder.toString();
	}

	/**
	 * 根据条件查询分拣方案数量sql
	 * @param chute_no  格口号
	 * @param site_code  站点编码
	 * @param box_site_name 站点名称
	 * @return
	 */
	public static String formatQuerySortSchemeDetailNumSql(int chute_no, String site_code,
			String box_site_name) {
		StringBuilder builder = new StringBuilder();
		builder.append("select count(*) as num from sortscheme_detail_run where 1=1");
		if (chute_no >= 0) {
			builder.append(" and chute_num = " + chute_no);
		}
		if (StringUtils.isNotEmpty(site_code)) {
			builder.append(" and site_code  like '%" + site_code + "%'");
		}
		if (StringUtils.isNotEmpty(box_site_name)) {
			builder.append(" and box_site_name  like '%" + box_site_name + "%'");
		}
		return builder.toString();
	}

	/**
	 * 根据条件查询分拣方案列表sql
	 * @param chute_no
	 * @param site_code
	 * @param box_site_name
	 * @param pages
	 * @param rows
	 * @return
	 */
	public static String formatQueryRunSortSchemeDetailSql(int chute_no, String site_code,
			String box_site_name, int pages, int rows) {
		pages = (pages < 0) ? 1 : pages;
		rows = (rows < 0) ? 30 : rows;
		StringBuilder builder = new StringBuilder();
		builder.append("select * from sortscheme_detail_run where 1=1");
		if (chute_no >= 0) {
			builder.append(" and chute_num = " + chute_no);
		}
		if (StringUtils.isNotEmpty(site_code)) {
			builder.append(" and site_code  like '%" + site_code + "%'");
		}
		if (StringUtils.isNotEmpty(box_site_name)) {
			builder.append(" and box_site_name  like '%" + box_site_name + "%'");
		}
		builder.append(" limit " + ((pages - 1) * rows) + "," + rows + ";");
		return builder.toString();
	}

	/**
	 * 根据条件查询系统事件数量sql
	 * @param startTime
	 * @param endTime
	 * @param eventType
	 * @return
	 */
	public static String formatQuerySystemEventNumSql(String startTime, String endTime,
			int eventType) {
		StringBuilder builder = new StringBuilder();
		builder.append("select count(*) as num from system_event where 1=1");
		if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
			builder.append(" and create_time between '" + startTime + "' and '" + endTime + "'");
		}
		if (eventType != 0) {
			String type = EventType.getEnum(eventType).getMessage().trim();
			builder.append(" and event_type  like '%" + type + "%'");
		}
		return builder.toString();
	}

	/**
	 * 根据条件查询系统事件列表sql
	 * @param startTime
	 * @param endTime
	 * @param eventType
	 * @param pages
	 * @param rows
	 * @return
	 */
	public static String formatQuerySystemEventSql(String startTime, String endTime, int eventType,
			int sort, int pages, int rows) {
		pages = (pages < 0) ? 1 : pages;
		rows = (rows < 0) ? 30 : rows;
		StringBuilder builder = new StringBuilder();
		builder.append("select f_recno,event_id,event_level,event_type,"
				+ "event_name,event_val,event_mark,event_time,event_status,"
				+ "upload_time,date_format(create_time,'%y-%m-%d %h:%i:%s') as create_time from system_event where 1=1");
		if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
			builder.append(" and create_time between '" + startTime + "' and '" + endTime + "'");
		}
		if (eventType != 0) {
			String type = EventType.getEnum(eventType).getMessage().trim();
			builder.append(" and event_type  like '%" + type + "%'");
		}
		if (sort == 1) {
			builder.append(" order by create_time desc ");
		} else {
			builder.append(" order by create_time");
		}
		builder.append(" limit " + ((pages - 1) * rows) + "," + rows + ";");
		return builder.toString();
	}

	/**
	 * 根据单号查询运单数量sql
	 * @param startTime
	 * @param endTime
	 * @param waybillCode
	 * @return
	 */
	public static String formatQueryWaybillNumSql(String waybillCode) {
		StringBuilder builder = new StringBuilder();
		builder.append("select count(*) as num from waybill where 1=1");
		if (StringUtils.isNotEmpty(waybillCode)) {
			builder.append(" and waybill_code = '" + waybillCode + "'");
		}
		return builder.toString();
	}

	/**
	 * 根据单号查询运单列表sql
	 * @param waybillCode
	 * @param pages
	 * @param rows
	 * @return
	 */
	public static String formatQueryWaybillSql(String waybillCode, int pages, int rows) {
		pages = (pages < 0) ? 1 : pages;
		rows = (rows < 0) ? 30 : rows;
		StringBuilder builder = new StringBuilder();
		builder.append("select f_recno,waybill_id,waybill_code,exp_code,waybill_status,"
				+ "waybill_weight,site_code,sort_routing,orig_routing,serialno,waybill_time,date_format(create_time,'%y-%m-%d %h:%i:%s') as create_time from waybill where 1=1");
		if (StringUtils.isNotEmpty(waybillCode)) {
			builder.append(" and waybill_code = '" + waybillCode + "'");
		}
		builder.append(" limit " + ((pages - 1) * rows) + "," + rows + ";");
		return builder.toString();
	}
}
