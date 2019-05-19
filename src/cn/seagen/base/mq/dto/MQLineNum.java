package cn.seagen.base.mq.dto;

import cn.seagen.base.mq.enums.MQMsgType;

/**
 * 线体编号设置
 * 
 * example {index: 60203,	layer_id:1, pipe_line:”2000-001”, 	addt_attrs:””,
 * create_time:”2016-04-13 10:21:25.452” }
 */
public class MQLineNum extends MQBase{
	// 线体编号
	private String pipe_line = "";

	public void setIndex_Ctrl() {
		this.index = MQMsgType.m_LineNum_C.getValue();
	}

	public void setIndex_App() {
		this.index = MQMsgType.m_LineNum_A.getValue();
	}

	public String getPipe_line() {
		return pipe_line;
	}

	public void setPipe_line(String pipe_line) {
		this.pipe_line = pipe_line;
	}

	@Override
	public String toString() {
		return "MQLineNum [pipe_line=" + pipe_line + ", index=" + index
				+ ", message=" + message + ", addt_attrs=" + addt_attrs
				+ ", create_time=" + create_time + ", chute_id=" + chute_id
				+ ", layer_id=" + layer_id + "]";
	}

	
}
