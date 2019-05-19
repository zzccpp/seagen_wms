package cn.seagen.base.mq.dto;

import cn.seagen.base.mq.enums.MQMsgType;

/**
 * 线体上分拣数据
 * 
 * example { index: 080101, sorting_id:"4e31e076843c4c0ca2ab845ee3e04ca6",layer_id:1,layer_num:"1",
 * package_code:"10706952729-1-1-3", weight:7000,site_code:”12580”, chute_id:32,
 * chute_num:”32”, car_id:8, car_num:”8”, supply_id:1, supply_num:”1”,
 * scan_id:1, scan_num:”1”, cycle:2, statue:0, sort_source:”暴力分拣”, change:false,
 * addt_attrs:””, create_time:”2016-04-13 10:21:25.4352”
 */
public class MQSort extends MQBase {
	private String sorting_id; // 分拣编号:一个包裹在线体上分拣时的唯一编号
	private String package_code = ""; // 包裹号
	private int weight; // 包裹重量
	private String site_code;// 目的地代码
	private int car_id;// 小车物理编号
	private int supply_id;// 导入台物理编号
	private int scan_id;// 扫描仪物理编号
	private String chute_num;// 格口逻辑编号
	private String car_num;// 小车逻辑编号
	private String supply_num;// 导入台逻辑编号
	private String scan_num;// 扫描仪号逻辑编号
	private int cycle;// 运行圈数
	private int statue;// 扫描(分拣)状态
	private String sort_source;// 分拣来源
	private boolean change; // 状态改变,默认为false，如果此次扫描与上次不一致,则为true
	private String supply_time; // 导入台导入时间
	private String scan_time; // 龙门架扫描时间
	private String sorting_time; // 快件分拣下格口时间
	private String layer_num;//层级逻辑编号

	public String getLayer_num() {
		return layer_num;
	}

	public void setLayer_num(String layer_num) {
		this.layer_num = layer_num;
	}

	public void setSanIndex_Ctrl() {
		this.index = MQMsgType.m_Scan_C.getValue();
	}

	public void setSanIndex_App() {
		this.index = MQMsgType.m_Scan_A.getValue();
	}

	public void setSortingIndex_Ctrl() {
		this.index = MQMsgType.m_Sorting_C.getValue();
	}

	public void setSortingIndex_App() {
		this.index = MQMsgType.m_Sorting_A.getValue();
	}

	public void setSortedIndex_Ctrl() {
		this.index = MQMsgType.m_Sorted_C.getValue();
	}

	public void setSortedIndex_App() {
		this.index = MQMsgType.m_Sorted_A.getValue();
	}

	public String getSorting_id() {
		return sorting_id;
	}

	public void setSorting_id(String sorting_id) {
		this.sorting_id = sorting_id;
	}

	public String getPackage_code() {
		return package_code;
	}

	public void setPackage_code(String package_code) {
		this.package_code = package_code;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getCar_id() {
		return car_id;
	}

	public void setCar_id(int car_id) {
		this.car_id = car_id;
	}

	public int getSupply_id() {
		return supply_id;
	}

	public void setSupply_id(int supply_id) {
		this.supply_id = supply_id;
	}

	public int getScan_id() {
		return scan_id;
	}

	public void setScan_id(int scan_id) {
		this.scan_id = scan_id;
	}

	public String getChute_num() {
		return chute_num;
	}

	public void setChute_num(String chute_num) {
		this.chute_num = chute_num;
	}

	public String getCar_num() {
		return car_num;
	}

	public void setCar_num(String car_num) {
		this.car_num = car_num;
	}

	public String getSupply_num() {
		return supply_num;
	}

	public void setSupply_num(String supply_num) {
		this.supply_num = supply_num;
	}

	public String getScan_num() {
		return scan_num;
	}

	public void setScan_num(String scan_num) {
		this.scan_num = scan_num;
	}

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	public String getSite_code() {
		return site_code;
	}

	public void setSite_code(String site_code) {
		this.site_code = site_code;
	}

	public String getSort_source() {
		return sort_source;
	}

	public void setSort_source(String sort_source) {
		this.sort_source = sort_source;
	}

	public int getStatue() {
		return statue;
	}

	public void setStatue(int statue) {
		this.statue = statue;
	}

	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}

	public String getSupply_time() {
		return supply_time;
	}

	public void setSupply_time(String supply_time) {
		this.supply_time = supply_time;
	}

	public String getScan_time() {
		return scan_time;
	}

	public void setScan_time(String scan_time) {
		this.scan_time = scan_time;
	}

	public String getSorting_time() {
		return sorting_time;
	}

	public void setSorting_time(String sorting_time) {
		this.sorting_time = sorting_time;
	}

	@Override
	public String toString() {
		return "MQSort [sorting_id=" + sorting_id + ", package_code="
				+ package_code + ", weight=" + weight + ", site_code="
				+ site_code + ", car_id=" + car_id + ", supply_id=" + supply_id
				+ ", scan_id=" + scan_id + ", chute_num=" + chute_num
				+ ", car_num=" + car_num + ", supply_num=" + supply_num
				+ ", scan_num=" + scan_num + ", cycle=" + cycle + ", statue="
				+ statue + ", sort_source=" + sort_source + ", change="
				+ change + ", supply_time=" + supply_time + ", scan_time="
				+ scan_time + ", sorting_time=" + sorting_time + ", layer_num="
				+ layer_num + ", index=" + index + ", message=" + message
				+ ", addt_attrs=" + addt_attrs + ", create_time=" + create_time
				+ ", chute_id=" + chute_id + ", layer=" + layer_id + "]";
	}
}
