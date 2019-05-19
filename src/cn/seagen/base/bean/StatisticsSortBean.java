package cn.seagen.base.bean;


/**
 * 分拣类统计实体类
 */
public class StatisticsSortBean {
	/** 主键 */
	private long f_recno;
	/** 批次id */
	private int batch_id;
	/** 分拣时间 */
	private String sorting_time;
	/** 分拣时间:int型 */
	private int sorting_time_long;
	/** 小车id */
	private int car_id;
	/** 格口id */
	private int chute_id;
	/** 龙门架id */
	private int scan_id;
	/** 导入台id */
	private int supply_id;
	/** 层级id */
	private int layer_id;
	/** 分拣状态:0正常分拣,1无分拣方案,2无数据,3无读,4取消,5最大圈数,6格口错,7多条码,8,迷失,255未知 */
	private int sorting_status;
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
	public String getSorting_time() {
		return sorting_time;
	}
	public void setSorting_time(String sorting_time) {
		this.sorting_time = sorting_time;
	}
	public int getSorting_time_long() {
		return sorting_time_long;
	}
	public void setSorting_time_long(int sorting_time_long) {
		this.sorting_time_long = sorting_time_long;
	}
	public int getCar_id() {
		return car_id;
	}
	public void setCar_id(int car_id) {
		this.car_id = car_id;
	}
	public int getChute_id() {
		return chute_id;
	}
	public void setChute_id(int chute_id) {
		this.chute_id = chute_id;
	}
	public int getScan_id() {
		return scan_id;
	}
	public void setScan_id(int scan_id) {
		this.scan_id = scan_id;
	}
	public int getSupply_id() {
		return supply_id;
	}
	public void setSupply_id(int supply_id) {
		this.supply_id = supply_id;
	}
	public int getLayer_id() {
		return layer_id;
	}
	public void setLayer_id(int layer_id) {
		this.layer_id = layer_id;
	}
	public int getSorting_status() {
		return sorting_status;
	}
	public void setSorting_status(int sorting_status) {
		this.sorting_status = sorting_status;
	}
	@Override
	public String toString() {
		return "StatisticsSortBean [f_recno=" + f_recno + ", batch_id="
				+ batch_id + ", sorting_time=" + sorting_time
				+ ", sorting_time_long=" + sorting_time_long + ", car_id="
				+ car_id + ", chute_id=" + chute_id + ", scan_id=" + scan_id
				+ ", supply_id=" + supply_id + ", layer_id=" + layer_id
				+ ", sorting_status=" + sorting_status + "]";
	}
}
