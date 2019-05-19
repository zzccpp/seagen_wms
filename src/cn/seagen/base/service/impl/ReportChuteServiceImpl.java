package cn.seagen.base.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.seagen.base.dao.ReportChuteDao;
import cn.seagen.base.enums.ReportType;
import cn.seagen.base.service.ReportChuteService;
import cn.seagen.base.utils.JUtils;
import cn.seagen.base.utils.ReportUtils;
import cn.seagen.base.vo.EasyuiGridSumData;
import cn.seagen.base.vo.Reportfun;
import cn.seagen.base.vo.RequestBase;
@Service
public class ReportChuteServiceImpl implements ReportChuteService,Serializable{
	private static final long serialVersionUID = -2952660292872328125L;
	private static Logger logger = LogManager.getLogger(ReportChuteService.class.getName());
	@Resource
	private ReportChuteDao reportChuteDaoImpl;
	
	private static String[] titles = new String[] { "快件量","快件占比", "建包量","包占比"};
	private static String[] fields = new String[] { "sorting_count","sorting_count_level", //
		"box_count","box_count_level"};
	@Override
	public Object queryChute(RequestBase requestBase) {
		logger.info("querychute:startTm=" + requestBase.getDate() + ",endTm=" //
				+ requestBase.getEnd_date() + ",type=" + requestBase.getType()+",rule="+requestBase.getRule());
		// 特殊规则：如排序、是否开启功能（显示该数据）（当有时）
		int rule = requestBase.getRule();
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
		List<Object> columns_row1 = new ArrayList<Object>();
		List<Object> columns_row2 = new ArrayList<Object>();
		// 根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)来设置不固定子标题
		if (type == ReportType.REP_TIME.getValue()) {
			//根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)来设置固定子标题
			frozencolumn = Reportfun.getColumn(rule == 0?"格口号":"区域", rule == 0?"chute_id":"area_name", true);
			columns_row1.add(Reportfun.getColumn(first_name, 1, titles.length));
			for(int i = 0;i < titles.length;i++){
				if(i%2==0){
					columns_row2.add(Reportfun.getColumn(titles[i], fields[i], true));
				} else {
					columns_row2.add(Reportfun.getColumn(titles[i], fields[i], "format_two_decimal", true));
				}
			}
		}
		if (type == ReportType.REP_DAY.getValue() || type == ReportType.REP_MONTH.getValue()//
				|| type == ReportType.REP_YEAR.getValue()) {
			//根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)来设置固定子标题
			frozencolumn = Reportfun.getColumn(first_name, rule == 0?"chute_id":"area_name", true);
			for (int i = 1; i <= nums; i++) {
				// 一天24小时
				String titlename = ReportUtils.getReportHead(type, i-1);;
				String fieldname = String.format("%2d", i).replace(" ", "0");
				columns_row1.add(Reportfun.getColumn(titlename, 1, titles.length));
				for(int j = 0;j < titles.length;j++){
//					columns_row2.add(Reportfun.getColumn(titles[j], fields[j]+fieldname, true));
					if(j%2==0){
						columns_row2.add(Reportfun.getColumn(titles[j], fields[j]+fieldname, true));
					} else {
						columns_row2.add(Reportfun.getColumn(titles[j], fields[j]+fieldname,//
								"format_two_decimal", true));
					}
				}
			}
		}
		columnsList.add(columns_row1);
		columnsList.add(columns_row2);
		frozenColumnsList.add(frozencolumn);
		List<Map<String, Object>> dataList = reportChuteDaoImpl.queryChute(requestBase);	
		// 求和数据，算比例
		Map<String, Object> totaldata = new HashMap<String, Object>();
		
		// 算比例 百分比
		String dname = "";
		String pname = "";
		String jbname = "";
		String p_pname = ""; // 快件占比
		String jb_pname = ""; // 包占比
		double percent = 0.0; // 百分比

		int total = 0;
		Object ob = null;
		if(type == ReportType.REP_TIME.getValue()){
			for (Map<String,Object> map : dataList) {
				// 以下是统计
				ob = map.get("sorting_count");
				total = (Integer) (totaldata.containsKey("sorting_count")//
						? ((Integer)totaldata.get("sorting_count") + Integer.parseInt(ob.toString()))//
								: Integer.parseInt(ob.toString()));
				totaldata.put("sorting_count", total); // 快件量
				ob = map.get("box_count");
				total =  (Integer) (totaldata.containsKey("box_count") //
						? ((Integer)totaldata.get("box_count") + Integer.parseInt(ob.toString()))//
								: Integer.parseInt(ob.toString()));
				totaldata.put("box_count", total); // 建包量
			}
		} else {
			for (Map<String,Object> map : dataList) {
				for (int j = 1; j <= nums; j++) {
					dname = String.format("%2d", j).replace(" ", "0");
					// 以下是统计
					ob = map.get("sorting_count"+dname);
					total = (Integer) (totaldata.containsKey("sorting_count"+dname) //
							? ((Integer)totaldata.get("sorting_count"+dname) + Integer.parseInt(ob.toString())) //
									: Integer.parseInt(ob.toString()));
					totaldata.put("sorting_count"+dname, total); // 快件量
					ob = map.get("box_count"+dname);
					total =  (Integer) (totaldata.containsKey("box_count"+dname) //
							? ((Integer)totaldata.get("box_count"+dname) + Integer.parseInt(ob.toString()))//
									: Integer.parseInt(ob.toString()));
					totaldata.put("box_count"+dname, total); // 建包量
				}
			}
		}
		
		// 算的是每个格口当天的数据量在当天的占比
		for (int i = 0; i < dataList.size(); i++) {
			if(type == ReportType.REP_TIME.getValue()){
				pname = ("sorting_count").toLowerCase(); // 快件量
				jbname = ("box_count").toLowerCase(); // 建包量
				p_pname = ("sorting_count_level").toLowerCase(); // 快件占比
				jb_pname = ("box_count_level").toLowerCase(); // 包占比
				//
				percent = (Integer) totaldata.get(pname);
				Map<String, Object> temp = dataList.get(i);
				double data = Integer.parseInt(temp.get(pname).toString()) * 1.00;
				percent = (percent > 0) ? ((data * 100) / percent) : 0;
				dataList.get(i).put(p_pname, JUtils.convertFloat(percent));
				//
				percent = (Integer) totaldata.get(jbname);
				data = Integer.parseInt(temp.get(jbname).toString()) * 1.00;
				percent = (percent > 0) ? ((data * 100) / percent) : 0;
				dataList.get(i).put(jb_pname, JUtils.convertFloat(percent));
			}
			for (int j = 1; j <= nums; j++) {
				dname = String.format("%2d", j).replace(" ", "0");
				pname = ("sorting_count" + dname).toLowerCase(); // 快件量
				jbname = ("box_count" + dname).toLowerCase(); // 建包量
				p_pname = ("sorting_count_level" + dname).toLowerCase(); // 快件占比
				jb_pname = ("box_count_level" + dname).toLowerCase(); // 包占比
				//
				percent = (Integer) totaldata.get(pname);
				Map<String, Object> temp = dataList.get(i);
				double data = Integer.parseInt(temp.get(pname).toString()) * 1.00;
				percent = (percent > 0) ? ((data * 100) / percent) : 0;
				dataList.get(i).put(p_pname, JUtils.convertFloat(percent));
				//
				percent = (Integer) totaldata.get(jbname);
				data = Integer.parseInt(temp.get(jbname).toString()) * 1.00;
				percent = (percent > 0) ? ((data * 100) / percent) : 0;
				dataList.get(i).put(jb_pname, JUtils.convertFloat(percent));
			}
		}

		int p_sum = 0; // 一个月的运单总量
		int jbsum = 0; // 一个月的建包总量
		if(type == ReportType.REP_TIME.getValue()){
			pname = ("sorting_count").toLowerCase(); // 快件量
			jbname = ("box_count").toLowerCase(); // 建包量
			p_sum += (totaldata.containsKey(pname) ? (Integer) totaldata.get(pname) : 0);
			jbsum += (totaldata.containsKey(jbname) ? (Integer) totaldata.get(jbname) : 0);
		}
		// 总量
		for (int i = 1; i <= nums; i++) {
			dname = String.format("%2d", i).replace(" ", "0");
			pname = ("sorting_count" + dname).toLowerCase(); // 快件量
			jbname = ("box_count" + dname).toLowerCase(); // 建包量
			p_sum += (totaldata.containsKey(pname) ? (Integer) totaldata.get(pname) : 0);
			jbsum += (totaldata.containsKey(jbname) ? (Integer) totaldata.get(jbname) : 0);
		}
		if(type == ReportType.REP_TIME.getValue()){
			pname = ("sorting_count").toLowerCase(); // 快件量
			jbname = ("box_count").toLowerCase(); // 建包量
			p_pname = ("sorting_count_level").toLowerCase(); // 快件占比
			jb_pname = ("box_count_level").toLowerCase(); // 包占比
			//
			percent = (totaldata.containsKey(pname) ? (Integer) totaldata.get(pname) : 0);
			percent = (p_sum > 0) ? ((percent * 1.0 / p_sum) * 100) : 0;
			totaldata.put(p_pname, JUtils.convertFloat(percent));
			//
			percent = (totaldata.containsKey(jbname) ? (Integer) totaldata.get(jbname) : 0);
			percent = (jbsum > 0) ? ((percent * 1.0 / jbsum) * 100) : 0;
			totaldata.put(jb_pname, JUtils.convertFloat(percent));
		}
		// 每个格口每天的数据量在当月的占比
		for (int i = 1; i <= nums; i++) {
			dname = String.format("%2d", i).replace(" ", "0");
			pname = ("sorting_count" + dname).toLowerCase(); // 快件量
			jbname = ("box_count" + dname).toLowerCase(); // 建包量
			p_pname = ("sorting_count_level" + dname).toLowerCase(); // 快件占比
			jb_pname = ("box_count_level" + dname).toLowerCase(); // 包占比
			//
			percent = (totaldata.containsKey(pname) ? (Integer) totaldata.get(pname) : 0);
			percent = (p_sum > 0) ? ((percent * 1.0 / p_sum) * 100) : 0;
			totaldata.put(p_pname, JUtils.convertFloat(percent));
			//
			percent = (totaldata.containsKey(jbname) ? (Integer) totaldata.get(jbname) : 0);
			percent = (jbsum > 0) ? ((percent * 1.0 / jbsum) * 100) : 0;
			totaldata.put(jb_pname, JUtils.convertFloat(percent));
		}

		easyuiGridSumData.setData(dataList);
		easyuiGridSumData.setColumns(columnsList);
		easyuiGridSumData.setFrozenColumns(frozenColumnsList);
		totaldata.put(rule == 0?"chute_id":"area_name", "合计");
		easyuiGridSumData.setFooter(totaldata);
		return easyuiGridSumData;
	}

}
