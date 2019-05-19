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
import cn.seagen.base.dao.ReportSupplyDao;
import cn.seagen.base.utils.DateUtils;
import cn.seagen.base.utils.ReportUtils;
import cn.seagen.base.vo.RequestBase;
@Component
public class ReportSupplyDaoImpl implements ReportSupplyDao {
	private static Logger logger = LogManager.getLogger(ReportSupplyDaoImpl.class
			.getName());
	@Resource
	private JdbcTemplate template;
	
	@Override
	public List<Map<String, Object>> querySupply(RequestBase requestBase) {
		List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>();
		String sdate = requestBase.getDate();//指定查询某日、月、年的数据，也是时间段查询的开始日期
		String edate = requestBase.getEnd_date();//DateUtils.getNow();//指定时间段查询的结束日期
		int type = requestBase.getType();//0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)
		int more = requestBase.getMore();//是否显示更多信息0不显示，非0显示
		int layerId = requestBase.getLayer();
		int supplyNum  = 0;
		if(layerId == 1){
			supplyNum = EquipmentConstant.firsrLayerSupplyNum;
		}else if(layerId == 2){
			supplyNum = EquipmentConstant.secondLayerSupplyNum;
		}
		
		StringBuilder r_sql = new StringBuilder();
		StringBuilder e_sql = new StringBuilder();
		if(type == 0){
			r_sql.append("(select supply_id,layer_id, sum(all_sum) as all_sum, sum(success_sum) as success_sum, sum(no_reade) as no_reade,");
			if(more !=0){
				r_sql.append("sum(no_chute) as no_chute, sum(more_data) as more_data,sum(no_data) as no_data, sum(cancel_sum) as cancel_sum,");
				r_sql.append("sum(err_chute) as err_chute, sum(max_cycles) as max_cycles, sum(lost_data) as lost_data, ");
			}
			r_sql.append("sum(err_sum) as err_sum from report_supply where");//导入台数量;
			r_sql.append(" report_date between date_format('").append(sdate).append("', '%Y-%m-%d %H:%i') and date_format('").append(edate).append("', '%Y-%m-%d %H:%i')");
			r_sql.append(" and layer_id = ").append(layerId).append(" and supply_id >=1 ").append(" and supply_id <= ").append(supplyNum);
			r_sql.append(" group by supply_id order by supply_id) a ");
			
			e_sql.append("select concat('").append(layerId).append("','-',lpad(date_val, 3, '0'), '导入台') as supply_id");
			e_sql.append(", ifnull(sum(all_sum), 0) as all_sum, ifnull(sum(success_sum), 0) as success_sum");
			e_sql.append(", ifnull(sum(err_sum), 0) as err_sum, ifnull(sum(no_reade), 0) as no_reade");
			if(more !=0){
				e_sql.append(", ifnull(sum(no_chute), 0) as no_chute, ifnull(sum(more_data), 0) as more_data");
				e_sql.append(", ifnull(sum(no_data), 0) as no_data, ifnull(sum(cancel_sum), 0) as cancel_sum");
				e_sql.append(", ifnull(sum(err_chute), 0) as err_chute, ifnull(sum(max_cycles), 0) as max_cycles");
				e_sql.append(", ifnull(sum(lost_data), 0) as lost_data ");
			}
			
			e_sql.append(" from flag_date left join ").append(r_sql);
			e_sql.append("on flag_date.f_recno = supply_id ");
			e_sql.append("where flag_date.f_recno >=1 ").append(" and flag_date.f_recno <= ").append(supplyNum).append(" group by date_val order by supply_id;");
		}else{
			String dateFormatGroupStr = ReportUtils.getTimeFormat(3, type);
			//String dateFormatWhereStr = ReportUtils.getTimeFormat(1, type);
			String dateFormatTimeStr = ReportUtils.getTimeFormat(2,type);
			int colNum = ReportUtils.getColNum(type, sdate);
			String startDate = "";
			String endDate = "";
			if(type == 1){
				String temp = DateUtils.formatReportStartTime(sdate, 1);
				startDate = temp + " 00:00:00";
				endDate = temp + " 23:59:59";
			}else if(type == 2){
				String temp = DateUtils.formatReportStartTime(sdate, 2);
				startDate = temp + "-01 00:00:00";
				endDate = temp + "-"+colNum+" 23:59:59";
			}else{
				String temp = DateUtils.formatReportStartTime(sdate, 3);
				startDate = temp + "01-01 00:00:00";
				endDate = temp + "-"+colNum+"-31 23:59:59";
			}
			
			String date_val = "convert(date_format(report_date, '"+dateFormatTimeStr+"'), signed)";
			if(type == 1)
				date_val = "("+date_val+"+1)";
			r_sql.append("(select supply_id,").append(date_val).append(" as date_val, sum(all_sum) as all_sum,");
			r_sql.append("sum(success_sum) as success_sum, sum(no_reade) as no_reade,");
			
			if(more !=0){
				r_sql.append("	sum(no_chute) as no_chute, sum(more_data) as more_data, sum(no_data) as no_data,");
				r_sql.append("sum(cancel_sum) as cancel_sum, sum(err_chute) as err_chute, sum(max_cycles) as max_cycles, sum(lost_data) as lost_data,");
			}
			
			r_sql.append(" sum(err_sum) as err_sum from report_supply where");
			r_sql.append(" report_date >= '").append(startDate).append("' and report_date<= '").append(endDate).append("'and layer_id =").append(layerId);
			r_sql.append(" and supply_id >=1 ").append(" and supply_id <= ").append(supplyNum).append(" group by date_val, supply_id,");
			r_sql.append("date_format(report_date, '").append(dateFormatGroupStr).append("')) a group by date_val,supply_id");
			
			StringBuilder t_sql = new StringBuilder();
			t_sql.append(" (select supply_id ");
			for (int i =1; i <= colNum; i++) {
				String iToStr = ReportUtils.formatStr(i+"", 2);
				t_sql.append(", sum(case when convert(date_val, signed) = ").append(i).append(" then all_sum else 0 end) as all_sum").append(iToStr);
				t_sql.append(", sum(case when convert(date_val, signed) = ").append(i).append(" then success_sum else 0 end) as success_sum").append(iToStr);
				t_sql.append(", sum(case when convert(date_val, signed) = ").append(i).append(" then err_sum else 0 end) as err_sum").append(iToStr);
				t_sql.append(", sum(case when convert(date_val, signed) = ").append(i).append(" then no_reade else 0 end) as no_reade").append(iToStr);
				if(more != 0){
					t_sql.append(", sum(case when convert(date_val, signed) = ").append(i).append(" then no_chute else 0 end) as no_chute").append(iToStr);
					t_sql.append(", sum(case when convert(date_val, signed) = ").append(i).append(" then more_data else 0 end) as more_data").append(iToStr);
					t_sql.append(", sum(case when convert(date_val, signed) = ").append(i).append(" then no_data else 0 end) as no_data").append(iToStr);
					t_sql.append(", sum(case when convert(date_val, signed) = ").append(i).append(" then cancel_sum else 0 end) as cancel_sum").append(iToStr);
					t_sql.append(", sum(case when convert(date_val, signed) = ").append(i).append(" then err_chute else 0 end) as err_chute").append(iToStr);
					t_sql.append(", sum(case when convert(date_val, signed) = ").append(i).append(" then max_cycles else 0 end) as max_cycles").append(iToStr);
					t_sql.append(", sum(case when convert(date_val, signed) = ").append(i).append(" then lost_data else 0 end) as lost_data").append(iToStr);
				}
			}
			
			t_sql.append(" from ").append(r_sql).append(") b ");
			e_sql.append("select concat('").append(layerId).append("','-',lpad(date_val, 3, '0'), '导入台') as supply_id");
			for (int i = 1; i <= colNum; i++) {
				String iToStr = ReportUtils.formatStr(i+"", 2);
				e_sql.append(", ifnull(sum(all_sum").append(iToStr).append("), 0) as all_sum").append(iToStr);
				e_sql.append(", ifnull(sum(success_sum").append(iToStr).append("), 0) as success_sum").append(iToStr);
				e_sql.append(", ifnull(sum(err_sum").append(iToStr).append("), 0) as err_sum").append(iToStr);
				e_sql.append(", ifnull(sum(no_reade").append(iToStr).append("), 0) as no_reade").append(iToStr);
				if(more != 0){
					e_sql.append(", ifnull(sum(no_chute").append(iToStr).append("), 0) as no_chute").append(iToStr);
					e_sql.append(", ifnull(sum(more_data").append(iToStr).append("), 0) as more_data").append(iToStr);
					e_sql.append(", ifnull(sum(no_data").append(iToStr).append("), 0) as no_data").append(iToStr);
					e_sql.append(", ifnull(sum(cancel_sum").append(iToStr).append("), 0) as cancel_sum").append(iToStr);
					e_sql.append(", ifnull(sum(err_chute").append(iToStr).append("), 0) as err_chute").append(iToStr);
					e_sql.append(", ifnull(sum(max_cycles").append(iToStr).append("), 0) as max_cycles").append(iToStr);
					e_sql.append(", ifnull(sum(lost_data").append(iToStr).append("), 0) as lost_data").append(iToStr);
				}
			}
			
			e_sql.append(" from flag_date left join ").append(t_sql).append(" on flag_date.f_recno = supply_id ");
			e_sql.append("where flag_date.f_recno <= ").append(supplyNum).append(" group by date_val order by supply_id; ");
		}
		try {
			list = template.queryForList(e_sql.toString());
		} catch (Exception e) {
			logger.error("querySupply->获取导入台报表出错："+e.getMessage(),e);
		}
		System.out.println(e_sql.toString());
		return list;
	}

	@Override
	public List<Map<String, Object>> queryScanSupply(RequestBase requestBase) {
		List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>();
		int layerId = requestBase.getLayer();
		
		String sql="select supply_id,"+requestBase.getLayer()+" as layer_id,max(all_sum) spike_num,max(six_spike) six_spike,sum(all_sum) all_sum,count(report_date) valid_time from report_supply where report_date between ?  and ? and layer_id =? group by layer_id,supply_id";
		try {
			list = template.queryForList(sql,requestBase.getDate(),requestBase.getEnd_date(),layerId);
		} catch (Exception e) {
			logger.error("queryScanSupply->获取导入台峰值列表："+e.getMessage(),e);
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getSixSpikeTime(String stime,
			String etime,int supplyNo, int sixSpikeNum,int layer) {
		List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>();
		String sql="select date_format(date_sub(report_date, interval 61 minute),'%Y-%m-%d %H:%i:%s') stime,date_format(date_sub(report_date, interval 1 minute),'%Y-%m-%d %H:%i:%s') etime from "
				+ "report_supply where report_date between ? and ? and layer_id =? and supply_id=? and six_spike=? ";
		try {
			list = template.queryForList(sql,stime,etime,layer,supplyNo,sixSpikeNum);
		} catch (Exception e) {
			logger.error("getSixSpikeTime->获取导入台峰值时间出错："+e.getMessage(),e);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getSixSpikeData(String stime, String etime,int supplyNo,int layer) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select date_format(report_date,'%H:%i') report_date ,all_sum from report_supply where report_date between ? and ? and layer_id =? and supply_id=? ";
		try {
			list = template.queryForList(sql, stime,etime,layer,supplyNo);
		} catch (Exception e) {
			logger.error("getSixSpikeData->获取导入台峰值出错："+e.getMessage(),e);
		}
		return list;
	}

}
