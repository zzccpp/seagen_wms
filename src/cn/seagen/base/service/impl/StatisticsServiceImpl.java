package cn.seagen.base.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.seagen.base.bean.ReportMinuteBean;
import cn.seagen.base.bean.ReportScanBean;
import cn.seagen.base.bean.ReportSortingBean;
import cn.seagen.base.bean.ReportSupplyBean;
import cn.seagen.base.bean.StatisticsBoxBean;
import cn.seagen.base.bean.StatisticsProgressBean;
import cn.seagen.base.bean.StatisticsScanBean;
import cn.seagen.base.bean.StatisticsSortBean;
import cn.seagen.base.dao.StatisticsDao;
import cn.seagen.base.service.StatisticsService;
import cn.seagen.base.utils.StatisticsScanUtils;
import cn.seagen.base.utils.StatisticsSortingUtils;
import cn.seagen.base.utils.StatisticsUtils;
@Service
public class StatisticsServiceImpl implements StatisticsService{
	private static Logger logger = LogManager.getLogger(StatisticsServiceImpl.class);

	@Resource
	private StatisticsDao statisticsDaoImpl;
	
	public StatisticsServiceImpl() {
		super();
	}

	@Override
	public List<StatisticsSortBean> findSortList(Long f_recno, int counts,String statistics_table) {
		String sql = StatisticsSortingUtils.formatFindSortList(f_recno, counts,statistics_table);
		List<StatisticsSortBean> list = statisticsDaoImpl.findSortList(sql);  
		logger.info("分拣类统计查询"+statistics_table+"表中数据：共<"+list.size()+">条");
		return list;
	}

	@Override
	public List<StatisticsScanBean> findScanList(Long f_recno, int counts,String statistics_table) {
		String sql = StatisticsScanUtils.formatFindScanList(f_recno, counts,statistics_table);
		List<StatisticsScanBean> list = statisticsDaoImpl.findScanList(sql);  
		logger.info("扫描量统计查询"+statistics_table+"表中数据：共<"+list.size()+">条");
		return list;
	}

	@Override
	public List<StatisticsBoxBean> findBoxList(Long f_recno, int counts) {
		String sql = StatisticsSortingUtils.formatFindBoxList(f_recno, counts);
		List<StatisticsBoxBean> list = statisticsDaoImpl.findBoxList(sql);
		logger.info("封包类统计查询box_temp表中数据：共<"+list.size()+">条");
		return list;
	}

	@Override
	public int[] insertReportTable(List<String> sqls) {
		String[] s = new String[sqls.size()];
		for(int i = 0;i < sqls.size();i++){
			s[i] = sqls.get(i);
		}
		return statisticsDaoImpl.insertReportTable(s);
	}

	@Override
	public boolean insertStatisticsProgress(String sql) {
		return statisticsDaoImpl.insertStatisticsProgress(sql);
	}

	@Override
	public boolean deleteStatisticsProgress(String sql) {
		return statisticsDaoImpl.deleteStatisticsProgress(sql);
	}

	@Override
	public boolean insertBoxTemp(String sql) {
		return statisticsDaoImpl.insertBoxTemp(sql);
	}

	@Override
	public StatisticsProgressBean getStatisticsProgressBean(int type) {
		String sql = StatisticsUtils.formatFindStatisticsProgressBeanSql(type);
		return statisticsDaoImpl.getStatisticsProgressBean(sql);
	}

	@Override
	public int findStatisticsCount(Long f_recno, int type) {
		//statistics_count
		String sql = StatisticsUtils.findStatisticsCount(f_recno, type);
		if(StringUtils.isEmpty(sql))
			return 0;
		return statisticsDaoImpl.findStatisticsCount(sql);
	}

	@Override
	public boolean findIfExitsInReportTable(String sql) {
		return statisticsDaoImpl.findIfExitsInReportTable(sql);
	}

	@Override
	public ReportMinuteBean findReportMinuteBean(String sql) {
		return statisticsDaoImpl.findReportMinuteBean(sql);
	}

	@Override
	public ReportSortingBean findReportSortingBean(String sql) {
		return statisticsDaoImpl.findReportSortingBean(sql);
	}

	@Override
	public ReportSupplyBean findReportSupplyBean(
			String sql) {
		return statisticsDaoImpl.findReportSupplyBean(sql);
	}

	@Override
	public int findReportSixSpike(String sql) {
		return statisticsDaoImpl.findReportSixSpike(sql);
	}

	@Override
	public ReportScanBean findReportScanBean(String sql) {
		return statisticsDaoImpl.findReportScanBean(sql);
	}

	@Override
	public StatisticsProgressBean getStatisticsProgressBean(int type,
			String statistics_table) {
		String sql = StatisticsUtils.formatFindStatisticsProgressBeanSql(type,statistics_table);
		return statisticsDaoImpl.getStatisticsProgressBean(sql);
	}

	@Override
	public boolean updateStatisticsProgress(String sql) {
		return statisticsDaoImpl.updateStatisticsProgress(sql);
	}
}
