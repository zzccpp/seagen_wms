package cn.seagen.base.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.seagen.base.bean.RbacNode;
import cn.seagen.base.dao.NodeDao;
import cn.seagen.base.enums.MenuType;
import cn.seagen.base.service.NodeService;
import cn.seagen.base.vo.Menu;
@Service
public class NodeServiceImpl implements NodeService {
	@Resource
	private NodeDao nodeDaoIpml;
	
	protected static final int powerMenu = 0; // 0为权限和菜单 //
	protected static final int onlyPower = 1; // 1为权限
	protected static final int closeMeun = 2; // 2未开启
	private List<Menu> menuAllList = new ArrayList<Menu>(); // 所有菜单列表(包括权限)
	private List<Menu> menuMainList = new ArrayList<Menu>(); // 所有能显示的菜单列表

	public NodeServiceImpl() {
		menuAllList.clear();
	}

	// 添加菜单
	protected void addMenu(int key, int pid, int index, String name, String url, String iconCls, MenuType mt, int mtype) {
		menuAllList.add(new Menu(key, pid, index, name, url, iconCls, mt, mtype));
	}
	
	//添加对应的权限

	/** 是否是根操作员权限 */
	/*private boolean isRootPower(int key) {
		int[] keylist = new int[] { 2 };
		for (int i = 0; i < keylist.length; i++) {
			if (keylist[i] == key)
				return true;
		}
		return false;
	}*/

	

	private void verifyMenu() {
		// 判断是否有子节点，这里是方便后期出菜单权限时用
		int listLen = menuAllList.size();
		Menu menun = null;
		Menu menunj = null;
		for (int i = listLen - 1; i >= 0; i--) {
			menun = (Menu) (menuAllList.get(i));
			for (int j = listLen - 1; j >= 0; j--) {
				if (i == j)
					continue;
				menunj = (Menu) (menuAllList.get(j));
				if (menun.getKey() == menunj.getPid()) {
					menun.setHaschild(true);
					break;
				}
			}
		}
	}

	private void sort(List<Menu> menunList) {
		Collections.sort(menunList, new Comparator<Menu>() {
			@Override
			public int compare(Menu o1, Menu o2) {
				int i = o1.getPid() - o2.getPid();
				if (i == 0) {
					int j = o1.getIndex() - o2.getIndex();
					return j;
				}
				return i;
			}
		});
	}


	@Override
	public boolean insertNode(RbacNode node) {
		return nodeDaoIpml.insertNode(node);
	}
	
	/** 获取有权限的菜单列表 */
	@Override
	public List<Menu> getNodeList(int roleId) {
		menuMainList.clear();
		menuAllList = nodeDaoIpml.getNodeList(roleId);
		verifyMenu();
		// 取有菜单的列表
		for (Menu mu : menuAllList) {
			if (mu.getPower() != 0)
				continue;
			
			menuMainList.add(mu);
		}
		// 排序
		sort(menuMainList);
		return menuMainList;
	}

	@Override
	public Object getNodeList() {
		return nodeDaoIpml.getNodeList();
	}

}
