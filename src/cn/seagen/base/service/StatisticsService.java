package cn.seagen.base.service;

import java.util.List;

import cn.seagen.base.bean.ReportMinuteBean;
import cn.seagen.base.bean.ReportScanBean;
import cn.seagen.base.bean.ReportSortingBean;
import cn.seagen.base.bean.ReportSupplyBean;
import cn.seagen.base.bean.StatisticsBoxBean;
import cn.seagen.base.bean.StatisticsProgressBean;
import cn.seagen.base.bean.StatisticsScanBean;
import cn.seagen.base.bean.StatisticsSortBean;


public interface StatisticsService {
	
	/**
	 * 分拣类统计时获取sorting_temp表中的数据
	 * @param f_recno sorting_temp表中主键
	 * @param counts 每次统计的数量控制
	 * @param statistics_table 统计查询的表名
	 * @return sorting_temp列表
	 */
	public List<StatisticsSortBean> findSortList(Long f_recno,int counts,String statistics_table);
	
	/**
	 * 扫描类统计时获取scan_temp表中的数据
	 * @param f_recno scan_temp表中主键
	 * @param counts 每次统计的数量控制
	 * @param statistics_table 统计查询的表名
	 * @return scan_temp列表
	 */
	public List<StatisticsScanBean> findScanList(Long f_recno,int counts,String statistics_table);

	/**
	 * 封包类统计时获取box_temp表中的数据
	 * @param f_recno box_temp表中主键
	 * @param counts 每次统计的数量控制
	 * @return box_temp列表
	 */
	public List<StatisticsBoxBean> findBoxList(Long f_recno,int counts);
	
	/**
	 * 统计类数据插入到report表中
	 * @param sqls
	 * @return 批量执行的结果
	 */
	public int[] insertReportTable(List<String> sqls);
	
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
	 * @param type 0：小车量；1，格口；2，分钟；3，扫描；4，站点；5，汇总；6，导入台；7，格口封包；8，汇总封包；9，批次；10，批次封包
	 * @return StatisticsProgressBean
	 */
	public StatisticsProgressBean getStatisticsProgressBean(int type);
	
	
	/**
	 * 统计时获取表中的数据中可统计的数量
	 * @param f_recno 表中主键
	 * @param type 0：小车量；1，格口；2，分钟；3，扫描；4，站点；5，汇总；
	 * 6，导入台；7，格口封包；8，汇总封包；9，批次统计；10，批次封包；
	 * @return 表中可统计的数量
	 */
	public int findStatisticsCount(Long f_recno,int type);
	
	/**
	 * 判断是否存在统计记录
	 * 若记录不存在，则执行新增的sql语句，否则，将获取已存在的记录，并进行更新操作
	 * 批次量统计时，判断批次不存在，则不进行下一步操作
	 * 其他操作时，判断记录不存在，则需要新增记录（不需要第一时间新增，本次统计过程中，会统计若干条记录的统计数据，每条记录都是唯一的，故不会导致数据紊乱）
	 * 	存在时，则进行更新操作
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
	 * 获取分拣量统计的单条记录
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
	 * 根据类型获取统计进度列表
	 * @param type 0：小车量；1，格口；2，分钟；3，扫描；4，站点；5，汇总；6，导入台；7，格口封包；8，汇总封包；9，批次；10，批次封包
	 * @param statistics_table 统计表名
	 * @return StatisticsProgressBean
	 */
	public StatisticsProgressBean getStatisticsProgressBean(int type,String statistics_table);
	
	/**
	 * 统计进度更新到report_statistics_pro表中
	 * @param sql
	 * @return true，成功；false，失败
	 */
	public boolean updateStatisticsProgress(String sql);
}
