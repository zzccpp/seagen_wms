package cn.seagen.base.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import cn.seagen.base.bean.Waybill;
import cn.seagen.base.constant.DbPartitionConstant;
import cn.seagen.base.constant.EquipmentConstant;
import cn.seagen.base.dao.SortedDao;
import cn.seagen.base.utils.DateUtils;
import cn.seagen.base.utils.JUtils;
import cn.seagen.base.utils.SqlUtils;
@Component
public class SortedDaoImpl implements SortedDao{
	private Logger logger = LogManager.getLogger(SortedDaoImpl.class.getName());
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean insertSorted(String sql) {
		boolean result = false;
		int res = 0; 
		try {
			res = jdbcTemplate.update(sql);
			if(res >0)
				result = true;
		} catch (Exception e) {
			logger.error("insertSorted:" + e.getMessage(), e);
			result = false;
			throw new RuntimeException("分拣消息入库失败："+sql);
		} 
		return result;
	}
	
	@Override
	public boolean insertSorted(final Waybill waybill,String tab) {
		int res = 0;
		String sql = "insert into "+tab+" (batch_id, sort_mode, sort_source, waybill_id, waybill_num, waybill_code, waybill_site_code,"
			+ " package_code, waybill_status, waybill_time, site_code, site_name, car_id, chute_id, scan_id, supply_id, layer_id,"
			+ " car_num, chute_num, scan_num, supply_num, layer_num, package_weight, package_length, package_width, package_height, "
			+ "supply_type, supply_time, scan_cycle, scan_status, scan_time, sorting_status, sorting_time, re_mark, update_flag,"
			+ " update_time, receive_time,jb_status,box_site_code,box_site_name,sorting_time_date) values(%s)";
		
		try {
			sql = SqlUtils.formatSqlPargram(sql, 41);
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
					ps.setInt(++tag, waybill.getSorting_status());//分拣状态
					ps.setString(++tag, waybill.getSorting_time());// 分拣时间(YYYY-MM-DD HH:MM:SS.FFFFFFFFFF)
					ps.setString(++tag, waybill.getRe_mark());// 附加消息
					ps.setInt(++tag, waybill.getUpdate_flag());// 更新标识0：未更新1：已更新2不需要更新
					ps.setString(++tag, waybill.getUpdate_time());// 更新时间(YYYY-MM-DD HH:MM:SS.FFFFFFFFFF)
					ps.setString(++tag, waybill.getReceive_time());// 消息生成时间
					ps.setInt(++tag, waybill.getJb_status());//建包标识0未建包1已建包2不可建包
					ps.setString(++tag, waybill.getBox_site_code());//箱号目的地代码
					ps.setString(++tag, waybill.getBox_site_name());//箱号目的地名称
					ps.setString(++tag, waybill.getSorting_time_date());//分拣时间(YYYY-MM-DD HH:MM:SS):date类型
				}
			});
			
			return res > 0;
		} catch (Exception e) {
			logger.error("insertSorted:" + e.getMessage(), e);
			return false;
			//throw new RuntimeException("分拣消息入库失败："+sql);
		} 
		
	}
	@Override
	public List<Map<String, Object>> findSortedInfos(String packageCode) {
		StringBuilder sql = new StringBuilder();
		for (String table : DbPartitionConstant.sortingPartitionList) {
		 sql.append("select f_recno,package_code,car_id,chute_id,scan_time,sorting_time,scan_cycle from ")
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
	public List<Map<String, Object>> findCarBeforeOrAfter(int carId,String scanTime) {
		int beforeCarId = carId-1>0? carId-1:EquipmentConstant.carNum;
		int	afterCarId = carId+1>EquipmentConstant.carNum? 1:carId+1;
		StringBuilder beforeSql = new StringBuilder();
		StringBuilder afterSql = new StringBuilder();
		//拼装查询前一小车的beforeSql
		beforeSql.append("(select *,11 as type from (");
		afterSql.append("(select *,21 as type from (");
		for (String table : DbPartitionConstant.sortingPartitionList) {
			beforeSql.append("(select f_recno,package_code,car_id,chute_id,scan_time,sorting_time,scan_cycle from ")
					 .append(table)
					 .append(" where car_id="+beforeCarId+" and scan_time<='"+scanTime+"' order by scan_time desc limit 0,1)")
					 .append(" union all ");
			 afterSql.append("(select f_recno,package_code,car_id,chute_id,scan_time,sorting_time,scan_cycle from ")
					 .append(table)
					 .append(" where car_id="+afterCarId+" and scan_time<='"+scanTime+"' order by scan_time desc limit 0,1)")
					 .append(" union all ");
		}
		if(DbPartitionConstant.sortingPartitionList.size()>0){
			beforeSql.delete(beforeSql.length()-11,beforeSql.length());//去掉最后的 union all 
			beforeSql.append(") temp order by  scan_time desc limit 0, 1) ");
			afterSql.delete(afterSql.length()-11,afterSql.length());//去掉最后的 union all 
			afterSql.append(") temp order by  scan_time desc limit 0, 1) ");
		}
		//前一小车,后一时间的
		beforeSql.append(" union all (select *,12 as type from (");
		afterSql.append(" union all (select *,22 as type from (");
		for (String table : DbPartitionConstant.sortingPartitionList) {
			beforeSql.append("(select f_recno,package_code,car_id,chute_id,scan_time,sorting_time,scan_cycle from ")
					 .append(table)
					 .append(" where car_id="+beforeCarId+" and scan_time>='"+scanTime+"' order by scan_time limit 0,1)")
					 .append(" union all ");
			 afterSql.append("(select f_recno,package_code,car_id,chute_id,scan_time,sorting_time,scan_cycle from ")
			 		 .append(table)
			 		 .append(" where car_id="+afterCarId+" and scan_time>='"+scanTime+"' order by scan_time limit 0,1)")
			 		 .append(" union all ");
		}
		if(DbPartitionConstant.sortingPartitionList.size()>0){
			beforeSql.delete(beforeSql.length()-11,beforeSql.length());//去掉最后的 union all 
			beforeSql.append(") temp order by  scan_time limit 0, 1) ");
			afterSql.delete(afterSql.length()-11,afterSql.length());//去掉最后的 union all 
			afterSql.append(") temp order by  scan_time limit 0, 1) ");
		}
		//System.out.println("前后小车:"+beforeSql+" union all "+afterSql);
		return jdbcTemplate.queryForList(beforeSql+" union all "+afterSql);
	}
	@Override
	public List<Map<String, Object>> findSortedList(String sql) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		try {
			dataList = jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			logger.error("findSortedList:" + e.getMessage(), e);
		}
		return dataList;
	}

	@Override
	public int findSortedListNum(String sql) {
		try {
			List<Integer> list = jdbcTemplate.queryForList(sql, Integer.class);
			if (list!=null&&list.size()>0) {
				return list.get(0);
			}
		} catch (Exception e) {
			logger.error("findSortedListNum error:"+e.getMessage(),e);
		}
		return 0;
	}

	@Override
	public List<Waybill> getUnJbSortedList(int chuteId) {
		List<Waybill> list = new ArrayList<Waybill>();
		StringBuilder sqlBuilder = new StringBuilder();
		int num = 0 ;
		int tab_flag = 0;//用于标记哪个表
		for (String table : DbPartitionConstant.sortingPartitionList) {
			int len = table.length();
			num = JUtils.strToInt(table.substring(len - 1, len), 0);
			tab_flag = JUtils.strToInt(table.substring(len - 9, len-3), 0);
			if((chuteId%3) != num)
				continue;
			sqlBuilder.append("(select f_recno, waybill_id, waybill_site_code, waybill_code, package_code, waybill_num, ")
					  .append(" waybill_status, site_code, site_name, box_site_code, box_site_name, supply_id, car_id, chute_id, scan_id,")
					  .append(" supply_num, car_num, chute_num, scan_num, supply_type, supply_time,  scan_time, package_weight, sort_source,")
			          .append(" sort_mode, package_length, package_width, package_height, scan_status, scan_cycle, waybill_time, update_flag,")
			          .append(" sorting_time, sorting_status, re_mark, serialno, ")
			          .append(tab_flag)
			          .append(" as tab_flag from ")
			          .append(table)
					  .append(" where jb_status = 0 and chute_id = ")
					  .append(chuteId)
					  .append("  order by f_recno)")
			          .append(" union all ");
		}
		if(sqlBuilder.length()> 11){
			sqlBuilder.delete(sqlBuilder.length()-11,sqlBuilder.length());//去掉最后的 union all 
		}
		
		try {
			list = jdbcTemplate.query(sqlBuilder.toString(), new BeanPropertyRowMapper<Waybill>(Waybill.class));
		} catch (Exception e) {
			logger.error("getUnJbSortedList error:"+e.getMessage(),e);
		}
		return list;
	}
	
	@Override
	public boolean updateJbSortedList(String[] sqls) {
		
		
		try {
			jdbcTemplate.batchUpdate(sqls);
		} catch (Exception e) {
			logger.error("updateJbSortedList error:"+e.getMessage(),e);
			return false;
		}
		
		return true;
	}

	@Override
	public int getBatchId(boolean isNew) {
		final String sql = "insert into report_sorting(sum_name,sum_type,report_date,begin_time,end_time) values(?,?,?,?,?);";
		
		 KeyHolder keyHolder = new GeneratedKeyHolder();
		 
		 jdbcTemplate.update(new PreparedStatementCreator() {
		        @Override
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
		            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
					ps.setString(1,DateUtils.getNewBatch());
					ps.setInt(2,0);
					ps.setString(3, DateUtils.getNow());
					ps.setString(4, DateUtils.getNow());
					ps.setString(5, DateUtils.getNow());
		            return ps;
		        }
		 }, keyHolder);
		 return keyHolder.getKey().intValue();
		 
	}
	public static void main(String[] args) {
		System.out.println(JUtils.strToInt("sorting_201803_00".substring("sorting_201803_00".length() - 9, "sorting_201803_00".length()-3), 0));
	}
	
}
