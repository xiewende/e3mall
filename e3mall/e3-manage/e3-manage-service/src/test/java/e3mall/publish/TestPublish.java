package e3mall.publish;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import e3mall.domain.TbItem;
import e3mall.domain.TbItemExample;
import e3mall.mapper.TbItemMapper;

public class TestPublish {
	
	@Test
	public void testPublish() throws IOException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		System.out.println("服务已经启动。。。。。。");
		System.in.read();
		System.out.println("服务已经关闭。。。。。。。");
		
	}

}
