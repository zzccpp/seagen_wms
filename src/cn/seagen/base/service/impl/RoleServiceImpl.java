package cn.seagen.base.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.seagen.base.dao.RoleDao;
import cn.seagen.base.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleDao RoleDaoImpl;

	@Override
	public int getRoleIdByUserId(int userId) {
		return RoleDaoImpl.getRoleIdByUserId(userId);
	}

	@Override
	public Object getRoleList(int roleId) {
		return RoleDaoImpl.getRoleList(roleId);
	}

	@Override
	public boolean insertUserRoleInfo(int userId, int roleId) {
		return RoleDaoImpl.insertUserRoleInfo(userId, roleId);
	}

	@Override
	public boolean updateUserRoleInfo(int userId, int roleId) {
		return RoleDaoImpl.updateUserRoleInfo(userId, roleId);
	}

	@Override
	public boolean insertRoleNodeInfo(int roleName, int[] nodeId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRoleNodeInfo(int roleId, int[] nodeId) {
		if(!RoleDaoImpl.deleteRoleNodeInfo(roleId))
			return false;
		if(RoleDaoImpl.insertRoleNodeInfo(roleId, nodeId))
			return true;
		else
			throw new RuntimeException();
		
	}

}
