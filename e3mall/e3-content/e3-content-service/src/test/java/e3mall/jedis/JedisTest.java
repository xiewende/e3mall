package e3mall.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import e3mall.common.jedis.JedisClient;

public class JedisTest {
	
	@Test
	public void testJedisClient() {
		//初始化spring容器/e3-content-service/src/main/resources/spring/applicationContext-redis.xml
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		//从容器中获取jedisClient对象
		JedisClient jedisClient = ac.getBean(JedisClient.class);
		jedisClient.set("test", "myClient");
		System.out.println("laile");
		System.out.println(jedisClient.get("test"));
	}

}
