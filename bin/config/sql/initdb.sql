--------------------------------提示---------------------------------------------
-- 建表语句与存储过程,写在这里面,以$$结束,
-- 注意：执行如下建表前(启动服务器前),手动创建执行建库语句如下
-- create database seagen_wms default character set utf8 collate utf8_general_ci;
-- ------------------------------------------------------------------------------
-- 用户表rbac_user
-- ----------------------------
drop table if exists rbac_user
$$
create table rbac_user (
  id int(12) not null auto_increment comment '主键',
  user_name varchar(32) not null comment '用户名称',
  pwd varchar(32) not null comment '用户密码',
  telno varchar(15) default null comment '手机号码',
  email varchar(48) default null comment '邮箱地址',
  use_flag char(1) default '0' comment '启动状态,0:启用  1:不启用',
  remark varchar(50) default null comment '附加属性或备注',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (id),
  unique key user_name_key (user_name)
) engine=innodb default charset=utf8 comment='操作员表'
$$
-- ----------------------------
-- 角色表rbac_role
-- ----------------------------
drop table if exists rbac_role
$$
create table rbac_role (
  id int(4) not null auto_increment comment '主键',
  role_name varchar(50) default null,
  use_flag char(1) default '0' comment '启动状态0:启用  1:不启用',
  remark varchar(50) default null,
  create_time timestamp null default current_timestamp,
  modify_time timestamp null default '0000-00-00 00:00:00' on update current_timestamp,
  primary key (id)
) engine=innodb default charset=utf8 comment='角色表'
$$
-- ----------------------------
-- 用户与角色中间表rbac_user_role
-- ----------------------------
drop table if exists rbac_user_role
$$
create table rbac_user_role (
  id int(4) not null auto_increment comment '主键',
  user_id int(4) not null comment '用户id',
  role_id int(4) not null comment '角色id',
  create_time timestamp null default current_timestamp,
  modify_time timestamp null default '0000-00-00 00:00:00' on update current_timestamp,
  primary key (id),
  key user_id (user_id),
  key role_id (role_id)
) engine=innodb default charset=utf8 comment='用户-角色表'
$$
-- ----------------------------
-- 资源节点表rbac_node
-- ----------------------------
drop table if exists rbac_node
$$
create table rbac_node (
  id int(4) not null auto_increment comment '主键',
  node_url varchar(100) comment '路径',
  node_name varchar(50) not null comment '节点名字',
  node_icon varchar(50) default null comment '图标',
  node_type int(4) default null comment '类型',
  use_flag char(1) default '0' comment '启动状态,0:启用  1:不启用',
  remark varchar(50) default null comment '备注',
  sort int(4) default null comment '排序',
  pid int(4) default 0 comment '父节点',
  create_time timestamp null default current_timestamp,
  modify_time timestamp null default '0000-00-00 00:00:00' on update current_timestamp,
  primary key (id)
) engine=innodb default charset=utf8 comment='菜单节点'
$$
-- ----------------------------
-- 角色与资源中间表rbac_role_node
-- ----------------------------
drop table if exists rbac_role_node
$$
create table rbac_role_node (
  id int(4) not null auto_increment comment '主键',
  node_id int(4) not null comment '菜单节点id',
  role_id int(4) not null comment '角色id',
  create_time timestamp null default current_timestamp,
  modify_time timestamp null default '0000-00-00 00:00:00' on update current_timestamp,
  primary key (id),
  key node_id (node_id),
  key role_id (role_id)
) engine=innodb default charset=utf8 comment='菜单-角色表'
$$

