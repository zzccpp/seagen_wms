package cn.seagen.base.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.seagen.base.service.ReportCarService;
import cn.seagen.base.utils.ReportUtils;
import cn.seagen.base.vo.RequestBase;

@Controller
public class ReportCarController {

	protected Logger logger = LogManager.getLogger(ReportCarController.class.getName());

	@Resource
	private ReportCarService reportCarServiceImpl;
	/**
	 * @param requestBase
	 * @param request
	 * @return
	 */
	@RequestMapping("/reportcar.do")
	@ResponseBody
	public Object querycar(RequestBase requestBase,HttpServletRequest request) {
		return reportCarServiceImpl.querycar(requestBase);
	}
	
	/**
	 * 导出Excel：小车量统计
	 * @param requestBase
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportCar.do")
	@ResponseBody
	public void exportCar(RequestBase requestBase,HttpServletRequest request, HttpServletResponse response) {
		byte[] bytes = reportCarServiceImpl.exportCar(requestBase);
		ReportUtils.doResponse(ReportUtils.getReportFirstName(requestBase.getType(), //
				requestBase) + "_小车量统计报表", response, bytes);
	}

}
