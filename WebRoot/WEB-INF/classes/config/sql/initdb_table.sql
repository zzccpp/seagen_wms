--------------------------------提示---------------------------------------------
-- 建表语句与存储过程,写在这里面,以$$结束,
-- 注意：执行如下建表前(启动服务器前),手动创建执行建库语句如下
-- create database seagen_wms default character set utf8 collate utf8_general_ci;
-- ----------------------------
-- 接入应用记录表app_ctrl
-- ----------------------------
drop table if exists app_ctrl;
$$
create table app_ctrl (
  f_recno bigint(20) not null auto_increment comment '主键',
  app_code int(6) not null comment '接入应用代号',
  app_name varchar(32) default null comment '接入应用名称',
  app_power varchar(60) default '1111' comment '接入应用权限',
  app_mark varchar(100) default null comment '接入应用备注',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno),
  unique key code_key (app_code)
) engine=innodb default charset=utf8 comment='接入应用记录表';
$$
-- ----------------------------
-- 箱号表box
-- ----------------------------
drop table if exists box;
$$
create table box (
  f_recno bigint(20) not null auto_increment comment '主键',
  batch_id int(12) default '0' comment '批次id',
  box_code varchar(40) not null default '' comment '箱号',
  box_type varchar(32) default null comment '箱号类型bc',
  box_num varchar(32) default null comment '箱号序号',
  opt_code varchar(6) default '0' comment '操作码,保留暂时未用,京东的暂定为618',
  mixing_box_type varchar(10) default null comment '是否混箱',
  cate_gory varchar(10) default null comment '运输方式 公，铁，航',
  carriage_router varchar(50) default null comment '运输路由(字符数组，用逗号隔开)',
  carriage_router_num varchar(20) default null comment '运输路由编号',
  package_count smallint(6) unsigned default '0' comment '箱中包裹数量',
  package_weight int(10) default '0' comment '箱中包裹总重量(g)',
  from_site_code varchar(32) default null comment '始发目的地代码',
  from_site_name varchar(64) default null comment '始发目的地名称',
  to_site_name varchar(64) default null comment '接收目的地名称',
  to_site_code varchar(32) default null comment '接收目的地代码',
  print_status smallint(6) unsigned default '0' comment '0未打印1已打印',
  printer_ip varchar(64) default null comment '打印机ip',
  print_cmd varchar(2000) default null comment '打印机命令',
  print_imag mediumblob comment '打印机图片数据',
  print_time varchar(30) default null comment '箱号最后一次打印时间(yyyy-mm-dd hh:mm:ss.sss)',
  pad_code varchar(60) default null comment 'pad控制器序列号(750)',
  chute_id smallint(6) unsigned default '0' comment '格口物理编号',
  chute_num varchar(50) default null comment '格口逻辑编号',
  rece_time varchar(30) default null comment '数据接收时间(yyyy-mm-dd hh:mm:ss.sss)',
  use_flag tinyint(2) not null default '0' comment '是否已用0：未使用1：已使用',
  update_flag smallint(6) unsigned not null default '0' comment '更新标识0：未更新1：已更新2：不可更新',
  update_time varchar(30) default null comment '更新时间(yyyy-mm-dd hh:mm:ss.sss)',
  re_mark varchar(100) default null comment '附加属性或备注',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno,box_code,create_time),
  unique key box_code_key (box_code) using btree,
  key box_key (box_code,create_time),
  key box_type_key (box_type),
  key box_use_key (use_flag,update_flag)
) engine=innodb default charset=utf8 comment='箱号表';
$$
-- ----------------------------
-- 格口区域表chute_area
-- ----------------------------
drop table if exists chute_area;
$$
create table chute_area (
  f_recno int(12) not null auto_increment comment '主键',
  area_name varchar(20) not null comment '区域名称',
  chute_num_list text not null comment '附属格口',
  re_mark varchar(100) default null comment '备注',
  create_time timestamp not null default current_timestamp on update current_timestamp comment '创建时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno)
) engine=innodb default charset=utf8 comment='格口区域表';
$$
-- ----------------------------
-- 用户参数表custom_set
-- ----------------------------
drop table if exists custom_set;
$$
create table custom_set (
  f_recno int(12) not null auto_increment comment '主键',
  set_name varchar(255) not null comment '参数名称',
  set_name_cn varchar(255) not null comment '参数中文名称',
  set_value varchar(255) not null comment '参数值',
  set_mark varchar(255) default null comment '参数备注',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno)
) engine=innodb default charset=utf8 comment='客户参数表';
$$
-- ----------------------------
-- 日期操作表flag_date
-- ----------------------------
drop table if exists flag_date;
$$
create table flag_date (
  f_recno int(12) not null auto_increment comment '主键',
  date_val varchar(20) not null comment 'flag值',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno),
  key data_key (date_val)
) engine=innodb auto_increment=1001 default charset=utf8 comment='日期操作表';
$$
-- ----------------------------
-- 事件标识表flag_event
-- ----------------------------
drop table if exists flag_event;
$$
create table flag_event (
  f_recno int(12) not null auto_increment comment '主键(事件代号)',
  event_level smallint(6) unsigned not null comment '事件级别',
  event_type varchar(32) not null comment '事件类型',
  event_name varchar(32) not null comment '事件名称',
  event_flag bit(1) default b'0' comment '事件是否启用0启用1停用',
  event_mark varchar(128) default null comment '事件说明',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno)
) engine=innodb auto_increment=901 default charset=utf8 comment='事件标识表';
$$
-- ----------------------------
-- 操作标识表flag_log
-- ----------------------------
drop table if exists flag_log;
$$
create table flag_log (
  f_recno int(12) not null auto_increment comment '主键(操作标识代号)',
  flag_name varchar(32) not null comment '操作标识名称',
  flag_mark varchar(64) default null comment '操作标识备注',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno)
) engine=innodb auto_increment=21 default charset=utf8 comment='操作标识表';
$$
-- ----------------------------
-- mq消息处理错误记录表mq_err_data
-- ----------------------------
drop table if exists mq_err_data;
$$
create table mq_err_data (
  f_recno bigint(20) not null auto_increment comment '主键',
  mq_id smallint(6) unsigned not null default '0' comment '消息代号',
  mq_data varchar(2000) default null comment '消息数据',
  mq_mark varchar(2000) default null comment '消息备注',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno,create_time),
  key mq_key (create_time)
) engine=innodb default charset=utf8 comment='mq消息处理错误记录表';
$$
-- ----------------------------
-- 打印机表printer
-- ----------------------------
drop table if exists printer;
$$
create table printer (
  f_recno int(12) not null auto_increment comment '主键',
  printer_num varchar(20) not null comment '打印机编号',
  printer_ip varchar(128) not null comment '打印机ip',
  chute_num_list varchar(1000) default null comment '可打印的格口编号列表(多个格口用,隔开)',
  re_mark varchar(200) default null comment '附加属性或备注',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno)
) engine=innodb default charset=utf8 comment='打印机表';
$$
-- ----------------------------
-- 集包数据明细表printer_data
-- ----------------------------
drop table if exists printer_data;
$$
create table printer_data (
  f_recno bigint(20) not null auto_increment comment '主键',
  printer_ip varchar(64) not null comment '打印机ip',
  chute_id smallint(6) unsigned default '0' comment '格口物理编号',
  chute_num varchar(50) default null comment '格口逻辑编号',
  box_code varchar(40) default null comment '箱号',
  box_type varchar(32) default null comment '箱号类型bc',
  mixing_box_type varchar(10) default null comment '是否混箱',
  cate_gory varchar(10) default null comment '运输方式 公，铁，航',
  carriage_rounter varchar(50) default null comment '运输路由(字符数组，用逗号隔开)',
  cate_rounter_num varchar(20) default null comment '运输路由编号',
  carriage_router_num varchar(20) default null comment '运输路由编号',
  package_count smallint(6) unsigned default '0' comment '箱中包裹数量',
  package_weight int(10) default '0' comment '箱中包裹总重量(g)',
  site_name varchar(64) default null comment '目的地名称',
  site_code varchar(32) default null comment '目的地代码',
  print_cmd varchar(2000) default null comment '打印机命令',
  print_imag mediumblob comment '打印机图片数据',
  re_mark varchar(100) default null comment '附加属性或备注',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno,create_time),
  key printer_data_key (box_code,create_time)
) engine=innodb default charset=utf8 comment='集包数据明细表';
$$
-- ----------------------------
-- 按分钟分拣统计报表report_minute
-- ----------------------------
drop table if exists report_minute;
$$
create table report_minute (
  f_recno bigint(20) not null auto_increment comment '主键',
  report_date datetime not null comment '记录日期',
  layer_id tinyint(1) default null comment '层级id',
  sorting_count smallint(6) unsigned default '0' comment '分拣量',
  success_sum smallint(6) unsigned default '0' comment '正常分拣量 件数',
  no_reade smallint(6) unsigned default '0' comment '无读 件数',
  err_sum smallint(6) unsigned default '0' comment '异常分拣量 件数',
  no_chute smallint(6) unsigned default '0' comment '无目的地 件数',
  more_data smallint(6) unsigned default '0' comment '多条码 件数',
  no_data smallint(6) unsigned default '0' comment '无信息 件数',
  cancel_sum smallint(6) unsigned default '0' comment '订单取消 件数',
  err_chute smallint(6) unsigned default '0' comment '格口投错 件数',
  max_cycles smallint(6) unsigned default '0' comment '最大圈数 件数',
  lost_data smallint(6) unsigned default '0' comment '迷失件数',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno),
  unique key report_key (report_date,layer_id) using btree
) engine=innodb default charset=utf8 comment='按分钟分拣统计报表';
$$
-- ----------------------------
-- 汇总表report_sorting
-- ----------------------------
drop table if exists report_sorting;
$$
create table report_sorting (
  f_recno bigint(20) not null auto_increment comment '主键',
  report_date datetime not null comment '记录日期',
  sum_name varchar(32) not null comment '统计名称',
  sum_type smallint(6) unsigned default '0' comment '统计方式0为批次1为小时',
  begin_time datetime default current_timestamp comment '开始时间',
  end_time datetime default null comment '结束时间',
  supply_sum smallint(6) unsigned default '0' comment '导入台供件量 件数',
  layer_sum smallint(6) unsigned default '0' comment '层级 件数',
  all_sum smallint(6) unsigned default '0' comment '供件量(包含没有经过导入对口的) 件数',
  success_sum smallint(6) unsigned default '0' comment '正常分拣量 件数',
  err_sum smallint(6) unsigned default '0' comment '异常分拣量 件数',
  no_chute smallint(6) unsigned default '0' comment '无目的地 件数',
  more_data smallint(6) unsigned default '0' comment '多条码 件数',
  no_reade smallint(6) unsigned default '0' comment '无读 件数',
  no_data smallint(6) unsigned default '0' comment '无信息 件数',
  cancel_sum smallint(6) unsigned default '0' comment '订单取消 件数',
  err_chute smallint(6) unsigned default '0' comment '格口投错 件数',
  max_cycles smallint(6) unsigned default '0' comment '最大圈数 件数',
  lost_data smallint(6) unsigned default '0' comment '迷失件数',
  box_sum smallint(6) unsigned default '0' comment '打包(建包)数 件数',
  layer0 smallint(6) unsigned default '0' comment '层级0的件数',
  layer1 smallint(6) unsigned default '0' comment '层级2的件数',
  scan0 smallint(6) unsigned default '0' comment '扫描器0的件数(返回错误的人为的都为0)',
  scan1 smallint(6) unsigned default '0' comment '扫描器1的件数',
  scan2 smallint(6) unsigned default '0' comment '扫描器2的件数',
  scan3 smallint(6) unsigned default '0' comment '扫描器3的件数',
  scan4 smallint(6) unsigned default '0' comment '扫描器4的件数',
  scan5 smallint(6) unsigned default '0' comment '扫描器5的件数',
  scan6 smallint(6) unsigned default '0' comment '扫描器6的件数',
  scan7 smallint(6) unsigned default '0' comment '扫描器7的件数',
  scan8 smallint(6) unsigned default '0' comment '扫描器8的件数',
  scan9 smallint(6) unsigned default '0' comment '扫描器9的件数',
  scan10 smallint(6) unsigned default '0' comment '扫描器10的件数',
  scan11 smallint(6) unsigned default '0' comment '扫描器11的件数',
  scan12 smallint(6) unsigned default '0' comment '扫描器12的件数',
  scan13 smallint(6) unsigned default '0' comment '扫描器13的件数',
  scan14 smallint(6) unsigned default '0' comment '扫描器14的件数',
  scan15 smallint(6) unsigned default '0' comment '扫描器15的件数',
  scan16 smallint(6) unsigned default '0' comment '扫描器16的件数',
  supply0 smallint(6) unsigned default '0' comment '供件台0的件数(不经过导入台,人为放置小车上,或错误的)',
  supply1 smallint(6) unsigned default '0' comment '供件台1的件数',
  supply2 smallint(6) unsigned default '0' comment '供件台2的件数',
  supply3 smallint(6) unsigned default '0' comment '供件台3的件数',
  supply4 smallint(6) unsigned default '0' comment '供件台4的件数',
  supply5 smallint(6) unsigned default '0' comment '供件台5的件数',
  supply6 smallint(6) unsigned default '0' comment '供件台6的件数',
  supply7 smallint(6) unsigned default '0' comment '供件台7的件数',
  supply8 smallint(6) unsigned default '0' comment '供件台8的件数',
  supply9 smallint(6) unsigned default '0' comment '供件台9的件数',
  supply10 smallint(6) unsigned default '0' comment '供件台10的件数',
  supply11 smallint(6) unsigned default '0' comment '供件台11的件数',
  supply12 smallint(6) unsigned default '0' comment '供件台12的件数',
  supply13 smallint(6) unsigned default '0' comment '供件台13的件数',
  supply14 smallint(6) unsigned default '0' comment '供件台14的件数',
  supply15 smallint(6) unsigned default '0' comment '供件台15的件数',
  supply16 smallint(6) unsigned default '0' comment '供件台16的件数',
  supply17 smallint(6) unsigned default '0' comment '供件台17的件数',
  supply18 smallint(6) unsigned default '0' comment '供件台18的件数',
  supply19 smallint(6) unsigned default '0' comment '供件台19的件数',
  supply20 smallint(6) unsigned default '0' comment '供件台20的件数',
  supply21 smallint(6) unsigned default '0' comment '供件台21的件数',
  supply22 smallint(6) unsigned default '0' comment '供件台22的件数',
  supply23 smallint(6) unsigned default '0' comment '供件台23的件数',
  supply24 smallint(6) unsigned default '0' comment '供件台24的件数',
  noread0 smallint(6) unsigned default '0' comment '供件台无读0的件数(不经过导入台,人为放置小车上)',
  noread1 smallint(6) unsigned default '0' comment '供件台无读1的件数',
  noread2 smallint(6) unsigned default '0' comment '供件台无读2的件数',
  noread3 smallint(6) unsigned default '0' comment '供件台无读3的件数',
  noread4 smallint(6) unsigned default '0' comment '供件台无读4的件数',
  noread5 smallint(6) unsigned default '0' comment '供件台无读5的件数',
  noread6 smallint(6) unsigned default '0' comment '供件台无读6的件数',
  noread7 smallint(6) unsigned default '0' comment '供件台无读7的件数',
  noread8 smallint(6) unsigned default '0' comment '供件台无读8的件数',
  noread9 smallint(6) unsigned default '0' comment '供件台无读9的件数',
  noread10 smallint(6) unsigned default '0' comment '供件台无读10的件数',
  noread11 smallint(6) unsigned default '0' comment '供件台无读11的件数',
  noread12 smallint(6) unsigned default '0' comment '供件台无读12的件数',
  noread13 smallint(6) unsigned default '0' comment '供件台无读13的件数',
  noread14 smallint(6) unsigned default '0' comment '供件台无读14的件数',
  noread15 smallint(6) unsigned default '0' comment '供件台无读15的件数',
  noread16 smallint(6) unsigned default '0' comment '供件台无读16的件数',
  noread17 smallint(6) unsigned default '0' comment '供件台无读17的件数',
  noread18 smallint(6) unsigned default '0' comment '供件台无读18的件数',
  noread19 smallint(6) unsigned default '0' comment '供件台无读19的件数',
  noread20 smallint(6) unsigned default '0' comment '供件台无读20的件数',
  noread21 smallint(6) unsigned default '0' comment '供件台无读21的件数',
  noread22 smallint(6) unsigned default '0' comment '供件台无读22的件数',
  noread23 smallint(6) unsigned default '0' comment '供件台无读23的件数',
  noread24 smallint(6) unsigned default '0' comment '供件台无读24的件数',
  chaos_sum smallint(6) unsigned default '0' comment '乱走货总分拣量',
  chaos_success_sum smallint(6) unsigned default '0' comment '乱走货正常分拣量',
  chaos_err_sum smallint(6) unsigned default '0' comment '乱走货异常分拣量',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno,sum_name),
  unique key sum_type_key (sum_name,sum_type) using btree
) engine=innodb auto_increment=14 default charset=utf8 comment='汇总表';
$$
-- ----------------------------
-- 统计进度表report_statistics_pro
-- ----------------------------
drop table if exists report_statistics_pro;
$$
create table report_statistics_pro (
  f_recno bigint(20) not null auto_increment comment '主键',
  st_type tinyint(2) not null comment '统计类型：0：小车量；1，格口；2，分钟；3，扫描；4，站点；5，汇总；6，导入台；7，格口封包；8，汇总封包',
  statistics_name varchar(30) default '' comment '统计名称',
  statistics_table varchar(30) default '' comment '统计表单',
  current_progress bigint(20) default '0' comment '当前统计进度',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno),
  key report_key (st_type,statistics_name)
) engine=innodb default charset=utf8 comment='统计进度表';
$$
-- ----------------------------
-- 导入台小时明细表report_supply
-- ----------------------------
drop table if exists report_supply;
$$
create table report_supply (
  f_recno bigint(20) not null auto_increment comment '主键',
  report_date datetime not null comment '记录日期',
  supply_id smallint(6) unsigned default '0' comment '导入台id',
  layer_id tinyint(1) default null comment '层级id',
  all_sum smallint(6) unsigned default '0' comment '供件量(包含没有经过导入对口的) 件数',
  success_sum smallint(6) unsigned default '0' comment '正常分拣量 件数',
  err_sum smallint(6) unsigned default '0' comment '异常分拣量 件数',
  no_chute smallint(6) unsigned default '0' comment '无目的地 件数',
  more_data smallint(6) unsigned default '0' comment '多条码 件数',
  no_reade smallint(6) unsigned default '0' comment '无读 件数',
  no_data smallint(6) unsigned default '0' comment '无信息 件数',
  cancel_sum smallint(6) unsigned default '0' comment '订单取消 件数',
  err_chute smallint(6) unsigned default '0' comment '格口投错 件数',
  max_cycles smallint(6) unsigned default '0' comment '最大圈数 件数',
  lost_data smallint(6) unsigned default '0' comment '迷失件数',
  six_spike smallint(6) unsigned default '0' comment '连续60分钟峰值',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno),
  unique key report_key (report_date,supply_id,layer_id) using btree
) engine=innodb default charset=utf8 comment='导入台小时明细表';
$$
-- ----------------------------
-- 扫描表scan
-- ----------------------------
drop table if exists scan;
$$
create table scan (
  f_recno bigint(20) not null auto_increment comment '主键',
  batch_id int(12) default '0' comment '批次id',
  sort_mode varchar(30) default null comment '分拣模式',
  sort_source varchar(30) default null comment '分拣来源',
  waybill_id varchar(20) default null comment '快件追踪id',
  waybill_num varchar(40) default null comment '快件分拣过程唯一编号',
  waybill_code varchar(30) default null comment '运单号',
  waybill_site_code varchar(30) default null comment '运单表中目的地代码',
  package_code varchar(30) not null comment '包裹号',
  waybill_status smallint(6) unsigned not null default '0' comment '运单状态(0正常运单,1运单取消,2运单地址更改,3无数据)',
  waybill_time varchar(30) default null comment '运单生成时间(yyyy-mm-dd hh:mm:ss.sss)',
  serialno varchar(10) comment '流水号(德邦)',
  site_code varchar(32) default null comment '目的地代码或站点编码',
  site_name varchar(64) default null comment '目的地名称',
  car_id smallint(6) unsigned default '1' comment '小车物理编号',
  chute_id smallint(6) unsigned default '1' comment '滑槽物理编号',
  scan_id smallint(6) unsigned default '1' comment '扫描仪物理编号(龙门架)',
  supply_id smallint(6) unsigned default '1' comment '供件台物理编号',
  layer_id tinyint(1) default null comment '层级id',
  car_num varchar(32) default null comment '小车逻辑编号',
  chute_num varchar(150) default null comment '滑槽口逻辑编号',
  scan_num varchar(32) default null comment '扫描仪逻辑编号(龙门架)',
  supply_num varchar(32) default null comment '供件台逻辑编号',
  layer_num varchar(32) default null comment '层级编码',
  package_weight int(10) default '0' comment '重量(g)',
  package_length int(10) default '0' comment '长度(mm)',
  package_width int(10) default '0' comment '宽度(mm)',
  package_height int(10) default '0' comment '高度(mm)',
  supply_type varchar(4) default null comment '供件方式',
  supply_time varchar(30) default null comment '供件时间(yyyy-mm-dd hh:mm:ss.sss)',
  scan_cycle smallint(6) unsigned not null default '0' comment '扫描次数',
  scan_status smallint(6) unsigned not null default '0' comment '扫描状态(0正常扫描,1无分拣方案,2无数据,3无读,4取消,5最大圈数,6格口错,7多条码,8,迷失,255未知)',
  scan_time varchar(30) default null comment '扫描时间(yyyy-mm-dd hh:mm:ss.sss)',
  re_mark varchar(100) default null comment '附加属性或备注',
  update_flag smallint(6) unsigned not null default '0' comment '更新标识0：未更新1：已更新2：不可更新',
  update_time varchar(30) default null comment '更新时间(yyyy-mm-dd hh:mm:ss.sss)',
  receive_time varchar(30) default null comment '消息生成时间(yyyy-mm-dd hh:mm:ss.sss)',
  create_time timestamp not null default current_timestamp comment '数据库生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno,create_time),
  key waybill_num_key (waybill_num),
  key package_code_key (package_code),
  key update_flag_key (update_flag)
) engine=innodb default charset=utf8 comment='扫描表';
$$
-- ----------------------------
-- 龙门架图像记录表scan_images
-- ----------------------------
drop table if exists scan_images;
$$
create table scan_images (
  f_recno bigint(20) not null auto_increment comment '主键',
  is_read bit(1) default b'1' comment '0：noread，1：read',
  scan_id smallint(6) unsigned not null default '1' comment '龙门架id',
  car_id smallint(6) unsigned not null default '0' comment '小车号',
  layer_id tinyint(1) default null comment '层级id',
  package_code varchar(30) not null default 'noread' comment '包裹号',
  file_full_path varchar(255) default '/' comment '文件绝对路径带文件名',
  file_change_time datetime default null comment '文件修改时间(yyyy-mm-dd hh:mm:ss)',
  create_time timestamp not null default current_timestamp comment '接收时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno),
  key scan_time (scan_id,file_change_time,layer_id),
  key package_code_id (package_code,scan_id,layer_id),
  key change_time (file_change_time,is_read)
) engine=innodb default charset=utf8 comment='龙门架图像记录表';
$$
-- ----------------------------
-- 分拣表sorting
-- ----------------------------
drop table if exists sorting;
$$
create table sorting (
  f_recno bigint(20) not null auto_increment comment '主键',
  batch_id int(12) default '0' comment '批次id',
  sort_mode varchar(30) default null comment '分拣模式',
  sort_source varchar(30) default null comment '分拣来源',
  waybill_id varchar(20) default null comment '快件追踪id',
  waybill_num varchar(40) default null comment '快件分拣过程唯一编号',
  waybill_code varchar(30) default null comment '运单号',
  waybill_site_code varchar(30) default null comment '运单表中目的地代码',
  package_code varchar(30) not null comment '包裹号',
  waybill_status smallint(6) unsigned not null default '0' comment '运单状态(0正常运单,1运单取消,2运单地址更改)',
  waybill_time varchar(30) default null comment '运单生成时间(yyyy-mm-dd hh:mm:ss.sss)',
  serialno varchar(10) comment '流水号(德邦)',
  site_code varchar(32) default null comment '目的地代码或站点编码',
  site_name varchar(64) default null comment '目的地名称',
  car_id smallint(6) unsigned default '1' comment '小车物理编号',
  chute_id smallint(6) unsigned default '1' comment '滑槽物理编号',
  scan_id smallint(6) unsigned default '1' comment '扫描仪物理编号(龙门架)',
  supply_id smallint(6) unsigned default '1' comment '供件台物理编号',
  layer_id tinyint(1) default null comment '层级id',
  car_num varchar(32) default null comment '小车逻辑编号',
  chute_num varchar(32) default null comment '滑槽口逻辑编号',
  scan_num varchar(32) default null comment '扫描仪逻辑编号(龙门架)',
  supply_num varchar(32) default null comment '供件台逻辑编号',
  layer_num varchar(32) default null comment '层级编码',
  package_weight int(10) default '0' comment '重量(g)',
  package_length int(10) default '0' comment '长度(mm)',
  package_width int(10) default '0' comment '宽度(mm)',
  package_height int(10) default '0' comment '高度(mm)',
  supply_type varchar(4) default null comment '供件方式',
  supply_time varchar(30) default null comment '供件时间(yyyy-mm-dd hh:mm:ss.sss)',
  scan_cycle smallint(6) unsigned not null default '0' comment '扫描次数',
  scan_status smallint(6) unsigned not null default '0' comment '扫描状态(0正常扫描,1无分拣方案,2无数据,3无读,4取消,5最大圈数,6格口错,7多条码,8,迷失,255未知)',
  scan_time varchar(30) default null comment '扫描时间(yyyy-mm-dd hh:mm:ss.sss)',
  sorting_status smallint(6) unsigned default '0' comment '分拣状态(0正常分拣,1无分拣方案,2无数据,3无读,4取消,5最大圈数,6格口错,7多条码,8,迷失,255未知)',
  sorting_time varchar(30) default null comment '分拣时间(yyyy-mm-dd hh:mm:ss.sss)',
  box_code varchar(40) default '' comment '箱号',
  box_site_code varchar(32) default null comment '箱号目的地代码',
  box_site_name varchar(64) default null comment '箱号目的地名称',
  jb_status smallint(6) unsigned default '0' comment '建包标识0未建包1已建包2不可建包(综合格口异常格口)',
  jb_time varchar(30) default null comment '建包时间(yyyy-mm-dd hh:mm:ss.sss)',
  jb_update_flag smallint(6) unsigned not null default '0' comment '建包更新标识0：未更新1：已更新2：不可更新',
  jb_update_time varchar(30) default null comment '建包更新时间(yyyy-mm-dd hh:mm:ss.sss)',
  re_mark varchar(100) default null comment '附加属性或备注',
  update_flag smallint(6) unsigned not null default '0' comment '更新标识0：未更新1：已更新2：不可更新',
  update_time varchar(30) default null comment '更新时间(yyyy-mm-dd hh:mm:ss.sss)',
  receive_time varchar(30) default null comment '消息生成时间(yyyy-mm-dd hh:mm:ss.sss)',
  create_time timestamp not null default current_timestamp comment '接收时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno,create_time),
  key waybill_num_key (waybill_num),
  key package_code_key (package_code),
  key jb_update_key (jb_status,jb_update_flag),
  key jb_query_key (chute_id,site_code),
  key sorting_flag_key (update_flag),
  key box_code_key (jb_status,box_code),
  key jb_key (jb_status,chute_id)
) engine=innodb default charset=utf8 comment='分拣表';
$$
-- ----------------------------
-- 分拣主题方案表sortscheme
-- ----------------------------
drop table if exists sortscheme;
$$
create table sortscheme (
  f_recno bigint(20) not null auto_increment comment '主键',
  scheme_id varchar(30) not null comment '分拣方案主键',
  scheme_name varchar(64) not null comment '分拣方案名称',
  site_no varchar(64) default null comment '分拔中心中转场编号或站点编码',
  machine_no varchar(64) default null comment '分拣机编号',
  sort_mode varchar(10) default null comment '分拣模式1:最近2:瀑布3:循环',
  print_style int(12) default '0' comment '打印机打印样式',
  print_style_name varchar(30) default null comment '打印样式名称',
  channel_id int(12) default '0' comment '渠道id',
  channel_name varchar(64) default null comment '渠道名称或分拔中转场名称',
  multiple_chute varchar(20) default null comment '统合格口，多格口以逗号隔开，方案中无统合格口默认为空',
  re_mark varchar(100) default null comment '附加属性或备注',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno)
) engine=innodb default charset=utf8 comment='分拣主题方案表';
$$
-- ----------------------------
-- 分拣方案明细表sortscheme_detail
-- ----------------------------
drop table if exists sortscheme_detail;
$$
create table sortscheme_detail (
  f_recno bigint(20) not null auto_increment comment '主键',
  scheme_id varchar(30) not null comment '分拣方案主键',
  site_code varchar(32) not null comment '运单目的地代码或站点编码',
  box_site_name varchar(64) default null comment '箱号目的地名称(或站点名称)',
  box_site_code varchar(32) default null comment '箱号目的地代码',
  chute_num varchar(50) not null comment '滑槽号',
  weight int(12) default '0' comment '格口打包重量单位克',
  re_mark varchar(128) default null comment '附加属性或者德邦包类型',
  is_print    varchar(10) comment '是否打印(德邦)',
  print_name  varchar(64) comment '打印名称(德邦)',
  complement_name  varchar(32) comment '补码简称(德邦)',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno),
  key sortscheme_key (scheme_id,site_code)
) engine=innodb default charset=utf8 comment='分拣方案明细表';
$$
-- ----------------------------
-- 分拣方案明细表(当前运行分拣的方案明细)sortscheme_detail_run
-- ----------------------------
drop table if exists sortscheme_detail_run;
$$
create table sortscheme_detail_run (
  f_recno bigint(20) not null auto_increment comment '主键',
  scheme_id varchar(30) not null comment '分拣方案主键',
  site_code varchar(32) not null comment '运单目的地代码或站点编码',
  box_site_name varchar(64) default null comment '箱号目的地名称(或站点名称)',
  box_site_code varchar(32) default null comment '箱号目的地代码',
  chute_num varchar(50) not null comment '滑槽号',
  weight int(12) default '0' comment '格口打包重量单位克',
  re_mark varchar(128) default null comment '附加属性或者德邦包类型',
  is_print    varchar(10) comment '是否打印(德邦)',
  print_name  varchar(64) comment '打印名称(德邦)',
  complement_name  varchar(32) comment '补码简称(德邦)',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno),
  key sortscheme_key (scheme_id,site_code)
) engine=innodb default charset=utf8 comment='分拣方案明细表(当前运行分拣的方案明细)';
$$
-- ----------------------------
-- 系统错误日志表system_errlog
-- ----------------------------
drop table if exists system_errlog;
$$
create table system_errlog (
  f_recno bigint(20) not null auto_increment comment '主键',
  log_id smallint(6) unsigned not null comment '日志代号',
  operator_id smallint(6) unsigned not null default '1' comment '操作员id',
  log_name varchar(32) default null comment '日志名称',
  log_value varchar(2000) default null comment '日志相关值',
  log_mark varchar(2000) default null comment '日志备注',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno,create_time),
  key log_key (create_time)
) engine=innodb default charset=utf8 comment='系统错误日志表';
$$
-- ----------------------------
-- 事件日志表system_event
-- ----------------------------
drop table if exists system_event;
$$
create table system_event (
  f_recno bigint(20) not null auto_increment comment '主键',
  event_id smallint(6) unsigned not null comment '事件代号',
  event_level smallint(6) unsigned not null comment '事件级别',
  event_type varchar(32) not null comment '事件类型',
  event_name varchar(32) not null comment '事件名称',
  event_val varchar(1000) default null comment '事件属性',
  event_mark varchar(1000) default null comment '事件说明',
  event_time varchar(30) default null comment '事件时间(yyyy-mm-dd hh:mm:ss.sss)',
  event_status tinyint(2) default null comment '事件上传状态：0未上传；1上传成功；2关闭上传',
  upload_time varchar(30) default null comment '事件上传京东pms时间(yyyy-mm-dd hh:mm:ss.sss)',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno,create_time),
  key event_key (create_time)
) engine=innodb auto_increment=79 default charset=utf8 comment='事件日志表';
$$
-- ----------------------------
-- 系统操作日志表system_log
-- ----------------------------
drop table if exists system_log;
$$
create table system_log (
  f_recno bigint(20) not null auto_increment comment '主键',
  log_id smallint(6) unsigned not null comment '日志代号',
  operator_id smallint(6) unsigned not null default '1' comment '操作员id',
  log_name varchar(32) default null comment '日志名称',
  log_value varchar(2000) default null comment '日志相关值',
  log_mark varchar(2000) default null comment '日志备注',
  log_time varchar(30) default null comment '日志时间(yyyy-mm-dd hh:mm:ss.sss)',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno,create_time),
  key log_key (create_time)
) engine=innodb auto_increment=74 default charset=utf8 comment='系统操作日志表';
$$
-- ----------------------------
-- 系统参数表system_set
-- ----------------------------
drop table if exists system_set;
$$
create table system_set (
  f_recno int(12) not null auto_increment comment '主键',
  set_name varchar(255) not null comment '参数名称',
  set_name_cn varchar(255) not null comment '参数中文名称',
  set_value varchar(255) not null comment '参数值',
  set_mark varchar(255) default null comment '参数备注',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno)
) engine=innodb auto_increment=39 default charset=utf8 comment='系统参数表';
$$
-- ----------------------------
-- 运单(快递原始单号)表waybill
-- ----------------------------
drop table if exists waybill;
$$
create table waybill (
  f_recno bigint(20) not null auto_increment comment '主键',
  waybill_id bigint(20) not null default '0' comment '运单号id',
  waybill_code varchar(30) not null comment '运单号',
  exp_code varchar(10) default '0' comment '异常代码(京东)',
  waybill_status smallint(6) unsigned not null default '0' comment '运单状态(0正常运单,1运单取消,2运单地址更改,3无数据)',
  waybill_weight int(10) default '0' comment '重量(g)',
  site_code varchar(32) default null comment '目的地代码',
  sort_routing varchar(200) comment '分拣路由(德邦)',
  orig_routing varchar(200) comment '原始路由(德邦)',
  serialno varchar(10) comment '流水号(德邦)',
  waybill_time varchar(30) default null comment '运单生成时间(yyyy-mm-dd hh:mm:ss.sss)重复运单号以生成时间最近的为准',
  create_time timestamp not null default current_timestamp comment '接收时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno,create_time),
  key waybill_key (waybill_code,waybill_id)
) engine=innodb default charset=utf8 comment='运单(快递原始单号)表';
$$
-- ----------------------------
-- 分拣表(缩减版)sorting_temp
-- ----------------------------
drop table if exists sorting_temp;
$$
create table sorting_temp (
  f_recno bigint(20) not null auto_increment comment '主键',
  batch_id int(12) default '0' comment '批次id',
  sorting_status smallint(6) unsigned default '0' comment '分拣状态(0正常扫描,1无分拣方案,2无数据,3无读,4取消,5最大圈数,6格口错,7多条码,8,迷失,255未知)',
  sorting_time varchar(30) default null comment '分拣时间(yyyy-mm-dd hh:mm:ss.sss)',
  sorting_time_long int(10) unsigned default null comment '分拣时间转换为long型',
  car_id smallint(6) unsigned default '1' comment '小车物理编号',
  chute_id smallint(6) unsigned default '1' comment '滑槽物理编号',
  supply_id smallint(6) unsigned default '1' comment '供件台物理编号',
  layer_id tinyint(6) default null comment '层级id',
  primary key (f_recno),
  key batch_key (batch_id),
  key sorting_key (sorting_time_long),
  key sorting_status_key (sorting_status),
  key car_key (car_id),
  key chute_key (chute_id),
  key supply_key (supply_id),
  key layer_key (layer_id)
) engine=innodb default charset=utf8 comment='分拣表(缩减版)';
$$
-- ----------------------------
-- 扫描表(缩减版)scan_temp
-- ----------------------------
drop table if exists scan_temp;
$$
create table scan_temp (
  f_recno bigint(20) not null auto_increment comment '主键',
  scan_status smallint(6) unsigned default '0' comment '扫描状态(0正常扫描,1无分拣方案,2无数据,3无读,4取消,5最大圈数,6格口错,7多条码,8,迷失,255未知)',
  scan_time varchar(30) default null comment '分拣时间(yyyy-mm-dd hh:mm:ss.sss)',
  scan_time_long int (10) unsigned  default null comment '分拣时间转换为long型',
  scan_id smallint(6) unsigned default '1' comment '扫描仪物理编号(龙门架)',
  primary key (f_recno),
  key scan_temp_key (scan_time_long),
  key scan_status_key (scan_status),
  key scan_key (scan_id)
) engine=innodb default charset=utf8 comment='扫描表(缩减版)';
$$

-- ----------------------------
-- 扫描、分拣分表存在记录表scan_sorting_exits_tables
-- ----------------------------
drop table if exists scan_sorting_exits_tables;
$$
create table scan_sorting_exits_tables (
  f_recno int(12) not null auto_increment comment '主键',
  table_name varchar(64) not null comment '扫描、分拣当前正在存在的分表名称',
  month varchar(10) not null comment '月份',
  table_type tinyint(2) not null comment '表类型：0,scan;1,sorting',
  create_time timestamp not null default current_timestamp comment '生成时间',
  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',
  primary key (f_recno)
) engine=innodb default charset=utf8 comment='扫描、分拣分表存在记录表';
$$
