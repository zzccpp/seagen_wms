--------------------------------提示---------------------------------------------
-- 数据库表初始化数据写在这里面,以$$结束,
-- 注意：需在建表语句执行完后再执行。
-- ------------------------------------------------------------------------------
-- 用户表初始化数据
-- ----------------------------
INSERT INTO `seagen_wms`.`rbac_user` (`id`, `user_name`, `pwd`, `telno`, `email`, `use_flag`, `remark`) 
	VALUES ('1', 'admin', '123456', '', '', '0', '');
$$

-- ----------------------------
-- 角色表初始化数据
-- ----------------------------
INSERT INTO `seagen_wms`.`rbac_role` (`id`, `role_name`, `use_flag`, `remark`) 
	VALUES ('1', '开发人员', '0', '');
$$
-- ----------------------------
-- 菜单节点表初始化数据
-- ----------------------------
INSERT INTO `seagen_wms`.`rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('1', '', '系统管理', 'cog', '0', '0', '', '6', '0');
$$
INSERT INTO `seagen_wms`.`rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('2', 'base_general_users', '用户管理', 'icon-shapes', '0', '0', '', '1', '1');
$$
-- ----------------------------
-- 角色-菜单节点对应表初始化数据
-- ----------------------------
INSERT INTO `seagen_wms`.`rbac_role_node` (`id`, `node_id`, `role_id`) VALUES ('1', '1', '1');
$$
INSERT INTO `seagen_wms`.`rbac_role_node` (`id`, `node_id`, `role_id`) VALUES ('2', '2', '1');
$$
-- ----------------------------
-- 用户-角色对应表初始化数据
-- ----------------------------
INSERT INTO `seagen_wms`.`rbac_user_role` (`id`, `user_id`, `role_id`) VALUES ('1', '1', '1');
$$


