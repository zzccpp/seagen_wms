package cn.seagen.base.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.seagen.base.bean.RbacUser;

public class SessionMarnager {
	public static RbacUser getLogOperater(HttpServletRequest request) {
		RbacUser operator = new RbacUser();
		try {
			HttpSession session = request.getSession();
			if (session != null) {
				operator.setId(JUtils.strToInt((String) session.getAttribute("userId")));
				operator.setUserName((String) session.getAttribute("userName"));
			}
		} catch (Exception e) {
		}
		return operator;
	}
}
