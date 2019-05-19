package cn.seagen.base.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import cn.seagen.base.bean.RbacUser;
import cn.seagen.base.dao.UserDao;
import cn.seagen.base.utils.JUtils;
@Component
public class UserDaoImpl implements UserDao{
	private Logger logger = LogManager.getLogger(UserDaoImpl.class.getName());
	@Resource
	private JdbcTemplate template;
	
	@Override
	public int login(String userName, String pwd) {
		List<Map<String, Object>> mapList = null;
		String sql = "select id, pwd from rbac_user where use_flag=0 and user_name = ? limit 1";
		try {
			mapList = template.queryForList(sql,userName);
			if(mapList.size() < 1) 
				return -1;
			
			if(pwd.equals(mapList.get(0).get("pwd"))) {
				return JUtils.strToInt(mapList.get(0).get("id").toString());
			}
		} catch (Exception e) {
			logger.error("获取用户ID失败："+e.getMessage(),e);
		}
		return 0;
	}
	
	@Override
	public boolean updatePwd(String id, String pwd, String npwd) {
		int res = 0;
		String sql = "update rbac_user set  pwd = ? where id = ? and pwd = ?";
		try {
			res = template.update(sql,npwd,id,pwd);
			return (res > 0);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return false;
	}

	@Override
	public Object getUsers() {
		List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
		try {
			String sql = "select u.id,user_name as userName,pwd,telno as telNo,"//
					+ "email,remark, date_format(u.create_time,'%Y-%m-%d %H:%i:%s') " //
					+ " as createTime,use_flag as useFlag, ifnull(role_id,0) as roleId "
					+ "from rbac_user as u left join rbac_user_role as ur on u.id = ur.user_id where ur.role_id > 1";
			userList =  template.queryForList(sql);
		} catch (Exception e) {
			logger.error("获取用户列表失败："+e.getMessage(),e);
		} 
		return userList;
	}
	
	@Override
	public Object getUsers(int roleId) {
		List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
		try {
			String sql = "select u.id,user_name as userName,pwd,telno as telNo,"//
					+ "email,remark, date_format(u.create_time,'%Y-%m-%d %H:%i:%s') " //
					+ " as createTime,use_flag as useFlag, ifnull(role_id,0) as roleId "
					+ "from rbac_user as u left join rbac_user_role as ur on u.id = ur.user_id where ur.role_id >"+roleId;
			userList =  template.queryForList(sql);
		} catch (Exception e) {
			logger.error("获取用户列表失败："+e.getMessage(),e);
		} 
		return userList;
	}

	@Override
	public int insertUser(RbacUser user) {
		final RbacUser tempUser = user;
		final String sql = "insert into rbac_user(user_name,pwd,telno,email,"//
				+ "remark,use_flag) values(?,?,?,?,?,?)";
		try {
			 KeyHolder keyHolder = new GeneratedKeyHolder();
			 template.update(new PreparedStatementCreator() {
			        @Override
			        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException{
			            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
						ps.setString(1, tempUser.getUserName());
						ps.setString(2, tempUser.getPwd());
						ps.setString(3, tempUser.getTelNo());
						ps.setString(4, tempUser.getEmail());
						ps.setString(5, tempUser.getRemark());
						ps.setString(6, "0");
	
			            return ps;
			        }
			 }, keyHolder);
			 return keyHolder.getKey().intValue();
		} catch (Exception e) {
			logger.error("添加用户失败："+e.getMessage(),e);
		}
		return 0;
	}

	@Override
	public boolean updateUser(RbacUser user) {
		int res = 0;
		String sql = "update rbac_user set user_name=?,pwd=?,telno=?,email=?,remark=? where id=?";
		try {
			res = template.update(sql,user.getUserName(),user.getPwd(),user.getTelNo(),user.getEmail(),user.getRemark(),user.getId());
			return (res > 0);
		} catch (Exception e) {
			logger.error("更新用户失败："+e.getMessage(),e);
		} 
		return false;
	}

	@Override
	public boolean updateUseFlag(int id, String use_flag) {
		int res = 0;
		String sql = "update rbac_user set use_flag=? where id=?";
		try {
			res = template.update(sql, use_flag,id);
			return (res > 0);
		} catch (Exception e) {
			logger.error("更新用户状态异常："+e.getMessage(),e);
		} 
		return false;
	}

	@Override
	public int findUser(String userName) {
		String sql = "select id from rbac_user where user_name = ? limit 1";
		try {
			List<Integer> list = template.queryForList(sql, Integer.class, userName);
			if(list == null||list.size() <= 0){
				return 0;
			}else{
				return list.get(0);
			}
		} catch (Exception e) {
			logger.error("existUser 异常："+e.getMessage(),e);
		}
		return -1;
	}
}
