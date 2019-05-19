package cn.seagen.base.dao;

import java.util.List;

import cn.seagen.base.bean.ReportMinuteBean;
import cn.seagen.base.bean.ReportScanBean;
import cn.seagen.base.bean.ReportSortingBean;
import cn.seagen.base.bean.ReportSupplyBean;
import cn.seagen.base.bean.StatisticsBoxBean;
import cn.seagen.base.bean.StatisticsProgressBean;
import cn.seagen.base.bean.StatisticsScanBean;
import cn.seagen.base.bean.StatisticsSortBean;

/** 统计业务相关操作，如获取统计数据、删除等 */
public interface StatisticsDao {
	
	/**
	 * 分拣类统计时获取sorting_temp表中的数据
	 * @param sql
	 * @return
	 */
	public List<StatisticsSortBean> findSortList(String sql);
	
	/**
	 * 扫描类统计时获取scan_temp表中的数据
	 * @param sql
	 * @return
	 */
	public List<StatisticsScanBean> findScanList(String sql);
	
	/**
	 * 封包类统计时获取box_temp表中的数据
	 * @param sql
	 */
	public List<StatisticsBoxBean> findBoxList(String sql);
	
	/**
	 * 统计类数据插入到report表中
	 * @param sqls
	 * @return 批量执行的结果
	 */
	public int[] insertReportTable(String[] sqls);
	
	/**
	 * 统计进度插入到report_statistics_pro表中
	 * @param sql
	 * @return true，成功；false，失败
	 */
	public boolean insertStatisticsProgress(String sql);
	
	/**
	 * 删除report_statistics_pro表中已失效的统计进度
	 * @param sql
	 * @return true，成功；false，失败
	 */
	public boolean deleteStatisticsProgress(String sql);
	
	/**
	 * 封包时插入数据到box_temp表中，供封包统计使用
	 * @param sql
	 * @return true，成功；false，失败
	 */
	public boolean insertBoxTemp(String sql);
	
	/**
	 * 根据类型获取统计进度
	 * @param sql 
	 * @return StatisticsProgressBean
	 */
	public StatisticsProgressBean getStatisticsProgressBean(String sql);
	
	/**
	 * 统计时获取表中的数据中可统计的数量
	 * @param sql 
	 * @return 表中可统计的数量
	 */
	public int findStatisticsCount(String sql);
	
	/**
	 * 判断记录是否存在
	 * @param sql
	 * @return
	 */
	public boolean findIfExitsInReportTable(String sql);
	
	/**
	 * 获取分钟量统计的单条记录
	 * @param sql
	 * @return
	 */
	public ReportMinuteBean findReportMinuteBean(String sql);
	
	/**
	 * 获取汇总、批次量统计的单条记录
	 * @param sql
	 * @return
	 */
	public ReportSortingBean findReportSortingBean(String sql);
	
	/**
	 * 获取导入台量统计的单条记录
	 * @param sql
	 * @return
	 */
	public ReportSupplyBean findReportSupplyBean(String sql);
	
	/**
	 * 获取扫描量统计的单条记录
	 * @param sql 
	 * @return
	 */
	public ReportScanBean findReportScanBean(String sql);

	/**
	 * 获取当前分钟之前的连续60分钟峰值
	 * @param sql
	 * @return
	 */
	public int findReportSixSpike(String sql);

	/**
	 * 统计进度更新到report_statistics_pro表中
	 * @param sql
	 * @return true，成功；false，失败
	 */
	public boolean updateStatisticsProgress(String sql);
	
}
