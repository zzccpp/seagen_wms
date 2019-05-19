package cn.seagen.base.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cn.seagen.base.utils.JUtils;

public class UdpSocketClient {
	private static Logger logger = LogManager.getLogger(UdpSocketClient.class.getName());
	private static DatagramSocket ds;
	
	static {
		try {
			ds = new DatagramSocket();
		} catch (SocketException e) {
			logger.error("UDP socket初始化失败："+ e.getMessage(),e);
		}
	}
	
	public static synchronized void getSocketInstance(){
		try {
			if (ds == null)
				ds = new DatagramSocket();
		} catch (SocketException e) {
			logger.error("创建UDP socket失败："+ e.getMessage(),e);
		}
	}
	/**发送UDP信息*/
	public static void send(String data,String host,int port) {
		try {
			if(JUtils.isEmpty(data)){
				logger.error("UDP信息为空！");
				return ;
			}
			if(JUtils.isEmpty(host)){
				logger.error("IP地址为空！");
				return ;
			}
			if(0 >= port){
				logger.error("端口号错误！");
				return ;
			}
			DatagramPacket dp;
			if (ds == null) {
				getSocketInstance();
			}
			try {
				dp = new DatagramPacket(data.getBytes(), data.length(),
						InetAddress.getByName(host), port);
				ds.send(dp);
			} catch (UnknownHostException e) {
				logger.error("无法识别远程主机："+ e.getMessage(),e);
			} catch (IOException e) {
				logger.error("发送UDP信息失败："+ e.getMessage(),e);
			}
		} catch (Exception e) {
			logger.error("SocketUDPClient-> send异常："+ e.getMessage(),e);
		}
		
	}
	
}
