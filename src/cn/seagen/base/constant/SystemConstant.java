package cn.seagen.base.constant;

import java.util.List;

import cn.seagen.base.bean.Sortscheme;
import cn.seagen.base.bean.SortschemeDetail;

/** 系统配制类型 */
public class SystemConstant {
	// ****常用配制与数据库无关*****
	/** 运行状态 */
	public static boolean RUNING = true;
	/** NOREAD常量 */
	public static String NOREAD = "noread";
	/** 测试开关*/
	public static Boolean DEBUG = false;
	/** 批次ID */
	public static int BATCH_ID = 0;
	/** 分拣主题方案主题 */
	public static Sortscheme SORTSCHEME = new Sortscheme();
	/** 分拣主题方案明细 */
	public static List<SortschemeDetail> SORTSCHEMELIST = null;
	/** 线程数量控制*/
	public static int THREAD_COUNT = 40;
	
	public static String DB_NAME = "seagen_wms";
}
