package cn.seagen.base.mq.dto;

import cn.seagen.base.mq.enums.MQMsgType;

/**
 * 线体分拣模式
 * 
 * example {index: 60102,layer_id:1, sort_mode:”sorting”,addt_attrs:””,
 * create_time:”2016-04-13 10:21:25.452” }
 */
public class MQSortMode extends MQBase {
	// 分拣模式
	private String sort_mode = "";

	public void setIndex_Ctrl() {
		this.index = MQMsgType.m_SortMode_C.getValue();
	}

	public void setIndex_App() {
		this.index = MQMsgType.m_SortMode_A.getValue();
	}

	public String getSort_mode() {
		return sort_mode;
	}

	public void setSort_mode(String sort_mode) {
		this.sort_mode = sort_mode;
	}

	@Override
	public String toString() {
		return "MQSortMode [sort_mode=" + sort_mode + ", index=" + index
				+ ", message=" + message + ", addt_attrs=" + addt_attrs
				+ ", create_time=" + create_time + ", chute_id=" + chute_id
				+ ", layer=" + layer_id + "]";
	}
}
