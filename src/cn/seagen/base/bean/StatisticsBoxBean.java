package cn.seagen.base.bean;


/**
 * 封包类统计实体类
 */
public class StatisticsBoxBean {
	/** 主键 */
	private long f_recno;
	/** 批次id */
	private int batch_id;
	/** 格口id */
	private int chute_id;
	/** 封包时间 */
	private String print_time;
	/** 封包时间int */
	private int print_time_long;
	public long getF_recno() {
		return f_recno;
	}
	public void setF_recno(long f_recno) {
		this.f_recno = f_recno;
	}
	public int getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(int batch_id) {
		this.batch_id = batch_id;
	}
	public int getChute_id() {
		return chute_id;
	}
	public void setChute_id(int chute_id) {
		this.chute_id = chute_id;
	}
	public String getPrint_time() {
		return print_time;
	}
	public void setPrint_time(String print_time) {
		this.print_time = print_time;
	}
	public int getPrint_time_long() {
		return print_time_long;
	}
	public void setPrint_time_long(int print_time_long) {
		this.print_time_long = print_time_long;
	}
	@Override
	public String toString() {
		return "StatisticsBoxBean [f_recno=" + f_recno + ", batch_id="
				+ batch_id + ", chute_id=" + chute_id + ", print_time="
				+ print_time + ", print_time_long=" + print_time_long + "]";
	}
}
