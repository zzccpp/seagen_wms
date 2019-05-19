package cn.seagen.base.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import cn.seagen.base.bean.Waybill;
import cn.seagen.base.constant.DbPartitionConstant;
import cn.seagen.base.dao.ScanDao;
import cn.seagen.base.utils.SqlUtils;
@Component
public class ScanDaoImpl implements ScanDao{
	private Logger logger = LogManager.getLogger(ScanDaoImpl.class.getName());
	@Resource
	private JdbcTemplate jdbcTemplate;
	@Override
	public boolean insertScanInfo(final Waybill waybill,String tab) {
		int res = 0;
		String sql = "insert into "+tab+" (batch_id, sort_mode, sort_source, waybill_id, waybill_num, waybill_code, waybill_site_code,"
			+ " package_code, waybill_status, waybill_time, site_code, site_name, car_id, chute_id, scan_id, supply_id, layer_id,"
			+ " car_num, chute_num, scan_num, supply_num, layer_num, package_weight, package_length, package_width, package_height, "
			+ "supply_type, supply_time, scan_cycle, scan_status, scan_time,re_mark, update_flag,"
			+ " update_time, receive_time) values(%s)";
		
		try {
			sql = SqlUtils.formatSqlPargram(sql, 35);
			res = jdbcTemplate.update(sql,new PreparedStatementSetter(){

				@Override
				public void setValues(PreparedStatement ps)
						throws SQLException {
					int tag = 0;
					ps.setInt(++tag, waybill.getBatch_id());// 批次ID
					ps.setString(++tag, waybill.getSort_mode());// 分拣模式
					ps.setString(++tag, waybill.getSort_source());// 分拣来源
					ps.setString(++tag, waybill.getWaybill_id());// 快件追踪ID
					ps.setString(++tag, waybill.getWaybill_num());// 快件分拣过程唯一编号
					ps.setString(++tag, waybill.getWaybill_code());// 运单号
					ps.setString(++tag, waybill.getWaybill_site_code());// 库中的目的地代码
					ps.setString(++tag, waybill.getPackage_code());// 包裹号
					ps.setInt(++tag, waybill.getWaybill_status());// 运单状态(0正常运单,1运单取消,2运单地址更改,3无数据)
					ps.setString(++tag, waybill.getWaybill_time());//运单生成日期
					ps.setString(++tag, waybill.getSite_code());// 目的地代码
					ps.setString(++tag, waybill.getSite_name());// 目的地名称
					ps.setInt(++tag, waybill.getCar_id());// 小车编号
					ps.setInt(++tag, waybill.getChute_id());// 滑槽口号
					ps.setInt(++tag, waybill.getScan_id());// 扫描器号(龙门架)
					ps.setInt(++tag, waybill.getSupply_id());// 供件台编号
					ps.setInt(++tag, waybill.getLayer_id());// 层级编号
					ps.setString(++tag, waybill.getCar_num());// 小车编号
					ps.setString(++tag, waybill.getChute_num());// 滑槽口号
					ps.setString(++tag, waybill.getScan_num());// 扫描器号(龙门架)
					ps.setString(++tag, waybill.getSupply_num());// 供件台编号
					ps.setString(++tag, waybill.getLayer_num());// 层级编号
					ps.setInt(++tag, waybill.getPackage_weight());//重量
					ps.setInt(++tag, waybill.getPackage_length());//长度
					ps.setInt(++tag, waybill.getPackage_width());//宽度
					ps.setInt(++tag, waybill.getPackage_height());//高度
					ps.setString(++tag, waybill.getSupply_type());// 供件方式
					ps.setString(++tag, waybill.getSupply_time());// 供件时间(YYYY-MM-DD HH:MM:SS.FFFFFFFFFF)
					ps.setInt(++tag, waybill.getScan_cycle());//运行圈数
					ps.setInt(++tag, waybill.getScan_status());// 扫描状态(0正常扫描,1无分拣方案,2无目的地,3noread,4订单取消)
					ps.setString(++tag, waybill.getScan_time());// 扫描时间(YYYY-MM-DD HH:MM:SS.FFFFFFFFFF)
					ps.setString(++tag, waybill.getRe_mark());// 附加消息
					ps.setInt(++tag, waybill.getUpdate_flag());// 更新标识0：未更新1：已更新2不需要更新
					ps.setString(++tag, waybill.getUpdate_time());// 更新时间(YYYY-MM-DD HH:MM:SS.FFFFFFFFFF)
					ps.setString(++tag, waybill.getReceive_time());// 消息生成时间
				}
			});
			
			return res > 0;
		} catch (Exception e) {
			logger.error("insertSorted:" + e.getMessage(), e);
			return false;
		} 
	}
	@Override
	public List<Map<String, Object>> findScanInfos(String packageCode) {
		StringBuilder sql = new StringBuilder();
		for (String table : DbPartitionConstant.scanPartitionList) {
		 sql.append("select f_recno,package_code,supply_id,chute_id,supply_time,site_code from ")
			.append(table)
			.append(" where package_code='"+packageCode+"'")
			.append(" union all ");
		}
		if(sql.length()>0){
			sql.delete(sql.length()-11,sql.length());
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
		return list;
	}
	@Override
	public List<Map<String, Object>> findScanBeforeOrAfter(int supplyId,String supplyTime) {
		
		StringBuilder sql = new StringBuilder();
		//拼装查询前一条的sql
		sql.append("(select *,1 as type from (");
		for (String table : DbPartitionConstant.scanPartitionList) {
		 sql.append("(select f_recno,package_code,supply_id,chute_id,scan_time,site_code,supply_time from ")
			.append(table)
			.append(" where supply_id="+supplyId+" and supply_time<'"+supplyTime+"' order by supply_time desc limit 0,1)")
			.append(" union all ");
		}
		if(DbPartitionConstant.scanPartitionList.size()>0){
			sql.delete(sql.length()-11,sql.length());//去掉最后的 union all 
			sql.append(") temp order by  supply_time desc limit 0, 1) ");
		}
		//拼装查询后一条的sql
		sql.append(" union all (select *,2 as type from (");
		for (String table : DbPartitionConstant.scanPartitionList) {
		 sql.append("(select f_recno,package_code,supply_id,chute_id,scan_time,site_code,supply_time from ")
			.append(table)
			.append(" where supply_id="+supplyId+" and supply_time>'"+supplyTime+"' order by supply_time limit 0,1)")
			.append(" union all ");
		}
		if(DbPartitionConstant.scanPartitionList.size()>0){
			sql.delete(sql.length()-11,sql.length());//去掉最后的 union all 
			sql.append(") temp order by supply_time limit 0, 1)");
		}
		System.out.println("查询："+sql.toString());
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString());
		return list;
	}

	@Override
	public List<Map<String, Object>> findScanList(String sql) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		try {
			dataList = jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			logger.error("findScanList:" + e.getMessage(), e);
		}
		return dataList;
	}

	@Override
	public int findScanListNum(String sql) {
		try {
			List<Integer> list = jdbcTemplate.queryForList(sql, Integer.class);
			if (list!=null&&list.size()>0) {
				return list.get(0);
			}
		} catch (Exception e) {
			logger.error("findScanListNum error",e);
		}
		return 0;
	}
}
