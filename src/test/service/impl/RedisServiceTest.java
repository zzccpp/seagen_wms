package test.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import test.BaseSpringTest;
import cn.seagen.base.redis.JedisService;
/**
 * redis操作的测试,
 * 编写测试类 1、继承BaseSpringTest类   2、在测试方法上添加@Test注解  3、执行(选中方法名右键选中Run As>Junit执行 )
 * @author zcp
 */
public class RedisServiceTest extends BaseSpringTest {

	private JedisService jedisService;
	@Resource
	public void setJedisService(JedisService jedisService) {
		this.jedisService = jedisService;
	}
	@Test
	public void set(){
		boolean result = jedisService.setString("junit", "junit");
		Assert.assertTrue(result);
	}
	@Test
	public void setdb(){
		boolean result = jedisService.setString("xx1", "junit44",2);
		Assert.assertTrue(result);
		jedisService.setString("junit1", "junit");
	}
	@Test//测试并发情况是否会写串的情况
	public void delkey1(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i <1000; i++) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					jedisService.setString("db0", "junit"+i);
				}
				System.out.println("db0执行完成");
			}
		}).start();
		/*new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i <100; i++) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					jedisService.setString("db1", "junit"+i,1);
				}
				System.out.println("db1执行完成");			
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i <100; i++) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					jedisService.setString("db2", "junit"+i,2);
				}
				System.out.println("db2执行完成");
			}
		}).start();*/
		try {
			Thread.sleep(20*60*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("整个测试完成!");
	}
	@Test
	public void setExpire(){
		jedisService.setStringExpire("expire", "Expire");
	}
	@Test
	public void setExpiredb(){
		jedisService.setStringExpire("expire", "Expire2",2);
	}
	@Test
	public void getString(){
		System.out.println(jedisService.getString("expire"));
	}
	@Test
	public void getStringdb(){
		System.out.println(jedisService.getString("expire1",2));
	}
	@Test
	public void existsData(){
		System.out.println(jedisService.existsData("expire","Expire"));
	}
	@Test
	public void existsDatadb(){
		System.out.println(jedisService.existsData("expire","Expire2",2));
	}
	@Test
	public void getList(){
		List<Object> list = jedisService.getList("list1");
		for (Object o:list) {
			System.out.println(o);
		}
	}
	@Test
	public void getListdb(){
		List<String> list = jedisService.getList("xx_list",2);
		for (String o:list) {
			System.out.println(o);
		}
	}
	@Test
	public void setList(){
		System.out.println(jedisService.setList("list1","test"));
	}
	@Test
	public void setListdb(){
		System.out.println(jedisService.setList("xx_list","test1",2));
	}
	@Test
	public void delkey(){
		System.out.println(jedisService.delkey("list1"));
	}
	@Test
	public void delkeydb(){
		System.out.println(jedisService.delkey("xx_list",2));
	}
}
