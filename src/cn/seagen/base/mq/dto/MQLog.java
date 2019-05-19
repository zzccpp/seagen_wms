package cn.seagen.base.mq.dto;

import cn.seagen.base.mq.enums.MQMsgType;

/**
 * MQ日志消息
 * 
 * example { index: 070102, log_id:8, log_target:”张三”, log_val:”登陆了后台管理软件”,
 * addt_attrs:””, create_time:”2016-04-13 10:21:25.4352” }
 *
 */
public class MQLog extends MQBase {
	private int log_id; // 日志ID 唯一编号
	private String log_target; // 日志目标 是指发生该日志的对象是谁(可能是某个操作员)
	private String log_val; // 日志属性 产生日志的值是多少

	public void setIndex_Ctrl() {
		this.index = MQMsgType.m_Log_C.getValue();
	}

	public void setIndex_App() {
		this.index = MQMsgType.m_Log_A.getValue();
	}

	public int getLog_id() {
		return log_id;
	}

	public void setLog_id(int log_id) {
		this.log_id = log_id;
	}

	public String getLog_target() {
		return log_target;
	}

	public void setLog_target(String log_target) {
		this.log_target = log_target;
	}

	public String getLog_val() {
		return log_val;
	}

	public void setLog_val(String log_val) {
		this.log_val = log_val;
	}

	@Override
	public String toString() {
		return "MQLog [log_id=" + log_id + ", log_target=" + log_target
				+ ", log_val=" + log_val + ", index=" + index + ", message="
				+ message + ", addt_attrs=" + addt_attrs + ", create_time="
				+ create_time + ", chute_id=" + chute_id + ", layer_id="
				+ layer_id + "]";
	}
}
