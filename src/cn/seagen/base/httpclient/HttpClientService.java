package cn.seagen.base.httpclient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
/**
 * HttpClient操作封装类
 * @author zcp
 */
@Service
public class HttpClientService {
	private Logger logger = LogManager.getLogger(HttpClientService.class.getName());
	private final String CHARSET = "UTF-8";
	//httpClient对象
	private CloseableHttpClient httpClient;
	//请求配置对象
	private RequestConfig requestConfig;
	@Resource
	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}
	@Resource
	public void setRequestConfig(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}
	/**
	 * 无参Get请求,
	 * @param url 请求资源地址
	 * @return 成功(200)返回【正确字符串信息】、非200 返回 【"0"+StatusCode】、请求异常返回 【"-1"+e.getMessage()】
	 */
	public String doGet(String url){
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			if(response.getStatusLine().getStatusCode()==200){
				return EntityUtils.toString(response.getEntity(),CHARSET);
			}else{
				logger.info("请求"+url+"返回:"+response.getStatusLine().getStatusCode());
				return "0"+response.getStatusLine().getStatusCode();
			}
		} catch (Exception e) {
			logger.error("无参doGet请求异常!", e);
			return "-1"+e.getMessage();
		} finally{
			try {
				if(null!=response)
					response.close();
			} catch (IOException e) {
				logger.error("response关闭异常!", e);
			}
		}
	}
	/**
	 * 有参Get请求
	 * @param url 请求资源地址
	 * @param params 参数Map集合
	 * @return 成功(200)返回【正确字符串信息】、非200 返回 【"0"+StatusCode】、请求异常返回 【"-1"+e.getMessage()】
	 */
	public String doGet(String url,Map<String,String> params){
		try {
			URIBuilder uriBuilder = new URIBuilder(url);
			if(null!=params){
				for (Map.Entry<String, String> entry : params.entrySet()) {
					uriBuilder.addParameter(entry.getKey(), entry.getValue());
				}
			}
			return doGet(uriBuilder.build().toString());
		} catch (URISyntaxException e) {
			logger.error("拼装URL异常!", e);
			return "-1"+e.getMessage();
		}
	}
	/**
	 * 无参Post请求
	 * @param url 请求资源地址
	 * @return 成功(200)返回【正确字符串信息】、非200 返回 【"0"+StatusCode】、请求异常返回 【"-1"+e.getMessage()】
	 */
	public String doPost(String url){
		return doPost(url, null);
	}
	/**
	 * 有参Post请求
	 * @param url 请求资源地址
	 * @param params 请求参数Map集合
	 * @return 成功(200)返回【正确字符串信息】、非200 返回 【"0"+StatusCode】、请求异常返回 【"-1"+e.getMessage()】
	 */
	public String doPost(String url,Map<String, String> params){
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		try {
			//设置参数
			if(null!=params){
				List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs);
				httpPost.setEntity(entity);
			}
			//执行请求
			response = httpClient.execute(httpPost);
			if(response.getStatusLine().getStatusCode()==200){
				return EntityUtils.toString(response.getEntity(),CHARSET);
			}else{
				logger.info("请求"+url+"返回:"+response.getStatusLine().getStatusCode());
				return "0"+response.getStatusLine().getStatusCode();
			}
		} catch (Exception e) {
			logger.error("doPost请求异常!", e);
			return "-1"+e.getMessage();
		} finally{
			try {
				if(null!=response)
					response.close();
			} catch (IOException e) {
				logger.error("response关闭异常!", e);
			}
		}
	}
	/**
	 * 有参Post请求,参数JSON格式字符串
	 * @param url 请求资源地址
	 * @param json 请求参数json字符串
	 * @return 成功(200)返回【正确字符串信息】、非200 返回 【"0"+StatusCode】、请求异常返回 【"-1"+e.getMessage()】
	 */
	public String doPostJson(String url,String json){
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		try {
			if(StringUtils.isNotBlank(json)){
				httpPost.addHeader("Content-Type", "application/json");
				httpPost.setEntity(new StringEntity(json,CHARSET));
			}
			//执行请求
			response = httpClient.execute(httpPost);
			if(response.getStatusLine().getStatusCode()==200){
				return EntityUtils.toString(response.getEntity(),CHARSET);
			}else{
				logger.info("请求"+url+"返回:"+response.getStatusLine().getStatusCode());
				return "0"+response.getStatusLine().getStatusCode();
			}
		} catch (Exception e) {
			logger.error("doPost请求异常!", e);
			return "-1"+e.getMessage();
		} finally{
			try {
				if(null!=response)
					response.close();
			} catch (IOException e) {
				logger.error("response关闭异常!", e);
			}
		}
	}
}
