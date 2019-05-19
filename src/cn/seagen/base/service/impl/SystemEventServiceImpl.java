package cn.seagen.base.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.seagen.base.bean.SystemEvent;
import cn.seagen.base.dao.SystemEventDao;
import cn.seagen.base.enums.ReportType;
import cn.seagen.base.excel.PoiCell;
import cn.seagen.base.excel.PoiData;
import cn.seagen.base.excel.PoiExcel;
import cn.seagen.base.mq.dto.MQEvent;
import cn.seagen.base.service.SystemEventService;
import cn.seagen.base.utils.QueryUtils;
import cn.seagen.base.utils.ReportUtils;
import cn.seagen.base.vo.EasyuiGridData;
import cn.seagen.base.vo.RequestBase;

@Service
public class SystemEventServiceImpl implements SystemEventService {
	
	private Logger logger = LogManager.getLogger(SystemEventServiceImpl.class.getName());
	private SystemEventDao systemEventDaoImpl;
	
	private static String[] titles = new String[] { "序号","代号","级别", "类型","名称","内容","备注",//
	"生成时间"};
	private static String[] fields = new String[] { "f_recno","event_id","event_level",//
	"event_type","event_name","event_val","event_mark","create_time"};
	
	@Resource
	public void setSystemEventDaoImpl(SystemEventDao systemEventDaoImpl) {
		this.systemEventDaoImpl = systemEventDaoImpl;
	}

	@Override
	public boolean insertSystemEvent(MQEvent mQEvent) {
		try {
			SystemEvent systemEvent = systemEventDaoImpl.findFlagEventById(mQEvent.getEvent_id());
			if(null==systemEvent){
				systemEvent = new SystemEvent();
				systemEvent.setEventId(mQEvent.getEvent_id());
				systemEvent.setEventLevel(-1);
				systemEvent.setEventType("-1");
				systemEvent.setEventName(mQEvent.getEvent_target());
				systemEvent.setEventMark("未定义");
			}
			systemEvent.setEventVal(mQEvent.getEvent_val());
			systemEvent.setEventTime(mQEvent.getCreate_time());
			systemEvent.setLayerId(mQEvent.getLayer_id());
			return systemEventDaoImpl.insertSystemEvent(systemEvent);
		} catch (Exception e) {
			logger.error("insertSystemEvent异常!", e);
			return false;
		}
	}

	
	@Override
	public Object findSystemEventList(int page, int rows, int sort,String startTime, String endTime,
			int eventType) {
		EasyuiGridData easyuiGridData = new EasyuiGridData();
		//获取数量的sql
		String sql = QueryUtils.formatQuerySystemEventNumSql(startTime, endTime,eventType);
		//判断sql语句是否正常
		if(StringUtils.isEmpty(sql))
			return easyuiGridData;
		int total = systemEventDaoImpl.findSystemEventListNum(sql);
		//设置页面总数
		easyuiGridData.setTotal(total);
		//获取数据列表的sql
		sql = QueryUtils.formatQuerySystemEventSql(startTime,endTime,eventType,sort, page, rows);
		if(StringUtils.isEmpty(sql))
			return easyuiGridData;
		List<Map<String, Object>> list= systemEventDaoImpl.findSystemEventList(sql);
		//设置页面显示数据
		easyuiGridData.setRows(list);
		return easyuiGridData;
	}

	@Override
	public byte[] exportEvent(RequestBase requestBase,int sort) {
		logger.info("exportEvent:startTm=" + requestBase.getDate() +",endTm=" //
				+ requestBase.getEnd_date() + ",type=" + requestBase.getType());
		PoiData poiData = new PoiData();

		// 根据type：0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)
		int type = requestBase.getType();
		// 固定标题第一行
		String first_name = ReportUtils.getReportFirstName(type, requestBase);

		// Title
		PoiCell pcTitle = new PoiCell("运行事件查询");
		poiData.setTitle(pcTitle);
		// Query
		PoiCell pcQuery = new PoiCell(first_name);
		poiData.setQuery(pcQuery);
		// Head
		List<List<PoiCell>> heads = new ArrayList<List<PoiCell>>();
		if (type == ReportType.REP_TIME.getValue()) {
			List<PoiCell> pcHead1 = new ArrayList<PoiCell>();
			for (int i = 0; i < titles.length; i++)
				pcHead1.add(new PoiCell(titles[i]));
			heads.add(pcHead1);
		} 
		poiData.setHeads(heads);

		// Data
		List<List<PoiCell>> datas = new ArrayList<List<PoiCell>>();
		EasyuiGridData easyuiGridData = new EasyuiGridData();
		int page = 1;
		easyuiGridData = (EasyuiGridData) findSystemEventList(page, requestBase.getRows(), sort, requestBase.getDate(), requestBase.getEnd_date(), requestBase.getType());
		List<Map<String, Object>> dataList = easyuiGridData.getRows();	
		if (type == ReportType.REP_TIME.getValue()) {
			Map<String, Object> map = null;
			for (int i = 0; i < dataList.size(); i++) {
				map = dataList.get(i);// 拿到一行数据
				List<PoiCell> pcdata = new ArrayList<PoiCell>();
				for (int j = 0; j < fields.length; j++) {
					pcdata.add(new PoiCell(map.get(fields[j]).toString()));
				}
				datas.add(pcdata);
			}
		}
		poiData.setDatas(datas);

		PoiExcel pe = new PoiExcel();
		pe.setSplitPos(5, 2); // 冻结行列
		pe.setDataAndExcute(poiData);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			pe.getWorkbook().write(out);
			return out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			ReportUtils.CloseOpStream(out);
		}
	}
}

