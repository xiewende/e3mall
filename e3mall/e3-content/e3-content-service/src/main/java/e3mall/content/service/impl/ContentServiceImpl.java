package e3mall.content.service.impl;

import java.util.Date;
import java.util.List;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.tools.internal.xjc.model.CElement;

import e3mall.common.domain.EasyUIDataGridResult;
import e3mall.common.jedis.JedisClient;
import e3mall.common.utils.E3Result;
import e3mall.common.utils.JsonUtils;
import e3mall.content.service.ContentService;
import e3mall.domain.TbContent;
import e3mall.domain.TbContentExample;
import e3mall.domain.TbContentExample.Criteria;

import e3mall.mapper.TbContentMapper;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;

	//查询某叶子节点的对应的内容
	public EasyUIDataGridResult getContentList(int page, int rows, Long categoryId) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExample(example);
		//创建返回值对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		//取分页信息
		PageInfo<TbContent> pageinfo = new PageInfo<>(list);
		//取总记录数
		long total = pageinfo.getTotal();
		result.setTotal(total);
		return result;
	}

	@Override
	public E3Result addContent(TbContent content) {
		// TODO Auto-generated method stub
		content.setUpdated(new Date());
		content.setCreated(new Date());
		contentMapper.insert(content);
		
		//缓存数据同步
		jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
		
		return E3Result.ok();
	}

	//根据分类的id查询内容
	public List<TbContent> getContentListByCid(long id) {
		//查询缓存
		try {
			//如果缓存中又数据  直接响应
			String json = jedisClient.hget(CONTENT_LIST, id+"");
			if(StringUtils.isNotBlank(json)) {
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//如果没有查询数据库
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(id);
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		
		//将查询到的结果存到缓存中
        try {
			jedisClient.hset(CONTENT_LIST, id+"", JsonUtils.objectToJson(list));  //CONTENT_LIST为key  cid为filedid
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	

}
