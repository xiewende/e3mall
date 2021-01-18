package e3mall.pagehelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import e3mall.domain.TbItem;
import e3mall.domain.TbItemExample;
import e3mall.mapper.TbItemMapper;

public class PageHelperTest {
	
	@Test
	public void testPageHelper() {
		//执行sql语句之前设置分页信息使用PageHelper的startfangfa
		//执行查询
		//取分页信息 PageInfo 1.总记录数  2.总页数 3.当前页码
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		TbItemMapper it = ac.getBean(TbItemMapper.class);
		PageHelper.startPage(1, 10);
		TbItemExample ex = new TbItemExample();
		List<TbItem> list = it.selectByExample(ex);
		PageInfo<TbItem> pageinfo = new PageInfo<>(list);
		System.out.println(pageinfo.getTotal());
		System.out.println(pageinfo.getPages());
		System.out.println(list.size());
		
	}
	
	public static void main(String[] args) {
		//执行sql语句之前设置分页信息使用PageHelper的startfangfa
		//执行查询
		//取分页信息 PageInfo 1.总记录数  2.总页数 3.当前页码
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		TbItemMapper it = ac.getBean(TbItemMapper.class);
		PageHelper.startPage(1, 10);
		TbItemExample ex = new TbItemExample();
		List<TbItem> list = it.selectByExample(ex);
		PageInfo<TbItem> pageinfo = new PageInfo<>(list);
		System.out.println(pageinfo.getTotal());
		System.out.println(pageinfo.getPages());
		System.out.println(list.size());
	}

}
