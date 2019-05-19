package cn.seagen.base.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.seagen.base.bean.SystemSet;
import cn.seagen.base.service.SysSetService;
import cn.seagen.base.utils.SystemSetUtils;
import cn.seagen.base.vo.ResponseBase;

@Controller
public class SystemSetController {
	protected Logger logger = LogManager.getLogger(SystemSetController.class.getName());
	@Resource
	private SysSetService sysSetServiceImpl;
	
	/**
	 * 系统配置参数类型标题数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTitleMapList.do")
	public Object getTitleMapList() {
		return SystemSetUtils.getTitleMap();
	}
	
	@RequestMapping("/getSystemSetList.do")
	@ResponseBody
	public Object getSystemSetList (String timestamp, HttpServletRequest request) {
		String sets = request.getParameter("sets");
		List<SystemSet> systemSetList = sysSetServiceImpl.getSystemSetListByNames(SystemSetUtils.initConstant(sets));
		return systemSetList;
	}
	
	/**
	 * 更新参数
	 * @param systemSet
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateSystemSet.do")
	public Object updateSystemSet(SystemSet systemSet, HttpServletRequest request) throws Exception {
		ResponseBase responseBase = new ResponseBase();
		SystemSet set = sysSetServiceImpl.getSystemSet(systemSet.getfRecno());
		//更新参数值
		try {
			SystemSetUtils.updateValue(set.getSetName(),systemSet.getSetValue());
		} catch (RuntimeException e) {
			logger.error("更新"+set.getSetName()+"参数"+systemSet.getSetValue()+"格式不符合,"+e.getMessage());
			responseBase.setResult(1);
			responseBase.setMessage(e.getMessage());
			return responseBase;
		} catch (Exception e) {
			logger.error("更新系统配置参数异常!"+e);
			responseBase.setResult(1);
			responseBase.setMessage("更新失败!");
			return responseBase;
		}
		//更新数据库
		if (sysSetServiceImpl.updateSystemSet(systemSet)) {
			responseBase.setResult(0);
			responseBase.setMessage("更新成功！");
		} else {
			responseBase.setResult(1);
			responseBase.setMessage("更新失败!");
		}
		return responseBase;
	}
}
