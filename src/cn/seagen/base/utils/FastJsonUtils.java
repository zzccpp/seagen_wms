package cn.seagen.base.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.seagen.base.bean.EmailInfo;

import com.alibaba.fastjson.JSON;
/**
 * 对fastjson进行封装,可以处理避免如下功能.
 * 1、统一处理异常
 * 2、日期等格式转换-可扩展
 * @author zcp
 */
public class FastJsonUtils {
	private static Logger logger = LogManager.getLogger(FastJsonUtils.class.getName());
	/**
	 * 字符串转成对象
	 * @param text 待转换字符串
	 * @return 如果字符串转成功则返回对象,失败返回null
	 */
	public static Object parseObject(String text){
		try {
			return JSON.parseObject(text);
		} catch (Exception e) {
			logger.error("字符串转对象异常!", e);
		}
		return null;
	}
	/**
	 * 字符串转成对象
	 * @param text 待转换字符串
	 * @param clazz 对象字节码
	 * @return 如果字符串转成功则返回对象,失败返回null
	 */
	public static <T> T parseObject(String text,Class<T> clazz){
		try {
			return JSON.parseObject(text, clazz);
		} catch (Exception e) {
			logger.error("字符串转对象异常!", e);
		}
		return null;
	}
	/**
	 * 对象序列化转成字符串
	 * @param object 待转换的对象
	 * @return 成功返回字符串,失败返回null
	 */
	public static String toJSONString(Object object){
		try {
			return JSON.toJSONString(object);
		} catch (Exception e) {
			logger.error("对象转成字符串异常!", e);
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		EmailInfo info = new EmailInfo();
		info.setContent("asdasdasd");
		info.setCcs(new String[]{"3332","123123"});
		info.setSubject("00000000");
		
		String cc = toJSONString(info);
		System.out.println(cc);
		
		System.out.println(parseObject("{123123}", EmailInfo.class).getSubject());
		
		
	}
}
