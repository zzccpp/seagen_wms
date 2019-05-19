package cn.seagen.base.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.seagen.base.bean.ScanSortingExitsBean;
import cn.seagen.base.constant.DbPartitionConstant;


public class SqlUtils {
	/**
	 * 格式化时间
	 * @param dateStr
	 * @return
	 */
	public static String formatDateToStr(String dateStr,String format) {
		try {
			if (StringUtils.isEmpty(dateStr))
				return null;
			DateFormat fmt = null;
			if (StringUtils.isEmpty(format)) {
				fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			} else {
				fmt = new SimpleDateFormat(format);
			}
			return fmt.format(fmt.parse(dateStr));
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 格式化时间
	 * @param dateStr
	 * @return
	 */
	public static Date formatStrToDate(String dateStr,String format) {
		try {
			if (StringUtils.isEmpty(dateStr))
				return new Date();
			DateFormat fmt = null;
			if (StringUtils.isEmpty(format)) {
				fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			} else {
				fmt = new SimpleDateFormat(format);
			}
			return fmt.parse(dateStr);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	
	/**
	 * 日期格式时间戳转字符串
	 * @param longDate 时间戳
	 * @param format 如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String formatDateLongToStr(int longDate, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(new Date(longDate));  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	} 
	
	/**
	 * 数据库拼接语句
	 * @param sql
	 * @param pargramnum
	 * @return
	 */
	public static String formatSqlPargram(String sql, int pargramnum) {
		int num = pargramnum;
		num = (num < 0) ? 0 : ((num > 100) ? 0 : num);
		String formatstr = "";
		if (num > 0)
			formatstr = "?";
		for (int i = 1; i < num; i++) {
			formatstr = formatstr + ",?";
		}
		return String.format(sql, formatstr);
	}
	
	/**
	 * 获取表名
	 * @param chute_id 格口id
	 * @param type 类型：0,scan;1,sorting;2,report_car;3,report_chute;
	 * 		4,report_minute;5,report_scan;6,report_site;7,report_sorting;
	 * 		8,report_supply;9,waybill;10,box;
	 * @return
	 */
	public static String findTableName(int chute_id,String month,int type) {
		switch (type) {
		case 0:
			return "scan_"+month+"_0"+(chute_id % DbPartitionConstant.PARTITION_TALBE_NUM);
		case 1:
			return "sorting_"+month+"_0"+(chute_id % DbPartitionConstant.PARTITION_TALBE_NUM);
		case 2:
			return "report_car_"+month+"_0"+(chute_id % DbPartitionConstant.PARTITION_TALBE_NUM);
		case 3:
			return "report_chute_"+month+"_0"+(chute_id % DbPartitionConstant.PARTITION_TALBE_NUM);
		case 4:
			return "report_minute_"+month+"_0"+(chute_id % DbPartitionConstant.PARTITION_TALBE_NUM);
		case 5:
			return "report_scan_"+month+"_0"+(chute_id % DbPartitionConstant.PARTITION_TALBE_NUM);
		case 6:
			return "report_site_"+month+"_0"+(chute_id % DbPartitionConstant.PARTITION_TALBE_NUM);
		case 7:
			return "report_sorting_"+month+"_0"+(chute_id % DbPartitionConstant.PARTITION_TALBE_NUM);
		case 8:
			return "report_supply_"+month+"_0"+(chute_id % DbPartitionConstant.PARTITION_TALBE_NUM);
		case 9:
			return "waybill_"+month+"_0"+(chute_id % DbPartitionConstant.PARTITION_TALBE_NUM);
		case 10:
			return "box_"+month+"_0"+(chute_id % DbPartitionConstant.PARTITION_TALBE_NUM);
		default:
			return "";
		}
	}
	
	/**
	 * 根据表名称获取表对应的月份:表key值+月份+分表标志（sorting_201801_01）
	 * @param tableName
	 * @return
	 */
	public static String getMonthFromTableName(String tableName) {
		if(StringUtils.isEmpty(tableName))
			return "";
		String[] str = tableName.split("_");
		if(null == str || str.length != 3)
			return "";
		return str[1];
	}
	
	/**
	 * 根据月份、天，新建表（业务表和统计类表单）
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getCreateTableSql(String month){
		StringBuilder builder = new StringBuilder();
		builder.append("--------------------------------提示---------------------------------------------").append("\r\n");
		builder.append("-- 建表语句与存储过程,写在这里面,以$$结束,").append("\r\n");
		builder.append("-- 注意：执行如下建表前(启动服务器前),手动创建执行建库语句如下").append("\r\n");
		builder.append("-- create database seagen_wms default character set utf8 collate utf8_general_ci;").append("\r\n");
		//scan
		for(int i = 1;i <= DbPartitionConstant.PARTITION_TALBE_NUM;i++){
			String name = "scan_"+month+"_0"+i;
			builder.append("drop table if exists "+name).append(";\r\n");
			builder.append("$$\r\n");
			builder.append("create table "+name+" (").append("\r\n");
			builder.append("  f_recno bigint(20) not null auto_increment comment '主键',").append("\r\n");
			builder.append("  batch_id int(12) default '0' comment '批次id',").append("\r\n");
			builder.append("  sort_mode varchar(30) default null comment '分拣模式',").append("\r\n");
			builder.append("  sort_source varchar(30) default null comment '分拣来源',").append("\r\n");
			builder.append("  waybill_id varchar(20) default null comment '快件追踪id',").append("\r\n");
			builder.append("  waybill_num varchar(40) default null comment '快件分拣过程唯一编号',").append("\r\n");
			builder.append("  waybill_code varchar(30) default null comment '运单号',").append("\r\n");
			builder.append("  waybill_site_code varchar(30) default null comment '运单表中目的地代码',").append("\r\n");
			builder.append("  package_code varchar(30) not null comment '包裹号',").append("\r\n");
			builder.append("  waybill_status int(4) not null default '0' comment '运单状态(0正常运单,1运单取消,2运单地址更改,3无数据)',").append("\r\n");
			builder.append("  waybill_time varchar(30) default null comment '运单生成时间(yyyy-mm-dd hh:mm:ss.sss)',").append("\r\n");
			builder.append("  serialno varchar(10) comment '流水号(德邦)',").append("\r\n");
			builder.append("  site_code varchar(32) default null comment '目的地代码或站点编码',").append("\r\n");
			builder.append("  site_name varchar(64) default null comment '目的地名称',").append("\r\n");
			builder.append("  car_id int(11) default '1' comment '小车物理编号',").append("\r\n");
			builder.append("  chute_id int(11) default '1' comment '滑槽物理编号',").append("\r\n");
			builder.append("  scan_id int(11) default '1' comment '扫描仪物理编号(龙门架)',").append("\r\n");
			builder.append("  supply_id int(11) default '1' comment '供件台物理编号',").append("\r\n");
			builder.append("  layer_id tinyint(1) default null comment '层级id',").append("\r\n");
			builder.append("  car_num varchar(32) default null comment '小车逻辑编号',").append("\r\n");
			builder.append("  chute_num varchar(150) default null comment '滑槽口逻辑编号',").append("\r\n");
			builder.append("  scan_num varchar(32) default null comment '扫描仪逻辑编号(龙门架)',").append("\r\n");
			builder.append("  supply_num varchar(32) default null comment '供件台逻辑编号',").append("\r\n");
			builder.append("  layer_num varchar(32) default null comment '层级编码',").append("\r\n");
			builder.append("  package_weight int(10) default '0' comment '重量(g)',").append("\r\n");
			builder.append("  package_length int(10) default '0' comment '长度(mm)',").append("\r\n");
			builder.append("  package_width int(10) default '0' comment '宽度(mm)',").append("\r\n");
			builder.append("  package_height int(10) default '0' comment '高度(mm)',").append("\r\n");
			builder.append("  supply_type varchar(4) default null comment '供件方式',").append("\r\n");
			builder.append("  supply_time varchar(30) default null comment '供件时间(yyyy-mm-dd hh:mm:ss.sss)',").append("\r\n");
			builder.append("  scan_cycle int(4) not null default '0' comment '扫描次数',").append("\r\n");
			builder.append("  scan_status int(4) not null default '0' comment '扫描状态(0正常扫描,1无分拣方案,2无数据,3无读,4取消,5最大圈数,6格口错,7多条码,8,迷失,255未知)',").append("\r\n");
			builder.append("  scan_time varchar(30) default null comment '扫描时间(yyyy-mm-dd hh:mm:ss.sss)',").append("\r\n");
			builder.append("  re_mark varchar(100) default null comment '附加属性或备注',").append("\r\n");
			builder.append("  update_flag int(4) not null default '0' comment '更新标识0：未更新1：已更新2：不可更新',").append("\r\n");
			builder.append("  update_time varchar(30) default null comment '更新时间(yyyy-mm-dd hh:mm:ss.sss)',").append("\r\n");
			builder.append("  receive_time varchar(30) default null comment '消息生成时间(yyyy-mm-dd hh:mm:ss.sss)',").append("\r\n");
			builder.append("  create_time timestamp not null default current_timestamp comment '数据库生成时间',").append("\r\n");
			builder.append("  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',").append("\r\n");
			builder.append("  primary key (f_recno),").append("\r\n");
			builder.append("  key waybill_num_key (waybill_num),").append("\r\n");
			builder.append("  key package_code_key (package_code),").append("\r\n");
			builder.append("  key update_flag_key (update_flag),").append("\r\n");
			builder.append("  key create_time_key (create_time)").append("\r\n");
			builder.append(") engine=innodb default charset=utf8 comment='扫描表_"+month+"_0"+i+"';").append("\r\n");
			builder.append("$$\r\n");
		}
		builder.append("\r\n");
		//sorting
		for(int i = 1;i <= DbPartitionConstant.PARTITION_TALBE_NUM;i++){
			String name = "sorting_"+month+"_0"+i;
			builder.append("drop table if exists "+name).append(";\r\n");
			builder.append("$$\r\n");
			builder.append("create table "+name+" (").append("\r\n");
			builder.append("  f_recno bigint(20) not null auto_increment comment '主键',").append("\r\n");
			builder.append("  batch_id int(12) default '0' comment '批次id',").append("\r\n");
			builder.append("  sort_mode varchar(30) default null comment '分拣模式',").append("\r\n");
			builder.append("  sort_source varchar(30) default null comment '分拣来源',").append("\r\n");
			builder.append("  waybill_id varchar(20) default null comment '快件追踪id',").append("\r\n");
			builder.append("  waybill_num varchar(40) default null comment '快件分拣过程唯一编号',").append("\r\n");
			builder.append("  waybill_code varchar(30) default null comment '运单号',").append("\r\n");
			builder.append("  waybill_site_code varchar(30) default null comment '运单表中目的地代码',").append("\r\n");
			builder.append("  package_code varchar(30) not null comment '包裹号',").append("\r\n");
			builder.append("  waybill_status int(4) not null default '0' comment '运单状态(0正常运单,1运单取消,2运单地址更改)',").append("\r\n");
			builder.append("  waybill_time varchar(30) default null comment '运单生成时间(yyyy-mm-dd hh:mm:ss.sss)',").append("\r\n");
			builder.append("  serialno varchar(10) comment '流水号(德邦)',").append("\r\n");
			builder.append("  site_code varchar(32) default null comment '目的地代码或站点编码',").append("\r\n");
			builder.append("  site_name varchar(64) default null comment '目的地名称',").append("\r\n");
			builder.append("  car_id int(11) default '1' comment '小车物理编号',").append("\r\n");
			builder.append("  chute_id int(11) default '1' comment '滑槽物理编号',").append("\r\n");
			builder.append("  scan_id int(11) default '1' comment '扫描仪物理编号(龙门架)',").append("\r\n");
			builder.append("  supply_id int(11) default '1' comment '供件台物理编号',").append("\r\n");
			builder.append("  layer_id tinyint(1) default null comment '层级id',").append("\r\n");
			builder.append("  car_num varchar(32) default null comment '小车逻辑编号',").append("\r\n");
			builder.append("  chute_num varchar(32) default null comment '滑槽口逻辑编号',").append("\r\n");
			builder.append("  scan_num varchar(32) default null comment '扫描仪逻辑编号(龙门架)',").append("\r\n");
			builder.append("  supply_num varchar(32) default null comment '供件台逻辑编号',").append("\r\n");
			builder.append("  layer_num varchar(32) default null comment '层级编码',").append("\r\n");
			builder.append("  package_weight int(10) default '0' comment '重量(g)',").append("\r\n");
			builder.append("  package_length int(10) default '0' comment '长度(mm)',").append("\r\n");
			builder.append("  package_width int(10) default '0' comment '宽度(mm)',").append("\r\n");
			builder.append("  package_height int(10) default '0' comment '高度(mm)',").append("\r\n");
			builder.append("  supply_type varchar(4) default null comment '供件方式',").append("\r\n");
			builder.append("  supply_time varchar(30) default null comment '供件时间(yyyy-mm-dd hh:mm:ss.sss)',").append("\r\n");
			builder.append("  scan_cycle int(4) not null default '0' comment '扫描次数',").append("\r\n");
			builder.append("  scan_status int(4) not null default '0' comment '扫描状态(0正常扫描,1无分拣方案,2无数据,3无读,4取消,5最大圈数,6格口错,7多条码,8,迷失,255未知)',").append("\r\n");
			builder.append("  scan_time varchar(30) default null comment '扫描时间(yyyy-mm-dd hh:mm:ss.sss)',").append("\r\n");
			builder.append("  sorting_status int(4) default '0' comment '分拣状态(0正常分拣,1无分拣方案,2无数据,3无读,4取消,5最大圈数,6格口错,7多条码,8,迷失,255未知)',").append("\r\n");
			builder.append("  sorting_time varchar(30) default null comment '分拣时间(yyyy-mm-dd hh:mm:ss.sss)',").append("\r\n");
			builder.append("  box_code varchar(40) default '' comment '箱号',").append("\r\n");
			builder.append("  box_site_code varchar(32) default null comment '箱号目的地代码',").append("\r\n");
			builder.append("  box_site_name varchar(64) default null comment '箱号目的地名称',").append("\r\n");
			builder.append("  jb_status int(4) default '0' comment '建包标识0未建包1已建包2不可建包(综合格口异常格口)',").append("\r\n");
			builder.append("  jb_time varchar(30) default null comment '建包时间(yyyy-mm-dd hh:mm:ss.sss)',").append("\r\n");
			builder.append("  jb_update_flag int(4) not null default '0' comment '建包更新标识0：未更新1：已更新2：不可更新',").append("\r\n");
			builder.append("  jb_update_time varchar(30) default null comment '建包更新时间(yyyy-mm-dd hh:mm:ss.sss)',").append("\r\n");
			builder.append("  re_mark varchar(100) default null comment '附加属性或备注',").append("\r\n");
			builder.append("  update_flag int(4) not null default '0' comment '更新标识0：未更新1：已更新2：不可更新',").append("\r\n");
			builder.append("  update_time varchar(30) default null comment '更新时间(yyyy-mm-dd hh:mm:ss.sss)',").append("\r\n");
			builder.append("  receive_time varchar(30) default null comment '消息生成时间(yyyy-mm-dd hh:mm:ss.sss)',").append("\r\n");
			builder.append("  create_time timestamp not null default current_timestamp comment '接收时间',").append("\r\n");
			builder.append("  modify_time timestamp not null default '0000-00-00 00:00:00' on update current_timestamp comment '修改时间',").append("\r\n");
			builder.append("  primary key (f_recno),").append("\r\n");
			builder.append("  key waybill_num_key (waybill_num),").append("\r\n");
			builder.append("  key package_code_key (package_code),").append("\r\n");
			builder.append("  key jb_update_key (jb_status,jb_update_flag),").append("\r\n");
			builder.append("  key jb_query_key (chute_id,site_code),").append("\r\n");
			builder.append("  key sorting_flag_key (update_flag),").append("\r\n");
			builder.append("  key box_code_key (jb_status,box_code),").append("\r\n");
			builder.append("  key jb_key (jb_status,chute_id),").append("\r\n");
			builder.append("  key create_time_key (create_time)").append("\r\n");
			builder.append(") engine=innodb default charset=utf8 comment='分拣表_"+month+"_0"+i+"';").append("\r\n");
			builder.append("$$\r\n");
		}
		builder.append("\r\n");
		
		return builder.toString();
	}
	
	/**
	 * 根据月份、类型创建新的分表
	 * @param month 201801
	 * @param type 0，扫描表；1，分拣表；2，待定；
	 * @return
	 */
	public static List<String> getCreateTablePartitionSql(String month,int type){
		List<String> list = new ArrayList<String>();
		switch (type) {
		case 0:
			for(int i = 0 ;i < DbPartitionConstant.PARTITION_TALBE_NUM;i++){
				list.add("create table scan_"+month+"_0"+i+" like scan;");
			}
			return list;
		case 1:
			for(int i = 0 ;i < DbPartitionConstant.PARTITION_TALBE_NUM;i++){
				list.add("create table sorting_"+month+"_0"+i+" like sorting;");
			}
			return list;
		default:
			return null;
		}
	}
	
	/**
	 * 根据sql语句和类型，获取分表名称
	 * @param sql
	 * @param type 0，扫描表；1，分拣表；2，待定；
	 * @return
	 */
	public static ScanSortingExitsBean getScanSortingExitsTableName(String sql,int type){
		ScanSortingExitsBean scanSortingExitsBean = new ScanSortingExitsBean();
		switch (type) {
		case 0:
			scanSortingExitsBean.setTable_name(sql.substring(13, 27));
			return scanSortingExitsBean;
		case 1:
			scanSortingExitsBean.setTable_name(sql.substring(13, 30));
			return scanSortingExitsBean;
		default:
			scanSortingExitsBean = null;
		}
		return scanSortingExitsBean;
	}
	
	
	/**
	 * 根据月份、类型返回表名集合
	 * @param month 201801
	 * @param type 0，扫描表；1，分拣表；2，待定；
	 * @return
	 */
	public static List<String> getCreateTablePartitionNames(String month,int type){
		List<String> list = new ArrayList<String>();
		switch (type) {
		case 0:
			for(int i = 0 ;i < DbPartitionConstant.PARTITION_TALBE_NUM;i++){
				list.add("scan_"+month+"_0"+i+"");
			}
			return list;
		case 1:
			for(int i = 0 ;i < DbPartitionConstant.PARTITION_TALBE_NUM;i++){
				list.add("sorting_"+month+"_0"+i+"");
			}
			return list;
		default:
			return (new ArrayList<String>());
		}
	}
	
	
	/**
	 * 根据分表集合判断集合中是否存在异常，创建失败的分表名称
	 * @return List<String> 
	 */
	public static List<String> findNotExitsPartitionTable(List<ScanSortingExitsBean> list,String month,int type){
		List<String> names = getCreateTablePartitionNames(month, type);
		if(names == null || names.size() == 0)
			return null;
		if(list == null || list.size() == 0)
			return names;
		for(ScanSortingExitsBean bean : list) {
			if(names.contains(bean.getTable_name()))
				names.remove(bean.getTable_name());
		}
		return names;
	}
	
	
	/**
	 * 根据月份、类型创建新的分表
	 * @param tableName scan_201801_01
	 * @param type 0，扫描表；1，分拣表；2，待定；
	 * @return
	 */
	public static String getCreateTablePartitionSqlByTableName(String tableName,int type){
		String sql = "";
		switch (type) {
		case 0:
			sql = "create table "+tableName+" like scan;";
			return sql;
		case 1:
			sql = "create table "+tableName+" like sorting;";
			return sql;
		default:
			sql = "";
		}
		return sql;
	}
	
	/**
	 * 组装创建触发器的sql语句
	 * @param tableName
	 * @param type
	 * @return
	 */
	public static String getCreateTriggerSql(String tableName,int type){
		StringBuilder builder = new StringBuilder();
		switch (type) {
		case 0:
//			builder.append("drop trigger if exists "+tableName+"_trigger; \r\n");
			builder.append("create trigger "+tableName+"_trigger after insert on "+tableName+" for each row \r\n");
			builder.append("begin \r\n");
			builder.append("	insert into scan_temp (scan_status,scan_time,scan_time_long,scan_id,layer_id \r\n");
			builder.append("	) \r\n");
			builder.append("	values \r\n");
			builder.append("	( new.scan_status,date_format(new.scan_time, '%Y-%m-%d %H:%i:%s'),unix_timestamp(date_format(new.scan_time, '%Y-%m-%d %H:%i:%s')),new.scan_id,new.layer_id \r\n");
			builder.append("	); \r\n");
			builder.append("end; \r\n");
			return builder.toString();
		case 1:
//			builder.append("drop trigger if exists "+tableName+"_trigger; \r\n");
			builder.append("create trigger "+tableName+"_trigger after insert on "+tableName+" for each row \r\n");
			builder.append("begin \r\n");
			builder.append("	insert into sorting_temp (batch_id,sorting_status,sorting_time, \r\n");
			builder.append("		sorting_time_long,car_id,chute_id,scan_id,supply_id,layer_id \r\n");
			builder.append("	) \r\n");
			builder.append("	values \r\n");
			builder.append("	( new.batch_id,new.sorting_status,new.sorting_time_date,unix_timestamp(new.sorting_time_date), \r\n");
			builder.append("	  new.car_id,new.chute_id,new.scan_id,new.supply_id,new.layer_id \r\n");
			builder.append("	); \r\n");
			builder.append("end; \r\n");
			return builder.toString();
		default:
			return "";
		}
	}
	
	/**
	 * 判断触发器是否存在的sql语句
	 * @param tableName
	 * @return
	 */
	public static String findTriggerExitsSql(String tableName) {
		StringBuilder builder = new StringBuilder();
		builder.append("drop trigger if exists "+tableName+"_trigger;");
		return builder.toString();
	}
	
	/**
	 * 将list的表名集合转化为字符串类型进行输出
	 * @param tableNames
	 * @return
	 */
	public static String formatTablesToOutput(List<String> tableNames){
		if(tableNames == null) 
			return "";
		String names = "";
		for(String name : tableNames)
			names = names + name+ ",";
		return names.substring(0, names.length()-1);
	}
	
	/**
	 * 根据表名、保留天数、删除数量来组装获取数据表中可删除的数量
	 * @param tableName 表名
	 * @param holdday 保留天数
	 * @param type 类型：0，box_temp;1,scan_temp;2,sorting_temp;
	 * 			3,box,waybill,printer_data,system_event,system_errlog,
	 * 			system_log,report_minute,report_scan,report_sorting,report_supply
	 * @return
	 */
	public static String formatFindCanDeleteCount(String tableName,int holdday,int type){
		String sql = "";
		switch (type) {
		case 0:
			sql = "select count(1) as candel from " + tableName + " where print_time < date_add(now(), "
					+ "interval - "+ holdday + " day);";
			return sql;
		case 1:
			sql = "select count(1) as candel from " + tableName + " where create_time < date_add(now(), "
					+ "interval - "+ holdday + " day);";
			return sql;
		case 2:
			sql = "select count(1) as candel from " + tableName + " where create_time < date_add(now(), "
					+ "interval - "+ holdday + " day);";
			return sql;
		case 3:
			sql = "select count(1) as candel from " + tableName + " where create_time < date_add(now(), "
					+ "interval - "+ holdday + " day);";
			return sql;
		default:
			return sql;
		}
	}
	
	/**
	 * 根据表名组装获取数据表中总的数据量的sql语句
	 * @param tableName 表名
	 * @return
	 */
	public static String formatFindTableDataCount(String tableName){
		String sql = "select count(f_recno) as data_num from "+tableName+";";
		return sql;
	}
	
	/**
	 * 根据表名、删除数量组装删除数据表中数据的sql语句
	 * @param tableName 表名
	 * @param delCount 删除数量
	 * @return
	 */
	public static String formatDeleteTableData(String tableName,int delCount){
		String sql = "delete from " + tableName + " order by f_recno asc limit " + delCount + ";";
		return sql;
	}
}
