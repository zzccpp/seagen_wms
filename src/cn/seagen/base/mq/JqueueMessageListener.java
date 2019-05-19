package cn.seagen.base.mq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import com.rabbitmq.client.Channel;
/**
 * 被动接收jsendqueue队列中的消息
 * @author zcp
 */
public class JqueueMessageListener implements ChannelAwareMessageListener {

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
	    String content = new String(message.getBody(),"utf-8");
		System.out.println(channel.getChannelNumber()+",收到Jqueue消息:"+content);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 主动发送应答
	}
}
