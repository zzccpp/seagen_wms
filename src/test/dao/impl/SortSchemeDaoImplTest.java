package test.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import test.BaseSpringTest;
import cn.seagen.base.bean.Sortscheme;
import cn.seagen.base.bean.SortschemeDetail;
import cn.seagen.base.dao.SortSchemeDao;
import cn.seagen.base.dao.SortSchemeDetailDao;
import cn.seagen.base.dao.SortSchemeDetailRunDao;

public class SortSchemeDaoImplTest extends BaseSpringTest  {
	@Resource
	private SortSchemeDao sortSchemeDaoImpl;
	
	@Resource
	private SortSchemeDetailDao sortSchemeDetailDaoImpl;
	
	@Resource
	private SortSchemeDetailRunDao sortSchemeDetailRunDaoImpl;
	
	@Test
	public void querySupply(){
		
		Sortscheme sortscheme = new Sortscheme();
		sortscheme.setF_recno(1);
		sortscheme.setChannel_id(0);
		sortscheme.setChannel_name("渠道名称");
		sortscheme.setMachine_no("2");
		sortscheme.setPrint_style(1);
		sortscheme.setScheme_id("2");
		sortscheme.setScheme_name("分拣主题名称");
		sortscheme.setSite_no("3");
//		sortSchemeDaoImpl.insertSortscheme(sortscheme);
		
//		Sortscheme ss = sortSchemeDaoImpl.getSortscheme();
//		System.out.println(ss.toString());
		
//		sortSchemeDaoImpl.deleteSortscheme();
		
		
		SortschemeDetail detail = new SortschemeDetail();
		detail.setF_recno(1);
		detail.setBox_site_code("B201801310001");
		detail.setBox_site_name("深圳盐田中转场");
		detail.setChute_num("12");
		detail.setComplement_name("盐田中转");
		detail.setIs_print("N");
		detail.setPrint_name("盐田");
		detail.setRe_mark("直达包");
		detail.setScheme_id("2");
		detail.setSite_code("W2141241");
		detail.setWeight(1000);
		
//		sortSchemeDetailDaoImpl.insertSortschemeDetail(detail);
		
//		List<SortschemeDetail>  returnlist = sortSchemeDetailDaoImpl.getSortschemeDetail("2");
//		System.out.println(returnlist.get(0).toString());
//		
//		sortSchemeDetailDaoImpl.deleteSortschemeDetail();
		
		List<SortschemeDetail>  list = new ArrayList<SortschemeDetail>();
		list.add(detail);
		sortSchemeDetailRunDaoImpl.insertSortSchemeDetailRun(list);
		
	}
}
