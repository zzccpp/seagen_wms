package cn.seagen.base.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

@SuppressWarnings("unchecked")
public class SpringContextUtils implements BeanFactoryAware {//BeanFactoryAware
	
	private static BeanFactory beanFactory;
	
	/**
	 * 获取Spring容器中bean对象
	 * @param beanName 对象ID名称
	 * @param cls 对象类型
	 * @return
	 */
	public synchronized static <T> T getBean(String beanName,Class<T> cls){
        return (T) beanFactory.getBean(beanName,cls);
    }
	/**
	 * 获取Spring容器中bean对象
	 * @param beanName 对象ID名称
	 * @return
	 */
	public synchronized static <T> T getBean(String beanName){
        return (T) beanFactory.getBean(beanName);
    }
	
	@Override
	public void setBeanFactory(BeanFactory factory) throws BeansException {
		beanFactory = factory;
	}

}
