package cn.seagen.base.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.seagen.base.bean.Sortscheme;
import cn.seagen.base.dao.SortSchemeDao;

@Component
public class SortSchemeDaoImpl implements SortSchemeDao {
	
	private Logger logger = LogManager.getLogger(SortSchemeDaoImpl.class.getName());
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public boolean insertSortscheme(Sortscheme sortscheme) {
		String sql = "insert into sortscheme(scheme_id,scheme_name,site_no,machine_no,sort_mode," +
				"print_style,print_style_name,channel_id,channel_name,multiple_chute,re_mark) " +
				"values(?,?,?,?,?,?,?,?,?,?,?)";
		//设置占位符值
		Object [] args = new Object[]{sortscheme.getScheme_id(),sortscheme.getScheme_name(),sortscheme.getSite_no(),
				sortscheme.getMachine_no(),sortscheme.getSort_mode(),sortscheme.getPrint_style(),sortscheme.getPrint_style_name(),
				sortscheme.getChannel_id(),sortscheme.getChannel_name(),sortscheme.getMultiple_chute(),sortscheme.getRe_mark()};
		try {
			System.out.println(sql);
			return jdbcTemplate.update(sql, args)>0;
		} catch (Exception e) {
			logger.error("添加分拣主题方案!", e);
		}
		return false;
	}
	
	@Override
	public Sortscheme getSortscheme() {
		Sortscheme theme = new Sortscheme();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = "select * from sortscheme limit 1";
		try {
			list = jdbcTemplate.queryForList(sql);
			if(list.size()>0){
				Map<String,Object> map = list.get(0);
				long f_recno = Integer.parseInt(String.valueOf(map.get("f_recno")));
				theme.setF_recno(f_recno);
				theme.setScheme_id(String.valueOf(map.get("scheme_id")));
				theme.setScheme_name(String.valueOf(map.get("scheme_name")));
				theme.setSite_no(String.valueOf(map.get("site_no")));
				theme.setMachine_no(String.valueOf(map.get("machine_no")));
				theme.setSort_mode(String.valueOf(map.get("sort_mode")));
				theme.setPrint_style(Integer.parseInt(String.valueOf(map.get("print_style"))));
				theme.setPrint_style_name(String.valueOf(map.get("print_style_name")));
				theme.setChannel_id(Integer.parseInt(String.valueOf(map.get("channel_id"))));
				theme.setChannel_name(String.valueOf(map.get("channel_name")));
				theme.setMultiple_chute(String.valueOf(map.get("multiple_chute")));
				theme.setRe_mark(String.valueOf(map.get("re_mark")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("SortSchemeDaoImpl->getSortscheme:()：" + e.getMessage(), e);
		} 
		return theme;
	}
	
	@Override
	public boolean deleteSortscheme() {
		String sql = "delete from sortscheme";
		try {
			return jdbcTemplate.update(sql)>0;
		} catch (Exception e) {
			logger.error("删除分拣主题方案!", e);
		}
		return false;
	}

	@Override
	public Sortscheme getSortschemeChange() {
		Sortscheme theme = new Sortscheme();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = "select * from sortscheme where channel_id = 1 limit 1";
		try {
			list = jdbcTemplate.queryForList(sql);
			if(list.size()>0){
				Map<String,Object> map = list.get(0);
				long f_recno = Integer.parseInt(String.valueOf(map.get("f_recno")));
				theme.setF_recno(f_recno);
				theme.setScheme_id(String.valueOf(map.get("scheme_id")));
				theme.setScheme_name(String.valueOf(map.get("scheme_name")));
				theme.setSite_no(String.valueOf(map.get("site_no")));
				theme.setMachine_no(String.valueOf(map.get("machine_no")));
				theme.setSort_mode(String.valueOf(map.get("sort_mode")));
				theme.setPrint_style(Integer.parseInt(String.valueOf(map.get("print_style"))));
				theme.setPrint_style_name(String.valueOf(map.get("print_style_name")));
				theme.setChannel_id(Integer.parseInt(String.valueOf(map.get("channel_id"))));
				theme.setChannel_name(String.valueOf(map.get("channel_name")));
				theme.setMultiple_chute(String.valueOf(map.get("multiple_chute")));
				theme.setRe_mark(String.valueOf(map.get("re_mark")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("SortSchemeDaoImpl->getSortscheme:()：" + e.getMessage(), e);
		} 
		return theme;
	}
}
