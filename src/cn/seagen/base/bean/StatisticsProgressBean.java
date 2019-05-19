package cn.seagen.base.bean;


/**
 * 统计进度实体类
 */
public class StatisticsProgressBean {
	/** 主键 */
	private long f_recno;
	/** 统计类型：0：小车量；1，格口；2，分钟；3，扫描；4，站点；5，汇总；6，导入台；7，格口封包；8，汇总封包；9，批次；10，批次封包*/
	private int st_type;
	/** 统计名称*/
	private String statistics_name;
	/** 统计表名*/
	private String statistics_table;
	/** 当前统计进度*/
	private long current_progress;
	public long getF_recno() {
		return f_recno;
	}
	public void setF_recno(long f_recno) {
		this.f_recno = f_recno;
	}
	public int getSt_type() {
		return st_type;
	}
	public void setSt_type(int st_type) {
		this.st_type = st_type;
	}
	public String getStatistics_name() {
		return statistics_name;
	}
	public void setStatistics_name(String statistics_name) {
		this.statistics_name = statistics_name;
	}
	public String getStatistics_table() {
		return statistics_table;
	}
	public void setStatistics_table(String statistics_table) {
		this.statistics_table = statistics_table;
	}
	public long getCurrent_progress() {
		return current_progress;
	}
	public void setCurrent_progress(long current_progress) {
		this.current_progress = current_progress;
	}
	@Override
	public String toString() {
		return "StatisticsProgressBean [f_recno=" + f_recno + ", st_type="
				+ st_type + ", statistics_name=" + statistics_name
				+ ", statistics_table=" + statistics_table
				+ ", current_progress=" + current_progress + "]";
	}
	
}
