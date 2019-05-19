package cn.seagen.base.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.seagen.base.dao.DbDataBackUpDao;
@Component
public class DbDataBackUpDaoImpl implements DbDataBackUpDao{
	private Logger logger = LogManager.getLogger(DbDataBackUpDaoImpl.class.getName());
	@Resource
	private JdbcTemplate template;
	
	@Override
	public int findCanDeleteCount(String sql) {
		try {
			List<Integer> list = template.queryForList(sql, Integer.class);
			if (list!=null&&list.size()>0) {
				return list.get(0);
			}
		} catch (Exception e) {
			logger.error("findCanDeleteCount 异常:"+sql,e);
			return 0;
		}
		return 0;
	}

	@Override
	public int findTableDataCount(String sql) {
		try {
			List<Integer> list = template.queryForList(sql, Integer.class);
			if (list!=null&&list.size()>0) {
				return list.get(0);
			}
		} catch (Exception e) {
			logger.error("findTableDataCount 异常:"+sql,e);
			return 0;
		}
		return 0;
	}

	@Override
	public boolean deleteTableData(String sql) {
		try {
			if(template.update(sql) > 0){
				return true;
			}
		} catch (Exception e) {
			logger.error("deleteTableData:" + e.getMessage(), e);
			throw new RuntimeException("删除deleteTableData失败："+sql);
		}
		return false;
	}

}
