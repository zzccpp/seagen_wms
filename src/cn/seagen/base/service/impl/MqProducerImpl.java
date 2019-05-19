package cn.seagen.base.service.impl;

import javax.annotation.Resource;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import cn.seagen.base.service.MqProducer;
@Service
public class MqProducerImpl implements MqProducer {
	@Resource
	private AmqpTemplate jsendTemplate;
	@Resource
	private AmqpTemplate csendTemplate;
	@Override
	public boolean sendDataToQueue(String queueKey, Object object) {
		try { 
			jsendTemplate.convertAndSend(queueKey,object);
		} catch (Exception e) {
			//logger.error("发送MQ消息错误：" + e.getMessage() + "[" + cmd + "]", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean sendToCqueue(Object object) {
		try { 
			csendTemplate.convertAndSend("csendqueue",object);
		} catch (Exception e) {
			//logger.error("发送MQ消息错误：" + e.getMessage() + "[" + cmd + "]", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean sendToJqueue(Object object) {
		try { 
			jsendTemplate.convertAndSend("jsendqueue",object);
		} catch (Exception e) {
			//logger.error("发送MQ消息错误：" + e.getMessage() + "[" + cmd + "]", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean sendToFqueue(Object object) {
		try { 
			jsendTemplate.convertAndSend("jdbfailqueue",object);
		} catch (Exception e) {
			//logger.error("发送MQ消息错误：" + e.getMessage() + "[" + cmd + "]", e);
			return false;
		}
		return true;
	}
}
