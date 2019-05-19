package cn.seagen.base.mq;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import cn.seagen.base.mq.dto.MQBase;
import cn.seagen.base.mq.dto.MQSort;
import cn.seagen.base.mq.enums.MQMsgType;
import cn.seagen.base.mq.work.MQMessageWork;
import cn.seagen.base.service.ScanService;
import cn.seagen.base.service.SortedService;
import cn.seagen.base.utils.ConvertUtils;
import cn.seagen.base.utils.FastJsonUtils;
import cn.seagen.base.utils.JUtils;
import cn.seagen.base.utils.ScheduledRuntimerManager;
import cn.seagen.base.vo.ScheduledRuntimer;

import com.rabbitmq.client.Channel;
/**
 * 被动接收jdbfailqueue队列中的消息,jdbfailqueue线程用于存储处理csendqueue队列中消息入库失败后的消息
 * @author zcp
 */
public class FqueueMessageListener implements ChannelAwareMessageListener {
	@Resource
	private SortedService sortedServiceImpl;
	@Resource
	private ScanService scanServiceImpl;
	
	private static Logger logger = LogManager.getLogger(FqueueMessageListener.class.getName());
	private static ScheduledRuntimer runtimer = null;
	private ScheduledRuntimer getRuntimer() {
		if (runtimer == null)
			runtimer = ScheduledRuntimerManager.getRuntimer("Fail_MQConsumer");
		return runtimer;
	}
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		while(!MQMessageWork.getCanConsumer()){
			JUtils.sleep(10);
		}
		long begintime = System.currentTimeMillis();
		try {
			String content = new String(message.getBody(),"utf-8");
			MQBase mqbase = FastJsonUtils.parseObject(content,MQBase.class);
			mqbase.setMessage(content);
			logger.debug("收到Fqueue MQ消息:"+content);
			if(doWork(mqbase)){
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 主动发送应答
				long runtime = System.currentTimeMillis() - begintime;
				getRuntimer().add(runtime);
			}else{
				System.out.println("................................数据库连接异常.............................");
				JUtils.sleep(3000);
				channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);//拒绝消费MQ消息，消息重回队列。
			}
		} catch (Exception e) {
			logger.error("Fail MQ接收消息出错："+e.getMessage(),e);
		}
	}
	
	private boolean doWork(MQBase mqbase){
		MQMsgType mQMsgType = MQMsgType.getEnum(mqbase.getIndex());
		boolean result = false;
		try {
			switch (mQMsgType) {
				case m_Scan_C:
					MQSort mQScan = FastJsonUtils.parseObject(mqbase.getMessage(), MQSort.class);
					if (mQScan != null) { // 转换正确
						result =scanServiceImpl.insertScanInfo(ConvertUtils.convertFromObject(mQScan));
					}
					return result;
				case m_Sorting_C:
					MQSort mQSorting = FastJsonUtils.parseObject(mqbase.getMessage(), MQSort.class);
					if (mQSorting != null) { // 转换正确
						result =scanServiceImpl.insertScanInfo(ConvertUtils.convertFromObject(mQSorting));
					}
					return result;
				case m_Sorted_C:
					MQSort mQSorted = FastJsonUtils.parseObject(mqbase.getMessage(), MQSort.class);
					if (mQSorted != null) { // 转换正确
						result = sortedServiceImpl.insertSorted(ConvertUtils.convertFromObject(mQSorted));
					}
					return result;
				default:
					return true;
			}
		} catch (Exception e) {
			return false;
		}
		
	}
}
