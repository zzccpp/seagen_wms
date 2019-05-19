package test.dao.impl;

import javax.annotation.Resource;

import org.junit.Test;

import test.BaseSpringTest;
import cn.seagen.base.dao.ReportChuteDao;
import cn.seagen.base.service.ReportChuteService;
import cn.seagen.base.vo.RequestBase;

public class ReportChuteDaoImplTest extends BaseSpringTest  {
	@Resource
	private ReportChuteDao reportChuteDaoImpl;
	@Resource
	private ReportChuteService reportChuteServiceImpl;
	@Test
	public void querySupply(){
		RequestBase base = new RequestBase();
		base.setDate("2018-01-25 07:00:00");
		base.setType(1);
		base.setRule(0);
		System.out.println(reportChuteDaoImpl.queryChute(base));
	}
}
