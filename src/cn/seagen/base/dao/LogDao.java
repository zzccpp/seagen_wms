package cn.seagen.base.dao;
import java.util.List;

import cn.seagen.base.bean.RbacUser;
import cn.seagen.base.bean.SystemLog;

public interface LogDao {
	/** 记录日志 */
	public boolean record_log(int log_id, String log_val, String log_mark) ;

	/** 记录日志 */
	public  boolean record_log(int log_id, String log_val, String log_mark, String create_time);

	/** 记录日志 */
	public  boolean record_log(int log_id, int operator_id, String log_val, String log_mark);

	/** 记录日志 */
	public  boolean record_log(int log_id, int operator_id, String log_val, String log_mark, String create_time);

	/** 记录错误日志 */
	public  boolean record_errlog(int log_id, String log_name, String log_val, String log_mark);

	/** 根据日期获取某天的日志数据 */
	public  List<SystemLog> getSystemLogByDay(String day);

	/** 根据日期获取某天的错误日志数据 */
	public  List<SystemLog> getSystemErrLogByDay(String day);

	/** 记录报表查询日志 */
	public  void record_report_log(String report_name, RbacUser opt);
}
