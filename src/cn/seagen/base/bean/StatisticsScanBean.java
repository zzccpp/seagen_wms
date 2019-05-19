package cn.seagen.base.bean;


/**
 * 扫描量统计实体类
 */
public class StatisticsScanBean {
	/** 主键 */
	private long f_recno;
	/** 扫描时间 */
	private String scan_time;
	/** 扫描时间 int*/
	private int scan_time_long;
	/** 扫描仪id */
	private int scan_id;
	/** 层级id */
	private int layer_id;
	/** 扫描状态:0正常扫描,1无分拣方案,2无数据,3无读,4取消,5最大圈数,6格口错,7多条码,8,迷失,255未知 */
	private int scan_status;
	public long getF_recno() {
		return f_recno;
	}
	public void setF_recno(long f_recno) {
		this.f_recno = f_recno;
	}
	public String getScan_time() {
		return scan_time;
	}
	public void setScan_time(String scan_time) {
		this.scan_time = scan_time;
	}
	public int getScan_time_long() {
		return scan_time_long;
	}
	public void setScan_time_long(int scan_time_long) {
		this.scan_time_long = scan_time_long;
	}
	public int getScan_id() {
		return scan_id;
	}
	public void setScan_id(int scan_id) {
		this.scan_id = scan_id;
	}
	public int getLayer_id() {
		return layer_id;
	}
	public void setLayer_id(int layer_id) {
		this.layer_id = layer_id;
	}
	public int getScan_status() {
		return scan_status;
	}
	public void setScan_status(int scan_status) {
		this.scan_status = scan_status;
	}
	@Override
	public String toString() {
		return "StatisticsScanBean [f_recno=" + f_recno + ", scan_time="
				+ scan_time + ", scan_time_long=" + scan_time_long
				+ ", scan_id=" + scan_id + ", layer_id=" + layer_id
				+ ", scan_status=" + scan_status + "]";
	}
}
