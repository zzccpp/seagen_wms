package cn.seagen.base.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.seagen.base.service.NodeService;
import cn.seagen.base.utils.JUtils;
import cn.seagen.base.vo.Menu;

/** 用于页面的请求 */
@Controller
public class PagesController {
	protected Logger logger = LogManager.getLogger(PagesController.class.getName());
	@Resource
	private NodeService nodeServiceImpl;
	
	/** 请求各页面数据、菜单数据 */
	@RequestMapping("/pages.do")
	public ModelAndView pages(String para) {
		try {
			String[] paralist = para.split("_");
			String viewName = null;
			for (int i = 0; i < paralist.length; i++) {
				if (viewName == null)
					viewName = paralist[i];
				else
					viewName += "/" + paralist[i];
			}
			return new ModelAndView(viewName);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("pages.do error:" + e.getMessage());
			return new ModelAndView("system/common/404");
		}
	}

	/** 获取菜单 */
	@RequestMapping("/getMenu.do")
	@ResponseBody
	public List<Menu> getNode(HttpSession session, HttpServletRequest request) {
		int roleId = 0;
		try {
			if (session != null) {
				roleId = JUtils.strToInt(session.getAttribute("roleId").toString());
			}
		} catch (Exception e) {
		}
		return nodeServiceImpl.getNodeList(roleId);
	}

}
