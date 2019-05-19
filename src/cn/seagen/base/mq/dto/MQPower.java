package cn.seagen.base.mq.dto;
import cn.seagen.base.mq.enums.MQMsgType;

/**
 * 线体开关机结构体
 * 
 * example {index:60101, layer_id:1,turn_on:1, reset:0, sort_mode:”sorting”,
 * addt_attrs:””,	create_time:”2016-04-13 10:21:25.452” }
 */
public class MQPower extends MQBase{
	// 开关机
	private boolean turn_on = false;
	// 是否计数清零，只对开机有用，开机计数清零，表示重新记录批次
	private boolean reset = false;
	// 分拣模式
	private String sort_mode = "";

	public void setIndex_Ctrl() {
		this.index = MQMsgType.m_Power_C.getValue();
	}

	public void setIndex_App() {
		this.index = MQMsgType.m_Power_A.getValue();
	}

	public boolean isTurn_on() {
		return turn_on;
	}

	public void setTurn_on(boolean turn_on) {
		this.turn_on = turn_on;
	}

	public boolean isReset() {
		return reset;
	}

	public void setReset(boolean reset) {
		this.reset = reset;
	}

	public String getSort_mode() {
		return sort_mode;
	}

	public void setSort_mode(String sort_mode) {
		this.sort_mode = sort_mode;
	}

	@Override
	public String toString() {
		return "MQPower [turn_on=" + turn_on + ", reset=" + reset
				+ ", sort_mode=" + sort_mode + ", index=" + index
				+ ", message=" + message + ", addt_attrs=" + addt_attrs
				+ ", create_time=" + create_time + ", chute_id=" + chute_id
				+ ", layer=" + layer_id + "]";
	}
}
