package cn.seagen.base.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.seagen.base.service.LogService;
import cn.seagen.base.service.UserService;

/** 用于页面的登陆、登出、操作员相关操作 */
@Controller
public class LoginController {
	protected Logger logger = LogManager.getLogger(LoginController.class.getName());
	protected String clsName = "[OUT]-" + LoginController.class.getName() + "-> ";
	@Resource
	LogService logServiceImpl;
	@Resource
	UserService userServiceImpl;
	

	/** 登陆 */
	// @RequestMapping(value = "/login.do", method = RequestMethod.POST)
	@RequestMapping("/login.do")
	@ResponseBody
	public Map<String, Object> login(String userName, String pwd, String timestamp, HttpServletRequest request) {
		// 客户端地址IP与端口
		//String clientIP = request.getRemoteAddr() + ":" + request.getRemotePort();

		Map<String, Object> map = new HashMap<String, Object>();
		HttpSession seesion = request.getSession();
		map = userServiceImpl.login(userName, pwd);
		int res = (Integer) map.get("res");
		if (res == 0) {
			seesion.setAttribute("userId", map.get("userId"));
			seesion.setAttribute("userName", userName);
			seesion.setAttribute("roleId", map.get("roleId"));
		}
		return map;
	}

	/** 退出 */
	@RequestMapping("/logout.do")
	@ResponseBody
	public Object logout(HttpServletRequest request) {
		HttpSession seesion = request.getSession();
		//String userId = (String) seesion.getAttribute("userId");
		//String userName = (String) seesion.getAttribute("userName");
		seesion.removeAttribute("userId");
		seesion.removeAttribute("userName");
		seesion.removeAttribute("roleId");
		return "0";
	}

	/** 修改密码 */
	// @RequestMapping(value = "/modpassword.do", method = RequestMethod.POST)
	@RequestMapping("/updatepwd.do")
	@ResponseBody
	public Object updatePwd(String userId, String pwd, String npwd, String timestamp, HttpServletRequest request) {
		return userServiceImpl.updatePwd(userId, pwd, npwd);
	}

}
