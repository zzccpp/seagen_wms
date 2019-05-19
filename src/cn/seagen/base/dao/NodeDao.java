package cn.seagen.base.dao;

import java.util.List;

import cn.seagen.base.bean.RbacNode;
import cn.seagen.base.vo.Menu;

public interface NodeDao {
	public boolean insertNode(RbacNode node);
	public List<Menu> getNodeList(int roleId);
	public Object getNodeList();
}
