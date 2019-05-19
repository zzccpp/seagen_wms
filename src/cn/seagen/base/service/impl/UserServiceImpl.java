package cn.seagen.base.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.seagen.base.bean.RbacUser;
import cn.seagen.base.dao.UserDao;
import cn.seagen.base.service.LogService;
import cn.seagen.base.service.RoleService;
import cn.seagen.base.service.UserService;

/** 前端页面操作员相关操作，如登陆、修改密码等 */
@Service
public class UserServiceImpl implements UserService {
	@Resource
	private UserDao userDaoImpl;
	@Resource
	private RoleService roleServiceImpl;
	@Resource
	private LogService logServiceImpl;
	
	@Override
	public Map<String, Object> login(String operatorname, String password) {
		Map<String,Object> map = new HashMap<String, Object>();
		int userId = userDaoImpl.login(operatorname, password);
		
		if(0 == userId){
			map.put("res", -10);
			map.put("msg", "用户登陆密码错误!");
			return map;
		}
		
		if(-1 == userId){
			map.put("res", -4);
			map.put("msg", "用户不存在或已禁用!");
			return map;
		}
		
		int roleId = roleServiceImpl.getRoleIdByUserId(userId);
		if(roleId < 1){
			map.put("res", -2);
			map.put("msg", "用户角色不存在或已禁用!");
			return map;
		}
		map.put("userId", userId);
		map.put("roleId", roleId);
		map.put("res", 0);
		map.put("msg", "登陆成功!");
		return map;
	}

	@Override
	public Map<String, Object> updatePwd(String id, String pwd, String npwd) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean res = userDaoImpl.updatePwd(id, pwd, npwd);
		if (res) {
			map.put("res", 0);
			map.put("msg", "密码修改成功!");
		} else {
			map.put("res", -4);
			map.put("msg", "密码修改失败!");
		}
		
		return map;
	}
	
	@Override
	public Object getUsers() {
		return userDaoImpl.getUsers();
	}
	
	@Override
	public boolean insertUser(RbacUser user, int roleId) {
		int userId = userDaoImpl.insertUser(user);
		if(userId <= 0){
			return false;
		}
		if(roleServiceImpl.insertUserRoleInfo(userId, roleId)){
			return true;
		}else{
			throw new RuntimeException();//事务回滚
		}
	}
	
	@Override
	public boolean updateUser(RbacUser user, int roleId) {
		if(!userDaoImpl.updateUser(user))
			return false;
		
		if(roleServiceImpl.updateUserRoleInfo(user.getId(), roleId)){
			return true;
		}else{
			throw new RuntimeException();//事务回滚
		}
	}
	
	@Override
	public boolean updateUseFlag(int id, String useFlag) {
		return userDaoImpl.updateUseFlag(id, useFlag);
	}

	@Override
	public int findUser(String userName) {
		return userDaoImpl.findUser(userName);
	}

	@Override
	public Object getUsers(int roleId) {
		return userDaoImpl.getUsers(roleId);
	}

}
