package cn.seagen.base.mq.work;

import cn.seagen.base.mq.dto.MQBase;
import cn.seagen.base.mq.dto.MQEvent;
import cn.seagen.base.mq.dto.MQJb;
import cn.seagen.base.mq.dto.MQLineNum;
import cn.seagen.base.mq.dto.MQLog;
import cn.seagen.base.mq.dto.MQPower;
import cn.seagen.base.mq.dto.MQSort;
import cn.seagen.base.mq.dto.MQSortMode;

/** 这里处理接收的MQ消息接口 */
public interface MQMessage {
	/** 已扫描 */
	boolean scan_process(MQSort mQScan);

	/** 分拣中(还在线体上运行) */
	boolean sorting_process(MQSort mQSorting);

	/** 分拣完成 */
	boolean sorted_process(MQSort mQSorted);

	/** 建包请求 */
	boolean jb_process(MQJb mQJb);

	/** 线体开关机 */
	boolean power_process(MQPower mQPower);

	/** 设置流水线号 */
	boolean linenum_process(MQLineNum mQLineNum);

	/** 设置分拣模式 */
	boolean sortmode_process(MQSortMode mQSortMode);

	/** 记录事件 */
	boolean event_process(MQEvent mQEvent);

	/** 记录日志 */
	boolean log_process(MQLog mQLog);

	/** 记录错误日志 */
	boolean doExcMsg(MQBase mqBase);

	/** 记录错误日志 */
	boolean doExcMsg(String message);

	/** 项目扩展接口 MQ消息ID在9000至100000之间 */
	boolean sorting_extent(MQBase mqBase);
}
