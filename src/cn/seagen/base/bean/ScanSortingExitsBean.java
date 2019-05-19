package cn.seagen.base.bean;


/**
 * 扫描、分拣分表存在记录表实体类
 */
public class ScanSortingExitsBean {
	/** 封包时间 */
	private long f_recno;
	/** 表名 */
	private String table_name;
	/** 月份 */
	private String month;
	/** 表类型：scan、sorting */
	private int table_type;
	public long getF_recno() {
		return f_recno;
	}
	public void setF_recno(long f_recno) {
		this.f_recno = f_recno;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getTable_type() {
		return table_type;
	}
	public void setTable_type(int table_type) {
		this.table_type = table_type;
	}
	@Override
	public String toString() {
		return "ScanSortingExitsBean [f_recno=" + f_recno + ", table_name="
				+ table_name + ", month=" + month + ", table_type="
				+ table_type + "]";
	}
}
