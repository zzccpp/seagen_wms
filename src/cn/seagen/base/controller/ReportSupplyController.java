package cn.seagen.base.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.seagen.base.service.ReportSupplyService;
import cn.seagen.base.utils.ReportUtils;
import cn.seagen.base.vo.RequestBase;
import cn.seagen.base.vo.ScanSupply;
import cn.seagen.base.vo.SixSpike;

@Controller
public class ReportSupplyController {
	@Resource
	private ReportSupplyService reportSupplyServiceImpl;
	
	/**
	 * @param requestBase
	 * @param request
	 * @return
	 */
	@RequestMapping("/reportsupply.do")
	@ResponseBody
	public Object querysupply(RequestBase requestBase,HttpServletRequest request) {
		return reportSupplyServiceImpl.querySupply(requestBase);
	}
	/**
	 * 导入台的峰值统计
	 * @param stime
	 * @param etime
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryScanSupply.do")
	@ResponseBody
	public Object queryScanSupply(RequestBase requestBase,HttpServletRequest request){
		
		List<ScanSupply> list = reportSupplyServiceImpl.queryScanSupply(requestBase);
		return ReportUtils.getDealData(list);
	}
	/**
	 * 查询某个导入台时间段的,连续60分钟之内的峰值
	 * @param stime 开始时间
	 * @param etime 结束时间
	 * @param supplyNo 导入台号
	 * @param sixSpikeNum 峰值
	 */
	@RequestMapping("/querySpikeNum.do")
	@ResponseBody
	public List<SixSpike> querySpikeNum(String stime,String etime,String supplyNo,String sixSpikeNum){
		int supplyId = Integer.parseInt(supplyNo.substring(2,supplyNo.length()-3));
		int SpikeNum = Integer.parseInt(sixSpikeNum);
		int layerId = Integer.parseInt(supplyNo.substring(0,1));
		return reportSupplyServiceImpl.getSixSpikeData(stime, etime, supplyId, SpikeNum,layerId);
	}
	
	/**
	 * 导出Excel：导入台统计
	 * @param requestBase
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportSupply.do")
	@ResponseBody
	public void exportSupply(RequestBase requestBase,HttpServletRequest request, HttpServletResponse response) {
		//LogDao.record_report_log("导出导入台量统计", SessionMarnager.getLogOperater(request));
		byte[] bytes = reportSupplyServiceImpl.exportSupply(requestBase);
		ReportUtils.doResponse(ReportUtils.getReportFirstName(requestBase.getType(), //
				requestBase) + "_导入台统计报表", response, bytes);
	}
	
	/**
	 * 导入台统计数据导出
	 * @param stime
	 * @param etime
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportScanSupply.do")//String stime,String etime
	public void exportScanSupply(RequestBase requestBase,HttpServletRequest request,HttpServletResponse response){
		//LogDao.record_report_log("导出导入台供件统计", SessionMarnager.getLogOperater(request));
		byte[] bytes = reportSupplyServiceImpl.exportSpikeSupply( requestBase);
		ReportUtils.doResponse(requestBase.getDate()+"至"+requestBase.getEnd_date()+"导入台供件统计报表",response, bytes);
	}
}
