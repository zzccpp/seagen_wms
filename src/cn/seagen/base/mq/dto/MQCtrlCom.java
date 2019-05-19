package cn.seagen.base.mq.dto;

import cn.seagen.base.mq.enums.MQMsgType;

/**
 * MQ开关特性的设备控制
 * 
 * example{	index: 60204,	layer_id:1,ctrl_id:2, 	ctrl_data:”11110101”, 	addt_attrs:””,
 * create_time:”2016-04-13 10:21:25.452” }
 */
public class MQCtrlCom extends MQBase{
	private int ctrl_id;// 设备ID
	private String ctrl_data; // 命令数据

	public void setIndex_Ctrl() {
		this.index = MQMsgType.m_CtrCom_C.getValue();
	}

	public void setIndex_App() {
		this.index = MQMsgType.m_CtrCom_A.getValue();
	}

	public int getCtrl_id() {
		return ctrl_id;
	}

	public void setCtrl_id(int ctrl_id) {
		this.ctrl_id = ctrl_id;
	}

	public String getCtrl_data() {
		return ctrl_data;
	}

	public void setCtrl_data(String ctrl_data) {
		this.ctrl_data = ctrl_data;
	}

	@Override
	public String toString() {
		return "MQCtrlCom [ctrl_id=" + ctrl_id + ", ctrl_data=" + ctrl_data
				+ ", index=" + index + ", message=" + message + ", addt_attrs="
				+ addt_attrs + ", create_time=" + create_time + ", chute_id="
				+ chute_id + ", layer_id=" + layer_id + "]";
	}
}
