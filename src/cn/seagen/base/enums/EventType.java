package cn.seagen.base.enums;

/**
 * 运行事件类型
 */
public enum EventType {
	ALL(0, "全部"),
	LINE_RUN(1, "线体运行"), 
	REMOTE_CONN(2, "远程连接"),
	CAR(3, "小车"), 
	CHUTE(4, "格口"), 
	SCAN(5, "龙门架"), 
	SUPPLY(6, "导入台"), 
	DEV_NULL(-1, "UNDEFINE");

	private int value;
	private String msg;

	private EventType(int value, String msg) {
		this.value = value;
		this.msg = msg;
	}

	public int getValue() {
		return value;
	}

	public String getMessage() {
		return msg;
	}

	public static EventType getEnum(int value) {
		for (EventType ms : EventType.values()) {
			if (ms.getValue() == value) {
				return ms;
			}
		}
		return EventType.DEV_NULL;
	}

	public static EventType getEnum(String msg) {
		for (EventType ms : EventType.values()) {
			if (ms.getMessage().equalsIgnoreCase(msg)) {
				return ms;
			}
		}
		return EventType.DEV_NULL;
	}
}
