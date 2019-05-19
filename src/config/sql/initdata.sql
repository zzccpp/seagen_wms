--------------------------------提示---------------------------------------------
-- 数据库表初始化数据写在这里面,以$$结束,
-- 注意：需在建表语句执行完后再执行。
-- ------------------------------------------------------------------------------
-- 用户表初始化数据
-- ----------------------------
INSERT INTO `rbac_user` (`id`, `user_name`, `pwd`, `telno`, `email`, `use_flag`, `remark`) 
	VALUES ('1', 'admin', '123456', '', '', '0', '');
$$
INSERT INTO `rbac_user` (`id`, `user_name`, `pwd`, `telno`, `email`, `use_flag`, `remark`)
	VALUES ('2', 'user', '123456', '', '', '0', '');
$$
INSERT INTO `rbac_user` (`id`, `user_name`, `pwd`, `telno`, `email`, `use_flag`, `remark`)
	VALUES ('3', 'wcs_user', '123456', '', '', '0', '');
$$
-- ----------------------------
-- 角色表初始化数据
-- ----------------------------
INSERT INTO `rbac_role` (`id`, `role_name`, `use_flag`, `remark`) 
	VALUES ('1', '开发人员', '0', '');
$$
INSERT INTO `rbac_role` (`id`, `role_name`, `use_flag`, `remark`) 
	VALUES ('2', '管理员', '0', '');
$$
INSERT INTO `rbac_role` (`id`, `role_name`, `use_flag`, `remark`) 
	VALUES ('3', '操作员', '0', '');
$$
INSERT INTO `rbac_role` (`id`, `role_name`, `use_flag`, `remark`) 
	VALUES ('4', 'wcs免登陆', '0', '');
$$
-- ----------------------------
-- 菜单节点表初始化数据
-- ----------------------------
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('1', '', '系统管理', 'cog', '0', '0', '', '6', '0');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('2', 'base_general_users', '用户管理', 'icon-shapes', '0', '0', '', '1', '1');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('3', 'base_general_systemset', '参数设置', 'icon-shapes', '0', '0', '', '3', '1');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`)
	VALUES ('4', 'base_general_rolenode', '角色管理', 'icon-shapes', '0', '0', '', '4', '1');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`)
	VALUES ('5', 'base_general_performance', '性能监控', 'icon-shapes', '0', '0', '', '5', '1');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`)
	VALUES ('6', 'base_test_mqtest', 'MQ测试', 'icon-shapes', '0', '0', '', '6', '1');
$$
-- ----------------------------
-- 角色-菜单节点对应表初始化数据
-- ----------------------------
INSERT INTO `rbac_role_node` (`id`, `node_id`, `role_id`) 
	VALUES ('1', '1', '1');
$$
INSERT INTO `rbac_role_node` (`id`, `node_id`, `role_id`) 
	VALUES ('2', '2', '1');
$$
INSERT INTO `rbac_role_node` (`id`, `node_id`, `role_id`) 
	VALUES ('3', '3', '1');
$$
INSERT INTO `rbac_role_node` (`id`, `node_id`, `role_id`) 
	VALUES ('4', '4', '1');
$$
INSERT INTO `rbac_role_node` (`id`, `node_id`, `role_id`) 
	VALUES ('5', '5', '1');
$$
INSERT INTO `rbac_role_node` (`id`, `node_id`, `role_id`) 
	VALUES ('6', '6', '1');
$$
-- ----------------------------
-- 用户-角色对应表初始化数据
-- ----------------------------
INSERT INTO `rbac_user_role` (`id`, `user_id`, `role_id`) 
	VALUES ('1', '1', '1');
$$
INSERT INTO `rbac_user_role` (`id`, `user_id`, `role_id`) 
	VALUES ('2', '2', '3');
$$
INSERT INTO `rbac_user_role` (`id`, `user_id`, `role_id`) 
	VALUES ('3', '4', '1');
$$
-- ----------------------------
-- 菜单节点表初始化数据
-- 统计菜单 2018-01-22 10:18:31 klq
-- ----------------------------
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('7', '', '报表统计', 'server_chart', '0', '0', '', '4', '0');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('8', 'base_report_sum', '分拣量统计', 'icon-shapes', '0', '0', '', '1', '7');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('9', 'base_report_batch', '批次量统计', 'icon-shapes', '0', '0', '', '2', '7');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('10', 'base_report_minute', '分钟量统计', 'icon-shapes', '0', '0', '', '3', '7');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('11', 'base_report_supply', '导入台统计', 'icon-shapes', '0', '0', '', '4', '7');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('12', 'base_report_car', '小车量统计', 'icon-shapes', '0', '0', '', '5', '7');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('13', 'base_report_scan', '扫描量统计', 'icon-shapes', '0', '0', '', '6', '7');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('14', 'base_report_chute', '封包量统计', 'icon-shapes', '0', '0', '', '7', '7');
$$

-- ----------------------------
-- 菜单节点表初始化数据
-- 查询菜单 2018-01-24 18:27:38 klq
-- ----------------------------
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('61', '', '数据查询', 'server_database', '0', '0', '', '3', '0');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('62', 'base_query_queryscheme', ' 当前分拣方案查询', 'icon-shapes', '0', '0', '', '1', '61');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('63', 'base_query_querywaybill', ' 原始运单查询', 'icon-shapes', '0', '0', '', '2', '61');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('64', 'base_query_queryprinterdata', '建包数据查询', 'icon-shapes', '0', '0', '', '3', '61');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('65', 'base_query_queryscan', '扫描数据查询', 'icon-shapes', '0', '0', '', '4', '61');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('66', 'base_query_querysorted', '分拣数据查询', 'icon-shapes', '0', '0', '', '5', '61');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('67', 'base_query_querywrongsort', '错分辅助查询', 'icon-shapes', '0', '0', '', '6', '61');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('68', 'base_query_querysortdetail', '分拣去重查询', 'icon-shapes', '0', '0', '', '7', '61');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('69', 'base_query_queryevent', '线体事件查询', 'icon-shapes', '0', '0', '', '8', '61');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('73', 'base_query_querywaybilltrace', ' 分拣轨迹查询', 'icon-shapes', '0', '0', '', '9', '61');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('70', '', '信息管理', 'cog', '0', '0', '', '1', '0');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('71', 'base_general_printerlist', ' 打印机设置', 'icon-shapes', '0', '0', '', '1', '70');
$$
INSERT INTO `rbac_node` (`id`, `node_url`, `node_name`, `node_icon`, `node_type`, `use_flag`, `remark`, `sort`, `pid`) 
	VALUES ('72', 'base_general_chuteareas', '格口区域设置', 'icon-shapes', '0', '0', '', '2', '70');
$$

INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('1', '1', '线体运行', '开机', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('2', '1', '线体运行', '关机', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('3', '1', '远程连接', '成功', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('4', '1', '远程连接', '失败', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('5', '1', '小车', '小车禁用启用', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('6', '1', '格口', '格口禁用启用', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('7', '1', '龙门架', '龙门架禁用启用', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('8', '1', '导入台', '导入台禁用启用', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('401', '1', '按钮急停', '急停按钮1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('402', '1', '按钮急停', '急停按钮2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('403', '1', '按钮急停', '急停按钮3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('404', '1', '按钮急停', '急停按钮4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('405', '1', '按钮急停', '急停按钮5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('406', '1', '按钮急停', '急停按钮6', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('407', '1', '按钮急停', '急停按钮7', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('408', '1', '按钮急停', '急停按钮8', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('409', '1', '按钮急停', '急停按钮9', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('410', '1', '按钮急停', '急停按钮10', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('411', '1', '按钮急停', '急停按钮11', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('412', '1', '按钮急停', '急停按钮12', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('413', '1', '按钮急停', '急停按钮13', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('414', '1', '按钮急停', '急停按钮14', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('415', '1', '按钮急停', '急停按钮15', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('416', '1', '按钮急停', '急停按钮16', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('417', '1', '按钮急停', '急停按钮17', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('418', '1', '按钮急停', '急停按钮18', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('419', '1', '按钮急停', '急停按钮19', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('420', '1', '按钮急停', '急停按钮20', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('421', '1', '按钮急停', '急停按钮21', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('422', '1', '按钮急停', '急停按钮22', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('423', '1', '按钮急停', '急停按钮23', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('424', '1', '按钮急停', '急停按钮24', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('425', '1', '按钮急停', '急停按钮25', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('426', '1', '按钮急停', '急停按钮26', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('427', '1', '按钮急停', '急停按钮27', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('428', '1', '按钮急停', '急停按钮28', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('429', '1', '按钮急停', '急停按钮29', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('430', '1', '按钮急停', '急停按钮30', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('431', '1', '按钮急停', '急停按钮31', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('432', '1', '按钮急停', '急停按钮32', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('433', '1', '按钮急停', '急停按钮33', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('434', '1', '按钮急停', '急停按钮34', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('435', '1', '按钮急停', '急停按钮35', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('436', '1', '按钮急停', '急停按钮36', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('437', '1', '按钮急停', '急停按钮37', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('438', '1', '按钮急停', '急停按钮38', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('439', '1', '按钮急停', '急停按钮39', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('440', '1', '按钮急停', '急停按钮40', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('441', '1', '按钮急停', '急停按钮41', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('442', '1', '按钮急停', '急停按钮42', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('443', '1', '按钮急停', '急停按钮43', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('444', '1', '按钮急停', '急停按钮44', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('445', '1', '按钮急停', '急停按钮45', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('446', '1', '按钮急停', '急停按钮46', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('447', '1', '按钮急停', '急停按钮47', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('448', '1', '按钮急停', '急停按钮48', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('449', '1', '按钮急停', '急停按钮49', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('450', '1', '按钮急停', '急停按钮50', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('451', '1', '按钮急停', '急停按钮51', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('452', '1', '按钮急停', '急停按钮52', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('453', '1', '按钮急停', '急停按钮53', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('454', '1', '按钮急停', '急停按钮54', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('455', '1', '按钮急停', '急停按钮55', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('456', '1', '按钮急停', '急停按钮56', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('457', '1', '按钮急停', '急停按钮57', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('458', '1', '按钮急停', '急停按钮58', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('459', '1', '按钮急停', '急停按钮59', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('460', '1', '按钮急停', '急停按钮60', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('461', '1', '按钮急停', '急停按钮61', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('462', '1', '按钮急停', '急停按钮62', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('463', '1', '按钮急停', '急停按钮63', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('464', '1', '按钮急停', '急停按钮64', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('465', '1', '按钮急停', '急停按钮65', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('466', '1', '按钮急停', '急停按钮66', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('467', '1', '按钮急停', '急停按钮67', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('468', '1', '按钮急停', '急停按钮68', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('469', '1', '按钮急停', '急停按钮69', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('470', '1', '按钮急停', '急停按钮70', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('471', '1', '按钮急停', '急停按钮71', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('472', '1', '按钮急停', '急停按钮72', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('473', '1', '按钮急停', '急停按钮73', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('474', '1', '按钮急停', '急停按钮74', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('475', '1', '按钮急停', '急停按钮75', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('476', '1', '按钮急停', '急停按钮76', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('477', '1', '按钮急停', '急停按钮77', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('478', '1', '按钮急停', '急停按钮78', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('479', '1', '按钮急停', '急停按钮79', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('480', '1', '按钮急停', '急停按钮80', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('481', '1', '按钮急停', '急停按钮81', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('482', '1', '按钮急停', '急停按钮82', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('483', '1', '按钮急停', '急停按钮83', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('484', '1', '按钮急停', '急停按钮84', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('485', '1', '按钮急停', '急停按钮85', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('486', '1', '按钮急停', '急停按钮86', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('487', '1', '按钮急停', '急停按钮87', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('488', '1', '按钮急停', '急停按钮88', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('489', '1', '按钮急停', '急停按钮89', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('490', '1', '按钮急停', '急停按钮90', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('491', '1', '按钮急停', '急停按钮91', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('492', '1', '按钮急停', '急停按钮92', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('493', '1', '按钮急停', '急停按钮93', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('494', '1', '按钮急停', '急停按钮94', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('495', '1', '按钮急停', '急停按钮95', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('496', '1', '按钮急停', '急停按钮96', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('497', '1', '按钮急停', '急停按钮97', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('498', '1', '按钮急停', '急停按钮98', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('499', '1', '按钮急停', '急停按钮99', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('500', '1', '按钮急停', '急停按钮100', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('501', '1', '主控柜急停', '主控柜急停1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('502', '1', '主控柜急停', '主控柜急停2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('503', '1', '主控柜急停', '主控柜急停3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('504', '1', '主控柜急停', '主控柜急停4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('505', '1', '主控柜急停', '主控柜急停5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('506', '1', '主控柜急停', '主控柜急停6', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('507', '1', '主控柜急停', '主控柜急停7', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('508', '1', '主控柜急停', '主控柜急停8', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('509', '1', '主控柜急停', '主控柜急停9', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('510', '1', '主控柜急停', '主控柜急停10', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('511', '1', '电机热保护', '1#电机热保护1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('512', '1', '电机热保护', '2#电机热保护1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('513', '1', '电机热保护', '3#电机热保护1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('514', '1', '电机热保护', '4#电机热保护1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('515', '1', '电机热保护', '5#电机热保护1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('516', '1', '电机热保护', '6#电机热保护1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('517', '1', '电机热保护', '7#电机热保护1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('518', '1', '电机热保护', '8#电机热保护1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('519', '1', '电机热保护', '9#电机热保护1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('520', '1', '电机热保护', '10#电机热保护1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('521', '1', '电机热保护', '11#电机热保护1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('522', '1', '电机热保护', '12#电机热保护1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('523', '1', '电机热保护', '13#电机热保护1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('524', '1', '电机热保护', '14#电机热保护1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('525', '1', '电机热保护', '15#电机热保护1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('526', '1', '电机热保护', '1#电机热保护2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('527', '1', '电机热保护', '2#电机热保护2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('528', '1', '电机热保护', '3#电机热保护2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('529', '1', '电机热保护', '4#电机热保护2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('530', '1', '电机热保护', '5#电机热保护2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('531', '1', '电机热保护', '6#电机热保护2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('532', '1', '电机热保护', '7#电机热保护2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('533', '1', '电机热保护', '8#电机热保护2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('534', '1', '电机热保护', '9#电机热保护2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('535', '1', '电机热保护', '10#电机热保护2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('536', '1', '电机热保护', '11#电机热保护2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('537', '1', '电机热保护', '12#电机热保护2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('538', '1', '电机热保护', '13#电机热保护2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('539', '1', '电机热保护', '14#电机热保护2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('540', '1', '电机热保护', '15#电机热保护2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('541', '1', '电机热保护', '1#电机热保护3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('542', '1', '电机热保护', '2#电机热保护3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('543', '1', '电机热保护', '3#电机热保护3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('544', '1', '电机热保护', '4#电机热保护3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('545', '1', '电机热保护', '5#电机热保护3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('546', '1', '电机热保护', '6#电机热保护3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('547', '1', '电机热保护', '7#电机热保护3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('548', '1', '电机热保护', '8#电机热保护3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('549', '1', '电机热保护', '9#电机热保护3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('550', '1', '电机热保护', '10#电机热保护3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('551', '1', '电机热保护', '11#电机热保护3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('552', '1', '电机热保护', '12#电机热保护3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('553', '1', '电机热保护', '13#电机热保护3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('554', '1', '电机热保护', '14#电机热保护3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('555', '1', '电机热保护', '15#电机热保护3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('556', '1', '电机热保护', '1#电机热保护4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('557', '1', '电机热保护', '2#电机热保护4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('558', '1', '电机热保护', '3#电机热保护4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('559', '1', '电机热保护', '4#电机热保护4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('560', '1', '电机热保护', '5#电机热保护4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('561', '1', '电机热保护', '6#电机热保护4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('562', '1', '电机热保护', '7#电机热保护4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('563', '1', '电机热保护', '8#电机热保护4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('564', '1', '电机热保护', '9#电机热保护4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('565', '1', '电机热保护', '10#电机热保护4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('566', '1', '电机热保护', '11#电机热保护4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('567', '1', '电机热保护', '12#电机热保护4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('568', '1', '电机热保护', '13#电机热保护4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('569', '1', '电机热保护', '14#电机热保护4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('570', '1', '电机热保护', '15#电机热保护4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('571', '1', '电机热保护', '1#电机热保护5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('572', '1', '电机热保护', '2#电机热保护5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('573', '1', '电机热保护', '3#电机热保护5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('574', '1', '电机热保护', '4#电机热保护5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('575', '1', '电机热保护', '5#电机热保护5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('576', '1', '电机热保护', '6#电机热保护5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('577', '1', '电机热保护', '7#电机热保护5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('578', '1', '电机热保护', '8#电机热保护5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('579', '1', '电机热保护', '9#电机热保护5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('580', '1', '电机热保护', '10#电机热保护5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('581', '1', '电机热保护', '11#电机热保护5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('582', '1', '电机热保护', '12#电机热保护5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('583', '1', '电机热保护', '13#电机热保护5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('584', '1', '电机热保护', '14#电机热保护5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('585', '1', '电机热保护', '15#电机热保护5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('586', '1', '防撞', '防撞1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('587', '1', '防撞', '防撞2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('588', '1', '防撞', '防撞3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('589', '1', '防撞', '防撞4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('590', '1', '防撞', '防撞5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('591', '1', '防撞', '防撞6', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('592', '1', '防撞', '防撞7', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('593', '1', '防撞', '防撞8', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('594', '1', '防撞', '防撞9', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('595', '1', '防撞', '防撞10', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('596', '1', '防撞', '防撞11', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('597', '1', '防撞', '防撞12', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('598', '1', '防撞', '防撞13', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('599', '1', '防撞', '防撞14', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('600', '1', '防撞', '防撞15', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('601', '1', '防撞', '防撞16', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('602', '1', '防撞', '防撞17', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('603', '1', '防撞', '防撞18', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('604', '1', '防撞', '防撞19', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('605', '1', '防撞', '防撞20', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('606', '1', '防撞', '防撞21', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('607', '1', '防撞', '防撞22', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('608', '1', '防撞', '防撞23', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('609', '1', '防撞', '防撞24', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('610', '1', '防撞', '防撞25', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('611', '1', '防撞', '防撞26', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('612', '1', '防撞', '防撞27', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('613', '1', '防撞', '防撞28', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('614', '1', '防撞', '防撞29', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('615', '1', '防撞', '防撞30', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('616', '1', '防撞', '防撞31', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('617', '1', '防撞', '防撞32', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('618', '1', '防撞', '防撞33', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('619', '1', '防撞', '防撞34', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('620', '1', '防撞', '防撞35', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('621', '1', '防撞', '防撞36', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('622', '1', '防撞', '防撞37', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('623', '1', '防撞', '防撞38', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('624', '1', '防撞', '防撞39', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('625', '1', '防撞', '防撞40', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('626', '1', '防撞', '防撞41', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('627', '1', '防撞', '防撞42', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('628', '1', '防撞', '防撞43', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('629', '1', '防撞', '防撞44', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('630', '1', '防撞', '防撞45', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('631', '1', '防撞', '防撞46', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('632', '1', '防撞', '防撞47', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('633', '1', '防撞', '防撞48', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('634', '1', '防撞', '防撞49', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('635', '1', '防撞', '防撞50', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('636', '1', '直线电机故障', '1#直线电机故障1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('637', '1', '直线电机故障', '2#直线电机故障1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('638', '1', '直线电机故障', '3#直线电机故障1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('639', '1', '直线电机故障', '4#直线电机故障1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('640', '1', '直线电机故障', '5#直线电机故障1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('641', '1', '直线电机故障', '6#直线电机故障1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('642', '1', '直线电机故障', '7#直线电机故障1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('643', '1', '直线电机故障', '8#直线电机故障1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('644', '1', '直线电机故障', '9#直线电机故障1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('645', '1', '直线电机故障', '10#直线电机故障1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('646', '1', '直线电机故障', '11#直线电机故障1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('647', '1', '直线电机故障', '12#直线电机故障1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('648', '1', '直线电机故障', '13#直线电机故障1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('649', '1', '直线电机故障', '14#直线电机故障1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('650', '1', '直线电机故障', '15#直线电机故障1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('651', '1', '直线电机故障', '1#直线电机故障2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('652', '1', '直线电机故障', '2#直线电机故障2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('653', '1', '直线电机故障', '3#直线电机故障2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('654', '1', '直线电机故障', '4#直线电机故障2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('655', '1', '直线电机故障', '5#直线电机故障2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('656', '1', '直线电机故障', '6#直线电机故障2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('657', '1', '直线电机故障', '7#直线电机故障2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('658', '1', '直线电机故障', '8#直线电机故障2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('659', '1', '直线电机故障', '9#直线电机故障2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('660', '1', '直线电机故障', '10#直线电机故障2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('661', '1', '直线电机故障', '11#直线电机故障2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('662', '1', '直线电机故障', '12#直线电机故障2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('663', '1', '直线电机故障', '13#直线电机故障2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('664', '1', '直线电机故障', '14#直线电机故障2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('665', '1', '直线电机故障', '15#直线电机故障2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('666', '1', '直线电机故障', '1#直线电机故障3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('667', '1', '直线电机故障', '2#直线电机故障3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('668', '1', '直线电机故障', '3#直线电机故障3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('669', '1', '直线电机故障', '4#直线电机故障3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('670', '1', '直线电机故障', '5#直线电机故障3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('671', '1', '直线电机故障', '6#直线电机故障3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('672', '1', '直线电机故障', '7#直线电机故障3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('673', '1', '直线电机故障', '8#直线电机故障3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('674', '1', '直线电机故障', '9#直线电机故障3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('675', '1', '直线电机故障', '10#直线电机故障3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('676', '1', '直线电机故障', '11#直线电机故障3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('677', '1', '直线电机故障', '12#直线电机故障3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('678', '1', '直线电机故障', '13#直线电机故障3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('679', '1', '直线电机故障', '14#直线电机故障3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('680', '1', '直线电机故障', '15#直线电机故障3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('681', '1', '直线电机故障', '1#直线电机故障4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('682', '1', '直线电机故障', '2#直线电机故障4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('683', '1', '直线电机故障', '3#直线电机故障4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('684', '1', '直线电机故障', '4#直线电机故障4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('685', '1', '直线电机故障', '5#直线电机故障4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('686', '1', '直线电机故障', '6#直线电机故障4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('687', '1', '直线电机故障', '7#直线电机故障4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('688', '1', '直线电机故障', '8#直线电机故障4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('689', '1', '直线电机故障', '9#直线电机故障4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('690', '1', '直线电机故障', '10#直线电机故障4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('691', '1', '直线电机故障', '11#直线电机故障4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('692', '1', '直线电机故障', '12#直线电机故障4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('693', '1', '直线电机故障', '13#直线电机故障4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('694', '1', '直线电机故障', '14#直线电机故障4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('695', '1', '直线电机故障', '15#直线电机故障4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('696', '1', '直线电机故障', '1#直线电机故障5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('697', '1', '直线电机故障', '2#直线电机故障5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('698', '1', '直线电机故障', '3#直线电机故障5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('699', '1', '直线电机故障', '4#直线电机故障5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('700', '1', '直线电机故障', '5#直线电机故障5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('701', '1', '直线电机故障', '6#直线电机故障5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('702', '1', '直线电机故障', '7#直线电机故障5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('703', '1', '直线电机故障', '8#直线电机故障5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('704', '1', '直线电机故障', '9#直线电机故障5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('705', '1', '直线电机故障', '10#直线电机故障5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('706', '1', '直线电机故障', '11#直线电机故障5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('707', '1', '直线电机故障', '12#直线电机故障5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('708', '1', '直线电机故障', '13#直线电机故障5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('709', '1', '直线电机故障', '14#直线电机故障5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('710', '1', '直线电机故障', '15#直线电机故障5', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('711', '1', '直线电机故障', '1#直线电机故障6', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('712', '1', '直线电机故障', '2#直线电机故障6', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('713', '1', '直线电机故障', '3#直线电机故障6', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('714', '1', '直线电机故障', '4#直线电机故障6', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('715', '1', '直线电机故障', '5#直线电机故障6', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('716', '1', '直线电机故障', '6#直线电机故障6', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('717', '1', '直线电机故障', '7#直线电机故障6', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('718', '1', '直线电机故障', '8#直线电机故障6', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('719', '1', '直线电机故障', '9#直线电机故障6', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('720', '1', '直线电机故障', '10#直线电机故障6', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('721', '1', '直线电机故障', '11#直线电机故障6', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('722', '1', '直线电机故障', '12#直线电机故障6', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('723', '1', '直线电机故障', '13#直线电机故障6', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('724', '1', '直线电机故障', '14#直线电机故障6', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('725', '1', '直线电机故障', '15#直线电机故障6', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('726', '1', '直线电机故障', '1#直线电机故障7', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('727', '1', '直线电机故障', '2#直线电机故障7', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('728', '1', '直线电机故障', '3#直线电机故障7', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('729', '1', '直线电机故障', '4#直线电机故障7', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('730', '1', '直线电机故障', '5#直线电机故障7', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('731', '1', '直线电机故障', '6#直线电机故障7', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('732', '1', '直线电机故障', '7#直线电机故障7', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('733', '1', '直线电机故障', '8#直线电机故障7', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('734', '1', '直线电机故障', '9#直线电机故障7', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('735', '1', '直线电机故障', '10#直线电机故障7', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('736', '1', '直线电机故障', '11#直线电机故障7', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('737', '1', '直线电机故障', '12#直线电机故障7', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('738', '1', '直线电机故障', '13#直线电机故障7', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('739', '1', '直线电机故障', '14#直线电机故障7', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('740', '1', '直线电机故障', '15#直线电机故障7', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('741', '1', '直线电机故障', '1#直线电机故障8', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('742', '1', '直线电机故障', '2#直线电机故障8', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('743', '1', '直线电机故障', '3#直线电机故障8', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('744', '1', '直线电机故障', '4#直线电机故障8', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('745', '1', '直线电机故障', '5#直线电机故障8', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('746', '1', '直线电机故障', '6#直线电机故障8', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('747', '1', '直线电机故障', '7#直线电机故障8', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('748', '1', '直线电机故障', '8#直线电机故障8', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('749', '1', '直线电机故障', '9#直线电机故障8', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('750', '1', '直线电机故障', '10#直线电机故障8', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('751', '1', '直线电机故障', '11#直线电机故障8', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('752', '1', '直线电机故障', '12#直线电机故障8', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('753', '1', '直线电机故障', '13#直线电机故障8', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('754', '1', '直线电机故障', '14#直线电机故障8', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('755', '1', '直线电机故障', '15#直线电机故障8', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('756', '1', '直线电机故障', '1#直线电机故障9', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('757', '1', '直线电机故障', '2#直线电机故障9', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('758', '1', '直线电机故障', '3#直线电机故障9', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('759', '1', '直线电机故障', '4#直线电机故障9', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('760', '1', '直线电机故障', '5#直线电机故障9', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('761', '1', '直线电机故障', '6#直线电机故障9', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('762', '1', '直线电机故障', '7#直线电机故障9', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('763', '1', '直线电机故障', '8#直线电机故障9', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('764', '1', '直线电机故障', '9#直线电机故障9', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('765', '1', '直线电机故障', '10#直线电机故障9', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('766', '1', '直线电机故障', '11#直线电机故障9', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('767', '1', '直线电机故障', '12#直线电机故障9', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('768', '1', '直线电机故障', '13#直线电机故障9', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('769', '1', '直线电机故障', '14#直线电机故障9', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('770', '1', '直线电机故障', '15#直线电机故障9', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('771', '1', '直线电机故障', '1#直线电机故障10', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('772', '1', '直线电机故障', '2#直线电机故障10', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('773', '1', '直线电机故障', '3#直线电机故障10', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('774', '1', '直线电机故障', '4#直线电机故障10', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('775', '1', '直线电机故障', '5#直线电机故障10', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('776', '1', '直线电机故障', '6#直线电机故障10', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('777', '1', '直线电机故障', '7#直线电机故障10', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('778', '1', '直线电机故障', '8#直线电机故障10', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('779', '1', '直线电机故障', '9#直线电机故障10', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('780', '1', '直线电机故障', '10#直线电机故障10', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('781', '1', '直线电机故障', '11#直线电机故障10', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('782', '1', '直线电机故障', '12#直线电机故障10', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('783', '1', '直线电机故障', '13#直线电机故障10', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('784', '1', '直线电机故障', '14#直线电机故障10', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('785', '1', '直线电机故障', '15#直线电机故障10', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('786', '1', '直线电机故障', '1#直线电机故障11', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('787', '1', '直线电机故障', '2#直线电机故障11', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('788', '1', '直线电机故障', '3#直线电机故障11', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('789', '1', '直线电机故障', '4#直线电机故障11', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('790', '1', '直线电机故障', '5#直线电机故障11', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('791', '1', '直线电机故障', '6#直线电机故障11', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('792', '1', '直线电机故障', '7#直线电机故障11', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('793', '1', '直线电机故障', '8#直线电机故障11', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('794', '1', '直线电机故障', '9#直线电机故障11', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('795', '1', '直线电机故障', '10#直线电机故障11', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('796', '1', '直线电机故障', '11#直线电机故障11', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('797', '1', '直线电机故障', '12#直线电机故障11', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('798', '1', '直线电机故障', '13#直线电机故障11', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('799', '1', '直线电机故障', '14#直线电机故障11', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('800', '1', '直线电机故障', '15#直线电机故障11', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('801', '1', '直线电机故障', '1#直线电机故障12', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('802', '1', '直线电机故障', '2#直线电机故障12', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('803', '1', '直线电机故障', '3#直线电机故障12', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('804', '1', '直线电机故障', '4#直线电机故障12', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('805', '1', '直线电机故障', '5#直线电机故障12', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('806', '1', '直线电机故障', '6#直线电机故障12', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('807', '1', '直线电机故障', '7#直线电机故障12', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('808', '1', '直线电机故障', '8#直线电机故障12', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('809', '1', '直线电机故障', '9#直线电机故障12', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('810', '1', '直线电机故障', '10#直线电机故障12', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('811', '1', '直线电机故障', '11#直线电机故障12', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('812', '1', '直线电机故障', '12#直线电机故障12', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('813', '1', '直线电机故障', '13#直线电机故障12', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('814', '1', '直线电机故障', '14#直线电机故障12', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('815', '1', '直线电机故障', '15#直线电机故障12', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('816', '1', '直线电机故障', '1#直线电机故障13', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('817', '1', '直线电机故障', '2#直线电机故障13', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('818', '1', '直线电机故障', '3#直线电机故障13', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('819', '1', '直线电机故障', '4#直线电机故障13', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('820', '1', '直线电机故障', '5#直线电机故障13', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('821', '1', '直线电机故障', '6#直线电机故障13', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('822', '1', '直线电机故障', '7#直线电机故障13', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('823', '1', '直线电机故障', '8#直线电机故障13', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('824', '1', '直线电机故障', '9#直线电机故障13', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('825', '1', '直线电机故障', '10#直线电机故障13', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('826', '1', '直线电机故障', '11#直线电机故障13', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('827', '1', '直线电机故障', '12#直线电机故障13', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('828', '1', '直线电机故障', '13#直线电机故障13', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('829', '1', '直线电机故障', '14#直线电机故障13', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('830', '1', '直线电机故障', '15#直线电机故障13', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('831', '1', '直线电机故障', '1#直线电机故障14', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('832', '1', '直线电机故障', '2#直线电机故障14', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('833', '1', '直线电机故障', '3#直线电机故障14', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('834', '1', '直线电机故障', '4#直线电机故障14', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('835', '1', '直线电机故障', '5#直线电机故障14', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('836', '1', '直线电机故障', '6#直线电机故障14', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('837', '1', '直线电机故障', '7#直线电机故障14', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('838', '1', '直线电机故障', '8#直线电机故障14', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('839', '1', '直线电机故障', '9#直线电机故障14', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('840', '1', '直线电机故障', '10#直线电机故障14', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('841', '1', '直线电机故障', '11#直线电机故障14', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('842', '1', '直线电机故障', '12#直线电机故障14', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('843', '1', '直线电机故障', '13#直线电机故障14', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('844', '1', '直线电机故障', '14#直线电机故障14', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('845', '1', '直线电机故障', '15#直线电机故障14', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('846', '1', '直线电机故障', '1#直线电机故障15', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('847', '1', '直线电机故障', '2#直线电机故障15', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('848', '1', '直线电机故障', '3#直线电机故障15', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('849', '1', '直线电机故障', '4#直线电机故障15', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('850', '1', '直线电机故障', '5#直线电机故障15', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('851', '1', '直线电机故障', '6#直线电机故障15', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('852', '1', '直线电机故障', '7#直线电机故障15', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('853', '1', '直线电机故障', '8#直线电机故障15', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('854', '1', '直线电机故障', '9#直线电机故障15', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('855', '1', '直线电机故障', '10#直线电机故障15', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('856', '1', '直线电机故障', '11#直线电机故障15', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('857', '1', '直线电机故障', '12#直线电机故障15', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('858', '1', '直线电机故障', '13#直线电机故障15', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('859', '1', '直线电机故障', '14#直线电机故障15', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('860', '1', '直线电机故障', '15#直线电机故障15', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('861', '1', '直线电机故障', '1#直线电机故障16', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('862', '1', '直线电机故障', '2#直线电机故障16', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('863', '1', '直线电机故障', '3#直线电机故障16', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('864', '1', '直线电机故障', '4#直线电机故障16', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('865', '1', '直线电机故障', '5#直线电机故障16', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('866', '1', '直线电机故障', '6#直线电机故障16', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('867', '1', '直线电机故障', '7#直线电机故障16', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('868', '1', '直线电机故障', '8#直线电机故障16', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('869', '1', '直线电机故障', '9#直线电机故障16', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('870', '1', '直线电机故障', '10#直线电机故障16', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('871', '1', '直线电机故障', '11#直线电机故障16', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('872', '1', '直线电机故障', '12#直线电机故障16', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('873', '1', '直线电机故障', '13#直线电机故障16', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('874', '1', '直线电机故障', '14#直线电机故障16', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('875', '1', '直线电机故障', '15#直线电机故障16', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('876', '1', '电机未上电', '1#电机未上电', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('877', '1', '电机未上电', '2#电机未上电', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('878', '1', '电机未上电', '3#电机未上电', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('879', '1', '电机未上电', '4#电机未上电', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('880', '1', '电机未上电', '5#电机未上电', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('881', '1', '电机未上电', '6#电机未上电', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('882', '1', '电机未上电', '7#电机未上电', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('883', '1', '电机未上电', '8#电机未上电', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('884', '1', '电机未上电', '9#电机未上电', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('885', '1', '电机未上电', '10#电机未上电', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('886', '1', '电机未上电', '11#电机未上电', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('887', '1', '电机未上电', '12#电机未上电', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('888', '1', '电机未上电', '13#电机未上电', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('889', '1', '电机未上电', '14#电机未上电', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('890', '1', '电机未上电', '15#电机未上电', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('891', '1', '速度', '速度低报警', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('892', '1', '速度', '速度高报警', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('893', '1', '速度', '速度波动异常报警', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('894', '1', '速度', '速度其它报警1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('895', '1', '速度', '速度其它报警2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('896', '1', '系统调试', '系统调试/运行1', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('897', '1', '系统调试', '系统调试/运行2', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('898', '1', '系统调试', '系统调试/运行3', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('899', '1', '系统调试', '系统调试/运行4', '', '');$$
INSERT INTO  `flag_event` (`F_RECNO`, `EVENT_LEVEL`, `EVENT_TYPE`, `EVENT_NAME`, `EVENT_FLAG`, `EVENT_MARK`) VALUES ('900', '1', '系统调试', '系统调试/运行5', '', '');$$


-- ----------------------------
-- 角色-菜单节点对应表初始化数据--wcs界面访问统计数据菜单
-- 2018-03-28 14:16:38 klq
-- ----------------------------
INSERT INTO `rbac_role_node` ( `node_id`, `role_id`) 
	VALUES ( '7', '4');
$$
INSERT INTO `rbac_role_node` (`node_id`, `role_id`) 
	VALUES ('8', '4');
$$
INSERT INTO `rbac_role_node` ( `node_id`, `role_id`) 
	VALUES ( '9', '4');
$$
INSERT INTO `rbac_role_node` ( `node_id`, `role_id`) 
	VALUES ('10', '4');
$$
INSERT INTO `rbac_role_node` ( `node_id`, `role_id`) 
	VALUES ('11', '4');
$$
INSERT INTO `rbac_role_node` ( `node_id`, `role_id`) 
	VALUES ('12', '4');
$$
INSERT INTO `rbac_role_node` (`node_id`, `role_id`) 
	VALUES ( '13', '4');
$$
INSERT INTO `rbac_role_node` ( `node_id`, `role_id`) 
	VALUES ('14', '4');
$$