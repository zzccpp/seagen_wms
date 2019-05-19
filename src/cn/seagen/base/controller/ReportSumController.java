package cn.seagen.base.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.seagen.base.service.ReportSumService;
import cn.seagen.base.utils.ReportUtils;
import cn.seagen.base.vo.RequestBase;

@Controller
public class ReportSumController {
	
	@Resource
	private ReportSumService reportSumServiceImpl;
	/**
	 * @param requestBase
	 * @param request
	 * @return
	 */
	@RequestMapping("/querySum.do")
	@ResponseBody
	public Object querySum(RequestBase requestBase,HttpServletRequest request) {
		return reportSumServiceImpl.querySum(requestBase);
	}
	
	/**
	 * 导出Excel：分拣量统计
	 * @param requestBase
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportSum.do")
	@ResponseBody
	public void exportSum(RequestBase requestBase,HttpServletRequest request, HttpServletResponse response) {
		byte[] bytes = reportSumServiceImpl.exportSum(requestBase);
		ReportUtils.doResponse(ReportUtils.getReportFirstName(requestBase.getType(), //
				requestBase) + "_分拣量统计报表", response, bytes);
	}
	
}
