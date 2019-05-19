package cn.seagen.base.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.seagen.base.dao.ReportBatchDao;
@Component
public class ReportBatchDaoImpl implements ReportBatchDao{
	private Logger logger = LogManager.getLogger(ReportBatchDaoImpl.class.getName());
	@Resource
	private JdbcTemplate template;
	
	@Override
	public List<Map<String, Object>> queryBatch(String sql) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		try {
			dataList = template.queryForList(sql);
		} catch (Exception e) {
			logger.error("查询批次量统计异常："+e.getMessage(), e);
		}
		return dataList;
	}

	@Override
	public int findBatchNum(String sql) {
		try {
			List<Integer> list = template.queryForList(sql, Integer.class);
			if (list!=null&&list.size()>0) {
				return list.get(0);
			}
		} catch (Exception e) {
			logger.error("findBatchNum 异常:"+sql,e);
			return 0;
		}
		return 0;
	}
	
}
