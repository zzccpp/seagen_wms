package cn.seagen.base.httpclient;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * 定时关闭无效的HttpClient连接
 * @author zcp
 */
public class IdleConnectionEvictor extends Thread{

	private Logger logger = LogManager.getLogger(IdleConnectionEvictor.class.getName());
    private HttpClientConnectionManager httpClientConnectionManager;

    private volatile boolean shutdown;

    public IdleConnectionEvictor(HttpClientConnectionManager httpClientConnectionManager) {
        this.httpClientConnectionManager = httpClientConnectionManager;
        this.start();
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(5000);
                   // System.out.println("执行关闭失效的连接");
                    httpClientConnectionManager.closeExpiredConnections();
                }
            }
        } catch (InterruptedException e) {
            // 结束
        	logger.error("关闭httpclient无效连接异常!", e);
        }
    }
    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
}
