package cn.seagen.base.bean;
/**
 * 扫描量统计实体类
 */
public class ReportScanBean extends ReportBaseBean {
	/**主键*/
	private long f_recno;
	/**记录日期*/
	private String report_date;
	/**龙门架id*/
	private int scan_id;
	/**层级id */
	private int layer_id;
	/**扫描 总量*/
	private int all_sum;
	public long getF_recno() {
		return f_recno;
	}
	public void setF_recno(long f_recno) {
		this.f_recno = f_recno;
	}
	public String getReport_date() {
		return report_date;
	}
	public void setReport_date(String report_date) {
		this.report_date = report_date;
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
	public int getAll_sum() {
		return all_sum;
	}
	public void setAll_sum(int all_sum) {
		this.all_sum = all_sum;
	}
	@Override
	public String toString() {
		return "ReportScanBean [f_recno=" + f_recno + ", report_date="
				+ report_date + ", scan_id=" + scan_id + ", layer_id="
				+ layer_id + ", all_sum=" + all_sum + "]";
	}
}
