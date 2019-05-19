package cn.seagen.base.dao.impl;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.seagen.base.bean.SystemLog;
import cn.seagen.base.dao.SystemLogDao;
@Service
public class SystemLogDaoImpl implements SystemLogDao {
	
	private static Logger logger = LogManager.getLogger(SystemLogDaoImpl.class.getName());
	private JdbcTemplate jdbcTemplate;
	@Resource
	public void setTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean insertSystemLog(SystemLog systemLog) {
		String sql = "insert into system_log(ip,user_name,method_name,type,method_args,log_mark,time) values(?,?,?,?,?,?,?)";
		//设置占位符值
		Object [] args = new Object[]{systemLog.getIp(),systemLog.getUserName(),systemLog.getMethodName(),
				systemLog.getType(),systemLog.getMethodArgs(),systemLog.getLogMark(),systemLog.getTime()};
		try {
			return jdbcTemplate.update(sql, args)>0;
		} catch (Exception e) {
			logger.error("添加操作日志异常!", e);
		}
		return false;
	}

}
