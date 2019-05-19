package cn.seagen.base.utils;

import cn.seagen.base.constant.EquipmentConstant;
import cn.seagen.base.enums.FunctionType;

public class DvirUtils {
	public static final String ascii_1A = String.valueOf((char) 0x1A);
	public static final String ascii_1B = String.valueOf((char) 0x1B);
	public static final String START_TEXT = "SORTINGDVIR";// 固定内容

	/**
	 * 扫描点
	 * 
	 * @param location
	 * @param barcode
	 * @param time
	 * @return
	 */
	public static String getScanData(int location, String barcode, String time) {
		if (location <= 0 || location > EquipmentConstant.scanNum)
			return "";
		if (JUtils.isEmpty(barcode))
			barcode = "noread";
		String data = START_TEXT + ascii_1A + FunctionType.F_SCAN.getValue()
				+ ascii_1A + location + ascii_1A + barcode + ascii_1B + time;
		return data;
	}

	/**
	 * 到站口
	 * 
	 * @param location
	 * @param barcode
	 * @param sitecode
	 * @param time
	 * @return
	 */
	public static String getSortedData(int location, String barcode,
			String sitecode, String time) {
		if (location <= 0 || location > EquipmentConstant.chuteNum)
			return "";
		String data = START_TEXT + ascii_1A + FunctionType.F_SORTED.getValue()
				+ ascii_1A + location + ascii_1A + barcode + ascii_1B
				+ sitecode + ascii_1B + time;
		return data;
	}

	/**
	 * 异常口
	 * 
	 * @param location
	 * @param barcode
	 * @param expcode
	 * @param time
	 * @return
	 */
	public static String getExpSortData(int location, String barcode,
			String expcode, String time) {
		if(JUtils.isEmpty(expcode))
			expcode = "EXP";
		String data = START_TEXT + ascii_1A
				+ FunctionType.F_EXPSORTED.getValue() + ascii_1A + location
				+ ascii_1A + barcode + ascii_1B + expcode + ascii_1B + time;
		return data;
	}

	/**
	 * 到站口满包
	 * 
	 * @param location
	 * @param boxcode
	 * @param sitecode
	 * @param time
	 * @return
	 */
	public static String getBoxData(int location, String boxcode,
			String sitecode, String time) {
		String data = START_TEXT + ascii_1A + FunctionType.F_BOX.getValue()
				+ ascii_1A + location + ascii_1A + boxcode + ascii_1B
				+ sitecode + ascii_1B + time;
		return data;
	}
}
