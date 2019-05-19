package cn.seagen.base.enums;
public enum FunctionType {
	F_SUPPLY(1,"上货区"),
	F_SCAN(2,"扫描点"),
	F_SORTING(1,"分拣口"),
	F_SORTED(4,"到站口"),
	F_EXPSORTED(5, "异常口"),
	F_BOX(6,"到站口满包"),
	UN_KNOWN(0,"未知");
	
	private int value;
	private String msg;
	
	private FunctionType(int value, String msg) {
		this.value = value;
		this.msg = msg;
	}

	public int getValue() {
		return value;
	}

	public String getMessage() {
		return msg;
	}
	
	public static FunctionType getEnum(int value) {
		for (FunctionType ms : FunctionType.values()) {
			if (ms.getValue() == value) {
				return ms;
			}
		}
		return FunctionType.UN_KNOWN;
	}

	public static String getMessage(int value) {
		for (FunctionType ms : FunctionType.values()) {
			if (ms.getValue() == value) {
				return ms.getMessage();
			}
		}
		return FunctionType.UN_KNOWN.getMessage();
	}
}
