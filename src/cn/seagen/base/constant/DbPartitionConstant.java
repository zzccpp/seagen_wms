package cn.seagen.base.constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DbPartitionConstant {

	/** 业务表分表数量 */
	public final static int PARTITION_TALBE_NUM = 3;
	
	/** 扫描业务表存在的分表集合 */
	public static List<String> scanPartitionList = Collections.synchronizedList(new ArrayList<String>());
	
	/** 分拣业务表存在的分表集合 */
	public static List<String> sortingPartitionList = Collections.synchronizedList(new ArrayList<String>());
}
