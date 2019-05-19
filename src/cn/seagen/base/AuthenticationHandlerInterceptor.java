package cn.seagen.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.seagen.base.constant.WcsIpConstant;
/**
 * 判断是否已登录拦截器
 * @author zcp
 */
public class AuthenticationHandlerInterceptor extends HandlerInterceptorAdapter {
	
	/**
	 * 在调用Controller方法前调用,如果做登录验证的话,可优先考虑使用Servlet规范中的filter过滤器,
	 * 因为HandlerInterceptorAdapter依赖SpringMVC
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//对登录与登出放行
		System.out.println("SrpingMVC拦截器...");
		String wcsIp = request.getRemoteAddr();
		if(!StringUtils.isEmpty(wcsIp)){
			for(String ip : WcsIpConstant.ipS){
				if(ip.equals(wcsIp)){
					HttpSession seesion = request.getSession();
					seesion.setAttribute("userId", "3");
					seesion.setAttribute("userName", "wcs_user");
					seesion.setAttribute("roleId", "4");
//					return super.preHandle(request, response, handler);
				}
			}
		}
		//判断是否已登录
		String userName = (String) request.getSession().getAttribute("userName");
		if(null!=userName){
			return super.preHandle(request, response, handler);
		}else{
			response.sendRedirect("login.jsp");//未登录,重定向到登录界面
			//response.sendRedirect(JUtils.isEmpty(request.getContextPath()) ? "login.jsp" : request.getContextPath());
			return false;
		}
	}
	/**
	 * 调用Controller方法后,视图渲染前调用
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
	/**
	 * 在视图渲染后调用
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

}
