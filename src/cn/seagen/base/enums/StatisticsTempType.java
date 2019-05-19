package cn.seagen.base.enums;

/**
 * 统计查询涉及到的表
 * 0：box_temp；1，scan_temp；2，sorting_temp；
 */
public enum StatisticsTempType {
	STATISTICS_TEMP_BOX(0, "box_temp"), //
	STATISTICS_TEMP_SCAN(1, "scan_temp"), //
	STATISTICS_TEMP_SROTING(2, "sorting_temp"); //

	private int value;
	private String msg;

	private StatisticsTempType(int value, String msg) {
		this.value = value;
		this.msg = msg;
	}

	public int getValue() {
		return value;
	}

	public String getMessage() {
		return msg;
	}

	public static StatisticsTempType getEnum(int value) {
		for (StatisticsTempType ms : StatisticsTempType.values()) {
			if (ms.getValue() == value) {
				return ms;
			}
		}
		return StatisticsTempType.STATISTICS_TEMP_SROTING;
	}
}
