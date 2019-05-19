package cn.seagen.base.enums;

/** 运单状态 */
public enum WaybillStatus {
	w_OK(0, "正常"), //
	w_Cancel(1, "运单取消"), //
	w_Change(2, "运单地址更改"), //
	w_NoData(3, "无数据"); //

	private int value;
	private String msg;

	private WaybillStatus(int value, String msg) {
		this.value = value;
		this.msg = msg;
	}

	public int getValue() {
		return value;
	}

	public String getMessage() {
		return msg;
	}

	public static WaybillStatus getEnum(int value) {
		for (WaybillStatus ms : WaybillStatus.values()) {
			if (ms.getValue() == value) {
				return ms;
			}
		}
		return WaybillStatus.w_NoData;
	}
}
