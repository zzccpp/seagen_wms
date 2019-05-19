package cn.seagen.base.bean;

import java.util.Arrays;

/**
 * 邮件实体类
 * @author zcp
 * 发送者的信息都在配置文件中配置，发送者也在配置文件中有默认配置.
 */
public class EmailInfo {

    /**抄送者*/
    private String[] ccs;
    /**邮件主题*/
    private String subject;
    /**邮件内容*/
    private String content;
	
	public void setCcs(String[] ccs) {
		this.ccs = ccs;
	}
	
	public String[] getCcs() {
		return ccs;
	}

	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "EmailInfo [ccs=" + Arrays.toString(ccs) + ", subject="
				+ subject + ", content=" + content + "]";
	}
}
