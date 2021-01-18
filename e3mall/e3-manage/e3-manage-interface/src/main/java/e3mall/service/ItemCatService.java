package e3mall.service;

import java.util.List;

import e3mall.common.domain.EasyUITreeNode;

//分类接口

public interface ItemCatService {
	
	//根据父节点id查询子节点  就是分类的层次结构
	public List<EasyUITreeNode> getItemCatlist(long parentId);

}
