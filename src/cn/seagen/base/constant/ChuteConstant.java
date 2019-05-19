package cn.seagen.base.constant;

/**
 * 定义格口常量配置
 * 【
 *    写法注意
 * 	  1.需要添加的数据库中的字段用public修饰如specialChute及specialChuteName及specialChuteCommet，
 * 	  2.不需要添加到数据库中用private修饰如specialChuteRegEx、specialChuteError,所有RegEx和Error需写get方法
 *    3.每个字段由四部分组成，(1.字段本身  2.字段+Name  3.字段+Comment  4.字段+RegEx正则表达式   5.字段+Error错误提示)
 *    4.如果添加字段的话,需要清空system_set表,重启系统后再次生成(防止配置丢失,需要先备份再清空)
 *    5.新加字段，需在SystemSetUtils中map加入该字段
 * 】
 */
public class ChuteConstant {
	
	/**特殊格口号设置 */
	public static String specialChute = "1,2";
	public static String specialChuteName = "特殊格口号";
	public static String specialChuteComment = "特殊格口号,多个用逗号隔开";
	private String specialChuteRegEx = "\\d{1,3}+(,\\d{1,3}+)*";
	private String specialChuteError = "请输入1-999数字，多个用英文逗号隔开";
	
	/**多站点综合格口设置 */
	public static String comChute  = "3,4";
	public static String comChuteName = "多站点综合格口";
	public static String comChuteComment = "多站点综合格口号,多个用逗号隔开";
	private String comChuteRegEx = "\\d{1,3}+(,\\d{1,3}+)*";
	private String comChuteError = "请输入1-999数字，多个用英文逗号隔开";
	
	public String getSpecialChuteRegEx() {
		return specialChuteRegEx;
	}
	public String getSpecialChuteError() {
		return specialChuteError;
	}
	public String getComChuteRegEx() {
		return comChuteRegEx;
	}
	public String getComChuteError() {
		return comChuteError;
	}
	
	
	
}
