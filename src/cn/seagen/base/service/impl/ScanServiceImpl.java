package cn.seagen.base.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.seagen.base.bean.Waybill;
import cn.seagen.base.dao.ScanDao;
import cn.seagen.base.service.ScanService;
import cn.seagen.base.utils.DateUtils;
import cn.seagen.base.utils.QueryUtils;
import cn.seagen.base.utils.ScheduledRuntimerManager;
import cn.seagen.base.vo.EasyuiGridData;
import cn.seagen.base.vo.ScheduledRuntimer;
@Service
public class ScanServiceImpl implements ScanService {
	private static Logger logger = LogManager.getLogger(ScanServiceImpl.class);
	@Resource
	private ScanDao scanDaoImpl;
	private static ScheduledRuntimer runtimer = null;
	private ScheduledRuntimer getRuntimer() {
		if (runtimer == null)
			runtimer = ScheduledRuntimerManager.getRuntimer("Scan");
		return runtimer;
	}
	@Override
	public boolean insertScanInfo(Waybill waybill) {
		long begintime = System.currentTimeMillis();
		if(scanDaoImpl.insertScanInfo(waybill,"scan_"+DateUtils.findYearMonth(1)+"_0"+(waybill.getChute_id()%3))){
			logger.info("扫描信息入库成功："+waybill.toString());
			long runtime = System.currentTimeMillis() - begintime;
			getRuntimer().add(runtime);
		} else {
			logger.error("扫描信息入库失败:"+waybill.toString());
			return false;
		}
		return true;
	}
	@Override
	public List<Map<String, Object>> findScanInfos(String packageCode) {
		return scanDaoImpl.findScanInfos(packageCode);
	}
	@Override
	public List<Map<String, Object>> findScanBeforeOrAfter(int supplyId,String scanTime) {
		return scanDaoImpl.findScanBeforeOrAfter(supplyId, scanTime);
	}
	@Override
	public Object findScanList(String packagecode,int pages,int rows) {
		EasyuiGridData easyuiGridData = new EasyuiGridData();
		//获取数量的sql
		String sql = QueryUtils.formatQueryScanNumSql(packagecode);
		//判断sql语句是否正常
		if(StringUtils.isEmpty(sql))
			return easyuiGridData;
		int total = scanDaoImpl.findScanListNum(sql);
		//设置页面总数
		easyuiGridData.setTotal(total);
		//获取数据列表的sql
		sql = QueryUtils.formatQueryScanSql(packagecode, pages, rows);
		if(StringUtils.isEmpty(sql))
			return easyuiGridData;
		List<Map<String, Object>> list= scanDaoImpl.findScanList(sql);
		//设置页面显示数据
		easyuiGridData.setRows(list);
		return easyuiGridData;
	}
}
