package cn.seagen.base.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.seagen.base.bean.ReportMinuteBean;
import cn.seagen.base.bean.ReportScanBean;
import cn.seagen.base.bean.ReportSortingBean;
import cn.seagen.base.bean.ReportSupplyBean;
import cn.seagen.base.bean.StatisticsBoxBean;
import cn.seagen.base.bean.StatisticsProgressBean;
import cn.seagen.base.bean.StatisticsScanBean;
import cn.seagen.base.bean.StatisticsSortBean;
import cn.seagen.base.dao.StatisticsDao;
@Component
public class StatisticsDaoImpl implements StatisticsDao{
	private Logger logger = LogManager.getLogger(StatisticsDaoImpl.class.getName());
	@Resource
	private JdbcTemplate template;
	
	@Override
	public List<StatisticsSortBean> findSortList(String sql) {
		List<StatisticsSortBean> list = new ArrayList<StatisticsSortBean>();
		try {
			list = template.query(sql, new BeanPropertyRowMapper<StatisticsSortBean>(StatisticsSortBean.class));
		} catch (Exception e) {
			logger.error("findSortList:" + e.getMessage(), e);
		}
		return list;
	}

	@Override
	public List<StatisticsScanBean> findScanList(String sql) {
		List<StatisticsScanBean> list = new ArrayList<StatisticsScanBean>();
		try {
			list = template.query(sql, new BeanPropertyRowMapper<StatisticsScanBean>(StatisticsScanBean.class));  
		} catch (Exception e) {
			logger.error("findScanList:" + e.getMessage(), e);
		}
		return list;
	}

	@Override
	public List<StatisticsBoxBean> findBoxList(String sql) {
		List<StatisticsBoxBean> list = new ArrayList<StatisticsBoxBean>();
		try {
			list = template.query(sql, new BeanPropertyRowMapper<StatisticsBoxBean>(StatisticsBoxBean.class));
		} catch (Exception e) {
			logger.error("findBoxList:" + e.getMessage(), e);
		}
		return list;
	}

	@Override
	public int[] insertReportTable(String[] sqls) {
		try {
			return template.batchUpdate(sqls);
		} catch (Exception e) {
			logger.error("insertReportTable:" + e.getMessage(), e);
		    throw new RuntimeException("插入统计表失败："+Arrays.toString(sqls));
		}
	}

	@Override
	public boolean insertStatisticsProgress(String sql) {
		boolean result = false;
		try {
			template.update(sql);
			result = true;
		} catch (Exception e) {
			logger.error("insertStatisticsProgress:" + e.getMessage(), e);
			result = false;
			throw new RuntimeException("插入统计进度表失败："+sql);
		}
		return result;
	}

	@Override
	public boolean deleteStatisticsProgress(String sql) {
		boolean result = false;
		try {
			template.update(sql);
			result = true;
		} catch (Exception e) {
			logger.error("deleteStatisticsProgress:" + e.getMessage(), e);
			result = false;
			throw new RuntimeException("删除统计进度记录失败："+sql);
		}
		return result;
	}

	@Override
	public boolean insertBoxTemp(String sql) {
		boolean result = false;
		try {
			template.update(sql);
			result = true;
		} catch (Exception e) {
			logger.error("insertBoxTemp:" + e.getMessage(), e);
			result = false;
			throw new RuntimeException("插入封包类统计失败："+sql);
		}
		return result;
	}

	@Override
	public StatisticsProgressBean getStatisticsProgressBean(String sql) {
		List<StatisticsProgressBean> list = new ArrayList<StatisticsProgressBean>();
		try {
			list = template.query(sql, new BeanPropertyRowMapper<StatisticsProgressBean>(StatisticsProgressBean.class));  
			if(list != null && list.size() > 0)
				return list.get(0);
		} catch (Exception e) {
			logger.error("getStatisticsProgressBean:" + e.getMessage(), e);
		}
		return null;
	}

	@Override
	public int findStatisticsCount(String sql) {
		try {
			List<Integer> list = template.queryForList(sql, Integer.class);
			if (list!=null&&list.size()>0) {
				return list.get(0);
			}
		} catch (Exception e) {
			logger.error("findStatisticsCount error",e);
		}
		return 0;
	}

	@Override
	public boolean findIfExitsInReportTable(String sql) {
		try {
			List<Integer> list = template.queryForList(sql, Integer.class);
			if (list!=null&&list.size()>0) {
				return (list.get(0) > 0);
			}
		} catch (Exception e) {
			logger.error("findStatisticsCount error",e);
		}
		return false;
	}

	@Override
	public ReportMinuteBean findReportMinuteBean(String sql) {
		List<ReportMinuteBean> list = new ArrayList<ReportMinuteBean>();
		try {
			list = template.query(sql, new BeanPropertyRowMapper<ReportMinuteBean>(ReportMinuteBean.class));  
			if(list != null && list.size() > 0)
				return list.get(0);
		} catch (Exception e) {
			logger.error("findReportMinuteBeanByReportDateLayerId:" + e.getMessage(), e);
		}
		return null;
	}

	@Override
	public ReportSortingBean findReportSortingBean(String sql) {
		List<ReportSortingBean> list = new ArrayList<ReportSortingBean>();
		try {
			list = template.query(sql, new BeanPropertyRowMapper<ReportSortingBean>(ReportSortingBean.class));  
			if(list != null && list.size() > 0)
				return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("findReportSortingBeanByReportDateSumType:" + e.getMessage(), e);
		}
		return null;
	}

	@Override
	public ReportSupplyBean findReportSupplyBean(String sql) {
		List<ReportSupplyBean> list = new ArrayList<ReportSupplyBean>();
		try {
			list = template.query(sql, new BeanPropertyRowMapper<ReportSupplyBean>(ReportSupplyBean.class));  
			if(list != null && list.size() > 0)
				return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("findReportSupplyBeanByReportDateLayerIdSupplyId:" + e.getMessage(), e);
		}
		return null;
	}

	@Override
	public int findReportSixSpike(String sql) {
		try {
			List<Integer> list = template.queryForList(sql, Integer.class);
			if (list!=null&&list.size()>0) {
				return list.get(0) == null ? 0:list.get(0);
			}
		} catch (Exception e) {
			logger.error("findReportSixSpike error:"+sql,e);
			return 0;
		}
		return 0;
	}

	@Override
	public ReportScanBean findReportScanBean(String sql) {
		List<ReportScanBean> list = new ArrayList<ReportScanBean>();
		try {
			list = template.query(sql, new BeanPropertyRowMapper<ReportScanBean>(ReportScanBean.class));  
			if(list != null && list.size() > 0)
				return list.get(0);
		} catch (Exception e) {
			logger.error("findReportScanBeanByReportDateLayerIdScanId:" + e.getMessage(), e);
		}
		return null;
	}

	@Override
	public boolean updateStatisticsProgress(String sql) {
		boolean result = false;
		try {
			template.update(sql);
			result = true;
		} catch (Exception e) {
			logger.error("updateStatisticsProgress:" + e.getMessage(), e);
			result = false;
			throw new RuntimeException("更新统计进度表失败："+sql);
		}
		return result;
	}
}
