package cn.seagen.base.bean;
/**
 *分钟量统计实体类
 */
public class ReportMinuteBean extends ReportBaseBean {
	/**主键*/
	private long f_recno;
	/**记录日期*/
	private String report_date;
	/**层级id*/
	private int layer_id;
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
	public int getLayer_id() {
		return layer_id;
	}
	public void setLayer_id(int layer_id) {
		this.layer_id = layer_id;
	}
	@Override
	public String toString() {
		return "ReportMinuteBean [f_recno=" + f_recno + ", report_date="
				+ report_date + ", layer_id=" + layer_id + ", toString()="
				+ super.toString() + "]";
	}
	
}
