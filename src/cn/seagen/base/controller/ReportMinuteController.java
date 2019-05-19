package cn.seagen.base.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.seagen.base.service.ReportMinuteService;
import cn.seagen.base.utils.ReportUtils;
import cn.seagen.base.vo.RequestBase;

@Controller
public class ReportMinuteController {
	
	@Resource
	private ReportMinuteService reportMinuteServiceImpl;
	/**
	 * @param requestBase
	 * @param request
	 * @return
	 */
	@RequestMapping("/reportMinute.do")
	@ResponseBody
	public Object reportMinute(RequestBase requestBase,HttpServletRequest request) {
		return reportMinuteServiceImpl.queryMinute(requestBase);
	}
	
	/**
	 * 导出Excel：分钟量统计
	 * @param requestBase
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportMinute.do")
	@ResponseBody
	public void exportMinute(RequestBase requestBase,HttpServletRequest request, HttpServletResponse response) {
		byte[] bytes = reportMinuteServiceImpl.exportMinute(requestBase);
		ReportUtils.doResponse(ReportUtils.getReportFirstName(requestBase.getType(), //
				requestBase) + "_分钟量统计报表", response, bytes);
	}
	
}
