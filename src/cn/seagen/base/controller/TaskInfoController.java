package cn.seagen.base.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.seagen.base.mq.work.MQMessageWork;
import cn.seagen.base.utils.ScheduledRuntimerManager;


@Controller
public class TaskInfoController {
	protected Logger logger = LogManager.getLogger(TaskInfoController.class.getName());

	/** 请求当前任务明细数据 */
	@RequestMapping("/querytasklist.do")
	@ResponseBody
	public Object querytasklist(String timestamp, HttpServletRequest request) {
		return MQMessageWork.getTaskInfo();
	}
	
	/** 请求任务执行效率明细数据 */
	@RequestMapping("/queryruntimerlist.do")
	@ResponseBody
	public Object queryruntimerlist(String timestamp, HttpServletRequest request) {
		return ScheduledRuntimerManager.getRuntimerList();
	}
	
}
