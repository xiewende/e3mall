package e3mall.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import e3mall.common.domain.EasyUIDataGridResult;
import e3mall.common.jedis.JedisClient;
import e3mall.common.utils.E3Result;
import e3mall.common.utils.IDUtils;
import e3mall.common.utils.JsonUtils;
import e3mall.domain.TbItem;
import e3mall.domain.TbItemDesc;
import e3mall.domain.TbItemExample;
import e3mall.domain.TbItemExample.Criteria;
import e3mall.mapper.TbItemDescMapper;
import e3mall.mapper.TbItemMapper;
import e3mall.service.ItemService;

//商品管理

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource
	private Destination topicDestination;
	@Autowired
	private JedisClient jedusClient;
	
	public TbItem getItemById(long id) {
		//查询huancun
		try {
			String json = jedusClient.get("ITEM_INFO:"+id+":BASE");
			if(StringUtils.isNotBlank(json)) {
				TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
				return tbItem;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	/**
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andIdEqualTo(id);
		//执行查询
		List<TbItem> list = itemMapper.selectByExample(example);
		TbItem it = list.get(0);
		*/
		TbItem tbItem = itemMapper.selectByPrimaryKey(id);
		//把结果添加到缓存
		try {
			jedusClient.set("ITEM_INFO:"+id+":BASE", JsonUtils.objectToJson(tbItem));
			//设置过期时间
			jedusClient.expire("ITEM_INFO:"+id+":BASE", 3600);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tbItem;
	}

	
	public EasyUIDataGridResult getItemList(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		//创建返回值对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		//取分页信息
		PageInfo<TbItem> pageinfo = new PageInfo<>(list);
		//取总记录数
		long total = pageinfo.getTotal();
		result.setTotal(total);
		return result;
	}


	@Override
	public E3Result addItem(TbItem item, String desc) {
		//工具类生成商品id
	    long itemid = IDUtils.genItemId();
		//补全商品信息
	    item.setId(itemid);
	    //1正常 2下架 3删除
	    item.setStatus((byte) 1);
	    item.setCreated(new Date());
	    item.setUpdated(new Date());
		//向商品表插入数据
	    itemMapper.insert(item);
		//创建商品描述表的domain对象 并且补全属性
	    TbItemDesc itemDesc = new TbItemDesc();
	    itemDesc.setItemId(itemid);
	    itemDesc.setItemDesc(desc);
	    itemDesc.setCreated(new Date());
	    itemDesc.setUpdated(new Date());
		//向商品描述表插入数据
	    itemDescMapper.insert(itemDesc);
	    
	    //发送商品添加信息，到索引库
	    jmsTemplate.send(topicDestination,new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(itemid+"");
				return textMessage;
			}
		});
	    
	    //返回成功数据
		return E3Result.ok();
	}


	@Override
	public TbItemDesc getItemDescById(long itemid) {
		//查询huancun
		try {
			String json = jedusClient.get("ITEM_INFO:"+itemid+":DESC");
			if(StringUtils.isNotBlank(json)) {
				TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return tbItemDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbItemDesc desc = itemDescMapper.selectByPrimaryKey(itemid);
		//把结果添加到缓存
		try {
			jedusClient.set("ITEM_INFO:"+itemid+":DESC", JsonUtils.objectToJson(desc));
			//设置过期时间
			jedusClient.expire("ITEM_INFO:"+itemid+":DESC", 3600);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return desc;
	}

}
