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

import cn.seagen.base.bean.SortschemeDetail;
import cn.seagen.base.dao.SortSchemeDetailRunDao;

@Component
public class SortSchemeDetailRunDaoImpl implements SortSchemeDetailRunDao {
	
	private Logger logger = LogManager.getLogger(SortSchemeDetailRunDaoImpl.class.getName());
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Map<String, Object>> findSortSchemeDetailList(String sql) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		try {
			dataList = jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			logger.error("findSortSchemeDetailList:" + e.getMessage(), e);
		}
		return dataList;
	}
	
	
	@Override
	public int findSortSchemeDetailListNum(String sql) {
		try {
			System.out.println(sql);
			List<Integer> list = jdbcTemplate.queryForList(sql, Integer.class);
			if (list!=null&&list.size()>0) {
				return list.get(0);
			}
		} catch (Exception e) {
			logger.error("findSortSchemeDetailListNum error",e);
		}
		return 0;
	}

	
	@Override
	public boolean deleteSortschemeDetailRun() {
		String sql = "delete from sortscheme_detail_run";
		try {
			return jdbcTemplate.update(sql)>0;
		} catch (Exception e) {
			logger.error("删除当前使用分拣方案明细!", e);
		}
		return false;
	}

	
	@Override
	public boolean insertSortSchemeDetailRun(final List<SortschemeDetail> list) {
		boolean res = false;
		String sql = "insert into sortscheme_detail_run(scheme_id,site_code,box_site_name,box_site_code," +
				"chute_num,weight,re_mark,is_print,print_name,complement_name) " +
				"values(?,?,?,?,?,?,?,?,?,?)";
		try {
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					int tag = 0;
					SortschemeDetail s = list.get(i);
					ps.setString(++tag, s.getScheme_id());
					ps.setString(++tag, s.getSite_code());
					ps.setString(++tag, s.getBox_site_name());
					ps.setString(++tag, s.getBox_site_code());
					ps.setString(++tag, s.getChute_num());
					ps.setInt(++tag,s.getWeight());
					ps.setString(++tag,s.getRe_mark());
					ps.setString(++tag,s.getIs_print());
					ps.setString(++tag,s.getPrint_name());
					ps.setString(++tag,s.getComplement_name());
				}
				public int getBatchSize() {
					return list.size();
				}
			});
			res = true;
		} catch (Exception e) {
			logger.error("insertSortSchemeDetailRun:" + e.getMessage(), e);
			throw new RuntimeException("添加当前使用分拣方案明细入库失败："+e);
		} 
		return res;
	}
	
	
	
}
