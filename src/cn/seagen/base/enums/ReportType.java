package cn.seagen.base.enums;

/** 统计报表统计类型 */
public enum ReportType {
	REP_TIME(0, "REP_TIME"), // 时间段
	REP_DAY(1, "REP_DAY"), // 1按日(明细为小时)
	REP_MONTH(2, "REP_MONTH"), // 2按月(明细为天)
	REP_YEAR(3, "REP_YEAR"), // 3按年(明细为月)
	REP_MINUTE(4, "REP_MINUTE"), // 3按年(明细为月)
	DEV_NULL(-1, "UNDEFINE");// 未知

	private int value;
	private String msg;

	private ReportType(int value, String msg) {
		this.value = value;
		this.msg = msg;
	}

	public int getValue() {
		return value;
	}

	public String getMessage() {
		return msg;
	}

	public static ReportType getEnum(int value) {
		for (ReportType ms : ReportType.values()) {
			if (ms.getValue() == value) {
				return ms;
			}
		}
		return ReportType.DEV_NULL;
	}

	public static ReportType getEnum(String msg) {
		for (ReportType ms : ReportType.values()) {
			if (ms.getMessage().equalsIgnoreCase(msg)) {
				return ms;
			}
		}
		return ReportType.DEV_NULL;
	}
}
