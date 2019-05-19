package cn.seagen.base.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.seagen.base.bean.Waybill;
import cn.seagen.base.constant.OptConstant;
import cn.seagen.base.dao.SortedDao;
import cn.seagen.base.service.SortedService;
import cn.seagen.base.utils.DateUtils;
import cn.seagen.base.utils.QueryUtils;
import cn.seagen.base.utils.ScheduledRuntimerManager;
import cn.seagen.base.vo.EasyuiGridData;
import cn.seagen.base.vo.ScheduledRuntimer;
@Service
public class SortedServiceImpl implements SortedService{
	private static Logger logger = LogManager.getLogger(SortedServiceImpl.class);

	@Resource
	private SortedDao sortedDaoImpl;
	private static ScheduledRuntimer runtimer = null;
	private ScheduledRuntimer getRuntimer() {
		if (runtimer == null)
			runtimer = ScheduledRuntimerManager.getRuntimer("Sorted");
		return runtimer;
	}
	@Override
	public boolean insertSorted(Waybill waybill) {
		////String sql = SortingUtils.formatInsertSortSql(waybill);
		//String sql =  "insert into sorting(waybill_code,chute_id,package_code,waybill_status,scan_cycle,scan_status,sorting_time) values('"+waybill.getPackage_code()+"',"+waybill.getChute_id()+",'"+waybill.getPackage_code()+"',"+waybill.getWaybill_status()+","+waybill.getScan_cycle()+","+waybill.getScan_status()+",'"+waybill.getReceive_time()+"')";
		long begintime = System.currentTimeMillis();
		if(sortedDaoImpl.insertSorted(waybill,"sorting_"+DateUtils.findYearMonth(1)+"_0"+(waybill.getChute_id()%3))){
			logger.info("分拣信息入库成功："+waybill.toString());
			long runtime = System.currentTimeMillis() - begintime;
			getRuntimer().add(runtime);
		} else {
			logger.error("分拣信息入库失败:"+waybill.toString());
			return false;
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> findSortedInfos(String packageCode) {
		return sortedDaoImpl.findSortedInfos(packageCode);
	}

	@Override
	public List<Map<String, Object>> findCarBeforeOrAfter(int carId,String scanTime) {
		return sortedDaoImpl.findCarBeforeOrAfter(carId, scanTime);
	}
	
	@Override
	public Object findSortedList(String packagecode, String boxcode,int pages,int rows) {
		EasyuiGridData easyuiGridData = new EasyuiGridData();
		//获取数量的sql
		String sql = QueryUtils.formatQuerySoretedNumSql(packagecode, boxcode);
		//判断sql语句是否正常
		if(StringUtils.isEmpty(sql))
			return easyuiGridData;
		int total = sortedDaoImpl.findSortedListNum(sql);
		//设置页面总数
		easyuiGridData.setTotal(total);
		//获取数据列表的sql
		sql = QueryUtils.formatQuerySortedSql(packagecode, boxcode, pages, rows);
		if(StringUtils.isEmpty(sql))
			return easyuiGridData;
		List<Map<String, Object>> list= sortedDaoImpl.findSortedList(sql);
		//设置页面显示数据
		easyuiGridData.setRows(list);
		return easyuiGridData;
	}

	@Override
	public Object findSortDetailList(String date_start, String date_end,
			int update_flag, int pages, int rows) {
		EasyuiGridData easyuiGridData = new EasyuiGridData();
		//获取数量的sql
		String sql = QueryUtils.formatQuerySoretDetailNumSql(date_start, date_end, update_flag);
		//判断sql语句是否正常
		if(StringUtils.isEmpty(sql))
			return easyuiGridData;
		int total = sortedDaoImpl.findSortedListNum(sql);
		//设置页面总数
		easyuiGridData.setTotal(total);
		//获取数据列表的sql
		sql = QueryUtils.formatQuerySortDetailSql(date_start, date_end, update_flag, pages, rows);
		if(StringUtils.isEmpty(sql))
			return easyuiGridData;
		List<Map<String, Object>> list= sortedDaoImpl.findSortedList(sql);
		//设置页面显示数据
		easyuiGridData.setRows(list);
		return easyuiGridData;
	}

	@Override
	public List<Waybill> getUnJbSortedList(int chuteId) {
		return sortedDaoImpl.getUnJbSortedList(chuteId);
	}
	
	@Override
	public boolean updateJbSortedList(List<Waybill> list, int chuteId) {
		int len = list.size();
		String[] sqls = new String[len];
		for (int i = 0; i < len; i++) {
			String boxCode = "";
			if (list.get(i).getJb_status() == OptConstant.JB_DISABLE)
				boxCode = "";
			else
				boxCode = list.get(i).getBox_code();
			String sql = "update sorting_"+list.get(i).getTab_flag()+"_0"+(chuteId%3)+" set "//
					+ "jb_status = "+list.get(i).getJb_status()+", "
					+ "box_code = '"+list.get(i).getBox_code()+"', "
					+ "box_site_code = '"+boxCode+"', " //
					+ "box_site_name = '"+list.get(i).getBox_site_name()+"', "
					+ "jb_time = '"+list.get(i).getJb_time()+"', "
					+ "jb_update_flag = "+list.get(i).getJb_update_flag()+", "
					+ "jb_update_time = '"+list.get(i).getJb_update_time()+"' " //
					+ "where f_recno = "+list.get(i).getF_recno()+";";
			sqls[i] = sql;
		}
		return sortedDaoImpl.updateJbSortedList(sqls);
	}

	@Override
	public int getBatchId(boolean isNew) {
		return sortedDaoImpl.getBatchId(isNew);
	}
}
