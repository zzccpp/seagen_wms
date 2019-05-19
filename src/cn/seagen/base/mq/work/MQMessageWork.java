package cn.seagen.base.mq.work;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.seagen.base.constant.SystemConstant;
import cn.seagen.base.mq.dto.MQBase;

public class MQMessageWork implements Serializable {
	private static final long serialVersionUID = -6940411829320433090L;
	protected static Logger logger = LogManager.getLogger(MQMessageWork.class
			.getName());
	// 消息处理线程
	private static Map<Integer, MQTaskThread> taskMap = new HashMap<Integer, MQTaskThread>();
	private static MQMessage mqMesage = null;
	private static AtomicInteger taskCount = new AtomicInteger(0);
	// 消息开关
	private static Boolean canConsumer = false;

	public static MQTaskThread createTaskThread(int chute) {
		MQTaskThread taskThread = new MQTaskThread(
				"Thread-Line-ChuteQ" + chute, mqMesage);
		taskThread.start();
		return taskThread;
	}

	public static MQTaskThread getTaskThread(int chute) {
		MQTaskThread taskThread = null;
		synchronized (taskMap) {
			try {
				taskThread = taskMap.get(chute);
				if (taskThread == null) {
					taskThread = createTaskThread(chute);
					taskMap.put(chute, taskThread);
				} else {
					Thread.State state = taskThread.getState();
					if (Thread.State.TERMINATED.equals(state)) {
						taskThread = createTaskThread(chute);
						taskMap.put(chute, taskThread);
					}
				}
			} catch (Exception e) {
				taskThread = createTaskThread(chute);
				taskMap.put(chute, taskThread);
				logger.error("getTaskThread:" + e.getMessage(), e);
			}
		}
		return taskThread;
	}

	/**
	 * 
	 * @param chute
	 * @param mqbase
	 * @return
	 */
	public static boolean addtask(int chute, MQBase mqbase) {
		try {
			MQTaskThread taskThread = getTaskThread(chute);
			boolean canAdd = taskThread.addMessage(mqbase);
			if (canAdd)
				incTaskCount();
			return canAdd;
		} catch (Exception e) {
			logger.error("addtask:" + e.getMessage(), e);
			return false;
		}
	}

	public static Map<Integer, MQTaskThread> getTaskMap() {
		return taskMap;
	}

	/** 获取业务处理线程信息 */
	public static synchronized Object getTaskInfo() {
		Map<Integer, MQTaskThread> taskList = getTaskMap();
		Map<String, Object> taskMap = new HashMap<String, Object>();
		taskMap.put("threads", taskList.size());
		int messageCount = 0;
		List<Map<String, Integer>> dataList = new ArrayList<Map<String, Integer>>();
		for (Integer key : taskList.keySet()) {
			Map<String, Integer> taskdetail = new HashMap<String, Integer>();
			taskdetail.put("chuteid", key);
			taskdetail.put("count", taskList.get(key).getTaskList().size());
			messageCount += taskList.get(key).getTaskList().size();
			dataList.add(taskdetail);
		}
		taskMap.put("mesages", messageCount);
		taskMap.put("data", dataList);
		return taskMap;
	}

	public static boolean isExCount() {
		Map<Integer, MQTaskThread> taskList = getTaskMap();
		/*
		 * for (Integer key : taskList.keySet()) {
		 * if(taskList.get(key).getTaskList().size() > 10) return false; }
		 */
		int count = 0;
		int times = 0;
		for (Entry<Integer, MQTaskThread> entry : taskList.entrySet()) {
			count = entry.getValue().getTaskList().size();
			if (count > 25)
				return false;
			if (count > 15 && count < 25)
				++times;
			if (times > 10)
				return false;
			
			//if (entry.getValue().getTaskList().size() > 20) return false;
			 
		}
		return true;
	}

	public static boolean isCanReceiveMsg() {
		return canConsumer && (mqMesage != null) && (isExCount());
	}

	public static MQMessage getMqMesage() {
		return mqMesage;
	}

	public static void setMqMesage(MQMessage mQMesage) {
		mqMesage = mQMesage;
	}

	public static void incTaskCount() {
		taskCount.getAndIncrement();
	}

	public static void decTaskCount() {
		taskCount.getAndDecrement();
	}

	/**
	 * 处理接收到的MQ消息，根据格口号做线程数量控制
	 * 
	 * @param mqBase
	 * @return
	 */
	public static boolean receive(MQBase mqBase) {
		try {
			// 分拣建包的消息用线程去执行（以后要注意控制线程数)
			int chutId = mqBase.getChute_id();
			if (chutId <= 0)
				return addtask(0, mqBase);
			chutId = chutId % SystemConstant.THREAD_COUNT;// 控制线程数
			return addtask(chutId == 0 ? SystemConstant.THREAD_COUNT : chutId,
					mqBase);

		} catch (Exception e) {
			logger.error("添加任务时：" + e.getMessage(), e);
			return false;
		}
	}

	/** 获取消费开关状态 */
	public static Boolean getCanConsumer() {
		return canConsumer;
	}

	/** 设置消费开关状态 */
	public static void setCanConsumer(Boolean canConsumer) {
		MQMessageWork.canConsumer = canConsumer;
	}

}
