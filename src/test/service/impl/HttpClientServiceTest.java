package test.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import test.BaseSpringTest;
import cn.seagen.base.httpclient.HttpClientService;
import cn.seagen.base.utils.FastJsonUtils;

public class HttpClientServiceTest extends BaseSpringTest {

	private HttpClientService httpClientService;
	
	@Resource
	public void setHttpClientService(HttpClientService httpClientService) {
		this.httpClientService = httpClientService;
	}
	@Test//已测试
	public void doGetTest1(){
		String url = "http://www.weather.com.cn/data/cityinfo/101280501.html";
		String result = httpClientService.doGet(url);
		System.out.println("doGetTest1返回:"+result);
		//{"weatherinfo":{"city":"汕头","cityid":"101280501","temp1":"11℃","temp2":"20℃","weather":"晴","img1":"n0.gif","img2":"d0.gif","ptime":"18:00"}}
	}
	@Test//已测试
	public void doGetTest2(){
		/**
		 * http://www.kuaidi100.com/query?type=快递公司代号&postid=快递单号 测试用例 
			ps:快递公司编码:申通="shentong" EMS="ems" 顺丰="shunfeng" 圆通="yuantong" 中通="zhongtong" 韵达="yunda" 
			天天="tiantian" 汇通="huitongkuaidi" 全峰="quanfengkuaidi" 德邦="debangwuliu" 宅急送="zhaijisong"
		 */
		String url = "http://www.kuaidi100.com/query";
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", "shunfeng");
		map.put("postid", "025318850401");
		String result = httpClientService.doGet(url,map );
		System.out.println("doGetTest2返回:"+result);
		//{"message":"快递公司参数异常：cookie空指针异常","nu":"","ischeck":"0","condition":"","com":"","status":"404","state":"0","data":[]}
	}
	@Test//已测试
	public void doPostTest1(){
		String url = "http://apis.juhe.cn/mobile/get";
		String result = httpClientService.doPost(url);
		System.out.println("doPostTest1返回:"+result);
		//{"resultcode":"101","reason":"错误的请求KEY!","result":null,"error_code":10001}
	}
	@Test//已测试
	public void doPostTest2(){
		String url = "http://www.kuaidi100.com/query";
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", "yunda");
		map.put("postid", "3831182816959");
		String result = httpClientService.doPost(url,map);
		System.out.println("doPostTest2返回:"+result);
		//{"message":"ok","nu":"3831182816959","ischeck":"1","condition":"F00","com":"yunda","status":"200","state":"3","data":[{"time":"2017-11-08 16:16:46","ftime":"2017-11-08 16:16:46","context":"[陕西渭南公司职教园便民服务站分部]快件已被 本人 签收","location":"陕西渭南公司职教园便民服务站分部"},{"time":"2017-11-08 14:17:50","ftime":"2017-11-08 14:17:50","context":"[陕西渭南公司职教园便民服务站分部]进行派件扫描；派送业务员：罗飞凡；联系电话：13038425666","location":"陕西渭南公司职教园便民服务站分部"},{"time":"2017-11-08 13:32:06","ftime":"2017-11-08 13:32:06","context":"[陕西渭南公司]进行快件扫描，将发往：陕西渭南公司职教园便民服务站分部","location":"陕西渭南公司"},{"time":"2017-11-08 09:26:27","ftime":"2017-11-08 09:26:27","context":"[陕西西安分拨中心]从站点发出，本次转运目的地：陕西渭南公司","location":"陕西西安分拨中心"},{"time":"2017-11-08 08:50:01","ftime":"2017-11-08 08:50:01","context":"[陕西西安分拨中心]在分拨中心进行卸车扫描","location":"陕西西安分拨中心"},{"time":"2017-11-07 03:18:13","ftime":"2017-11-07 03:18:13","context":"[广东广州分拨中心]进行装车扫描，即将发往：陕西西安分拨中心","location":"广东广州分拨中心"},{"time":"2017-11-07 03:16:57","ftime":"2017-11-07 03:16:57","context":"[广东广州分拨中心]在分拨中心进行称重扫描","location":"广东广州分拨中心"},{"time":"2017-11-07 01:31:01","ftime":"2017-11-07 01:31:01","context":"[广东广州白云区嘉禾公司]进行下级地点扫描，将发往：陕西西安分拨中心","location":"广东广州白云区嘉禾公司"},{"time":"2017-11-06 19:10:37","ftime":"2017-11-06 19:10:37","context":"[广东广州白云区嘉禾公司]进行揽件扫描","location":"广东广州白云区嘉禾公司"}]}
	}
	@Test//已测试
	public void doPostJsonTest1(){
		String url = "http://localhost/DealDataServlet/MyServlet";
		Response response = new Response();
		List<BarCode> data = new ArrayList<BarCode>();
		BarCode barCode = new BarCode();
		barCode.setPackageBarcode("3333333");
		data.add(barCode);
		response.setData(data );
		response.setMachineCode("code");
		response.setToken("token");
		String json = FastJsonUtils.toJSONString(response);
		String result = httpClientService.doPostJson(url, json );
		System.out.println("doPostJsonTest1返回:"+result);
	}
	class Response {

		private String machineCode;
		private String token;
		List<BarCode> data;
		public String getMachineCode() {
			return machineCode;
		}
		public void setMachineCode(String machineCode) {
			this.machineCode = machineCode;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public List<BarCode> getData() {
			return data;
		}
		public void setData(List<BarCode> data) {
			this.data = data;
		}
	}
	class BarCode {

		private String packageBarcode;
		private long scanTime;
		public String getPackageBarcode() {
			return packageBarcode;
		}
		public void setPackageBarcode(String packageBarcode) {
			this.packageBarcode = packageBarcode;
		}
		@SuppressWarnings("deprecation")
		public String getScanTime() {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(scanTime);
			return calendar.getTime().toLocaleString();
		}
		public void setScanTime(long scanTime) {
			this.scanTime = scanTime;
		}
		@Override
		public String toString() {
			return "[条码=" + packageBarcode + ", 时间="+ getScanTime() + "]";
		}
	}
}
