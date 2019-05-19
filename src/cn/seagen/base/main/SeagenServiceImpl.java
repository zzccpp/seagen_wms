package cn.seagen.base.main;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.seagen.base.constant.SystemConstant;
import cn.seagen.base.mq.work.MQMessage;
import cn.seagen.base.mq.work.MQMessageWork;
import cn.seagen.base.timertasks.TaskMonitor;
@Service
public class SeagenServiceImpl implements SeagenService{
	@Resource
	private MQMessage mQTask;
	
	@Override
	public void initClientVer(String clientDbVer, String clientFileVer) {
		
	}
	
	@Override
	public MQMessage SetMQWork() {
		return mQTask;
	}

	@Override
	public void initTaskThread(List<Class<?>> cls) {
		
	}

	@Override
	public boolean setDebug() {
		return false;
	}

	@Override
	public void doAfterReady() {
		System.out.println("可以消费MQ消息了");
		MQMessageWork.setCanConsumer(true);//可以消费MQ消息
	}

	@Override
	public void configSystem() {
		new ScheduleInitTask(this).start();
		
	}

	@Override
	public void shutdown() {
		SystemConstant.RUNING = false;
		MQMessageWork.setCanConsumer(false);
	}

	@Override
	public void initClientDB() {
		
	}

}

/** 线程执行不影响其它任务 */
class ScheduleInitTask extends Thread {
	private SeagenService seagenServiceImpl;

	public ScheduleInitTask(SeagenService seagenServiceImpl) {
		this.seagenServiceImpl = seagenServiceImpl;
	}

	@Override
	public void run() {
		try {
			// 运行环境是否是DUBUG状态
			SystemConstant.DEBUG = seagenServiceImpl.setDebug();
			// 用于数据库升级
			seagenServiceImpl.initClientDB();
			
			// 添加MQ业务处理的实现类
			MQMessage mqMessage = seagenServiceImpl.SetMQWork();
			MQMessageWork.setMqMesage(mqMessage);

			// 添加任务线程组类,并监视运行状态
			List<Class<?>> cls = new ArrayList<Class<?>>();
			seagenServiceImpl.initTaskThread(cls);
			
			for (int i = 0; i < cls.size(); i++) {
				TaskMonitor.addThreads(cls.get(i));
			}

			//准备好后，在业务逻辑执行前要做的工作
			seagenServiceImpl.doAfterReady();
		} catch (Exception e) {
		}
		super.run();
	}
}
