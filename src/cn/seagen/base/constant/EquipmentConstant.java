package cn.seagen.base.constant;

/**
 * 定义设备常量配置
 * 【
 *    写法注意
 * 	  1.需要添加的数据库中的字段用public修饰如scanNum及scanNumName及scanNumCommet，
 * 	  2.不需要添加到数据库中用private修饰如scanNumRegEx、scanNumError,所有RegEx和Error需写get方法
 *    3.每个字段由四部分组成，(1.字段本身  2.字段+Name  3.字段+Comment  4.字段+RegEx正则表达式   5.字段+Error错误提示)
 *    4.如果添加字段的话,需要清空system_set表,重启系统后再次生成(防止配置丢失,需要先备份再清空)
 *    5.新加字段，需在SystemSetUtils中map加入该字段
 * 】
 */
public class EquipmentConstant {
	
	/**龙门架数量*/
	public static int scanNum = 6;
	public static String scanNumName = "龙门架个数";       
	public static String scanNumComment = "龙门架个数";   
	private String scanNumRegEx = "[1-9]\\d{0,1}";
	private String scanNumError = "请填入1-99的数字";
	
	/**第一层龙门架数量 */
	public static int firsrLayerScanNum  = 3;
	public static String firsrLayerScanNumName= "第一层级龙门架个数";
	public static String firsrLayerScanNumComment = "第一层级有3个龙门架";
	private String firsrLayerScanNumRegEx = "[1-9]";
	private String firsrLayerScanNumError = "请填入1-9的数字";
	
	
	/**第二层龙门架数量*/
	public static int secondLayerScanNum  = 3;
	public static String secondLayerScanNumName= "第二层级龙门架个数";
	public static String secondLayerScanNumComment = "第二层级有3个龙门架";
	private String secondLayerScanNumRegEx = "[1-9]";
	private String secondLayerScanNumError = "请填入正确的数字,数字1-9";
	
	
	/**导入台数量 */
	public static int supplyNum  = 12;
	public static String supplyNumName = "导入台个数";
	public static String supplyNumComment = "导入台个数";
	private String supplyNumRegEx = "[1-9]\\d{0,1}";
	private String supplyNumError = "请填入正确的数字，导入台个数1-99";
	
	/**第一层导入台数量*/
	public static int firsrLayerSupplyNum  = 6;
	public static String firsrLayerSupplyNumName= "第一层级导入台个数";
	public static String firsrLayerSupplyNumComment = "第一层级有6个导入台";
	private String firsrLayerSupplyNumRegEx = "[1-9]\\d{0,1}";
	private String firsrLayerSupplyNumError = "请填入1-99的数字";
	
	
	/**第二层导入台数量*/
	public static int secondLayerSupplyNum  = 6;
	public static String secondLayerSupplyNumName= "第二层级导入台个数";
	public static String secondLayerSupplyNumComment = "第二层级有6个导入台";
	private String secondLayerSupplyNumRegEx = "[1-9]\\d{0,1}";
	private String secondLayerSupplyNumError = "请填入正确的数字1-99";
	
	
	/**格口数量 */
	public static int chuteNum  = 120;
	public static String chuteNumName= "格口个数";
	public static String chuteNumComment = "格口个数";
	private String chuteNumRegEx = "[1-9]\\d{0,2}";
	private String chuteNumError = "请填入正确的数字，格口数1-999";
	
	
	/**小车数量 */
	public static int carNum = 270;
	public static String carNumName = "小车个数";
	public static String carNumComment = "小车个数";
	private String carNumRegEx = "[1-9]\\d{0,2}";
	private String carNumError = "请填入正确的数字，小车数1-999";
	
	/**第一层 */
	public static int firsrLayer = 120;
	public static String firsrLayerName= "第一层级小车个数";
	public static String firsrLayerComment = "120表示第一层级有1号至120号小车";
	private String firsrLayerRegEx = "[1-9]\\d{0,2}";
	private String firsrLayerError = "请填入正确的格式数字-数字,数字小于999";
	
	
	/**第二层 */
	public static int secondLayer = 150;
	public static String secondLayerName= "第二层级小车个数";
	public static String secondLayerComment = "150表示第二层级有1号至150号小车";
//	private String secendLayerRegEx = "\\d{1,3}+(-\\d{1,3})";
	private String secondLayerRegEx = "[1-9]\\d{0,2}";
	private String secondLayerError = "请填入正确的数字,数字1-999";
	
	
	public String getScanNumRegEx() {
		return scanNumRegEx;
	}
	public String getSupplyNumRegEx() {
		return supplyNumRegEx;
	}
	public String getCarNumRegEx() {
		return carNumRegEx;
	}
	public String getChuteNumRegEx() {
		return chuteNumRegEx;
	}
	public String getScanNumError() {
		return scanNumError;
	}
	public String getSupplyNumError() {
		return supplyNumError;
	}
	public String getCarNumError() {
		return carNumError;
	}
	public String getChuteNumError() {
		return chuteNumError;
	}
	public String getFirsrLayerRegEx() {
		return firsrLayerRegEx;
	}
	public String getFirsrLayerError() {
		return firsrLayerError;
	}
	public String getSecondLayerRegEx() {
		return secondLayerRegEx;
	}
	public String getSecondLayerError() {
		return secondLayerError;
	}
	public String getFirsrLayerScanNumRegEx() {
		return firsrLayerScanNumRegEx;
	}
	public void setFirsrLayerScanNumRegEx(String firsrLayerScanNumRegEx) {
		this.firsrLayerScanNumRegEx = firsrLayerScanNumRegEx;
	}
	public String getFirsrLayerScanNumError() {
		return firsrLayerScanNumError;
	}
	public void setFirsrLayerScanNumError(String firsrLayerScanNumError) {
		this.firsrLayerScanNumError = firsrLayerScanNumError;
	}
	public String getSecondLayerScanNumRegEx() {
		return secondLayerScanNumRegEx;
	}
	public void setSecondLayerScanNumRegEx(String secondLayerScanNumRegEx) {
		this.secondLayerScanNumRegEx = secondLayerScanNumRegEx;
	}
	public String getSecondLayerScanNumError() {
		return secondLayerScanNumError;
	}
	public void setSecondLayerScanNumError(String secondLayerScanNumError) {
		this.secondLayerScanNumError = secondLayerScanNumError;
	}
	public String getFirsrLayerSupplyNumRegEx() {
		return firsrLayerSupplyNumRegEx;
	}
	public void setFirsrLayerSupplyNumRegEx(String firsrLayerSupplyNumRegEx) {
		this.firsrLayerSupplyNumRegEx = firsrLayerSupplyNumRegEx;
	}
	public String getFirsrLayerSupplyNumError() {
		return firsrLayerSupplyNumError;
	}
	public void setFirsrLayerSupplyNumError(String firsrLayerSupplyNumError) {
		this.firsrLayerSupplyNumError = firsrLayerSupplyNumError;
	}
	public String getSecondLayerSupplyNumError() {
		return secondLayerSupplyNumError;
	}
	public void setSecondLayerSupplyNumError(String secondLayerSupplyNumError) {
		this.secondLayerSupplyNumError = secondLayerSupplyNumError;
	}
	public String getSecondLayerSupplyNumRegEx() {
		return secondLayerSupplyNumRegEx;
	}
	public void setSecondLayerSupplyNumRegEx(String secondLayerSupplyNumRegEx) {
		this.secondLayerSupplyNumRegEx = secondLayerSupplyNumRegEx;
	}
}
