package cn.seagen.base.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.seagen.base.service.ReportScanService;
import cn.seagen.base.utils.ReportUtils;
import cn.seagen.base.vo.RequestBase;

@Controller
public class ReportScanController {
	
	@Resource
	private ReportScanService reportScanServiceImpl;
	/**
	 * @param requestBase
	 * @param request
	 * @return
	 */
	@RequestMapping("/reportScan.do")
	@ResponseBody
	public Object reportScan(RequestBase requestBase,HttpServletRequest request) {
		return reportScanServiceImpl.queryScan(requestBase);
	}
	
	/**
	 * 导出Excel：扫描量统计导出
	 * @param requestBase
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportScan.do")
	@ResponseBody
	public void exportScan(RequestBase requestBase,HttpServletRequest request, HttpServletResponse response) {
		byte[] bytes = reportScanServiceImpl.exportScan(requestBase);
		ReportUtils.doResponse(ReportUtils.getReportFirstName(requestBase.getType(),//
				requestBase) + "_扫描量统计报表", response, bytes);
	}
	
}
