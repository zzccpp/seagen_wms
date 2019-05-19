package cn.seagen.base.bean;
/**
 *封包类统计实体类
 */
public class ReportBoxBean {
	/**主键*/
	private long f_recno;
	/**记录日期*/
	private String report_date;
	/**统计名称*/
	private String sum_name;
	/**统计方式：0为批次；1为小时*/
	private int sum_type;
	/**打包(建包)数*/
	private int box_sum;
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
	public String getSum_name() {
		return sum_name;
	}
	public void setSum_name(String sum_name) {
		this.sum_name = sum_name;
	}
	public int getSum_type() {
		return sum_type;
	}
	public void setSum_type(int sum_type) {
		this.sum_type = sum_type;
	}
	public int getBox_sum() {
		return box_sum;
	}
	public void setBox_sum(int box_sum) {
		this.box_sum = box_sum;
	}
	@Override
	public String toString() {
		return "ReportBoxBean [f_recno=" + f_recno + ", report_date="
				+ report_date + ", sum_name=" + sum_name + ", sum_type="
				+ sum_type + ", box_sum=" + box_sum + "]";
	}
}
