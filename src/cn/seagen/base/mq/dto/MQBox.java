package cn.seagen.base.mq.dto;

import java.util.List;

import cn.seagen.base.mq.enums.MQMsgType;
/**
 * 
 * 返回箱号
 * 
 * example { index:080205, style:6, printer_ip:"192.168.0.101", chute_id:8,
 * chute_num:”1008”, package_count:20, weight:7000,box_code:"BOX102943232",
 * box_type:"正普", mixing_box_type:"混", from_site_name:"天津",
 * from_site_code:"1025", to_site_name:"锦州站", to_site_code:"5624",
 * cate_gory:"公", cate_rounter:["沈阳沈北","沈阳亚一","北京双树","广州博展","佛山分拨"],
 * cate_rounter_num:"7-KN01", addt_attrs:””, create_time:”2016-04-13
 * 10:21:25.4352” }
 */
public class MQBox extends MQBase {

	private int style;// 打印样式
	private String printer_ip;// 打印机IP IPV4
	private String chute_num;// 格口逻辑编号
	private int package_count; // 快件数量
	private int weight; // 包裹总重量
	private String box_code; // 箱号条码
	private String box_type;// 箱号类型
	private String mixing_box_type;// 是否混箱
	private String from_site_name;// 出发站点名称
	private String from_site_code;// 出发站点代号
	private String to_site_name;// 接收站点名称
	private String to_site_code;// 接收站点代号
	private String cate_gory;// 运输方式
	private List<String> cate_rounter;// 运输路由 多个字符串，数组
	private String cate_rounter_num;// 运输路由编号

	public void setIndex_Ctrl() {
		this.index = MQMsgType.m_Box_C.getValue();
	}

	public void setIndex_App() {
		this.index = MQMsgType.m_Box_A.getValue();
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public String getPrinter_ip() {
		return printer_ip;
	}

	public void setPrinter_ip(String printer_ip) {
		this.printer_ip = printer_ip;
	}

	public String getChute_num() {
		return chute_num;
	}

	public void setChute_num(String chute_num) {
		this.chute_num = chute_num;
	}

	public int getPackage_count() {
		return package_count;
	}

	public void setPackage_count(int package_count) {
		this.package_count = package_count;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getBox_code() {
		return box_code;
	}

	public void setBox_code(String box_code) {
		this.box_code = box_code;
	}

	public String getBox_type() {
		return box_type;
	}

	public void setBox_type(String box_type) {
		this.box_type = box_type;
	}

	public String getMixing_box_type() {
		return mixing_box_type;
	}

	public void setMixing_box_type(String mixing_box_type) {
		this.mixing_box_type = mixing_box_type;
	}

	public String getFrom_site_name() {
		return from_site_name;
	}

	public void setFrom_site_name(String from_site_name) {
		this.from_site_name = from_site_name;
	}

	public String getFrom_site_code() {
		return from_site_code;
	}

	public void setFrom_site_code(String from_site_code) {
		this.from_site_code = from_site_code;
	}

	public String getTo_site_name() {
		return to_site_name;
	}

	public void setTo_site_name(String to_site_name) {
		this.to_site_name = to_site_name;
	}

	public String getTo_site_code() {
		return to_site_code;
	}

	public void setTo_site_code(String to_site_code) {
		this.to_site_code = to_site_code;
	}

	public String getCate_gory() {
		return cate_gory;
	}

	public void setCate_gory(String cate_gory) {
		this.cate_gory = cate_gory;
	}

	public List<String> getCate_rounter() {
		return cate_rounter;
	}

	public void setCate_rounter(List<String> cate_rounter) {
		this.cate_rounter = cate_rounter;
	}

	public String getCate_rounter_num() {
		return cate_rounter_num;
	}

	public void setCate_rounter_num(String cate_rounter_num) {
		this.cate_rounter_num = cate_rounter_num;
	}

	@Override
	public String toString() {
		return "MQBox [style=" + style + ", printer_ip=" + printer_ip
				+ ", chute_num=" + chute_num + ", package_count="
				+ package_count + ", weight=" + weight + ", box_code="
				+ box_code + ", box_type=" + box_type + ", mixing_box_type="
				+ mixing_box_type + ", from_site_name=" + from_site_name
				+ ", from_site_code=" + from_site_code + ", to_site_name="
				+ to_site_name + ", to_site_code=" + to_site_code
				+ ", cate_gory=" + cate_gory + ", cate_rounter=" + cate_rounter
				+ ", cate_rounter_num=" + cate_rounter_num + ", index=" + index
				+ ", message=" + message + ", addt_attrs=" + addt_attrs
				+ ", create_time=" + create_time + ", chute_id=" + chute_id
				+ ", layer_id=" + layer_id + "]";
	}
}
