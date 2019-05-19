package cn.seagen.base.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.seagen.base.service.SystemEventService;
import cn.seagen.base.utils.ReportUtils;
import cn.seagen.base.vo.RequestBase;

@Controller
public class SystemEventController {

	private SystemEventService systemEventServiceImpl;
	
	@Resource
	public void setSystemEventServiceImpl(SystemEventService systemEventServiceImpl) {
		this.systemEventServiceImpl = systemEventServiceImpl;
	}

	@RequestMapping(value="/querySystemEvents.do")
	@ResponseBody
	public Object querySystemEvent(int page,int rows,int sort,String startTime,String endTime,int eventType){
		return systemEventServiceImpl.findSystemEventList(page, rows,sort,startTime, endTime,eventType);
	}
	
	/**
	 * 导出Excel：运行事件
	 * @param requestBase
	 * @param request
	 * @return
	 */
	@RequestMapping("/exportEvent.do")
	@ResponseBody
	public void exportEvent(RequestBase requestBase,int sort,HttpServletRequest request,HttpServletResponse response) {
		byte[] bytes = systemEventServiceImpl.exportEvent(requestBase,sort);
		ReportUtils.doResponse(ReportUtils.getReportFirstName(requestBase.getType(), //
				requestBase) + "_运行事件报表", response, bytes);
	}
}
