package test.dao.impl;

import javax.annotation.Resource;

import org.junit.Test;

import cn.seagen.base.dao.ReportCarDao;
import cn.seagen.base.dao.ReportSupplyDao;
import cn.seagen.base.vo.RequestBase;
import test.BaseSpringTest;

public class ReportSupplyDaoImplTest extends BaseSpringTest {
	@Resource
	private ReportSupplyDao reportSupplyDaoImpl;
	@Resource
	private ReportCarDao ReportCarDaoImpl;
//	@Test
//	public void querySupply(){
//		RequestBase base = new RequestBase();
//		base.setDate("2018-01-24 07:00:00");
//		base.setType(3);
//		base.setMore(1);
//		System.out.println(reportSupplyDaoImpl.querySupply(base));
//	}
	
	@Test
	public void queryCar(){
		RequestBase base = new RequestBase();
		base.setDate("2018-01-29 21:00");
		base.setEnd_date("2018-01-29 21:03");
		base.setType(0);
		base.setMore(0);
		System.out.println(ReportCarDaoImpl.queryCar(base,0));
	}
}
