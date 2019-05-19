package cn.seagen.base.timertasks;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.seagen.base.utils.DateUtils;

@Service
public class TaskMonitor {
	private Logger logger = LogManager.getLogger(TaskMonitor.class.getName());
	// 除加任务的线程
	private static Map<Class<?>, Object> threads = new HashMap<Class<?>, Object>();
	
	@Scheduled(initialDelay=20*1000,fixedDelay = 5*60*1000)   //服务启动后5秒钟执行，然后执行后5分钟在执行
	private void timerTask(){
		long begintime = System.currentTimeMillis();
		doWork();
		long runtime = System.currentTimeMillis() - begintime;
		System.out.println("线程监控用时："+runtime);
	}
	
	public void doWork(){
		logger.info(DateUtils.getNow() + ":正在查看线程状态....");
		try {
			Map<Class<?>, Object> threads = getThreads();

			for (Entry<Class<?>, Object> entry : threads.entrySet()) {
				Object o = entry.getValue();
				try {
					if (o != null) {
						Thread t = (Thread) o;
						Thread.State state = t.getState();
						if (Thread.State.TERMINATED.equals(state))
							entry.setValue(createThread(entry.getKey()));
					} else
						entry.setValue(createThread(entry.getKey()));
				} catch (Exception e) {
					entry.setValue(null);
					e.printStackTrace();
					logger.error("TaskMonitor:" + e.getMessage(), e);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TaskMonitor:" + e.getMessage(), e);
		} 
	}
	
	private Thread createThread(Class<?> cl) {
		try {
			Thread t = (Thread) cl.newInstance();
			t.start();
			logger.info(DateUtils.getNow() + ":线程" + cl.getName() + "未能正常运行，正在重新启用该线程");
			return t;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ScheduledMonitor:" + e.getMessage(), e);
			return null;
		}
	}
	
	public static Map<Class<?>, Object> getThreads() {
		return threads;
	}

	public static void addThreads(Class<?> cl) {
		threads.put(cl, null);
	}

	public static void delThreads(Class<?> cl) {
		threads.remove(cl);
	}
}
