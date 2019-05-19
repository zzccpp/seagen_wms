package cn.seagen.base.enums;

/** 分拣扫描的状态 */
public enum SortStatus {
	s_OK(0, "正常"), //
	s_NoScheme(1, "无分拣方案"), //
	s_NoData(2, "无数据"), //
	s_NoRead(3, "无读"), //
	s_Cancel(4, "取消"), //
	s_MaxCycle(5, "最大圈数"), //
	s_ErrChute(6, "格口错"), //
	s_MoreCode(7, "多条码"), //
	s_Lost(8, "迷失"), //
	s_Undefine(255, "未知");//

	private int value;
	private String msg;

	private SortStatus(int value, String msg) {
		this.value = value;
		this.msg = msg;
	}

	public int getValue() {
		return value;
	}

	public String getMessage() {
		return msg;
	}

	public static SortStatus getEnum(int value) {
		for (SortStatus ms : SortStatus.values()) {
			if (ms.getValue() == value) {
				return ms;
			}
		}
		return SortStatus.s_Undefine;
	}
}
