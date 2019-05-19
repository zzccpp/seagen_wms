package test.dao.impl;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import cn.seagen.base.bean.SystemLog;
import cn.seagen.base.dao.SystemLogDao;
import test.BaseSpringTest;

public class SystemLogDaoImplTest extends BaseSpringTest {

	
	private SystemLogDao systemLogDaoImpl;

	@Resource
	public void setSystemLogDaoImpl(SystemLogDao systemLogDaoImpl) {
		this.systemLogDaoImpl = systemLogDaoImpl;
	}
	
	@Test
	public void insertTest(){
		
		SystemLog systemLog = new SystemLog();
		systemLog.setIp("127.0.0.1");
		systemLog.setUserName("zcp");
		systemLog.setMethodName("login");
		systemLog.setMethodArgs("args");
		systemLog.setType(0);
		systemLog.setTime(100);
		systemLog.setLogMark("");
		boolean result = systemLogDaoImpl.insertSystemLog(systemLog );
		Assert.assertTrue(result);
	}
}
