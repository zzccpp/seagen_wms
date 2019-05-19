package test.service.impl;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;

import test.BaseSpringTest;
import cn.seagen.base.mq.dto.MQBase;
import cn.seagen.base.mq.dto.MQJb;

public class MqServceTest extends BaseSpringTest {
	private AmqpTemplate jsendTemplate;
	private AmqpTemplate csendTemplate;
	private AmqpTemplate jdbfailTemplate;
	
	@Resource
	public void setAmqpTemplate(AmqpTemplate jsendTemplate) {
		this.jsendTemplate = jsendTemplate;
	}
	
	@Resource
	public void csendTemplate(AmqpTemplate csendTemplate) {
		this.csendTemplate = csendTemplate;
	}
	
	@Resource
	public void setJdbfailTemplate(AmqpTemplate jdbfailTemplate) {
		this.jdbfailTemplate = jdbfailTemplate;
	}

	@Test//往队列中发送字符串数据
	public void sendmq(){
		try {
			Message jmessage = MessageBuilder.withBody("hello rabbit jsendqueue".getBytes("utf-8"))
                .setMessageId(System.currentTimeMillis()+"")
                .build();
			Message cmessage = MessageBuilder.withBody("hello rabbit csendqueue".getBytes("utf-8"))
	                .setMessageId(System.currentTimeMillis()+"")
	                .build();
			jsendTemplate.send("jsendqueue", jmessage);
			csendTemplate.send("csendqueue", cmessage);
			System.out.println("发送完成...");
			Thread.sleep(8000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test//主动获取队列中获取字符串数据
	public void getmq(){
		try {
			//1、默认获取jsendTemplate配置中的队列
			//Message receive = jsendTemplate.receive();
			//2、获取指定的队列
			Message receive = jsendTemplate.receive("csendqueue");
			String content = new String(receive.getBody(),"utf-8");
			System.out.println("收到Jqueue消息:"+content);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	@Test//(往队列中拉取添加)发送json对象
	public void sendJsonObject(){
		MQJb jb = new MQJb();
		jb.setIndex(234234);
		jb.setChute_num("34");
		jb.setChute_id(34);
		jb.setIndex_App();
		//jsendTemplate.convertAndSend("jsendqueue",jb);
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Test//(从队列中拉取数据---注意：主动拉取与被动监听不要同时打开      )json对象转成字符串
	public void getJsonObject(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 1000; i++) {
					MQBase base = (MQBase) jsendTemplate.receiveAndConvert("jsendqueue");
					System.out.println(base);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		try {
			Thread.sleep(5*60*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void sendDBFailQueue(){
		MQJb jb = new MQJb();
		jb.setChute_num("34");
		jb.setChute_id(34);
		jb.setIndex_App();
		for (int i = 0; i < 10000; i++) {
			jb.setIndex(23423+i);
			jb.setChute_num("34"+i);
			jdbfailTemplate.convertAndSend("jdbfailqueue",jb);
		}
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
