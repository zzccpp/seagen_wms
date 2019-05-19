package cn.seagen.base.dao;

import cn.seagen.base.bean.SystemLog;

/**
 * 系统操作日志,主要针对前端页面操作Controller日志
 * @author zcp
 */
public interface SystemLogDao {
	/**
	 * 记录终端操作日志
	 * @param systemLog
	 * @return true 添加成功   false 添加失败
	 */
	public boolean insertSystemLog(SystemLog systemLog);
}
