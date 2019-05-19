package cn.seagen.base.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.seagen.base.service.ReportBatchService;
import cn.seagen.base.utils.ReportUtils;
import cn.seagen.base.vo.RequestBase;
	
@Controller
public class ReportBatchController {
	
	@Resource
	private ReportBatchService reportBatchServiceImpl;
	/**
	 * @param requestBase
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryBatch.do")
	@ResponseBody
	public Object queryBatch(RequestBase requestBase,HttpServletRequest request) {
		return reportBatchServiceImpl.queryBatch(requestBase);
	}
	
	/**
	 * 导出Excel：批次统计
	 * @param requestBase
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportBatch.do")
	@ResponseBody
	public void exportBatch(RequestBase requestBase,HttpServletRequest request, HttpServletResponse response) {
		byte[] bytes = reportBatchServiceImpl.exportBatch(requestBase);
		ReportUtils.doResponse(ReportUtils.getReportFirstName(requestBase.getType()) //
				+ "_批次量统计报表", response, bytes);
	}
	
	/**
	 * @param requestBase
	 * @param request
	 * @return
	 */
	@RequestMapping("/getBatchColumns.do")
	@ResponseBody
	public Object getBatchColumns(RequestBase requestBase,HttpServletRequest request) {
		return reportBatchServiceImpl.getBatchColumns(requestBase);
	}
	
}
