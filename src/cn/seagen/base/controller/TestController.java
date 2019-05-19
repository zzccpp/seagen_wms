package cn.seagen.base.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.seagen.base.bean.Waybill;
import cn.seagen.base.service.SortedService;
import cn.seagen.base.service.StatisticsService;

@Controller
public class TestController {
	
	public TestController() {
		super();
		System.out.println("===========构建TestController");
	}
	@Resource
	private SortedService sortServiceImpl;
	@Resource
	private StatisticsService statisticsServiceImpl;
	
	/*@ResponseBody
	@RequestMapping(value="/test.do")
	public List<Map<String, Object>> test(HttpSession session){
		System.out.println("------------test------------"+testServiceImpl);
		return testServiceImpl.getBarCodes();
	}*/
	@RequestMapping(value="/testTx.do")
	public String testTx(){
		System.out.println("------------testTx------------");
		//testServiceImpl.updateUser();
		return "index1";
	}
	@RequestMapping(value="/testTx1.do")
	public String testTx1(){
		System.out.println("------------testTx1------------");
		//int i = 1/0;
		//testServiceImpl.updateSpringTx1();
		return "index1";
	}
	@RequestMapping(value="/testTx2.do")
	public String testTx2(){
		System.out.println("------------testTx2------------");
		//testServiceImpl.deleteSpringTx1();;
		return "index1";
	}
	@RequestMapping(value="/testMail.do")
	public String testMail(){
		System.out.println("------------testMail------------");
		//testServiceImpl.sendMail();
		return "index1";
	}
	
	@RequestMapping(value="/testSorted.do")
	public String testSorted(){
		Waybill waybill = new Waybill();
		waybill.setBatch_id(1);
	    //sort_mode
	    waybill.setSort_mode(""+2);
	    //sort_source
	    waybill.setSort_source(""+3);  
	    //waybill_id运单跟踪ID
	    waybill.setWaybill_id(""+4);  
	    //waybill_num快件分拣过程唯一编号GUID
	    waybill.setWaybill_num(""+5);  
	    //waybill_code运单号
	    waybill.setWaybill_code(""+6); 
	    //waybill_site_code运单表中目的地代码
	    waybill.setWaybill_site_code(""+7);
	    //package_code包裹号
	    waybill.setPackage_code(""+8);
	    //waybill_status运单状态(0正常运单,1运单取消,2运单地址更改,3无数据)
	    waybill.setWaybill_status(9); 
	    //waybill_time运单生成时间
	    waybill.setWaybill_time(""+10); 
	    //site_code目的地代码
	    waybill.setSite_code(""+11);
	    //site_name目的地名称
	    waybill.setSite_name(""+12);
	    //car_id小车编号
	    waybill.setCar_id(13);
	    //chute_id滑槽口号
	    waybill.setChute_id(14);
	    //scan_id扫描仪编号
	    waybill.setScan_id(15);
	    //supply_id供件台编号导入号台
	    waybill.setSupply_id(16);
	    //layer_id层级id
	    waybill.setLayer_id(17);
	    //car_num小车编号
	    waybill.setCar_num(""+18);
	    //chute_num滑槽口编号
	    waybill.setChute_num(""+19);
	    //scan_num扫描仪编号
	    waybill.setScan_num(""+20);
	    //supply_num供件台编号
	    waybill.setSupply_num(""+21);
	    //layer_num层级编号
	    waybill.setLayer_num(""+22);
	    //package_weight重量
	    waybill.setPackage_weight(23);
	    //package_length长度
	    waybill.setPackage_length(24);
	    //package_width宽度
	    waybill.setPackage_width(25);
	    //package_height高度
	    waybill.setPackage_height(26);
	    //supply_type供件方式暂定1
	    waybill.setSupply_type(""+27);
	    //supply_time供件时间
	    waybill.setSupply_time(""+28);
	    //scan_cycle扫描圈数
	    waybill.setScan_cycle(29);
	    //scan_status扫描状态(0正常扫描,1无分拣方案,2无数据,3无读,4取消,5最大圈数,6格口错,7多条码,8迷失,255未知)
	    waybill.setScan_status(30);
	    //scan_time扫描时间
	    waybill.setScan_time(""+31); 
	    //sorting_status分拣状态(0正常扫描,1无分拣方案,2无数据,3无读,4取消,5最大圈数,6格口错,7多条码,8迷失,255未知)
	    waybill.setSorting_status(32);
	    //sorting_time分拣时间
	    waybill.setSorting_time(""+33);
	    //re_mark备注
	    waybill.setRe_mark(""+34);
	    //update_flag更新标识0：未更新1：已更新2不需要更新
	    waybill.setUpdate_flag(35);
	    //update_time更新时间
	    waybill.setUpdate_time(""+36);
	    //receive_time消息接收时间
	    waybill.setReceive_time(""+37);
	    sortServiceImpl.insertSorted(waybill);
	    
	    return "";
	}

	@RequestMapping(value="/testStatistics.do")
	public String testStatistics(){
		System.out.println("------------testStatistics------------");
		//statisticsServiceImpl.findSortList((long) 0, 10);
		return "index1";
	}	
	
}
