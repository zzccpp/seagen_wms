package cn.seagen.base.mq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import cn.seagen.base.mq.dto.MQBase;
import cn.seagen.base.mq.work.MQMessageWork;
import cn.seagen.base.utils.FastJsonUtils;
import cn.seagen.base.utils.JUtils;
import cn.seagen.base.utils.ScheduledRuntimerManager;
import cn.seagen.base.vo.ScheduledRuntimer;

import com.rabbitmq.client.Channel;
/**
 * 被动接收csendqueue队列中的消息
 * @author zcp
 */
public class CqueueMessageListener implements ChannelAwareMessageListener {
	private static Logger logger = LogManager.getLogger(CqueueMessageListener.class.getName());
	private static ScheduledRuntimer runtimer = null;
	private ScheduledRuntimer getRuntimer() {
		if (runtimer == null)
			runtimer = ScheduledRuntimerManager.getRuntimer("MQConsumer");
		return runtimer;
	}
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		while(true){
			if (MQMessageWork.isCanReceiveMsg()){
				break;
			}else {
				//continue;
				JUtils.sleep(10);
			}
		}
//		if (MQMessageWork.isCanReceiveMsg() == false){
//			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);//拒绝消费MQ消息，消息重回队列。
//		}else{
			long begintime = System.currentTimeMillis();
			try {
				String content = new String(message.getBody(),"utf-8");
				MQBase base = FastJsonUtils.parseObject(content,MQBase.class);
				base.setMessage(content);
				MQMessageWork.receive(base);// 处理消息事件
				logger.debug("收到Cqueue MQ消息:"+content);
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 主动发送应答
				long runtime = System.currentTimeMillis() - begintime;
				getRuntimer().add(runtime);
			} catch (Exception e) {
				logger.error("MQ接收消息出错："+e.getMessage(),e);
			}
//		}
	    
	}
}
