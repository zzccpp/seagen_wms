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
import cn.seagen.base.dao.SortSchemeDetailDao;

@Component
public class SortSchemeDetailDaoImpl implements SortSchemeDetailDao {
	
	private Logger logger = LogManager.getLogger(SortSchemeDetailDaoImpl.class.getName());
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean deleteSortschemeDetail() {
		String sql = "delete from sortscheme_detail";
		try {
			return jdbcTemplate.update(sql)>0;
		} catch (Exception e) {
			logger.error("删除本地分拣方案明细!", e);
		}
		return false;
	}
	
	@Override
	public boolean insertSortschemeDetail(SortschemeDetail sortSchemeDetail) {
		boolean res = false;
		String sql = "insert into sortscheme_detail(scheme_id,site_code,box_site_name,box_site_code," +
				"chute_num,weight,re_mark,is_print,print_name,complement_name) " +
				"values(?,?,?,?,?,?,?,?,?,?)";
		//设置占位符值
		Object [] args = new Object[]{sortSchemeDetail.getScheme_id(),sortSchemeDetail.getSite_code(),
				sortSchemeDetail.getBox_site_name(),sortSchemeDetail.getBox_site_code(),sortSchemeDetail.getChute_num(),
				sortSchemeDetail.getWeight(),sortSchemeDetail.getRe_mark(),sortSchemeDetail.getIs_print(),
				sortSchemeDetail.getPrint_name(),sortSchemeDetail.getComplement_name()};
		try {
			res = jdbcTemplate.update(sql, args)>0;
		} catch (Exception e) {
			logger.error("添加本地分拣方案明细!", e);
			throw new RuntimeException("添加本地分拣方案明细入库失败："+e);
		}
		return res;
	}
	
	@Override
	public boolean insertSortschemeDetailList(final List<SortschemeDetail> list) {
		boolean res = false;
		String sql = "insert into sortscheme_detail(scheme_id,site_code,box_site_name,box_site_code," +
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
			logger.error("insertSortschemeDetailList:" + e.getMessage(), e);
			throw new RuntimeException("添加本地分拣方案明细入库失败："+e);
		} 
		return res;
	}
	
	@Override
	public List<SortschemeDetail> getSortschemeDetail(String schemeId) {
		List<SortschemeDetail> detailList = new ArrayList<SortschemeDetail>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = "select f_recno,scheme_id,site_code,box_site_name,box_site_code," +
				"chute_num,weight,re_mark,is_print,print_name,complement_name, " 
				+ " date_format(create_time,'%Y-%m-%d %H:%i:%s') as create_time, " 
				+ " date_format(modify_time,'%Y-%m-%d %H:%i:%s') as modify_time "
				+ " from sortscheme_detail where scheme_id = ? order by f_recno";
		try {
			list = jdbcTemplate.queryForList(sql,schemeId);
			if(list.size()>0){
				for(Map<String,Object> map:list){
					SortschemeDetail setVal = new SortschemeDetail();
					int index = Integer.parseInt(map.get("f_recno").toString());
					setVal.setF_recno(index);
					setVal.setScheme_id(String.valueOf(map.get("scheme_id")));
					setVal.setSite_code(String.valueOf(map.get("site_code")));
					setVal.setBox_site_name(String.valueOf(map.get("box_site_name")));
					setVal.setBox_site_code(String.valueOf(map.get("box_site_code")));
					setVal.setChute_num(String.valueOf(map.get("chute_num")));
					setVal.setWeight(Integer.parseInt(map.get("weight").toString()));
					setVal.setRe_mark(String.valueOf(map.get("re_mark")));
					setVal.setIs_print(String.valueOf(map.get("is_print")));
					setVal.setPrint_name(String.valueOf(map.get("print_name")));
					setVal.setComplement_name(String.valueOf(map.get("complement_name")));
					setVal.setCreate_time(String.valueOf(map.get("create_time")));
					detailList.add(setVal);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getSortschemeDetail fail：" + e.getMessage(), e);
		} 
		return detailList;
	}
}
