package cn.seagen.base.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.seagen.base.dao.ReportScanDao;
@Component
public class ReportScanDaoImpl implements ReportScanDao{
	private Logger logger = LogManager.getLogger(ReportScanDaoImpl.class.getName());
	@Resource
	private JdbcTemplate template;
	
	@Override
	public List<Map<String, Object>> queryScan(String sql) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		try {
			dataList = template.queryForList(sql);
		} catch (Exception e) {
			logger.error("查询扫描量统计异常："+e.getMessage(), e);
		}
		return dataList;
	}
}
