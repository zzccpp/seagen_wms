package cn.seagen.base.constant;


/**
 * 定义版本常量配置
 * 【
 *    写法注意
 * 	  1.需要添加的数据库中的字段用public修饰如sysDbVer及sysDbVerName及sysDbVerCommet，
 * 	  2.不需要添加到数据库中用private修饰如sysDbVerRegEx、sysDbVerError,所有RegEx和Error需写get方法
 *    3.每个字段由四部分组成，(1.字段本身  2.字段+Name  3.字段+Comment  4.字段+RegEx正则表达式   5.字段+Error错误提示)
 *    4.如果添加字段的话,需要清空system_set表,重启系统后再次生成(防止配置丢失,需要先备份再清空)
 *    5.新加字段，需在SystemSetUtils中map加入该字段
 * 】
 */
public class VersionConstant {
	
	/** 系统数据库版本 */
	public static String sysDbVer = "mysql 5.6.21";
	public static String sysDbVerName = "系统数据库版本";
	public static String sysDbVerComment = "系统数据库版本";
	private String sysDbVerRegEx = "(.*)";
	private String sysDbVerError = "请输入字符";
	
	/** 软件源码 */
	public static String softSourceCode  = "seagen_wms2018.01.001源码.rar";
	public static String softSourceCodeName = "软件源码";
	public static String softSourceCodeComment = "软件源码";
	private String softSourceCodeRegEx = ".*?[.rar|.zip]";
	private String softSourceCodeError = "以.rar或者.zip结尾";
	
	/** 软件输出 */
	public static String softOutput  = "seagen_wms2018.01.001输出.rar";
	public static String softOutputName = " 软件输出";
	public static String softOutputComment = " 软件输出";
	private String softOutputRegEx = ".*?[.rar|.zip]";
	private String softOutputError = "以.rar或者.zip结尾";
	
	/** 软件版本说明 */
	public static String softVersion  = "seagen_wms2018.01.001版本说明.txt";
	public static String softVersionName = "软件版本说明 ";
	public static String softVersionComment = "软件版本说明 ";
	private String softVersionRegEx = ".*?.txt*";
	private String softVersionError = "以.txt结尾";
	
	
	public String getSysDbVerRegEx() {
		return sysDbVerRegEx;
	}
	public String getSysDbVerError() {
		return sysDbVerError;
	}
	public String getSoftSourceCodeRegEx() {
		return softSourceCodeRegEx;
	}
	public String getSoftSourceCodeError() {
		return softSourceCodeError;
	}
	public String getSoftOutputRegEx() {
		return softOutputRegEx;
	}
	public String getSoftOutputError() {
		return softOutputError;
	}
	public String getSoftVersionRegEx() {
		return softVersionRegEx;
	}
	public String getSoftVersionError() {
		return softVersionError;
	}
	
}

