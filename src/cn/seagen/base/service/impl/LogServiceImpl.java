package cn.seagen.base.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.seagen.base.bean.RbacUser;
import cn.seagen.base.bean.SystemLog;
import cn.seagen.base.dao.LogDao;
import cn.seagen.base.service.LogService;
@Service
public class LogServiceImpl implements LogService {
	
	@Resource
	private LogDao logDaoImpl;
	
	@Override
	public boolean record_log(int log_id, String log_val, String log_mark) {
		return logDaoImpl.record_log(log_id, log_val, log_mark);
	}

	@Override
	public boolean record_log(int log_id, String log_val, String log_mark,
			String create_time) {
		return logDaoImpl.record_log(log_id, log_val, log_mark, create_time);
	}

	@Override
	public boolean record_log(int log_id, int operator_id, String log_val,
			String log_mark) {
		return logDaoImpl.record_log(log_id, log_val, log_mark);
	}

	@Override
	public boolean record_log(int log_id, int operator_id, String log_val,
			String log_mark, String create_time) {
		return logDaoImpl.record_log(log_id, log_val, log_mark, create_time);
	}

	@Override
	public boolean record_errlog(int log_id, String log_name, String log_val,
			String log_mark) {
		return logDaoImpl.record_errlog(log_id, log_name, log_val, log_mark);
	}

	@Override
	public List<SystemLog> getSystemLogByDay(String day) {
		return logDaoImpl.getSystemLogByDay(day);
	}

	@Override
	public List<SystemLog> getSystemErrLogByDay(String day) {
		return logDaoImpl.getSystemErrLogByDay(day);
	}

	@Override
	public void record_report_log(String report_name, RbacUser opt) {
		logDaoImpl.record_report_log(report_name, opt);
	}

}
