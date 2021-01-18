package e3mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import e3mall.common.domain.EasyUITreeNode;
import e3mall.domain.TbItemCat;
import e3mall.domain.TbItemCatExample;
import e3mall.domain.TbItemCatExample.Criteria;
import e3mall.mapper.TbItemCatMapper;
import e3mall.service.ItemCatService;

//分类管理
@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private TbItemCatMapper itenCatmapper;

	@Override
	public List<EasyUITreeNode> getItemCatlist(long parentId) {
		//根据parentID查询子节点列表
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbItemCat> list = itenCatmapper.selectByExample(example);
		//创建返回结果集list
		List<EasyUITreeNode> resultList = new ArrayList<>();
		//将从数据库中查询的数据取需要的封装到EasyUITreeNode对象中并且存到list集合
		for(TbItemCat cat : list) {
			//创建EasyUITreeNode对象
			EasyUITreeNode node = new EasyUITreeNode();
			//设置属性
			node.setId(cat.getId());
			node.setText(cat.getName());
			node.setState(cat.getIsParent()?"closed":"open");
			//将EasyUITreeNode对象添加到结果集
			resultList.add(node);
		}
		return resultList;
	}

}
