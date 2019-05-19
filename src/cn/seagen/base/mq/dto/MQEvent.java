package cn.seagen.base.mq.dto;

import cn.seagen.base.mq.enums.MQMsgType;

/**
 * MQ事件信息
 * 
 * example{ index: 070101, event_id:8, layer_id:1,event_target:”电机一”,
 * event_val:”电流过大，自保护，停止运行”, addt_attrs:””, create_time:”2016-04-13
 * 10:21:25.4352” }
 */
public class MQEvent extends MQBase {
	private int event_id;// 事件ID
	private String event_target; // 事件目标 是指发生该事件的对象是谁
	private String event_val; // 事件属性 产生事件的值是多少

	public void setIndex_Ctrl() {
		this.index = MQMsgType.m_Event_C.getValue();
	}

	public void setIndex_App() {
		this.index = MQMsgType.m_Event_A.getValue();
	}

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

	public String getEvent_target() {
		return event_target;
	}

	public void setEvent_target(String event_target) {
		this.event_target = event_target;
	}

	public String getEvent_val() {
		return event_val;
	}

	public void setEvent_val(String event_val) {
		this.event_val = event_val;
	}

	@Override
	public String toString() {
		return "MQEvent [event_id=" + event_id + ", event_target="
				+ event_target + ", event_val=" + event_val + ", index="
				+ index + ", message=" + message + ", addt_attrs=" + addt_attrs
				+ ", create_time=" + create_time + ", chute_id=" + chute_id
				+ ", layer_id=" + layer_id + "]";
	}
}
