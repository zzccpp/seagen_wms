package cn.seagen.base.service;

public interface MqProducer {
	/**
     * 发送消息到指定队列
     * @param queueKey
     * @param object
     */
    public boolean sendDataToQueue(String queueKey, Object object);
    /**
     * 发送消息到CQueue队列
     * @param object
     */
    public boolean sendToCqueue(Object object);
    /**
     * 发送消息到JQueue队列
     * @param object
     */
    public boolean sendToJqueue(Object object);
    /**
     * 发送消息到FQueue队列
     * @param object
     */
    public boolean sendToFqueue(Object object);
}
