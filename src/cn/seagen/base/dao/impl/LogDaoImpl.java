package cn.seagen.base.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.seagen.base.bean.RbacUser;
import cn.seagen.base.bean.SystemLog;
import cn.seagen.base.constant.LogType;
import cn.seagen.base.dao.LogDao;
import cn.seagen.base.utils.DateUtils;
import cn.seagen.base.utils.JUtils;
@Component
public class LogDaoImpl implements LogDao {
	private static Logger logger = LogManager.getLogger(LogDao.class.getName());
	@Resource
	private JdbcTemplate template;

	/** 记录日志 */
	@Override
	public boolean record_log(int log_id, String log_val, String log_mark) {
		return record_log(log_id, log_val, log_mark, DateUtils.getNow()); // operator_id=1系统操作
	}

	/** 记录日志 */
	@Override
	public boolean record_log(int log_id, String log_val, String log_mark,
			String create_time) {
		return record_log(log_id, 1, log_val, log_mark, create_time); // operator_id=1系统操作
	}

	/** 记录日志 */
	@Override
	public boolean record_log(int log_id, int operator_id, String log_val,
			String log_mark) {
		return record_log(log_id, operator_id, log_val, log_mark,
				DateUtils.getNow());
	}

	/** 记录日志 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean record_log(final int log_id,final int operator_id,final String log_val,final
			String log_mark,final String create_time) {
		boolean res = false;
		/*if ((!JUtils.isEmpty(log_val)) && (log_val.length() > 1000))
			log_val = log_val.substring(0, 999);
		if ((!JUtils.isEmpty(log_mark)) && (log_mark.length() > 1000))
			log_mark = log_mark.substring(0, 999);*/

		try {
			int r = template.execute(new CallableStatementCreator() {
						public CallableStatement createCallableStatement(
								Connection con) throws SQLException {
							String storedProc = "{CALL PROC_INSERT_LOG(?,?,?,?,?,?)}";// 调用的sql
							CallableStatement cs = con.prepareCall(storedProc);
							cs.setInt(1, log_id);
							cs.setInt(2, operator_id);
							cs.setString(3, log_val);
							cs.setString(4, log_mark);
							cs.setString(5, create_time);
							cs.registerOutParameter(6, Types.INTEGER);
							return cs;
						}
					}, new CallableStatementCallback() {
						public Object doInCallableStatement(CallableStatement cs)
								throws SQLException, DataAccessException {
							cs.execute();
							return cs.getInt(6);// 获取输出参数的值
						}
					});
			/*String storedProc = "{CALL PROC_INSERT_LOG(?,?,?,?,?,?)}";// 调用的sql
			int r = template.execute(storedProc, new CallableStatementCallback() {
				public Object doInCallableStatement(CallableStatement cs)
						throws SQLException, DataAccessException {
					cs.setInt(1, log_id);
					cs.setInt(2, operator_id);
					cs.setString(3, log_val);
					cs.setString(4, log_mark);
					cs.setString(5, create_time);
					cs.registerOutParameter(6, Types.INTEGER);
					cs.execute();
					return cs.getInt(6);// 获取输出参数的值
				}
			});*/
			res = (r == 0);
			/*conn = template.getDataSource().getConnection();
			cs = conn.prepareCall("{CALL PROC_INSERT_LOG(?,?,?,?,?,?)}");
			cs.setInt(1, log_id);
			cs.setInt(2, operator_id);
			cs.setString(3, log_val);
			cs.setString(4, log_mark);
			cs.setString(5, create_time);
			cs.registerOutParameter(6, Types.INTEGER);
			cs.execute();
			res = (cs.getInt(6) == 0);*/
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("LogDao->record_log:" + e.getMessage(), e);
			res = false;
		} 
		return res;
	}

	/** 记录错误日志 */
	@Override
	public boolean record_errlog(int log_id, String log_name, String log_val,
			String log_mark) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean result = false;

		if ((!JUtils.isEmpty(log_val)) && (log_val.length() > 1000))
			log_val = log_val.substring(0, 999);
		if ((!JUtils.isEmpty(log_mark)) && (log_mark.length() > 1000))
			log_mark = log_mark.substring(0, 999);

		String sql = "INSERT INTO system_errlog(LOG_ID, LOG_NAME, LOG_VALUE, LOG_MARK) VALUES(?,?,?,?)";
		int tag = 0;
		try {
			conn = template.getDataSource().getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(++tag, log_id);
			ps.setString(++tag, log_name);
			ps.setString(++tag, log_val);
			ps.setString(++tag, log_mark);
			ps.executeUpdate();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("LogDao->record_errlog:" + e.getMessage(), e);
			result = false;
		} finally {
			//DBBase.close(conn, ps);
		}
		return result;
	}

	/** 根据日期获取某天的日志数据 */
	@Override
	public List<SystemLog> getSystemLogByDay(String day) {
		List<SystemLog> list = new ArrayList<SystemLog>();
		/*Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select l.f_recno, log_id, ifnull(operator_id, 1) as operator_id, " //
				+ " ifnull(opt_name, 'unkown') as opt_name, log_name, log_value, log_mark,"//
				+ " date_format(l.create_time, '%Y-%m-%d %h:%i:%s') as create_time "//
				+ " from system_log l left join operator o on l.operator_id = o.f_recno " //
				+ "where date_format(l.create_time, '%Y-%m-%d') = '" //
				+ day + "' order by l.create_time";//
		try {
			conn = template.getDataSource().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			SystemLog log = null;
			while (rs.next()) {
				log = new SystemLog();
				log.setfRecno(rs.getLong("f_recno"));
				log.setLogId(rs.getInt("log_id"));
				log.setOptName(rs.getString("opt_name"));
				log.setLogName(rs.getString("log_name"));
				log.setLogValue(rs.getString("log_value"));
				log.setLogMark(rs.getString("log_mark"));
				log.setCreateTime(rs.getString("create_time"));
				list.add(log);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("LogDao->getSystemLogByDay:" + e.getMessage(), e);
		} finally {
			//DBBase.close(conn, stmt, rs);
		}*/
		return list;
	}

	/** 根据日期获取某天的错误日志数据 */
	@Override
	public List<SystemLog> getSystemErrLogByDay(String day) {
		List<SystemLog> list = new ArrayList<SystemLog>();

		/*Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select l.f_recno, log_id, ifnull(operator_id, 1) as operator_id, " //
				+ " ifnull(opt_name, 'unkown') as opt_name, log_name, log_value, log_mark,"//
				+ " date_format(l.create_time, '%Y-%m-%d %h:%i:%s') as create_time "//
				+ " from system_errlog l left join operator o on l.operator_id = o.f_recno " //
				+ "where date_format(l.create_time, '%Y-%m-%d') = '" //
				+ day + "' order by l.create_time";//
		try {
			conn = template.getDataSource().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			//rs = (ResultSet) template.queryForList(sql);
			SystemLog log = null;
			while (rs.next()) {
				log = new SystemLog();
				log.setfRecno(rs.getLong("f_recno"));
				log.setLogId(rs.getInt("log_id"));
				log.setOptName(rs.getString("opt_name"));
				log.setLogName(rs.getString("log_name"));
				log.setLogValue(rs.getString("log_value"));
				log.setLogMark(rs.getString("log_mark"));
				log.setCreateTime(rs.getString("create_time"));
				list.add(log);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("LogDao->getSystemErrLogByDay:" + e.getMessage(), e);
		} finally {
			//DBBase.close(conn, stmt, rs);
		}*/
		return list;
	}

	/** 记录报表查询日志 */
	@Override
	public void record_report_log(String report_name, RbacUser user) {
		record_log(LogType.QUERY_REPORT, user.getId(), report_name, "ID:"
				+ user.getId() + ";NAME:" + user.getUserName());
	}

}
