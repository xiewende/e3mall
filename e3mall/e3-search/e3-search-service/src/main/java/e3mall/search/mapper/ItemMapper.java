package e3mall.search.mapper;

import java.util.List;

import e3mall.common.domain.SearchItem;

public interface ItemMapper {
	
	//查询所有商品信息
	List<SearchItem> getItemList();
	//根据id查询
	SearchItem getItemById(long itemId);

}
