package cn.seagen.base.bean;
/**
 *导入台量统计实体类
 */
public class ReportSupplyBean extends ReportBaseBean {
	/**主键*/
	private long f_recno;
	/**记录日期*/
	private String report_date;
	/**层级id*/
	private int layer_id;
	/**导入台id*/
	private int supply_id;
	/**总的分拣数*/
	private int all_sum;
	/**连续60分钟峰值*/
	private int six_spike;
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
	public int getSupply_id() {
		return supply_id;
	}
	public void setSupply_id(int supply_id) {
		this.supply_id = supply_id;
	}
	public int getAll_sum() {
		return all_sum;
	}
	public void setAll_sum(int all_sum) {
		this.all_sum = all_sum;
	}
	public int getSix_spike() {
		return six_spike;
	}
	public void setSix_spike(int six_spike) {
		this.six_spike = six_spike;
	}
	@Override
	public String toString() {
		return "ReportSupplyBean [f_recno=" + f_recno + ", report_date="
				+ report_date + ", layer_id=" + layer_id + ", supply_id="
				+ supply_id + ", all_sum=" + all_sum + ", six_spike="
				+ six_spike + ", toString()=" + super.toString() + "]";
	}
}
