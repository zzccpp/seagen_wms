package cn.seagen.base.mq.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * MQ消息基类example { index: 080104, addt_attrs:””,layer_id:1, create_time:”2016-04-13 10:21:25.4352”,chute_id:12}
 * 
 */
public class MQBase {
	protected int index; // 消息id
	@JSONField(serialize = false)//不序列化
	protected String message; // 消息原文
	protected String addt_attrs; // 附加属性值
	protected String create_time; // 消息时间戳
	protected int chute_id;// 格口物理编号
	protected int layer_id;//层级 默认1

	
	public int getLayer_id() {
		return layer_id;
	}

	public void setLayer_id(int layer_id) {
		this.layer_id = layer_id;
	}

	public String getAddt_attrs() {
		return addt_attrs;
	}

	public void setAddt_attrs(String addt_attrs) {
		this.addt_attrs = addt_attrs;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getChute_id() {
		return chute_id;
	}

	public void setChute_id(int chute_id) {
		this.chute_id = chute_id;
	}

	@Override
	public String toString() {
		return "MQBase [index=" + index + ", message=" + message
				+ ", addt_attrs=" + addt_attrs + ", create_time=" + create_time
				+ ", chute_id=" + chute_id + ", layer_id=" + layer_id + "]";
	}
}
