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

import cn.seagen.base.bean.SystemSet;
import cn.seagen.base.dao.SysSetDao;

@Component
public class SysSetDaoImpl implements SysSetDao {
	
	private Logger logger = LogManager.getLogger(SysSetDaoImpl.class.getName());
	
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	@Override
	public boolean updateSystemSet(SystemSet systemSet) {
		boolean res = false;
		String sql = null;
		if(systemSet == null) {
			return false;
		}
		if(systemSet.getfRecno() != -1){
			sql = "update system_set set set_name_cn=?,set_value=?,set_mark=? where f_recno=?";
		}
		try {
			int flag =jdbcTemplate.update(sql,systemSet.getSetNameCn(),systemSet.getSetValue(),systemSet.getSetMark(),systemSet.getfRecno());
			res =  (flag > 0);
		} catch (Exception e) {
			logger.error("updateSystemSet异常:" + e);
			throw new RuntimeException("更新系统设置失败："+sql);
		}
		return res;
	}

	@Override
	public SystemSet getSystemSet(int index) {
		SystemSet setVal = new SystemSet();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = "select f_recno,set_name_cn,set_name,set_value,set_mark from system_set where f_recno = ?";
		try {
			list = jdbcTemplate.queryForList(sql,index);
			if(list.size()>0){
				Map<String,Object> map = list.get(0);
				setVal.setfRecno(index);
				String val = String.valueOf(map.get("set_value"));
				if (val == null)
					val = "";
				val = val.trim();
				setVal.setSetValue(val);
				setVal.setSetName(String.valueOf(map.get("set_name")));
				setVal.setSetNameCn(String.valueOf(map.get("set_name_cn")));
				setVal.setSetMark(String.valueOf(map.get("set_mark")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("SystemSetDaoImpl->getSystemSet:(" + index + ")：" + e.getMessage(), e);
		} 
		return setVal;
	}

	@Override
	public List<SystemSet> getSystemSetList() {
		List<SystemSet> systemList = new ArrayList<SystemSet>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = "select f_recno, set_name, set_name_cn, set_value, set_mark, " 
				+ " date_format(create_time,'%Y-%m-%d %H:%i:%s') as create_time, " 
				+ " date_format(modify_time,'%Y-%m-%d %H:%i:%s') as modify_time "
				+ " from system_set order by f_recno";
		try {
			list = jdbcTemplate.queryForList(sql);
			if(list.size()>0){
				for(Map<String,Object> map:list){
					SystemSet setVal = new SystemSet();
					int index = Integer.parseInt(map.get("f_recno").toString());
					setVal.setfRecno(index);
					String val = String.valueOf(map.get("set_value"));
					if (val == null)
						val = "";
					val = val.trim();
					setVal.setSetValue(val);
					setVal.setSetName(String.valueOf(map.get("set_name")));
					setVal.setSetNameCn(String.valueOf(map.get("set_name_cn")));
					setVal.setSetMark(String.valueOf(map.get("set_mark")));
					setVal.setCreateTime(String.valueOf(map.get("create_time")));
					setVal.setModifyTime(String.valueOf(map.get("modify_time")));
					systemList.add(setVal);
				}
			}
			logger.info("getSystemSetList successfull!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getSystemSetList fail：" + e.getMessage(), e);
		} 
		return systemList;
	}

	@Override
	public boolean insertInitParam(final List<SystemSet> list) {
		boolean res = false;
		String sql = "insert into system_set(set_name,set_name_cn,set_value,set_mark) values(?,?,?,?)";
		try {
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					int tag = 0;
					SystemSet s = list.get(i);
					ps.setString(++tag, s.getSetName());
					ps.setString(++tag, s.getSetNameCn());
					ps.setString(++tag, s.getSetValue());
					ps.setString(++tag, s.getSetMark());
				}
				public int getBatchSize() {
					return list.size();
				}
			});
			res = true;
		} catch (Exception e) {
			logger.error("insertSorted:" + e.getMessage(), e);
			res = false;
			throw new RuntimeException("系统默认的参数配置集合初始化入库失败："+e);
		} 
		return res;
	}

	@Override
	public List<SystemSet> getSystemSetListByNames(String setNames) {
		List<SystemSet> systemList = new ArrayList<SystemSet>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = "select f_recno, set_name, set_name_cn, set_value, set_mark, " 
				+ " date_format(create_time,'%Y-%m-%d %H:%i:%s') as create_time, " 
				+ " date_format(modify_time,'%Y-%m-%d %H:%i:%s') as modify_time "
				+ " from system_set where instr(concat('"+setNames+"'),concat(',',set_name,',')) order by f_recno";
		try {
			list = jdbcTemplate.queryForList(sql);
			if(list.size()>0){
				for(Map<String,Object> map:list){
					SystemSet setVal = new SystemSet();
					int index = Integer.parseInt(map.get("f_recno").toString());
					setVal.setfRecno(index);
					String val = String.valueOf(map.get("set_value"));
					if (val == null)
						val = "";
					val = val.trim();
					setVal.setSetValue(val);
					setVal.setSetName(String.valueOf(map.get("set_name")));
					setVal.setSetNameCn(String.valueOf(map.get("set_name_cn")));
					setVal.setSetMark(String.valueOf(map.get("set_mark")));
					setVal.setCreateTime(String.valueOf(map.get("create_time")));
					setVal.setModifyTime(String.valueOf(map.get("modify_time")));
					systemList.add(setVal);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getSystemSetListByNames fail：" + e.getMessage(), e);
		} 
		return systemList;
	}
}
