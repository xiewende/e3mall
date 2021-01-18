package e3mall.content.service;

import java.util.List;

import e3mall.common.domain.EasyUITreeNode;
import e3mall.common.utils.E3Result;

public interface ContentCategoryService {
	
	List<EasyUITreeNode> getContentCatList(long parentId);
	
	public E3Result addContentCategory(long parentId,String name);

}
