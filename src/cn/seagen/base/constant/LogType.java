package cn.seagen.base.constant;

/** 日志类型,与数据库表flag_log中一一对应，修改时两处要同时修改 */
public class LogType {
	/** 普通操作 */
	public final static int COM = 0;
	public final static String COM_VAL = "普通操作";

	/** 开机操作 */
	public final static int TURN_ON = 1;
	public final static String TURN_ON_VAL = "开机";

	/** 关机操作 */
	public final static int TURN_OFF = 2;
	public final static String TURNOFF_VAL = "关机";

	/** 登陆操作 */
	public final static int LOGIN = 3;
	public final static String LOGIN_VAL = "登陆";

	/** 登出操作 */
	public final static int LOGOUT = 4;
	public final static String LOGOUT_VAL = "退出";

	/** 下载分拣方案操作 */
	public final static int DOWN_SORTSCHEME = 5;
	public final static String DOWN_SORTSCHEME_VAL = "下载分拣方案";

	/** 修改分拣方案操作 */
	public final static int MOD_SORTSCHEME = 6;
	public final static String MOD_SORTSCHEME_VAL = "修改分拣方案";

	/** 修改密码操作 */
	public final static int MOD_PASSWORD = 7;
	public final static String MOD_PASSWORD_VAL = "修改密码";

	/** 远程连接操作 */
	public final static int CONN_REMOTE = 8;
	public final static String CONN_REMOTE_VAL = "远程连接";

	/** 箱号操作 */
	public final static int BOX_CODE = 9;
	public final static String BOX_CODE_VAL = "箱号";

	/** 分拣模式操作 */
	public final static int SORT_MODE = 10;
	public final static String SORT_MODE_VAL = "分拣模式";

	/** 流水线号操作 */
	public final static int SORT_LINE = 11;
	public final static String SORT_LINE_VAL = "流水线号";

	/** MQ消息队列操作 */
	public final static int MQ_MESSAGE = 12;
	public final static String MQ_MESSAGE_VAL = "业务队列";

	/** REDIS操作 */
	public final static int REDIS = 13;
	public final static String REDIS_VAL = "内存数据";

	/** 下载运单数据操作 */
	public final static int DOWN_WAYBILL = 14;
	public final static String DOWN_WAYBILL_VAL = "下载运单数据";

	/** HTTP请求 */
	public final static int HTTP_QUEST = 15;
	public final static String HTTP_QUEST_VAL = "HTTP数据请求";

	/** 报表查询 */
	public final static int QUERY_REPORT = 16;
	public final static String QUERY_REPORT_VAL = "报表查询";

	/** 建包请求 */
	public final static int QUERY_JB = 17;
	public final static String QUERY_JB_VAL = "建包请求";

	/** 设备控制 */
	public final static int DEV_ENABLE = 18;
	public final static String DEV_ENABLE_VAL = "设备控制";

	/** 重要参数设置 */
	public final static int PROGRAM_SET = 19;
	public final static String PROGRAM_SET_VAL = "参数设置";

	/** 数据库结构升级 */
	public final static int DB_UPDATE = 20;
	public final static String DB_UPDATE_VAL = "库结构升级";
}
