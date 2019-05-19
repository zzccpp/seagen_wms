package cn.seagen.base.mq.work;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.seagen.base.constant.SystemConstant;
import cn.seagen.base.mq.dto.MQBase;
import cn.seagen.base.mq.dto.MQEvent;
import cn.seagen.base.mq.dto.MQJb;
import cn.seagen.base.mq.dto.MQLineNum;
import cn.seagen.base.mq.dto.MQLog;
import cn.seagen.base.mq.dto.MQPower;
import cn.seagen.base.mq.dto.MQSort;
import cn.seagen.base.mq.dto.MQSortMode;
import cn.seagen.base.service.JbService;
import cn.seagen.base.service.MqProducer;
import cn.seagen.base.service.ScanService;
import cn.seagen.base.service.SortedService;
import cn.seagen.base.service.SystemEventService;
import cn.seagen.base.utils.ConvertUtils;
import cn.seagen.base.utils.FastJsonUtils;
@Service
public class MQTask implements MQMessage{
	@Resource
	private SortedService sortedServiceImpl;
	@Resource
	private ScanService scanServiceImpl;
	@Resource
	private SystemEventService systemEventServiceImpl;
	@Resource
	private JbService jbServiceImpl;
	@Resource
	private MqProducer mqProducerImpl;
	@Override
	public boolean scan_process(MQSort mQScan) {
		boolean result = false;
		try {
			result =scanServiceImpl.insertScanInfo(ConvertUtils.convertFromObject(mQScan));
		} catch (Exception e) {
			//e.printStackTrace();
			mqProducerImpl.sendToFqueue(FastJsonUtils.parseObject(mQScan.getMessage(),MQSort.class));
			return false;
		}
		return result;
	}

	@Override
	public boolean sorting_process(MQSort mQSorting) {
		return scan_process(mQSorting);
	}

	@Override
	public boolean sorted_process(MQSort mQSorted) {
		boolean result = false;
		try {
			result = sortedServiceImpl.insertSorted(ConvertUtils.convertFromObject(mQSorted));
		} catch (Exception e) {
			//e.printStackTrace();
			mqProducerImpl.sendToFqueue(FastJsonUtils.parseObject(mQSorted.getMessage(),MQSort.class));
			return false;
		}
		return result;
	}

	@Override
	public boolean jb_process(MQJb mQJb) {
		return jbServiceImpl.jbProcess(mQJb);
	}

	@Override
	public boolean power_process(MQPower mQPower) {
		int event_id = (mQPower.isTurn_on() ? 1 : 2);
		String event_val = (mQPower.isTurn_on() ? "开机" : "关机");
		// 记录开机、关机操作事件
		MQEvent event = new MQEvent();
		event.setEvent_id(event_id);
		event.setEvent_val(event_val);
		event.setCreate_time(mQPower.getCreate_time());
		systemEventServiceImpl.insertSystemEvent(event);
		// 记录日志
		//LogDao.record_log(event_id, event_val, event_val, mQPower.getCreate_time());
		// 设置分拣模式 只有开机才设置
		if (mQPower.isTurn_on()) {
			// 开机要取批次
			SystemConstant.BATCH_ID = sortedServiceImpl.getBatchId(mQPower.isReset());
			//
			MQSortMode mQSortMode = new MQSortMode();
			mQSortMode.setSort_mode(mQPower.getSort_mode());
			mQSortMode.setCreate_time(mQPower.getCreate_time());
			return sortmode_process(mQSortMode);
		} else
			return true;
	}

	@Override
	public boolean linenum_process(MQLineNum mQLineNum) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sortmode_process(MQSortMode mQSortMode) {
		return true;
	}

	@Override
	public boolean event_process(MQEvent mQEvent) {
		return systemEventServiceImpl.insertSystemEvent(mQEvent);
	}

	@Override
	public boolean log_process(MQLog mQLog) {
		return false;
	}

	@Override
	public boolean doExcMsg(MQBase mqBase) {
		return true;
	}

	@Override
	public boolean doExcMsg(String message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sorting_extent(MQBase mqBase) {
		// TODO Auto-generated method stub
		return false;
	}

}
