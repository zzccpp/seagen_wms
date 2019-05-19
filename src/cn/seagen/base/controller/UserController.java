package cn.seagen.base.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.seagen.base.bean.RbacUser;
import cn.seagen.base.service.UserService;
import cn.seagen.base.vo.ResponseBase;

@Controller
public class UserController {
	protected Logger logger = LogManager.getLogger(UserController.class.getName());
	@Resource
	private UserService userServiceImpl;
	
	@ResponseBody
	@RequestMapping(value = "/getUsers.do")
	public Object getUsers(HttpServletRequest request) {
		HttpSession seesion = request.getSession();
		int roleId = Integer.valueOf(seesion.getAttribute("roleId").toString());
		return userServiceImpl.getUsers(roleId);
	}

	@RequestMapping(value = "/addUser.do")
	@ResponseBody
	public ResponseBase addUser(RbacUser user, int roleId) {
		ResponseBase responseBase = new ResponseBase();
		try {
			int res = userServiceImpl.findUser(user.getUserName());
			if(res == -1){
				responseBase.setResult(1);
				responseBase.setMessage("添加失败，请检查后再提交！");
				return responseBase;
			}
			if(res > 0){
				responseBase.setResult(1);
				responseBase.setMessage("用户已存在，请检查后再提交！");
				return responseBase;
			}
			if (userServiceImpl.insertUser(user,roleId)) {
				responseBase.setResult(0);
				responseBase.setMessage("success");
			}else{
				responseBase.setResult(1);
				responseBase.setMessage("添加失败，请检查后再提交！");
			} 
		} catch (Exception e) {
			responseBase.setResult(1);
			responseBase.setMessage("添加失败，请检查后再提交！");
		}
		
		return responseBase;
	}

	@RequestMapping(value = "/updateUser.do")
	@ResponseBody
	public ResponseBase updateUser(RbacUser user, int roleId) {
		ResponseBase responseBase = new ResponseBase();
		try {
			int res = userServiceImpl.findUser(user.getUserName());
			if(res == -1){
				responseBase.setResult(1);
				responseBase.setMessage("添加失败，请检查后再提交！");
				return responseBase;
			}
			if(res > 0 && res != user.getId()){
				
				responseBase.setResult(1);
				responseBase.setMessage("用户名已存在，请检查后再提交！");
				return responseBase;
			}
			
			if (userServiceImpl.updateUser(user, roleId)) {
				responseBase.setResult(0);
				responseBase.setMessage("success");
			} else {
				responseBase.setResult(1);
				responseBase.setMessage("修改失败，请检查后再修改！");
			}
		} catch (Exception e) {
			responseBase.setResult(1);
			responseBase.setMessage("修改失败，请检查后再修改！");
		}
		
		return responseBase;
	}

	@RequestMapping(value = "/lockOrUnlock.do")
	@ResponseBody
	public ResponseBase updateUseFlag(int id, String userName, String useFlag, HttpSession session) {
		ResponseBase responseBase = new ResponseBase();
		if (userServiceImpl.updateUseFlag(id, useFlag)) {
			// 使该用户session过期
			// seesion.getAttribute("userName");
			responseBase.setResult(0);
			responseBase.setMessage("success");
		} else {
			responseBase.setResult(1);
			responseBase.setMessage("error");
		}
		return responseBase;
	}
	
	
}
