package e3mall.content.service;

import java.util.List;

import e3mall.common.domain.EasyUIDataGridResult;
import e3mall.common.utils.E3Result;
import e3mall.domain.TbContent;


public interface ContentService {
	
	public EasyUIDataGridResult getContentList(int page,int rows,Long categoryId);
	
	public E3Result addContent(TbContent content);
	
	public List<TbContent> getContentListByCid(long id);

}
