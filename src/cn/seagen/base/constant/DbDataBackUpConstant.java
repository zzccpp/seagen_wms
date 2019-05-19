package cn.seagen.base.constant;

import java.util.HashMap;
import java.util.Map;

public class DbDataBackUpConstant {

	/** 删除数量 */
	public final static int DB_BACK_DEL_COUNT = 2000;
	
	/** 保留天数（普通备份） */
	public final static int DB_BACK_HOLDDAY_COMMON = 7;
	
	/** 保留天数 （主要业务表，box_temp,scan_temp,sorting_temp） */
	public final static int DB_BACK_HOLDDAY_MAIN = 7;
	
	/** 统计数据保留月份*/
	public final static int DB_BACK_HOLDMONTH = 12;
	
	/** 记录备份时，剩余可删除的数量，即备份前总的可删除量减去备份时删除的数量，做下一次备份时的参考，不足备份数量，则请求数据库重新获取 */
	public final static Map<String,Integer> dataBackCanDelCount = new HashMap<String, Integer>();

}
