package cn.seagen.base.service;

import java.util.Map;

import cn.seagen.base.bean.RbacUser;

public interface UserService {
	/**
	 * 登陆操作
	 * @param userName 用户名
	 * @param pwd 密码
	 * @return 
	 */
	public Map<String, Object> login(String userName, String pwd);

	/**
	 * 修改用户密码
	 * @param id 用户主键
	 * @param pwd 用户原密码
	 * @param npwd 用户新密码
	 * @return 
	 */
	public Map<String, Object> updatePwd(String id, String pwd, String npwd);

	/**
	 * 获取所有用户信息
	 * 
	 * @return List集合 
	 */
	public Object getUsers();

	/**
	 * 获取该角色信息权限可操作用户
	 * @param roleId
	 * @return List集合 
	 */
	public Object getUsers(int roleId);
	
	/**
	 * 添加操作员
	 * 
	 * @param user 用户实体对象
	 * @param roleId 用户角色
	 *            
	 * @return true 成功，false 失败
	 */
	public boolean insertUser(RbacUser user, int roleId);

	/**
	 * 修改用户
	 * 
	 * @param user 用户实体对象
	 * @param roleId 用户角色
	 * @return true 成功，false 失败
	 */
	public boolean updateUser(RbacUser user, int roleId);

	/**
	 * 禁用或启用用户
	 * 
	 * @param id 主键
	 * @param useFlag 用户状态
	 * @return true 成功，false 失败
	 */
	public boolean updateUseFlag(int id, String useFlag);
	
	/**
	 * 是否存在该用户
	 * @param userName 用户名
	 * @return id ,-1出错，>0 存在，0 不存在
	 */
	public int findUser(String userName);
}
