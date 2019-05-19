package cn.seagen.base.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import cn.seagen.base.bean.SystemEvent;
import cn.seagen.base.dao.SystemEventDao;

@Service
public class SystemEventDaoImpl implements SystemEventDao {
	
	private Logger logger = LogManager.getLogger(SystemEventDaoImpl.class.getName());
	private JdbcTemplate jdbcTemplate;
	@Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public SystemEvent findFlagEventById(int fRecno) {
		return jdbcTemplate.queryForObject("select f_recno,event_level,event_type,event_name,event_mark from flag_event where f_recno="+fRecno, new RowMapper<SystemEvent>(){
			@Override
			public SystemEvent mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				//SystemEvent systemEvent = null;
				SystemEvent systemEvent = new SystemEvent();
				systemEvent.setEventId(rs.getInt("f_recno"));
				systemEvent.setEventLevel(rs.getInt("event_level"));
				systemEvent.setEventType(rs.getString("event_type"));
				systemEvent.setEventName(rs.getString("event_name"));
				systemEvent.setEventMark(rs.getString("event_mark"));
				return systemEvent;
			}
		});
	}
	
	@Override
	public boolean insertSystemEvent(SystemEvent systemEvent) {
		int result = jdbcTemplate.update("insert into system_event(event_id,event_level,event_type,event_name,event_val,event_mark,event_time,layer_id) values(?,?,?,?,?,?,?,?);"
				, new Object[]{systemEvent.getEventId(),systemEvent.getEventLevel(),systemEvent.getEventType(),systemEvent.getEventName(),
						systemEvent.getEventVal(),systemEvent.getEventMark(),systemEvent.getEventTime(),systemEvent.getLayerId()});
		return result>0;
	}	

	@Override
	public List<Map<String, Object>> findSystemEventList(String sql) {
		System.out.println(sql);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		try {
			dataList = jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			logger.error("findSystemEventList:" + e.getMessage(), e);
		}
		return dataList;
	}

	@Override
	public int findSystemEventListNum(String sql) {
		try {
			List<Integer> list = jdbcTemplate.queryForList(sql, Integer.class);
			if (list!=null&&list.size()>0) {
				return list.get(0);
			}
		} catch (Exception e) {
			logger.error("findSystemEventListNum error",e);
		}
		return 0;
	}

}
