package cn.seagen.base.bean;

/** 运单信息实体类 */
public class Waybill {
	/** 主键,*/
	private long f_recno = 0;
	/** 批次ID,*/
	private int batch_id = 0;
	/** 快件追踪ID,*/
	private String waybill_id = null;
	/** 快件分拣过程唯一编号,*/
	private String waybill_num = null;
	/** 运单号,*/
	private String waybill_code = null;
	/** 异常代码,*/
	private String exp_code = "0";
	/** 运单表中目的地代码,*/
	private String waybill_site_code = null;
	/** 包裹号,*/
	private String package_code = null;
	/** 运单状态(0正常运单,1运单取消,2运单地址更改),*/
	private int Waybill_status = 0;
	/** 运单生成时间,,重复运单号以生成时间最近的为准*/
	private String waybill_time = null; 
	/** 目的地代码,*/
	private String site_code = null;
	/** 目的地名称,*/
	private String site_name = null;
	/** 分拣状态(0正常分拣,1无分拣方案,2无数据,3无读,4取消,5最大圈数,6格口错,7多条码,8迷失,255未知),*/
	private int sorting_status = 0;
	/** 分拣时间*/
	private String sorting_time = null;
	/** 箱号,*/
	private String box_code = null;
	/** 箱号目的地代码,*/
	private String box_site_code = null;
	/** 箱号目的地名称,*/
	private String box_site_name = null;
	/** 建包标识0未建包1已建包2不可建包(综合格口异常格口),*/
	private int jb_status = 0;
	/** 建包时间*/
	private String jb_time = null;
	/** 建包更新标识0：未更新1：已更新2：不可更新,*/
	private int jb_update_flag = 0;
	/** 建包更新时间*/
	private String jb_update_time = null;
	/** 小车物理编号,*/
	private int car_id = 0;
	/** 滑槽物理编号,*/
	private int chute_id = 0;
	/** 扫描仪物理编号(龙门架),*/
	private int scan_id = 0;
	/** 供件台物理编号,*/
	private int supply_id = 0;
	/** 层级物理编号,*/
	private int layer_id = 0;
	/** 小车逻辑编号,*/
	private String car_num = null;
	/** 滑槽口逻辑编号,*/
	private String chute_num = null;
	/** 扫描仪逻辑编号(龙门架),*/
	private String scan_num = null;
	/** 供件台逻辑编号,*/
	private String supply_num = null;
	/** 层级逻辑编号,*/
	private String layer_num = null;
	/** 供件方式,*/
	private String supply_type = null;
	/** 供件时间*/
	private String supply_time = null;
	/** 扫描状态(0正常扫描,1无分拣方案,2无数据,3无读,4取消,5最大圈数,6格口错,7多条码,8迷失,255未知),*/
	private int scan_status = 0;
	/** 扫描次数,*/
	private int scan_cycle = 0;
	/** 扫描时间*/
	private String scan_time = null;
	/** 重量(KG),*/
	private int package_weight = 0;
	/** 长度(MM),*/
	private int package_length = 0;
	/** 宽度(MM),*/
	private int package_width = 0;
	/** 高度(MM),*/
	private int package_height = 0;
	/** 分拣来源,*/
	private String sort_source = null;
	/** 分拣模式,*/
	private String sort_mode = null;
	/** 附加属性或备注,*/
	private String re_mark = null;
	/** 更新标识0：未更新1：已更新2：不可更新,*/
	private int update_flag = 0;
	/** 更新时间*/
	private String update_time = null;
	/**数据库操作时间*/
	private String db_opt_time = null;
	/**接收mq消息时间*/
	private String receive_time = null;
	/**流水号(德邦)*/
	private String serialno = null;
	/** 分拣时间：String格式*/
	private String sorting_time_date = null;
	/**分表标记，如201803*/
	private int tab_flag = 0;
	public int getTab_flag() {
		return tab_flag;
	}
	public void setTab_flag(int tab_flag) {
		this.tab_flag = tab_flag;
	}
	public String getSerialno() {
		return serialno;
	}
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
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
	public String getWaybill_id() {
		return waybill_id;
	}
	public void setWaybill_id(String waybill_id) {
		this.waybill_id = waybill_id;
	}
	public String getWaybill_num() {
		return waybill_num;
	}
	public void setWaybill_num(String waybill_num) {
		this.waybill_num = waybill_num;
	}
	public String getWaybill_code() {
		return waybill_code;
	}
	public void setWaybill_code(String waybill_code) {
		this.waybill_code = waybill_code;
	}
	public String getExp_code() {
		return exp_code;
	}
	public void setExp_code(String exp_code) {
		this.exp_code = exp_code;
	}
	public String getWaybill_site_code() {
		return waybill_site_code;
	}
	public void setWaybill_site_code(String waybill_site_code) {
		this.waybill_site_code = waybill_site_code;
	}
	public String getPackage_code() {
		return package_code;
	}
	public void setPackage_code(String package_code) {
		this.package_code = package_code;
	}
	public int getWaybill_status() {
		return Waybill_status;
	}
	public void setWaybill_status(int waybill_status) {
		Waybill_status = waybill_status;
	}
	public String getWaybill_time() {
		return waybill_time;
	}
	public void setWaybill_time(String waybill_time) {
		this.waybill_time = waybill_time;
	}
	public String getSite_code() {
		return site_code;
	}
	public void setSite_code(String site_code) {
		this.site_code = site_code;
	}
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public int getSorting_status() {
		return sorting_status;
	}
	public void setSorting_status(int sorting_status) {
		this.sorting_status = sorting_status;
	}
	public String getSorting_time() {
		return sorting_time;
	}
	public void setSorting_time(String sorting_time) {
		this.sorting_time = sorting_time;
	}
	public String getBox_code() {
		return box_code;
	}
	public void setBox_code(String box_code) {
		this.box_code = box_code;
	}
	public String getBox_site_code() {
		return box_site_code;
	}
	public void setBox_site_code(String box_site_code) {
		this.box_site_code = box_site_code;
	}
	public String getBox_site_name() {
		return box_site_name;
	}
	public void setBox_site_name(String box_site_name) {
		this.box_site_name = box_site_name;
	}
	public int getJb_status() {
		return jb_status;
	}
	public void setJb_status(int jb_status) {
		this.jb_status = jb_status;
	}
	public String getJb_time() {
		return jb_time;
	}
	public void setJb_time(String jb_time) {
		this.jb_time = jb_time;
	}
	public int getJb_update_flag() {
		return jb_update_flag;
	}
	public void setJb_update_flag(int jb_update_flag) {
		this.jb_update_flag = jb_update_flag;
	}
	public String getJb_update_time() {
		return jb_update_time;
	}
	public void setJb_update_time(String jb_update_time) {
		this.jb_update_time = jb_update_time;
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
	public String getCar_num() {
		return car_num;
	}
	public void setCar_num(String car_num) {
		this.car_num = car_num;
	}
	public String getChute_num() {
		return chute_num;
	}
	public void setChute_num(String chute_num) {
		this.chute_num = chute_num;
	}
	public String getScan_num() {
		return scan_num;
	}
	public void setScan_num(String scan_num) {
		this.scan_num = scan_num;
	}
	public String getSupply_num() {
		return supply_num;
	}
	public void setSupply_num(String supply_num) {
		this.supply_num = supply_num;
	}
	public String getLayer_num() {
		return layer_num;
	}
	public void setLayer_num(String layer_num) {
		this.layer_num = layer_num;
	}
	public String getSupply_type() {
		return supply_type;
	}
	public void setSupply_type(String supply_type) {
		this.supply_type = supply_type;
	}
	public String getSupply_time() {
		return supply_time;
	}
	public void setSupply_time(String supply_time) {
		this.supply_time = supply_time;
	}
	public int getScan_status() {
		return scan_status;
	}
	public void setScan_status(int scan_status) {
		this.scan_status = scan_status;
	}
	public int getScan_cycle() {
		return scan_cycle;
	}
	public void setScan_cycle(int scan_cycle) {
		this.scan_cycle = scan_cycle;
	}
	public String getScan_time() {
		return scan_time;
	}
	public void setScan_time(String scan_time) {
		this.scan_time = scan_time;
	}
	public int getPackage_weight() {
		return package_weight;
	}
	public void setPackage_weight(int package_weight) {
		this.package_weight = package_weight;
	}
	public int getPackage_length() {
		return package_length;
	}
	public void setPackage_length(int package_length) {
		this.package_length = package_length;
	}
	public int getPackage_width() {
		return package_width;
	}
	public void setPackage_width(int package_width) {
		this.package_width = package_width;
	}
	public int getPackage_height() {
		return package_height;
	}
	public void setPackage_height(int package_height) {
		this.package_height = package_height;
	}
	public String getSort_source() {
		return sort_source;
	}
	public void setSort_source(String sort_source) {
		this.sort_source = sort_source;
	}
	public String getSort_mode() {
		return sort_mode;
	}
	public void setSort_mode(String sort_mode) {
		this.sort_mode = sort_mode;
	}
	public String getRe_mark() {
		return re_mark;
	}
	public void setRe_mark(String re_mark) {
		this.re_mark = re_mark;
	}
	public int getUpdate_flag() {
		return update_flag;
	}
	public void setUpdate_flag(int update_flag) {
		this.update_flag = update_flag;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getDb_opt_time() {
		return db_opt_time;
	}
	public void setDb_opt_time(String db_opt_time) {
		this.db_opt_time = db_opt_time;
	}
	public String getReceive_time() {
		return receive_time;
	}
	public void setReceive_time(String receive_time) {
		this.receive_time = receive_time;
	}
	public String getSorting_time_date() {
		return sorting_time_date;
	}
	public void setSorting_time_date(String sorting_time_date) {
		this.sorting_time_date = sorting_time_date;
	}
	@Override
	public String toString() {
		return "Waybill [f_recno=" + f_recno + ", batch_id=" + batch_id
				+ ", waybill_id=" + waybill_id + ", waybill_num=" + waybill_num
				+ ", waybill_code=" + waybill_code + ", exp_code=" + exp_code
				+ ", waybill_site_code=" + waybill_site_code
				+ ", package_code=" + package_code + ", Waybill_status="
				+ Waybill_status + ", waybill_time=" + waybill_time
				+ ", site_code=" + site_code + ", site_name=" + site_name
				+ ", sorting_status=" + sorting_status + ", sorting_time="
				+ sorting_time + ", box_code=" + box_code + ", box_site_code="
				+ box_site_code + ", box_site_name=" + box_site_name
				+ ", jb_status=" + jb_status + ", jb_time=" + jb_time
				+ ", jb_update_flag=" + jb_update_flag + ", jb_update_time="
				+ jb_update_time + ", car_id=" + car_id + ", chute_id="
				+ chute_id + ", scan_id=" + scan_id + ", supply_id="
				+ supply_id + ", layer_id=" + layer_id + ", car_num=" + car_num
				+ ", chute_num=" + chute_num + ", scan_num=" + scan_num
				+ ", supply_num=" + supply_num + ", layer_num=" + layer_num
				+ ", supply_type=" + supply_type + ", supply_time="
				+ supply_time + ", scan_status=" + scan_status
				+ ", scan_cycle=" + scan_cycle + ", scan_time=" + scan_time
				+ ", package_weight=" + package_weight + ", package_length="
				+ package_length + ", package_width=" + package_width
				+ ", package_height=" + package_height + ", sort_source="
				+ sort_source + ", sort_mode=" + sort_mode + ", re_mark="
				+ re_mark + ", update_flag=" + update_flag + ", update_time="
				+ update_time + ", db_opt_time=" + db_opt_time
				+ ", receive_time=" + receive_time + ", serialno=" + serialno
				+ ", sorting_time_date=" + sorting_time_date + ", tab_flag="
				+ tab_flag + "]";
	}
	
}
