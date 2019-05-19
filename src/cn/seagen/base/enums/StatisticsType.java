package cn.seagen.base.enums;

/**
 * 统计类常量
 * 0：小车量；1，格口；2，分钟；3，扫描；4，站点；5，汇总；
 * 6，导入台；7，格口封包；8，汇总封包；9，批次统计；10，批次封包；
 */
public enum StatisticsType {
	STATISTICS_CAR(0, "report_car"), //
	STATISTICS_CHUTE(1, "report_chute"), //
	STATISTICS_MINUTE(2, "report_minute"), //
	STATISTICS_SCAN(3, "report_scan"), //
	STATISTICS_SITE(4, "report_site"), //
	STATISTICS_SORTING(5, "report_sorting"), //
	STATISTICS_SUPPLY(6, "report_supply"), //
	STATISTICS_CHUTE_BOX(7, "report_chute"), //
	STATISTICS_SORTING_BOX(8, "report_sorting"), //
	STATISTICS_BATCH(9, "report_sorting"), //
	STATISTICS_BATCH_BOX(10, "report_sorting"), //
	STATISTICS_UN(255, "未知");//

	private int value;
	private String msg;

	private StatisticsType(int value, String msg) {
		this.value = value;
		this.msg = msg;
	}

	public int getValue() {
		return value;
	}

	public String getMessage() {
		return msg;
	}

	public static StatisticsType getEnum(int value) {
		for (StatisticsType ms : StatisticsType.values()) {
			if (ms.getValue() == value) {
				return ms;
			}
		}
		return StatisticsType.STATISTICS_UN;
	}
}
