package cn.seagen.base.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.seagen.base.service.ScanService;
import cn.seagen.base.service.SortedService;

@Controller
public class QueryWrongSortController {
	private ScanService scanServiceImpl;
	private SortedService sortedServiceImpl;
	@Resource
	public void setScanServiceImpl(ScanService scanServiceImpl) {
		this.scanServiceImpl = scanServiceImpl;
	}
	@Resource
	public void setSortedServiceImpl(SortedService sortedServiceImpl) {
		this.sortedServiceImpl = sortedServiceImpl;
	}

	/** 查询运单的扫描记录 */
	@RequestMapping("/queryScanWaybill.do")
	@ResponseBody
	public Object queryScanWaybill(String packageCode){
		return scanServiceImpl.findScanInfos(packageCode);
	}
	
	/** 分拣消息查询 */
	@RequestMapping("/querySortWaybill.do")
	@ResponseBody
	public Object querySortWaybill(String packageCode){
		// 取得参数列表，以便进行数据库查询
		return sortedServiceImpl.findSortedInfos(packageCode);
	}
	/**查询统一导入台前后上件信息*/
	@RequestMapping("/queryScanBeforeOrAfter.do")
	@ResponseBody
	public Object queryScanBeforeOrAfter(int supplyId,String supplyTime){
		// 取得参数列表，以便进行数据库查询
		//int supplyId = Integer.parseInt(supply_Id);
		return scanServiceImpl.findScanBeforeOrAfter(supplyId, supplyTime);
	}
	/**查询当前小车前后小车，在临近扫描时间的分拣信息*/
	@RequestMapping("/queryCarBeforeOrAfter.do")
	@ResponseBody
	public Object queryCarBeforeOrAfter(int carId,String scanTime){
		return sortedServiceImpl.findCarBeforeOrAfter(carId, scanTime);
	}
}
