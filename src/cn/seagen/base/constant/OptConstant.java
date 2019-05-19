package cn.seagen.base.constant;

public class OptConstant {
	/** 执行失败、操作失败、未完成 */
	public final static int FAIL = 0;
	/** 执行成功、操作成功、完成 */
	public final static int SUCCESS = 1;

	/** 操作结果，1成功，非1失败 */
	public final static String getResult(int option_type) {
		return (option_type == 1) ? "完成" : "失败";
	}

	/** 未更新 */
	public final static int UPDATE_FAIL = 0;
	/** 已完成更新 */
	public final static int UPDATE_SUCCESS = 1;
	/** 不可更新 */
	public final static int UPDATE_DISABLE = 2;

	/** 更新结果，非0、1就是不可更新 */
	public final static String getUpdateResult(int update_type) {
		return (update_type == 0) ? "未更新" : ((update_type == 1) ? "已更新" : "不更新");
	}

	/** 未打印 */
	public final static int PRINTE_FAIL = 0;
	/** 已打印 */
	public final static int PRINTE_SUCCESS = 1;

	/** 打印结果，1已打印，非1未打印 */
	public final static String getPrintResult(int option_type) {
		return (option_type == 1) ? "已打印" : "未打印";
	}

	/** 未使用 */
	public final static int STATE_UN_USE = 0;
	/** 已使用 */
	public final static int STATE_USED = 1;
	/** 不可使用 */
	public final static int STATE_DISABLE = 2;
	/** 锁定使用状态 */
	public final static int STATE_LOCKED = 3;

	/** 使用结果，0未使用，非0已使用 */
	public final static String getUseResult(int use_type) {
		switch (use_type) {
		case 0:
			return "未使用";
		case 1:
			return "已使用";
		case 2:
			return "不可用";
		case 3:
			return "使用状态已锁";
		default:
			return "不可用";
		}
	}

	/** 未建包 */
	public final static int JB_FAIL = 0;
	/** 已完成建包 */
	public final static int JB_SUCCESS = 1;
	/** 不可建包 */
	public final static int JB_DISABLE = 2;

	/** 建包结果，非0、1就是不可建包 */
	public final static String getJbResult(int jb_type) {
		return (jb_type == 0) ? "未建包" : ((jb_type == 1) ? "已建包" : "不建包");
	}

	/** HTTP处理失败 */
	public final static int HTTP_FAIL = 0;
	/** HTTP处理成功 */
	public final static int HTTP_SUCCESS = 1;
	/** HTTP连接失败 */
	public final static int HTTP_DISABLE = 2;

	/** HTTP处理结果，非0、1就是HTTP连接失败(网络异常) */
	public final static String getHttpResult(int res_type) {
		return (res_type == 0) ? "失败" : ((res_type == 1) ? "成功" : "请求连接失败");
	}

	/** 建包打印样式：异常格口，综合格口，不可打印 */
	public final static int PRINT_STYLE_EXCEPT_CHUTE = 400;
	/** 建包打印样式：无包裹数据，不可打印 */
	public final static int PRINT_STYLE_NO_WAYBILL_DATA = 401;
	/** 建包打印样式：包裹总重量不达标，不可打印 */
	public final static int PRINT_STYLE_NO_WEIGHT = 402;
	/** 建包打印样式：当前无箱号可用，不可打印 */
	public final static int PRINT_STYLE_NO_BOX_CODE = 403;
	/** 建包打印样式：当前没有配制打印机，不可打印 */
	public final static int PRINT_STYLE_NO_PRINTER = 404;
	/** 建包打印样式：当前没有找到格口的相关信息，不可打印 */
	public final static int PRINT_STYLE_NO_CHUTE_INFO = 405;
	/** 建包打印样式：当前无法取箱号相关的图像信息 */
	public final static int PRINT_STYLE_NO_BOX_IMAGE = 406;
	/** 建包打印样式：重新打印时找不到原始数据 */
	public final static int PRINT_STYLE_NO_FIND_DATA = 407;
	/** 建包打印样式：打印数据存储失败 */
	public final static int PRINT_STYLE_SAVE_DATA_ERROR = 408;
}




