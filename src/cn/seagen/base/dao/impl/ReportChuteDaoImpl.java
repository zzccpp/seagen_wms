package cn.seagen.base.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.seagen.base.constant.EquipmentConstant;
import cn.seagen.base.dao.ReportChuteDao;
import cn.seagen.base.utils.DateUtils;
import cn.seagen.base.utils.ReportUtils;
import cn.seagen.base.vo.RequestBase;
@Component
public class ReportChuteDaoImpl implements ReportChuteDao {
	private static Logger logger = LogManager.getLogger(ReportSupplyDaoImpl.class
			.getName());
	@Resource
	private JdbcTemplate template;
	
	@Override
	public List<Map<String, Object>> queryChute(RequestBase requestBase) {
		List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>();
		String sdate = requestBase.getDate();//指定查询某日、月、年的数据，也是时间段查询的开始日期
		String edate = DateUtils.getNow();//指定时间段查询的结束日期
		int type = requestBase.getType();//0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)
		int sort = requestBase.getRule();//统计条件1按区域非1按格口
		long start =0l;
		long end = 0l;
		try {
			start = DateUtils.formatStartDateToLong(sdate, type);
			end = DateUtils.formatEndDateToLong(edate, type);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		StringBuilder r_sql = new StringBuilder();
		StringBuilder e_sql = new StringBuilder();
		
		if(type == 0){
			
			if(sort == 1){
				e_sql.append("select a.area_name,sorting_count,box_count from (");
				e_sql.append("select area_name, ifnull(count(sorting_temp.f_recno), 0) as sorting_count ");
				e_sql.append("from chute_area left join sorting_temp ");
				e_sql.append("on locate(concat(',',sorting_temp.chute_id,','),concat(',', chute_area.chute_num_list,','))>0 ");
				e_sql.append("and chute_id > 0 and chute_id <= ").append(EquipmentConstant.chuteNum).append(" and sorting_time_long > ");
				e_sql.append(start).append(" and sorting_time_long <= ");
				e_sql.append(end).append(" group by area_name) a  left join (");
				
				
				e_sql.append("select area_name,count(box_temp.f_recno) as box_count from chute_area ");
				e_sql.append("left join box_temp on locate(concat(',',box_temp.chute_id,','),concat(',', chute_area.chute_num_list,','))>0 ");
				e_sql.append("and chute_id > 0 and chute_id <= ").append(EquipmentConstant.chuteNum).append(" and print_time_long > ");
				e_sql.append(start).append(" and print_time_long <= ");
				e_sql.append(end).append(" group by area_name ) b ");
				
				e_sql.append("on a.area_name = b.area_name order by area_name");
				
			}else {
				
				r_sql.append("flag_date left join  ( select b.chute_id as chute_id,sorting_count,box_count from (");
				r_sql.append("select chute_id, count(f_recno) as sorting_count from sorting_temp ");
				r_sql.append("where sorting_time_long > ");
				r_sql.append(start).append(" and sorting_time_long <= ").append(end);
				r_sql.append(" group by chute_id) b LEFT JOIN ( ");
				
				r_sql.append("select chute_id, count(f_recno) as box_count ");
				r_sql.append("from box_temp where print_time_long > ");
				r_sql.append(start).append(" and print_time_long <= ").append(end);
				r_sql.append(" group by chute_id) c on b.chute_id = c.chute_id) d ON convert(date_val, signed) = convert(d.chute_id, signed)");
				r_sql.append("where convert(date_val, signed) <= ").append(EquipmentConstant.chuteNum);
				
				e_sql.append("select concat(lpad(date_val, 3, '0'), '格口') as chute_id, ifnull(sorting_count,0) as sorting_count, ifnull(box_count,0) as box_count  from ");
				e_sql.append(r_sql).append(" order by chute_id");
			}
			
		}else{
			String dateFormatGroupStr = ReportUtils.getTimeFormat(3, type);
			String dateFormatWhereStr = ReportUtils.getTimeFormat(1, type);
			String dateFormatTimeStr = ReportUtils.getTimeFormat(2,type);
			int colNum = ReportUtils.getColNum(type, sdate);
			
			String date_val = "convert(date_format(sorting_time, '"+dateFormatTimeStr+"'), signed)";
			if(type == 1)
				date_val = "("+date_val+"+1)";
			StringBuilder t_sql = new StringBuilder();
			t_sql.append(" select a.chute_id ");
			for (int i =1; i <= colNum; i++) {
				String iToStr = ReportUtils.formatStr(i+"", 2);
				t_sql.append(", (case when a.date_val = ").append(iToStr).append(" then sorting_count else 0 end) as sorting_count").append(iToStr);
				t_sql.append(", (case when b.date_val = ").append(iToStr).append(" then box_count else 0 end) as box_count").append(iToStr);
			}
			
			t_sql.append(" from (select ").append(date_val).append(" as date_val, chute_id, count(sorting_temp.f_recno) as sorting_count ,");
			t_sql.append("date_format(sorting_time, '").append(dateFormatGroupStr).append("')as report_date "); 
			t_sql.append("from sorting_temp ");
			t_sql.append(" where date_format(sorting_time, '").append(dateFormatWhereStr).append("') = date_format('");
			t_sql.append(sdate).append("','").append(dateFormatWhereStr).append("')");
			t_sql.append(" group by date_val, chute_id,report_date ) a ");
			//t_sql.append(" where chute_id is not null and convert(chute_id, signed) <= ").append(EquipmentConstant.chuteNum);
			t_sql.append(" left join ( ");
			
			
			date_val = "convert(date_format(print_time, '"+dateFormatTimeStr+"'), signed)";
			if(type == 1)
				date_val = "("+date_val+"+1)";
			r_sql.append("select ").append(date_val).append(" as date_val, chute_id, count(box_temp.f_recno) as box_count ,");
			r_sql.append("date_format(print_time, '").append(dateFormatGroupStr).append("')as report_date "); 
			r_sql.append("from box_temp ");
			r_sql.append(" where date_format(print_time, '").append(dateFormatWhereStr).append("') = date_format('");
			r_sql.append(sdate).append("','").append(dateFormatWhereStr).append("')");
			r_sql.append(" group by date_val, chute_id,report_date ) b ");
			
			t_sql.append(r_sql).append(" on a.chute_id = b.chute_id ");
			
			if(sort == 1){
				e_sql.append("select area_name ");
				for (int i =1; i <= colNum; i++) {
					String iToStr = ReportUtils.formatStr(i+"", 2);
					e_sql.append(", ifnull(sum(sorting_count").append(iToStr).append("), 0) as sorting_count").append(iToStr);
					e_sql.append(", ifnull(sum(box_count").append(iToStr).append("), 0) as box_count").append(iToStr);
				}
				e_sql.append(" from chute_area left join (").append(t_sql).append(" ) d on locate(concat(',',convert(d.chute_id, signed),','),");
				e_sql.append("concat(',', chute_area.chute_num_list,','))>0 group by area_name order by area_name; ");
			}else {
				e_sql.append("select concat(lpad(date_val, 3, '0'), '格口') as chute_id ");
				for (int i =1; i <= colNum; i++) {
					String iToStr = ReportUtils.formatStr(i+"", 2);
					e_sql.append(", ifnull(sum(sorting_count").append(iToStr).append("), 0) as sorting_count").append(iToStr);
					e_sql.append(", ifnull(sum(box_count").append(iToStr).append("), 0) as box_count").append(iToStr);
				}
				e_sql.append(" from flag_date left join (").append(t_sql).append(")d on convert(date_val, signed) = convert(d.chute_id, signed)");
				e_sql.append(" where convert(date_val, signed) <= ").append(EquipmentConstant.chuteNum).append(" group by date_val order by chute_id;");
			}
			
		}
		System.out.println(e_sql);
		try {
			list = template.queryForList(e_sql.toString());
		} catch (Exception e) {
			logger.error("querySupply->获取格口报表出错："+e.getMessage(),e);
		}
		
		return list;
	}

}
