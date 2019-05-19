package cn.seagen.base.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.seagen.base.dao.DbDataBackUpDao;
import cn.seagen.base.service.DbDataBackUpService;
@Service
public class DbDataBackUpServiceImpl implements DbDataBackUpService{
//	private static Logger logger = LogManager.getLogger(DbDataBackUpServiceImpl.class);
	
	@Resource
	private DbDataBackUpDao dbDataBackUpDaoImpl;
	
	public DbDataBackUpServiceImpl() {
		super();
	}

	@Override
	public int findCanDeleteCount(String sql) {
		return dbDataBackUpDaoImpl.findCanDeleteCount(sql);
	}

	@Override
	public int findTableDataCount(String sql) {
		return dbDataBackUpDaoImpl.findTableDataCount(sql);
	}

	@Override
	public boolean deleteTableData(String sql) {
		return dbDataBackUpDaoImpl.deleteTableData(sql);
	}
	
}
