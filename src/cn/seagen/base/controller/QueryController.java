package cn.seagen.base.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.seagen.base.mq.dto.MQJb;
import cn.seagen.base.mq.work.MQTask;
import cn.seagen.base.service.PrinterDataService;
import cn.seagen.base.service.ScanService;
import cn.seagen.base.service.SortSchemeService;
import cn.seagen.base.service.SortedService;
import cn.seagen.base.service.WaybillService;
import cn.seagen.base.vo.RequestBase;
import cn.seagen.base.vo.ResponseBase;

@Controller
public class QueryController {
	
	@Resource
	private ScanService scanServiceImpl;
	@Resource
	private SortedService sortedServiceImpl;
	@Resource
	private SortSchemeService sortSchemeServiceImpl;
	@Resource
	private PrinterDataService printerDataServiceImpl;
	@Resource
	private WaybillService WaybillServiceImpl;
	
	/**
	 * @param requestBase
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryScan.do")
	@ResponseBody
	public Object queryScan(String packagecode,int page,int rows,HttpServletRequest request) {
		return scanServiceImpl.findScanList(packagecode, page, rows);
	}
	
	/**
	 * @param packagecode
	 * @param boxcode
	 * @param request
	 * @return
	 */
	@RequestMapping("/querySorted.do")
	@ResponseBody
	public Object querySorted(String packagecode,String boxcode,int page,int rows,HttpServletRequest request) {
		return sortedServiceImpl.findSortedList(packagecode, boxcode, page, rows);
	}
	
	/**
	 * 查询当前使用的方案
	 * @param pages 页码
	 * @param rows 每页显示数量
	 * @param chute_no 
	 * @param site_code
	 * @param box_site_name
	 * @return
	 */
	@RequestMapping("/queryRunSortSchemeDetail.do")
	@ResponseBody
	public Object queryRunSortDetail(int page,int rows,int chute_no,String site_code,String box_site_name){
		return sortSchemeServiceImpl.findRunSortSchemeList(page, rows, chute_no, site_code, box_site_name);
	}

	/** 原始运单数据查询 */
	@RequestMapping("/querywaybill.do")
	@ResponseBody
	public Object querywaybill(int page,int rows,String waybillcode) {
		return WaybillServiceImpl.findWaybillList(page, rows, waybillcode);
	}
	
	/** 运单轨迹数据查询 */
	@RequestMapping("/querywaybilltrace.do")
	@ResponseBody
	public Object queryWaybillTrace(String waybillCode,HttpServletRequest request) {
		return WaybillServiceImpl.findWaybillTrace(waybillCode);
	}
	
	/**
	 * @param requestBase
	 * @param update_flag  0,未上传；1，已上传；
	 * @param request
	 * @return
	 */
	@RequestMapping("/querySortdetail.do")
	@ResponseBody
	public Object querySortdetail(RequestBase requestBase,int update_flag,HttpServletRequest request) {
		return sortedServiceImpl.findSortDetailList(requestBase.getDate(), requestBase.getEnd_date(), 
				update_flag, requestBase.getPage(), requestBase.getRows());
	}
	@ResponseBody
	@RequestMapping(value="/getPrinterRecord.do")
	public Object getPrinterRecord(int page,int rows,String boxCode,int chuteId){
		// page 当前页    rows 每页记录   PrinterDataDao.getlist(page, rows, boxCode, chuteId);
		return printerDataServiceImpl.queryPrinterData(page, rows, boxCode, chuteId);
	}
	@ResponseBody
	@RequestMapping(value="/rePrint.do")
	public ResponseBase rePrint(String boxCode){
		ResponseBase responseBase = new ResponseBase();
		MQJb mQJb = new MQJb();
		mQJb.setBox_code(boxCode);
		mQJb.setType(2);
		if(new MQTask().jb_process(mQJb)){
			responseBase.setResult(0);
			responseBase.setMessage("发送打印消息成功!");
		}else{
			responseBase.setResult(1);
			responseBase.setMessage("发送打印消息失败!");
		}
		return responseBase;
	}
}
