package cn.seagen.base.dao.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.seagen.base.constant.EquipmentConstant;
import cn.seagen.base.dao.ReportCarDao;
import cn.seagen.base.utils.DateUtils;
import cn.seagen.base.utils.ReportUtils;
import cn.seagen.base.vo.RequestBase;

@Component
public class ReportCarDaoImpl implements ReportCarDao {

	private Logger logger = LogManager.getLogger(ReportCarDaoImpl.class.getName());
	@Resource
	private JdbcTemplate template;

	@Override
	public List<Map<String, Object>> queryCar(RequestBase requestBase, int layerId) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		String startTime = requestBase.getDate();
		String endTime = requestBase.getEnd_date();
		int type = requestBase.getType();
		int more = requestBase.getMore();
		
		long start =0l;
		long end = 0l;
		try {
			start = DateUtils.formatStartDateToLong(startTime, type);
			end = DateUtils.formatEndDateToLong(endTime, type);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		int startCar  = 1;
		int endCar  = EquipmentConstant.carNum;
		if(layerId == 1){
			endCar = EquipmentConstant.firsrLayer;
		}else if(layerId == 2){
			endCar = EquipmentConstant.secondLayer;
		}
		
		String sql = "";
		// 时间段
		if (type == 0) {
			StringBuilder r_sql = new StringBuilder();
			StringBuilder e_sql = new StringBuilder();
			r_sql.append("(select car_id,layer_id,count(sorting_temp.f_recno) as sorting_count,sum(case when sorting_status = 0 then 1 else 0 end) as 'success_sum',");
			r_sql.append("(count(sorting_temp.f_recno)-sum(case when sorting_status = 0 then 1 else 0 end)) as err_sum,");
			r_sql.append("sum(case when sorting_status = 3 then 1 else 0 end) as no_reade");
			// 是否显示更多
			if (more != 0) {
				r_sql.append(",sum(case when sorting_status = 1 then 1 else 0 end) as no_chute,");
				r_sql.append("sum(case when sorting_status = 2 then 1 else 0 end) as no_data,");
				r_sql.append("sum(case when sorting_status = 4 then 1 else 0 end) as cancel_sum,");
				r_sql.append("sum(case when sorting_status = 5 then 1 else 0 end) as max_cycles,");
				r_sql.append("sum(case when sorting_status = 6 then 1 else 0 end) as err_chute,");
				r_sql.append("sum(case when sorting_status = 7 then 1 else 0 end) as more_data,");
				r_sql.append("sum(case when sorting_status = 8 then 1 else 0 end) as lost_data");
			}
			r_sql.append(" from sorting_temp");
			r_sql.append(" where ");//r_sql.append(" where car_id >= "+startCar+" and car_id <= "+endCar +" and ");
			r_sql.append(" sorting_time_long >= " + start + " and sorting_time_long <=" + end + "");
			if(layerId >0){
				r_sql.append(" and layer_id ="+layerId+"");
			}
			r_sql.append(" group by car_id )A ");//r_sql.append(" group by car_id order by car_id)A ");
			
			e_sql.append("select concat(lpad(date_val, 3, '0'), '小车') as car_id,layer_id");
			e_sql.append(", ifnull(sum(sorting_count), 0) as sorting_count, ifnull(sum(success_sum), 0) as success_sum");
			e_sql.append(", ifnull(sum(err_sum), 0) as err_sum, ifnull(sum(no_reade), 0) as no_reade");
			
			if(more !=0){
				e_sql.append(", ifnull(sum(no_chute), 0) as no_chute, ifnull(sum(more_data), 0) as more_data");
				e_sql.append(", ifnull(sum(no_data), 0) as no_data, ifnull(sum(cancel_sum), 0) as cancel_sum");
				e_sql.append(", ifnull(sum(err_chute), 0) as err_chute, ifnull(sum(max_cycles), 0) as max_cycles");
				e_sql.append(", ifnull(sum(lost_data), 0) as lost_data ");
			}
			
			e_sql.append(" from flag_date left join ").append(r_sql);
			e_sql.append("on flag_date.f_recno = car_id ");//e_sql.append("on convert(date_val, signed) = convert(car_id, signed) ");
			e_sql.append("where flag_date.f_recno >=  "+startCar+" and flag_date.f_recno <=  "+endCar+"");//e_sql.append("where convert(date_val, signed) >=  "+startCar+" and convert(date_val, signed) <=  "+endCar+"");
			e_sql.append(" group by flag_date.f_recno order by car_id;");
			sql = e_sql.toString();
		}else {
			int v_col = ReportUtils.getColNum(type, startTime);
			String date_format_group  = ReportUtils.getTimeFormat(1, type);
			String dateValHour = ReportUtils.getTimeFormat(2, type);
			String date_val = "convert(date_format(sorting_temp.sorting_time,'"+dateValHour+"'), signed)";
			if(type==1){
				date_val = "("+date_val+"+1)";
			}
			// sorting_temp表中明细段----
			StringBuilder sb = new StringBuilder();
			sb.append("(select "+date_val+"as date_val, ");
			
			sb.append("car_id,count(sorting_temp.f_recno) as sorting_count,");
			sb.append("date_format(sorting_time, '"+date_format_group+"') as report_date,");
			sb.append("sum(case when sorting_status = 0 then 1 else 0 end) as success_sum,");
			sb.append("(count(sorting_temp.f_recno)-sum(case when sorting_status = 0 then 1 else 0 end)) as err_sum,");
			sb.append("sum(case when sorting_status = 3 then 1 else 0 end) as no_reade ");
			
			if (more != 0) {
				sb.append(",sum(case when sorting_status = 1 then 1 else 0 end) as no_chute,");
				sb.append("sum(case when sorting_status = 2 then 1 else 0 end) as no_data,");
				sb.append("sum(case when sorting_status = 4 then 1 else 0 end) as cancel_sum,");
				sb.append("sum(case when sorting_status = 5 then 1 else 0 end) as max_cycles,");
				sb.append("sum(case when sorting_status = 6 then 1 else 0 end) as err_chute,");
				sb.append("sum(case when sorting_status = 7 then 1 else 0 end) as more_data,");
				sb.append("sum(case when sorting_status = 8 then 1 else 0 end) as lost_data");
			}
			
			sb.append(" from sorting_temp ");
			sb.append(" where sorting_time_long >= '" + start + "' and sorting_time_long <='" + end + "'");
			if(layerId >0){
				sb.append(" and layer_id ="+layerId+"");
			}
			sb.append(" group by date_val, car_id ) A");
			
			String r_sql = sb.toString();
			// 转换SQL-------------------------------------
			String t_sql = " (	select car_id ";

			int d_sql = 1;
			StringBuilder builder = new StringBuilder();
			while (d_sql <= v_col) {
				// 24个小时
				String d_sql2s = ReportUtils.formatStr("" + d_sql, 2); // 前补0，补齐2位
				builder.append(", sum(case when convert(date_val, signed) = " + d_sql
						+ " then sorting_count else 0 end) as sorting_count" + d_sql2s + "");
				builder.append(", sum(case when convert(date_val, signed) = " + d_sql
						+ " then success_sum else 0 end) as success_sum" + d_sql2s + "");
				builder.append(", sum(case when convert(date_val, signed) = " + d_sql
						+ " then no_reade else 0 end) as no_reade" + d_sql2s + "");
				builder.append(", sum(case when convert(date_val, signed) = " + d_sql
						+ " then err_sum else 0 end) as err_sum" + d_sql2s + "");
				
				// 是否显示更多
				if (more != 0) {
					builder.append(", sum(case when convert(date_val, signed) = " + d_sql
							+ " then no_chute else 0 end) as no_chute" + d_sql2s + "");
					builder.append(", sum(case when convert(date_val, signed) = " + d_sql
							+ " then more_data else 0 end) as more_data" + d_sql2s + "");
					builder.append(", sum(case when convert(date_val, signed) = " + d_sql
							+ " then no_data else 0 end) as no_data" + d_sql2s + "");
					builder.append(", sum(case when convert(date_val, signed) = " + d_sql
							+ " then cancel_sum else 0 end) as cancel_sum" + d_sql2s + "");
					builder.append(", sum(case when convert(date_val, signed) = " + d_sql
							+ " then err_chute else 0 end) as err_chute" + d_sql2s + "");
					builder.append(", sum(case when convert(date_val, signed) = " + d_sql
							+ " then max_cycles else 0 end) as max_cycles" + d_sql2s + "");
					builder.append(", sum(case when convert(date_val, signed) = " + d_sql
							+ " then lost_data else 0 end) as lost_data" + d_sql2s + "");
				}
				d_sql = d_sql + 1;
			}

			t_sql = t_sql + builder.toString() + " from " + r_sql
					+ " group by car_id) B";//+ " where car_id is not null group by car_id) B";

			// 输出SQL-----
			String e_sql = "select concat(lpad(date_val, 3, '0'), '小车') as car_id";

			// 时间条件段
			d_sql = 1;
			StringBuilder stringBuilder = new StringBuilder();
			while (d_sql <= v_col) {
				String d_sql2s = ReportUtils.formatStr("" + d_sql, 2); // 前补0，补齐2位
				stringBuilder.append(", ifnull(sum(sorting_count" + d_sql2s
						+ "), 0) as sorting_count" + d_sql2s + "");
				stringBuilder.append(", ifnull(sum(success_sum" + d_sql2s + "), 0) as success_sum"
						+ d_sql2s + "");
				stringBuilder.append(", ifnull(sum(no_reade" + d_sql2s + "), 0) as no_reade"
						+ d_sql2s + "");
				stringBuilder.append(", ifnull(sum(err_sum" + d_sql2s + "), 0) as err_sum"
						+ d_sql2s + "");
				// 是否显示更多
				if (more != 0) {
					stringBuilder.append(", ifnull(sum(no_chute" + d_sql2s + "), 0) as no_chute"
							+ d_sql2s + "");
					stringBuilder.append(", ifnull(sum(more_data" + d_sql2s + "), 0) as more_data"
							+ d_sql2s + "");
					stringBuilder.append(", ifnull(sum(no_data" + d_sql2s + "), 0) as no_data"
							+ d_sql2s + "");
					stringBuilder.append(", ifnull(sum(cancel_sum" + d_sql2s
							+ "), 0) as cancel_sum" + d_sql2s + "");
					stringBuilder.append(", ifnull(sum(err_chute" + d_sql2s + "), 0) as err_chute"
							+ d_sql2s + "");
					stringBuilder.append(", ifnull(sum(max_cycles" + d_sql2s
							+ "), 0) as max_cycles" + d_sql2s + "");
					stringBuilder.append(", ifnull(sum(lost_data" + d_sql2s + "), 0) as lost_data"
							+ d_sql2s + "");
				}
				d_sql = d_sql + 1;
			}
			
			e_sql = e_sql
					+ stringBuilder.toString()
					+ " from flag_date left join "
					+ t_sql
					+ " on flag_date.f_recno = car_id "
					+ " where flag_date.f_recno >= "+startCar+" and flag_date.f_recno <= "+endCar+" group by flag_date.f_recno order by car_id;";
			sql = e_sql;
		}
		try {
			dataList = template.queryForList(sql);
		} catch (Exception e) {
			logger.error("查询 小车量统计异常：" + e.getMessage(), e);
		}
		return dataList;
	}

	
	
	
	
}
