package cn.seagen.base.dao;

public interface RoleDao {
	/**
	 * 
	 * @param userId 用户主键
	 * @return 角色主键
	 */
	public int getRoleIdByUserId(int userId);
	/**
	 * 
	 * @param roleId 角色主键
	 * @return 角色List集合
	 */
	public Object getRoleList(int roleId);
	
	/**
	 * 添加用户角色对应关系
	 * @param userId
	 * @param roleId
	 * @return true 成功，false失败
	 */
	public boolean insertUserRoleInfo(int userId,int roleId);
	
	/**
	 * 更新用户角色对应关系
	 * @param userId
	 * @param roleId
	 * @return true 成功，false失败
	 */
	public boolean updateUserRoleInfo(int userId,int roleId);
	/**
	 * 添加角色菜单节点对应关系
	 * @param roleId 角色主键
	 * @param nodeId 菜单节点主键集合
	 * @return true 成功，false失败
	 */
	public boolean insertRoleNodeInfo(int roleId,int[] nodeId);
	
	/**
	 * 删除角色菜单节点对应关系
	 * @param roleId 角色主键
	 * @return true 成功，false失败
	 */
	public boolean deleteRoleNodeInfo(int roleId);
	
	/**
	 * 查询角色是否存在
	 * @param roleName
	 * @return true 存在，false 不存在
	 */
	public boolean findRoleByName(String roleName);
}
