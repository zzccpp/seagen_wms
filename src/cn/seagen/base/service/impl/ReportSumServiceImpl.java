package cn.seagen.base.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.seagen.base.dao.ReportSumDao;
import cn.seagen.base.enums.ReportType;
import cn.seagen.base.excel.PoiCell;
import cn.seagen.base.excel.PoiData;
import cn.seagen.base.excel.PoiExcel;
import cn.seagen.base.service.ReportSumService;
import cn.seagen.base.utils.DateUtils;
import cn.seagen.base.utils.ReportUtils;
import cn.seagen.base.vo.EasyuiGridSumData;
import cn.seagen.base.vo.Reportfun;
import cn.seagen.base.vo.RequestBase;
@Service
public class ReportSumServiceImpl implements ReportSumService{
	private static Logger logger = LogManager.getLogger(ReportSumServiceImpl.class);

	@Resource
	private ReportSumDao reportSumDaoImpl;
	
	public ReportSumServiceImpl() {
		super();
	}

	@Override
	public Object querySum(RequestBase requestBase) {
		logger.info("querySum:startTm=" + requestBase.getDate() + //
				",endTm=" + requestBase.getEnd_date() + ",type=" + requestBase.getType());
		EasyuiGridSumData easyuiGridSumData = new EasyuiGridSumData();
		// 根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)
		int type = requestBase.getType();
		// 存在多级标题时，第一行标题个数
		int nums = ReportUtils.getReportNums(type, requestBase.getDate());
		// 固定标题第一行
		String first_name = ReportUtils.getReportFirstName(type, requestBase);
		// 固定标题
		List<Object> frozenColumnsList = new ArrayList<Object>();
		// 固定标题的子标题
		Map<String, Object> frozencolumn = new HashMap<String,Object>();
		
		// 不固定标题
		List<Object> columnsList = new ArrayList<Object>();
		// 根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)来设置不固定子标题
		if (type == ReportType.REP_TIME.getValue()) {
			//根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)来设置固定子标题
			frozencolumn = Reportfun.getColumn("类型", "col_name",150,true);
			columnsList.add(Reportfun.getColumn(first_name, "date_vale",true));
		}
		if (type == ReportType.REP_DAY.getValue() || type == ReportType.REP_MONTH.getValue() //
				|| type == ReportType.REP_YEAR.getValue()) {
			//根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)来设置固定子标题
			frozencolumn = Reportfun.getColumn(first_name, "col_name",150,true);
			for (int i = 1; i <= nums; i++) {
				// 一天24小时
				String titlename = ReportUtils.getReportHead(type, i-1);
				String fieldname = "d"+String.format("%2d", i).replace(" ", "0");
				columnsList.add(Reportfun.getColumn(titlename, fieldname));
			}
		}
		frozenColumnsList.add(frozencolumn);
		//组装查询语句
		String sql = formatSumSql(requestBase.getDate(), requestBase.getEnd_date(), type, requestBase.getMore());
		List<Map<String, Object>> dataList = reportSumDaoImpl.querySum(sql);	
		easyuiGridSumData.setData(dataList);
		
		easyuiGridSumData.setColumns(columnsList);
		easyuiGridSumData.setFrozenColumns(frozenColumnsList);

		return easyuiGridSumData;
	}

	@Override
	public byte[] exportSum(RequestBase requestBase) {
		logger.info("exportSum:startTm=" + requestBase.getDate() +",endTm=" //
				+ requestBase.getEnd_date() + ",type=" + requestBase.getType());
		PoiData poiData = new PoiData();

		// 根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)
		int type = requestBase.getType();
		// 存在多级标题时，第一行标题个数
		int nums = ReportUtils.getReportNums(type, requestBase.getDate());
		// 固定标题第一行
		String first_name = ReportUtils.getReportFirstName(type, requestBase);

		// Title
		PoiCell pcTitle = new PoiCell("分拣统计查询");
		poiData.setTitle(pcTitle);
		// Query
		PoiCell pcQuery = new PoiCell(first_name);
		poiData.setQuery(pcQuery);
		// Head
		List<List<PoiCell>> heads = new ArrayList<List<PoiCell>>();
		if (type == ReportType.REP_TIME.getValue()) {
			List<PoiCell> pcHead1 = new ArrayList<PoiCell>();
			pcHead1.add(new PoiCell("类型"));
			pcHead1.add(new PoiCell(first_name));
			heads.add(pcHead1);
		} else {// (此处是提供相对于以左上1,1格子为参照的精准的CELL位置)
			List<PoiCell> pcHead1 = new ArrayList<PoiCell>();

			pcHead1.add(new PoiCell(1, 1, 1, 1, first_name));
			for (int i = 0; i < nums; i++) {
				String titlename = ReportUtils.getReportHead(type, i);
				pcHead1.add(new PoiCell(1, 1, 2 + i, 1, titlename));
			}
			heads.add(pcHead1);
		}
		poiData.setHeads(heads);

		// Data
		List<List<PoiCell>> datas = new ArrayList<List<PoiCell>>();
		//组装查询语句
		String sql = formatSumSql(requestBase.getDate(), requestBase.getEnd_date(), type, requestBase.getMore());
		List<Map<String, Object>> dataList = reportSumDaoImpl.querySum(sql);	
		if (type == ReportType.REP_TIME.getValue()) {
			Map<String, Object> map = null;
			for (int i = 0; i < dataList.size(); i++) {
				map = dataList.get(i);// 拿到一行数据
				List<PoiCell> pcdata = new ArrayList<PoiCell>();
				pcdata.add(new PoiCell(map.get("col_name").toString()));
				pcdata.add(new PoiCell(map.get("date_vale").toString()));
				datas.add(pcdata);
			}
		} else {
			Map<String, Object> map = null;
			for (int i = 0; i < dataList.size(); i++) {
				map = dataList.get(i);// 拿到一行数据
				List<PoiCell> pcdata = new ArrayList<PoiCell>();
				pcdata.add(new PoiCell(map.get("col_name").toString()));
				for (int j = 0; j < nums; j++) {
					String valuename = "d"+String.format("%2d", j + 1).replace(" ", "0");
					pcdata.add(new PoiCell(map.get(valuename).toString()));
				}
				datas.add(pcdata);
			}
		}
		poiData.setDatas(datas);

		//
		List<List<PoiCell>> footers = new ArrayList<List<PoiCell>>();
		List<PoiCell> footerdata = new ArrayList<PoiCell>();
		for (int i = 0; i < datas.get(0).size(); i++) {
			footerdata.add(new PoiCell(""));
		}
		footers.add(footerdata);
		poiData.setFooters(footers);
		//
		PoiExcel pe = new PoiExcel();
		pe.setSplitPos(5, 2); // 冻结行列
		pe.setDataAndExcute(poiData);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			pe.getWorkbook().write(out);
			return out.toByteArray();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			ReportUtils.CloseOpStream(out);
		}
	}
	
	/**
	 * 格式化汇总量统计的sql语句
	 * @param i_date 指定查询某日、月、年的数据，也是时间段查询的开始日期
	 * @param i_end_date 指定时间段查询的结束日期
	 * @param i_type 0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)
	 * @param i_more 是否显示更多信息0不显示，非0显示
	 * @return
	 */
	public static String formatSumSql(String i_date,String i_end_date ,int i_type,int i_more){
		//判断请求的数据类型
		int type = i_type;
		if(i_type < 0||i_type > 3)
			type = 1;

		//格式化时间，默认现在
		if(StringUtils.isEmpty(i_date))
			i_date = DateUtils.getNow();
		
		String e_sql = "";
		String t_sql = "";
		//0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)
		if (type==0){
			//格式化查询的结束时间，默认现在
			if(StringUtils.isEmpty(i_end_date))
				i_end_date = DateUtils.getNow();
			//中转SQL----------------------------
			t_sql = t_sql + " \n(select 'collect_name' as col_name,ifnull(field_name, 0) as date_vale from report_sorting where "
					+ "sum_type = 1 and date_format(begin_time, '%Y-%m-%d %H:%i') between date_format('"+i_date+"', '%Y-%m-%d %H:%i') "
					+ "and date_format('"+i_end_date+"', '%Y-%m-%d %H:%i') ) \n";
		} else {
			String date_format_where_str = ReportUtils.getTimeFormat(1, type);
			String date_format_deal_str = ReportUtils.getTimeFormat(2, type);
			//获取列数
			int v_col = ReportUtils.getColNum(type,i_date);

			//REPORT表中明细段----------------------------
			String r_sql = "\n(select date_format(begin_time, '"+ date_format_deal_str+"') as dt, field_name "
					+ "as date_vale from report_sorting where sum_type = 1 and date_format(begin_time, '"
					+date_format_where_str+"') = date_format('"+i_date+"', '"+date_format_where_str+"') group by dt ) a \n";

			//中转SQL-------------------------------------
			t_sql = t_sql + " \n(select 'collect_name' as col_name \n";

			//时间条件段
			int d_sql = 1;
			while(d_sql <= v_col){
				String d_sql2s = ReportUtils.formatStr(""+d_sql, 2); //前补0，补齐2位
				t_sql = t_sql + ", ifnull(sum(case when date_val = '"+ d_sql2s+"' then date_vale else 0 end), 0) as d"+d_sql2s+" \n";
				d_sql = d_sql + 1;
			}

			String t_sql_where ="";
			if(type >1)
				t_sql_where = " where dt = date_val";

			t_sql= t_sql + " from  flag_date left join "+ r_sql+" on convert(flag_date.date_val, signed) = "
					+ "(case "+type+" when 1 then convert(dt, signed) + 1 else convert(flag_date.date_val, signed) end)"+t_sql_where+") \n";
		}
		e_sql= e_sql + "SELECT * FROM ( \n";
		//组合报表
		//'分拣机吞吐量(件)
		e_sql = e_sql + t_sql.replace("collect_name", "分拣总吞吐量(件)").replace("field_name", "sum(all_sum)");
		//'正常分拣量(件)'
		e_sql = e_sql + " union " + t_sql.replace("collect_name", "正常分拣量(件)").replace("field_name", "sum(success_sum)");
		//'异常分拣量(件)'
		e_sql = e_sql + " union " + t_sql.replace("collect_name", "异常分拣量(件)").replace("field_name", "sum(err_sum)");
		//'无读量(件)'
		e_sql = e_sql + " union " + t_sql.replace("collect_name", "无读量(件)").replace("field_name", "sum(no_reade)");
		//正常分拣率[不含无读](%)'
		e_sql = e_sql + " union " + t_sql.replace("collect_name", "分拣率[不含无读](%)").replace("field_name", 
				"(case when sum(all_sum) > 0 then format((sum(success_sum) *100) /sum(all_sum),3) else 0 end)");
		//正常分拣率[包含无读](%)'
		e_sql = e_sql + " union " + t_sql.replace("collect_name", "分拣率[包含无读](%)").replace("field_name", 
				"(case when sum(all_sum) > 0 then format((sum(success_sum + no_reade) *100) /sum(all_sum),3) else 0 end)");
		//'打包量(包)'
		e_sql = e_sql + " union " + t_sql.replace("collect_name", "打包量(包)").replace("field_name", "sum(box_sum)");
		//'分拣机工作时间(H)'
		e_sql = e_sql + " union " + t_sql.replace("collect_name", "分拣机工作时间(H)").replace("field_name", 
				"format(sum(timestampdiff(second, begin_time, end_time) / 3600.0), 2)");
		//每小时处理件数(件)'
		e_sql = e_sql + " union " + t_sql.replace("collect_name", "每小时分拣量(件/H)").replace("field_name", 
				"(case when format(sum(timestampdiff(second, begin_time, end_time)) / 3600.0, 2) > 0 "
				+ "then truncate( sum(all_sum) / format(sum(timestampdiff(second, begin_time, end_time)) / 3600.0, 2), 0) else 0 end)");

		if(i_more != 0) {
			//无目的地(数据库未找到)'
			e_sql = e_sql + " union " + t_sql.replace("collect_name", "无目的地(无方案)").replace("field_name", "sum(no_data)");
			//'迷失(件数)'
			e_sql = e_sql + " union " + t_sql.replace("collect_name", "迷失(件)").replace("field_name", "sum(lost_data)");
			//'最大运行圈数(件数)'
			e_sql = e_sql + " union " + t_sql.replace("collect_name", "最大循环圈数(次)").replace("field_name", "sum(max_cycles)");
			//'无分拣方案(未分配格口)'
			e_sql = e_sql + " union " + t_sql.replace("collect_name", "无分拣方案(未分配格口)").replace("field_name", "sum(no_chute)");
			//'读取多条码'
			e_sql = e_sql + " union " + t_sql.replace("collect_name", "读取多条码(件)").replace("field_name", "sum(more_data)");
			//'快件拦截(订单取消)'
			e_sql = e_sql + " union " + t_sql.replace("collect_name", "格口投错(件)").replace("field_name", "sum(err_chute)");
			//'快件拦截(订单取消)'
			e_sql = e_sql + " union " + t_sql.replace("collect_name", "快件拦截/取消(件)").replace("field_name", "sum(cancel_sum)");
		}

		e_sql = e_sql +") B";
		
		return e_sql;
	}
}
