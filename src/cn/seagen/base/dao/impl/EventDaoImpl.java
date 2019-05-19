package cn.seagen.base.dao.impl;

import org.springframework.stereotype.Component;

import cn.seagen.base.bean.SystemEvent;
import cn.seagen.base.dao.EventDao;
@Component
public class EventDaoImpl implements EventDao {

	@Override
	public boolean insertEventInfo(SystemEvent event) {
		
		return true;
	}

}
