package cn.seagen.base.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import cn.seagen.base.bean.RbacNode;
import cn.seagen.base.dao.NodeDao;
import cn.seagen.base.enums.MenuType;
import cn.seagen.base.utils.JUtils;
import cn.seagen.base.vo.Menu;

@Component
public class NodeDaoImpl implements NodeDao {
	private static Logger logger = LogManager.getLogger(NodeDaoImpl.class
			.getName());
	@Resource
	private JdbcTemplate template;

	@Override
	public boolean insertNode(RbacNode node) {
		final RbacNode tempNode = node;
		int res = 0;
		String sql = "insert into rbac_node(id, node_url, node_name, node_icon, node_type, use_flag, remark, sort, pid) values(%s)";
		sql = JUtils.FormatSqlPargram(sql, 9);
		try {
			/*template.batchUpdate(sql, new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i)throws SQLException {
					int tag = 0;
					ps.setInt(++tag, tempNode.getId());
					ps.setString(++tag, tempNode.getNodeUrl());
					ps.setString(++tag, tempNode.getNodeName());
					ps.setString(++tag, tempNode.getNodeIcon());
					ps.setInt(++tag, tempNode.getNodeType());
					ps.setInt(++tag, tempNode.getUseFlag());
					ps.setString(++tag, tempNode.getReMark());
					ps.setInt(++tag, tempNode.getSort());
					ps.setInt(++tag, tempNode.getPid());
				}
				public int getBatchSize() {
					return tempList.size();
				}
			});*/
			res = template.update(sql, new PreparedStatementSetter(){
				@Override
				public void setValues(PreparedStatement ps)
						throws SQLException {
					int tag = 0;
					ps.setInt(++tag, tempNode.getId());
					ps.setString(++tag, tempNode.getNodeUrl());
					ps.setString(++tag, tempNode.getNodeName());
					ps.setString(++tag, tempNode.getNodeIcon());
					ps.setInt(++tag, tempNode.getNodeType());
					ps.setInt(++tag, tempNode.getUseFlag());
					ps.setString(++tag, tempNode.getReMark());
					ps.setInt(++tag, tempNode.getSort());
					ps.setInt(++tag, tempNode.getPid());
				}
			});
			return (res > 0);
		} catch (Exception e) {
			logger.error("菜单节点数据添加失败:" + e.getMessage(), e);
		} 
		return false;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List<Menu> getNodeList(int roleId) {
		List<Menu> menuList = new ArrayList<Menu>();
		String sql = "select n.id, n.node_url, n.node_name, n.node_icon, n.node_type, n.use_flag, n.remark, n.sort, n.pid from rbac_node AS n left join"
				+ " (select node_id from rbac_role_node where role_id = ? )"
				+ " as rn on n.use_flag = 0 where n.id = rn.node_id;";
		try {
			List rows = template.queryForList(sql,roleId);
			Iterator it = rows.iterator();   
			
			while (it.hasNext()) {
				Map nodeMap = (Map) it.next(); 
				menuList.add(
					new Menu(
					JUtils.strToInt(nodeMap.get("id").toString()),
					JUtils.strToInt(nodeMap.get("pid").toString()),
					JUtils.strToInt(nodeMap.get("sort").toString()),
					nodeMap.get("node_name").toString(),
					nodeMap.get("node_url").toString(),
					nodeMap.get("node_icon").toString(),
					MenuType.STANDARD,
					0
				));
			}
			
		} catch (Exception e) {
			logger.error("getNodeList by roleId出错：" + e.getMessage(), e);
		} 
		return menuList;
	}
	@Override
	public Object getNodeList() {
		List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>();
		String sql = "select id,node_name as nodeName,pid from rbac_node where use_flag = 0";
		try {
			list = template.queryForList(sql);
		} catch (Exception e) {
			logger.error("getNodeList 出错：" + e.getMessage(), e);
		}
		return list;
	}
}
