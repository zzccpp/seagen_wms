package cn.seagen.base.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import cn.seagen.base.bean.EmailInfo;

/**
 * 
 * 邮件发送服务对象
 * @author zcp
 * 那个地方需要则导入其对象即可
 */
public class MailSendService {

	private JavaMailSenderImpl javaMailSender;
	private ThreadPoolTaskExecutor taskExecutor;
	private SimpleMailMessage simpleMailMessage;
	public void setJavaMailSender(JavaMailSenderImpl javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}
	/**
	 * 发送邮件
	 * @param emailInfo 邮件实体
	 */
	public void sendEmail(EmailInfo emailInfo){
		sendByAsyn(emailInfo);
	}
	/**
	 * 异步发送,
	 * @param emailInfo 邮件实体
	 */
	public void sendByAsyn(final EmailInfo emailInfo){
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				sendBySync(emailInfo);
			}
		});
	}
	/**
	 * 同步发送,
	 * @param emailInfo 邮件实体
	 */
	public void sendBySync(EmailInfo emailInfo){
		simpleMailMessage.setText(emailInfo.getContent());
		//simpleMailMessage.setSubject(emailInfo.getSubject());
		simpleMailMessage.setCc(emailInfo.getCcs());
		javaMailSender.send(simpleMailMessage);
	}
}
