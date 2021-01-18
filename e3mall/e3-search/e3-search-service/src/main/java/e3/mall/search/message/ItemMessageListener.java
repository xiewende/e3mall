package e3.mall.search.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.or.ThreadGroupRenderer;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import e3mall.common.domain.SearchItem;
import e3mall.search.mapper.ItemMapper;

//接收商品添加时的ID完成索引库的同步

public class ItemMessageListener implements MessageListener {
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private SolrServer service;

	@Override
	public void onMessage(Message me) {
		
		try {
			//从消息中获取id
			TextMessage message = (TextMessage) me;
			String text = message.getText();
			Long itemid = new Long(text);
			
			//等待商品添加事务提交，再来查询
			Thread.sleep(1000);
			
			//根据id查询商品信息
			SearchItem searchItem = itemMapper.getItemById(itemid);
			//创建一个文本对象
			SolrInputDocument document = new SolrInputDocument();
			//像文本对象里面添加一个域
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			//将文档写入索引库
			service.add(document);
			//提交
			service.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
