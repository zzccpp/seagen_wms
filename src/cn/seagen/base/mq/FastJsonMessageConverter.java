package cn.seagen.base.mq;

import java.io.UnsupportedEncodingException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

import cn.seagen.base.mq.dto.MQBase;
import cn.seagen.base.utils.FastJsonUtils;
import cn.seagen.base.utils.JUtils;
/**
 * 使用FastJSON来转换JSON对象
 * @author zcp
 */
public class FastJsonMessageConverter extends AbstractMessageConverter {
	private volatile String defaultCharset = "UTF-8";

	public FastJsonMessageConverter() {
		super();
	}
	public void setDefaultCharset(String defaultCharset) {
		this.defaultCharset = (defaultCharset != null ? defaultCharset : "UTF-8");
	}
	@Override
	protected Message createMessage(Object objectToConvert, MessageProperties messageProperties) {
		byte[] bytes = null;
		try {
			String jsonString = FastJsonUtils.toJSONString(objectToConvert);
			//System.out.println("转换JSON串:"+jsonString);
			bytes = jsonString.getBytes(this.defaultCharset);
		} catch (UnsupportedEncodingException e) {
			throw new MessageConversionException("Failed to convert Message content", e);
		}
		messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
		messageProperties.setContentEncoding(this.defaultCharset);
		if (bytes != null) {
			messageProperties.setContentLength(bytes.length);
		}
		return new Message(bytes, messageProperties);
	}

	@Override//主动转换成基类
	public MQBase fromMessage(Message message) throws MessageConversionException {
		String json = "";
		MQBase base = null;
		try {
			json = new String(message.getBody(), defaultCharset);
			System.out.println("JSON串转换对象:"+json);
			if(JUtils.isEmpty(json))
				return null;
			
			base = FastJsonUtils.parseObject(json,MQBase.class);
			base.setMessage(json);
		} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
			return null;
		}
		return base;
	}
	
}
