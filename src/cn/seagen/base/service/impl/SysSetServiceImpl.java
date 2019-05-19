package cn.seagen.base.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.seagen.base.bean.SystemSet;
import cn.seagen.base.dao.SysSetDao;
import cn.seagen.base.service.SysSetService;

@Service
public class SysSetServiceImpl implements SysSetService {

	private SysSetDao systemSetDaoImpl;
	
	@Resource
	public void setSystemSetDaoImpl(SysSetDao systemSetDaoImpl) {
		this.systemSetDaoImpl = systemSetDaoImpl;
	}

	@Override
	public boolean updateSystemSet(SystemSet systemSet) {
		return systemSetDaoImpl.updateSystemSet(systemSet);
	}

	@Override
	public SystemSet getSystemSet(int index) {
		return systemSetDaoImpl.getSystemSet(index);
	}

	@Override
	public List<SystemSet> getSystemSetList() {
		return systemSetDaoImpl.getSystemSetList();
	}

	@Override
	public boolean initParam(List<SystemSet> list) {
		return systemSetDaoImpl.insertInitParam(list);
	}

	@Override
	public List<SystemSet> getSystemSetListByNames(String setNames) {
		return systemSetDaoImpl.getSystemSetListByNames(setNames);
	}

}
