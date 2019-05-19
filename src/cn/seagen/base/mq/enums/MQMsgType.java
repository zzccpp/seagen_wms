package cn.seagen.base.mq.enums;

/** MQ消息类型 */
public enum MQMsgType {
	m_Power_C(60101, "开关机C"), //
	m_Power_A(60201, "开关机A"), //
	m_SortMode_C(60102, "设置分拣模式C"), //
	m_SortMode_A(60202, "设置分拣模式A"), //
	m_LineNum_C(60103, "设置线体编号C"), //
	m_LineNum_A(60203, "设置线体编号A"), //
	m_CtrCom_C(60104, "设备拔码设置C"), //
	m_CtrCom_A(60204, "设备拔码设置A"), //
	m_Scan_C(80101, "运单扫描C"), //
	m_Scan_A(80201, "运单扫描A"), //
	m_Sorting_C(80102, "运单分拣中C"), //
	m_Sorting_A(80202, "运单分拣中A"), //
	m_Sorted_C(80103, "运单分拣C"), //
	m_Sorted_A(80203, "运单分拣A"), //
	m_Jb_C(80104, "建包请求C"), //
	m_Jb_A(80204, "建包请求A"), //
	m_Box_C(80105, "箱号打印数据C"), //
	m_Box_A(80205, "箱号打印数据A"), //
	m_BoxImg_C(80106, "箱号图像数据C"), //
	m_BoxImg_A(80206, "箱号图像数据A"), //
	m_Volume_C(80107, "包裹量方与体积C"), //
	m_Volume_A(80207, "包裹量方与体积A"), //
	m_GetChute_C(80108, "通过单号获取格口C"), //
	m_GetChute_A(80208, "返回分拣格口A"), //
	m_ChuteState_C(80109, "推送格口满包状态C"), //
	m_ChuteState_A(80209, "通知格口已建包清空A"), //
	m_Event_C(70101, "事件C"), //
	m_Event_A(70201, "事件A"), //
	m_Log_C(70102, "日志C"), //
	m_Log_A(70202, "日志A"), //
	m_Extent(90101, "扩展段90000-100000"), //	
	m_Unknown(-255, "未知");//
	//
	private int value;
	private String msg;

	private MQMsgType(int value, String msg) {
		this.value = value;
		this.msg = msg;
	}

	public int getValue() {
		return value;
	}

	public String getMessage() {
		return msg;
	}

	public static MQMsgType getEnum(int value) {
		for (MQMsgType ms : MQMsgType.values()) {
			if (ms.getValue() == value) {
				return ms;
			}
		}
		return MQMsgType.m_Unknown;
	}

	public static String getMessage(int value) {
		for (MQMsgType ms : MQMsgType.values()) {
			if (ms.getValue() == value) {
				return ms.getMessage();
			}
		}
		return MQMsgType.m_Unknown.getMessage();
	}
}
