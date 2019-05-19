package cn.seagen.base.service;

import cn.seagen.base.mq.dto.MQJb;


public interface JbService {
	/**
	 * 建包业务处理
	 * @param mQJb
	 * @return
	 */
	public boolean jbProcess(MQJb mqJb);
}
