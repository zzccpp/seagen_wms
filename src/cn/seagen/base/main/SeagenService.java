package cn.seagen.base.main;

import java.util.List;

import cn.seagen.base.mq.work.MQMessage;

public interface SeagenService {
	/** 记录客户端的版本号,为数据版本号与文件版本号 */
	public abstract void initClientVer(String clientDbVer, String clientFileVer);
	
	/** 用于客户对接相关的数据库升级 */
	public abstract void initClientDB();
	
	/** 添加MQ业务处理的实现类 */
	public abstract MQMessage SetMQWork();

	/** 添加任务线程组类 ,并监视运行状态 */
	public abstract void initTaskThread(List<Class<?>> cls);

	/** 测试环境下不执行定时备份任务和数据统计任务 */
	public abstract boolean setDebug();
	
	/** 准备好后，在业务逻辑执行前要做的工作 */
	public abstract void doAfterReady();

	/** 初使化 */
	public void configSystem();

	/** 关闭程序 */
	public void shutdown();
}
