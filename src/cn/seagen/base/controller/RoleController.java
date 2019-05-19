package cn.seagen.base.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.seagen.base.service.NodeService;
import cn.seagen.base.service.RoleService;
import cn.seagen.base.utils.JUtils;
import cn.seagen.base.vo.ResponseBase;

@Controller
public class RoleController {
	protected Logger logger = LogManager.getLogger(RoleController.class.getName());
	@Resource
	private RoleService roleServiceImpl;
	@Resource
	private NodeService nodeServiceImpl;
	@ResponseBody
	@RequestMapping(value = "/updateRoleNode.do")
	public ResponseBase updateRoleNode(int roleId,String nodeList){
		ResponseBase response = new ResponseBase();
		try {
			if(JUtils.isEmpty(nodeList)){
				response.setResult(1);
				response.setMessage("更新失败");
				return response;
			}
			String[] temp = nodeList.toString().split(",");
			int len = temp.length;
			int[] nodeId = new int[len];
			for(int i = 0;i < len;i++){
				nodeId[i] = JUtils.strToInt(temp[i]);
			}
			if(roleServiceImpl.updateRoleNodeInfo(roleId, nodeId)){
				response.setResult(0);
				response.setMessage("success");
				return response;
			}else{
				response.setResult(1);
				response.setMessage("更新失败");
				return response;
			}
			
		} catch (Exception e) {
			logger.error("updateRoleNode更新出错："+e.getMessage(),e);
			response.setResult(1);
			response.setMessage("更新失败");
			return response;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/getRoles.do")
	public Object getRoleList(HttpSession session) {
		int roleId = 0;
		try {
			if (session != null) {
				roleId = JUtils.strToInt(session.getAttribute("roleId").toString());
			}
		} catch (Exception e) {
		}
		return roleServiceImpl.getRoleList(roleId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getAllRoles.do")
	public Object getAllRoleList(HttpSession session) {
		int roleId = 0;
		try {
			if (session != null) {
				roleId = JUtils.strToInt(session.getAttribute("roleId").toString());
			}
		} catch (Exception e) {
		}
		if(roleId != 1)
			return null;
		return roleServiceImpl.getRoleList(-1);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getCurrNodes.do")
	public Object getCurrNodeList(int roleId) {
		return nodeServiceImpl.getNodeList(roleId);
	}
	@ResponseBody
	@RequestMapping(value = "/getAllNodes.do")
	public Object getAllNodeList(HttpSession session) {
		int roleId = 0;
		try {
			if (session != null) {
				roleId = JUtils.strToInt(session.getAttribute("roleId").toString());
			}
		} catch (Exception e) {
		}

		if(roleId != 1)
			return null;
		return nodeServiceImpl.getNodeList();
	}
	
	public static void main(String[] args) {
		System.out.println(("0,".split(","))[0]);
	}
}
