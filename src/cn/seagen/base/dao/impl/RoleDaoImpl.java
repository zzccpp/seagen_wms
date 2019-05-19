package cn.seagen.base.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.seagen.base.dao.RoleDao;
import cn.seagen.base.utils.JUtils;
@Component
public class RoleDaoImpl implements RoleDao {
	private Logger logger = LogManager.getLogger(RoleDaoImpl.class.getName());
	@Resource
	JdbcTemplate template;
	
	@Override
	public int getRoleIdByUserId(int userId) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			String sql = "select role_id from rbac_user_role where user_id =?  limit 1";
			list = template.queryForList(sql, userId);
			if(list.size() < 1)
				return 0;
			
			return JUtils.strToInt(list.get(0).get("role_id").toString());
		} catch (Exception e) {
			logger.error("获取角色id出错："+e.getMessage(),e);
			return 0;
		}
	}

	@Override
	public Object getRoleList(int roleId) {
		if(roleId == -1)//获取全部角色
			roleId = 0;
//		else	//
//			roleId = 1;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			String sql = "select id,role_name as roleName,date_format(create_time,'%Y-%m-%d %H:%i:%s') as createTime from rbac_role where id > ?";
			list = template.queryForList(sql,roleId);
			return list;
		} catch (Exception e) {
			logger.error("获取角色列表出错："+e.getMessage(),e);
		}
		return list;
	}

	@Override
	public boolean insertUserRoleInfo(int userId, int roleId) {
		int res = 0;
		String sql = "insert into rbac_user_role (user_id,role_id) values(?,?)";
		try {
			res = template.update(sql,userId,roleId);
			return (res > 0);
		} catch (Exception e) {
			logger.error("用户角色对应关系写入数据库出错："+e.getMessage(),e);
		}
		return false;
	}

	@Override
	public boolean updateUserRoleInfo(int userId, int roleId) {
		int res = 0;
		String sql = "update rbac_user_role set role_id = ? where user_id = ?";
		try {
			res = template.update(sql,roleId,userId);
			return (res > 0);
		} catch (Exception e) {
			logger.error("更新用户角色对应关系出错："+e.getMessage(),e);
		}
		return false;
	}

	@Override
	public boolean insertRoleNodeInfo(final int roleId, final int[] nodeId) {
		String sql = "insert into rbac_role_node (role_id,node_id) values(?,?)";
		try {
			template.batchUpdate(sql, new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)throws SQLException {
					int tag = 0;
					ps.setInt(++tag, roleId);
					ps.setInt(++tag, nodeId[i]);
				}
				public int getBatchSize() {
					return nodeId.length;
				}
			});
			return true;
		} catch (Exception e) {
			logger.error("添加角色菜单节点对应关系："+e.getMessage(),e);
			return false;
		}
		
	}

	@Override
	public boolean deleteRoleNodeInfo(int roleId) {
		String sql = "delete from rbac_role_node where role_id = ?";
		try {
			template.update(sql, roleId);
			
		} catch (Exception e) {
			logger.error("删除角色菜单节点对应关系："+e.getMessage(),e);
			return false;
		}
		return true;
	}

	@Override
	public boolean findRoleByName(String roleName) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
