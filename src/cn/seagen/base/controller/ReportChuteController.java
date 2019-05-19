package cn.seagen.base.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.seagen.base.service.ReportChuteService;
import cn.seagen.base.vo.RequestBase;

@Controller
public class ReportChuteController {
	@Resource
	private ReportChuteService reportChuteServiceImpl;
	/**
	 * @param requestBase
	 * @param request
	 * @return
	 */
	@RequestMapping("/reportchute.do")
	@ResponseBody
	public Object querychute(RequestBase requestBase,HttpServletRequest request) {
		//LogDao.record_report_log("查询封包量统计", SessionMarnager.getLogOperater(request));
		return reportChuteServiceImpl.queryChute(requestBase);
	}
}
