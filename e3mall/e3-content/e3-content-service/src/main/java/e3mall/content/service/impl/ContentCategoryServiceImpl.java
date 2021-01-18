package e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import e3mall.common.domain.EasyUITreeNode;
import e3mall.common.utils.E3Result;
import e3mall.content.service.ContentCategoryService;
import e3mall.domain.TbContentCategory;
import e3mall.domain.TbContentCategoryExample;
import e3mall.domain.TbContentCategoryExample.Criteria;
import e3mall.mapper.TbContentCategoryMapper;

//内容分离管理
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentMapper;
	
	public List<EasyUITreeNode> getContentCatList(long parentId) {
		//根据parentId查询子节点
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria cr = example.createCriteria();
		//设置执行条件
		cr.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> catlist = contentMapper.selectByExample(example);
		//转换成EasyUITreeNode列表返回
		List<EasyUITreeNode> nodelist = new ArrayList<>();
		for(TbContentCategory tc : catlist) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tc.getId());
			node.setText(tc.getName());
			node.setState(tc.getIsParent()?"closed":"open");
			nodelist.add(node);
		}
		return nodelist;
	}

	@Override
	public E3Result addContentCategory(long parentId, String name) {
		//创建一个domain对象
		TbContentCategory contentCategory = new TbContentCategory();
		//设置属性
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		//1正常   2 删除
		contentCategory.setStatus(1);
		//默认排序是1
		contentCategory.setSortOrder(1);
		//洗添加的节点一定是叶子节点
		contentCategory.setIsParent(false);
		contentCategory.setUpdated(new Date());
		contentCategory.setCreated(new Date());
		//插入数据库
		contentMapper.insert(contentCategory);
		//判断父节点的isparent属性   原来是否为父节点
		//根据parentId查询父节点
		TbContentCategory parent = contentMapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent()) {
			parent.setIsParent(false);
			//更新到数据库
			contentMapper.updateByPrimaryKey(parent);
		}
		//返回结果  包含damain
		return E3Result.ok(contentCategory);
	}

}
