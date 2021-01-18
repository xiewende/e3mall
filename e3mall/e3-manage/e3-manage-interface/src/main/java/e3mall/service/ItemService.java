package e3mall.service;

import e3mall.common.domain.EasyUIDataGridResult;
import e3mall.common.utils.E3Result;
import e3mall.domain.TbItem;
import e3mall.domain.TbItemDesc;

public interface ItemService {
	
	public TbItem getItemById(long id);
	public EasyUIDataGridResult getItemList(int page,int rows);
	public E3Result addItem(TbItem item,String desc);
	TbItemDesc getItemDescById(long itemid);

}
