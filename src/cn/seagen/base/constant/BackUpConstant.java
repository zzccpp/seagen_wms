package cn.seagen.base.constant;


/**
 * 定义数据备份常量配置
 * 【
 *    写法注意
 * 	  1.需要添加的数据库中的字段用public修饰如backupData及backupDataName及backupDataCommet，
 * 	  2.不需要添加到数据库中用private修饰如backupDataRegEx、backupDataError,所有RegEx和Error需写get方法
 *    3.每个字段由四部分组成，(1.字段本身  2.字段+Name  3.字段+Comment  4.字段+RegEx正则表达式   5.字段+Error错误提示)
 *    4.如果添加字段的话,需要清空system_set表,重启系统后再次生成(防止配置丢失,需要先备份再清空)
 *    5.新加字段，需在SystemSetUtils中map加入该字段
 * 】
 */
public class BackUpConstant {

	/**常规业务数据备份保留天数 */
	public static int backupGenralData= 7;
	public static String backupGenralDataName = "常规业务数据备份保留天数";
	public static String backupGenralDataComment = "保留最近几天的数据,建议7~31天";
	private String backupGenralDataRegEx = "[7-9]|(?:[1-2][0-9])|30|31";
	private String backupGenralDataError = "请输入7~31的整数";
	
	/**主要业务数据备份保留天数 */
	public static int backupMainData= 7;
	public static String backupMainDataName = "主要业务数据备份保留天数";
	public static String backupMainDataComment = "保留最近几天的数据,建议7~31天";
	private String backupMainDataRegEx = "[7-9]|(?:[1-2][0-9])|30|31";
	private String backupMainDataError = "请输入7~31的整数";
	
	
	/**报表数据备份月份 */
	public static int  backupReport  = 12;
	public static String backupReportName = "报表数据备份月份";
	public static String backupReportComment = "报表数据备份月份,建议1~6个月";
	private String backupReportRegEx = "[6-18]";
	private String backupReportError = "请输入6~18的整数";
	
	
	public String getBackupGenralDataRegEx() {
		return backupGenralDataRegEx;
	}
	public String getBackupGenralDataError() {
		return backupGenralDataError;
	}
	public String getBackupMainDataRegEx() {
		return backupMainDataRegEx;
	}
	
	public String getBackupMainDataError() {
		return backupMainDataError;
	}
	public String getBackupReportRegEx() {
		return backupReportRegEx;
	}
	public String getBackupReportError() {
		return backupReportError;
	}
}
