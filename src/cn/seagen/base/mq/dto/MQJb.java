package cn.seagen.base.mq.dto;


/**
 * 请求建包或箱号信息
 * 
 * example { index: 080104, type:1,layer_id:1,chute_id:8, chute_num:”1008”, box_code:1,
 * addt_attrs:””, create_time:”2016-04-13 10:21:25.4352” }
 */
public class MQJb extends MQBase {

	// 请求类型---------------------------------------------
	// 0正确从格口请求,后台数据处理分拣业务
	// 1格口重新请求,后台查找格口最后一个建包的箱号信息，供打印
	// 2箱号请求,后台查找完整箱号信息，返回供打印
	private int type;// 请求类型
	private String chute_num;// 格口逻辑编号
	private String box_code;// 箱号

	public void setIndex_Ctrl() {
		this.index = 1;
	}

	public void setIndex_App() {
		this.index = 2;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getChute_num() {
		return chute_num;
	}

	public void setChute_num(String chute_num) {
		this.chute_num = chute_num;
	}

	public String getBox_code() {
		return box_code;
	}

	public void setBox_code(String box_code) {
		this.box_code = box_code;
	}

	@Override
	public String toString() {
		return "MQJb [type=" + type + ", chute_num=" + chute_num
				+ ", box_code=" + box_code + ", index=" + index + ", message="
				+ message + ", addt_attrs=" + addt_attrs + ", create_time="
				+ create_time + ", chute_id=" + chute_id + ", layer_id="
				+ layer_id + "]";
	}
}