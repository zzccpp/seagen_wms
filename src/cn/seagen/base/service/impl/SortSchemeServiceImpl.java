package cn.seagen.base.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.seagen.base.bean.Sortscheme;
import cn.seagen.base.bean.SortschemeDetail;
import cn.seagen.base.dao.SortSchemeDao;
import cn.seagen.base.dao.SortSchemeDetailRunDao;
import cn.seagen.base.service.SortSchemeService;
import cn.seagen.base.utils.QueryUtils;
import cn.seagen.base.vo.EasyuiGridData;

@Service
public class SortSchemeServiceImpl implements SortSchemeService {

	@Resource
	private SortSchemeDetailRunDao sortSchemeDetailRunDaoImpl;
	
	@Resource
	private SortSchemeDao sortSchemeDaoImpl;
	
	
	@Override
	public boolean insertSortSchemeInfo(SortschemeDetail sortschemeDetail) {
		return false;
	}

	@Override
	public Object findRunSortSchemeList(int page, int rows, int chute_no, String site_code,
			String box_site_name) {
		EasyuiGridData easyuiGridData = new EasyuiGridData();
		//获取数量的sql
		String sql = QueryUtils.formatQuerySortSchemeDetailNumSql(chute_no, site_code,box_site_name);
		//判断sql语句是否正常
		if(StringUtils.isEmpty(sql))
			return easyuiGridData;
		int total = sortSchemeDetailRunDaoImpl.findSortSchemeDetailListNum(sql);
		//设置页面总数
		easyuiGridData.setTotal(total);
		//获取数据列表的sql
		sql = QueryUtils.formatQueryRunSortSchemeDetailSql(chute_no, site_code,box_site_name, page, rows);
		if(StringUtils.isEmpty(sql))
			return easyuiGridData;
		List<Map<String, Object>> list= sortSchemeDetailRunDaoImpl.findSortSchemeDetailList(sql);
		//设置页面显示数据
		easyuiGridData.setRows(list);
		return easyuiGridData;
	}

	@Override
	public boolean insertSortscheme(Sortscheme sortscheme) {
		return sortSchemeDaoImpl.insertSortscheme(sortscheme);
	}

	@Override
	public Sortscheme getSortscheme() {
		return sortSchemeDaoImpl.getSortscheme();
	}

	@Override
	public boolean deleteSortscheme() {
		return sortSchemeDaoImpl.deleteSortscheme();
	}

}
