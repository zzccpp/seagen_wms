package cn.seagen.base.mq.work;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.seagen.base.constant.SystemConstant;
import cn.seagen.base.mq.dto.MQBase;
import cn.seagen.base.mq.dto.MQEvent;
import cn.seagen.base.mq.dto.MQJb;
import cn.seagen.base.mq.dto.MQLineNum;
import cn.seagen.base.mq.dto.MQLog;
import cn.seagen.base.mq.dto.MQPower;
import cn.seagen.base.mq.dto.MQSort;
import cn.seagen.base.mq.dto.MQSortMode;
import cn.seagen.base.mq.enums.MQMsgType;
import cn.seagen.base.utils.FastJsonUtils;

/** 消息处理线程 */
public class MQTaskThread extends Thread {
	private Logger logger = LogManager.getLogger(MQTaskThread.class.getName());
	private List<MQBase> taskList = Collections.synchronizedList(new ArrayList<MQBase>());
	private MQMessage mqMesage = null;
	
	public MQTaskThread(String name, MQMessage mqMesage) {
		super(name);
		this.mqMesage = mqMesage;
	}

	@Override
	public void run() {
		while (SystemConstant.RUNING) {
			try {
				while (taskList.size() > 0) {
					try {
						// 消息处理应用
						if (mqMesage == null)
							continue;
						// 处理消息
						//if (doWork()) {
							doWork();
							MQMessageWork.decTaskCount();// 消息减一
							taskList.remove(0);
						//}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("MQTaskThread:" + e.getMessage(), e);
					}
				}
				sleep(2);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("MQTaskThread:" + e.getMessage(), e);
			}
		}
		super.run();
	}
	
	private MQBase getMessage() {
		try {
			if (taskList.size() == 0)
				return null;
			return taskList.get(0);
		} catch (Exception e) {
			return null;
		}
	}

	private boolean doWork() {
		MQBase mqbase = getMessage();
		if (mqbase == null)
			return true;

		try {
			boolean result = false;
			// 取消息类型
			MQMsgType mQMsgType = MQMsgType.getEnum(mqbase.getIndex());
			if ((mqbase.getIndex() > 90000) && (mqbase.getIndex() < 100000))
				mQMsgType = MQMsgType.m_Extent;

			// 处理事务
			switch (mQMsgType) {
			case m_Power_C:
				MQPower mQPower = FastJsonUtils.parseObject(mqbase.getMessage(), MQPower.class);
				if (mQPower != null) {// 转换正确
					mQPower.setMessage(mqbase.getMessage());// 将原文放进去
					result = mqMesage.power_process(mQPower); // 执行
				}
				return (result) ? true : mqMesage.doExcMsg(mqbase);
			case m_SortMode_C:
				MQSortMode mQSortMode = FastJsonUtils.parseObject(mqbase.getMessage(), MQSortMode.class);
				if (mQSortMode != null) {// 转换正确
					mQSortMode.setMessage(mqbase.getMessage());// 将原文放进去
					result = mqMesage.sortmode_process(mQSortMode);// 执行
				}
				return (result) ? true : mqMesage.doExcMsg(mqbase);
			case m_LineNum_C:
				MQLineNum mQLineNum = FastJsonUtils.parseObject(mqbase.getMessage(), MQLineNum.class);
				if (mQLineNum != null) { // 转换正确
					mQLineNum.setMessage(mqbase.getMessage());// 将原文放进去
					result = mqMesage.linenum_process(mQLineNum);// 执行
				}
				return (result) ? true : mqMesage.doExcMsg(mqbase);
			case m_Scan_C:
				MQSort mQScan = FastJsonUtils.parseObject(mqbase.getMessage(), MQSort.class);
				if (mQScan != null) { // 转换正确
					mQScan.setMessage(mqbase.getMessage());// 将原文放进去
					result = mqMesage.scan_process(mQScan);// 执行
				}
				return (result) ? true : mqMesage.doExcMsg(mqbase);
			case m_Sorting_C:
				MQSort mQSorting = FastJsonUtils.parseObject(mqbase.getMessage(), MQSort.class);
				if (mQSorting != null) { // 转换正确
					mQSorting.setMessage(mqbase.getMessage());// 将原文放进去
					result = mqMesage.sorting_process(mQSorting);// 执行
				}
				return (result) ? true : mqMesage.doExcMsg(mqbase);
			case m_Sorted_C:
				MQSort mQSorted = FastJsonUtils.parseObject(mqbase.getMessage(), MQSort.class);
				if (mQSorted != null) { // 转换正确
					mQSorted.setMessage(mqbase.getMessage());// 将原文放进去
					result = mqMesage.sorted_process(mQSorted);// 执行
				}
				return (result) ? true : mqMesage.doExcMsg(mqbase);
			case m_Jb_C:
				MQJb mQJb = FastJsonUtils.parseObject(mqbase.getMessage(), MQJb.class);
				if (mQJb != null) { // 转换正确
					mQJb.setMessage(mqbase.getMessage());// 将原文放进去
					result = mqMesage.jb_process(mQJb);// 执行
				}
				return (result) ? true : mqMesage.doExcMsg(mqbase);
			case m_Event_C:
				MQEvent mQEvent = FastJsonUtils.parseObject(mqbase.getMessage(), MQEvent.class);
				if (mQEvent != null) { // 转换正确
					mQEvent.setMessage(mqbase.getMessage());// 将原文放进去
					result = mqMesage.event_process(mQEvent);// 执行
				}
				return (result) ? true : mqMesage.doExcMsg(mqbase);
			case m_Log_C:
				MQLog mQLog = FastJsonUtils.parseObject(mqbase.getMessage(), MQLog.class);
				if (mQLog != null) { // 转换正确
					mQLog.setMessage(mqbase.getMessage());// 将原文放进去
					result = mqMesage.log_process(mQLog);// 执行
				}
				return (result) ? true : mqMesage.doExcMsg(mqbase);
			case m_Extent:
				if (!mqMesage.sorting_extent(mqbase))// 执行
					return mqMesage.doExcMsg(mqbase);
				return true;
			default:
				return mqMesage.doExcMsg(mqbase);
			}
		} catch (Exception e) {
			logger.error("MQTaskThread:" + e.getMessage(), e);
			return mqMesage.doExcMsg(mqbase.getMessage());
		}
	}

	public List<MQBase> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<MQBase> taskList) {
		this.taskList = taskList;
	}

	public boolean addMessage(MQBase mqbase) {
		taskList.add(mqbase);
		return true;
	}

}
