package test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试基础类
 * @author zcp
 */
@RunWith(SpringJUnit4ClassRunner.class)//使用junit4进行测试 
@ContextConfiguration({"classpath:config/spring/spring.xml"})
//@Transactional
public class BaseSpringTest {

}
