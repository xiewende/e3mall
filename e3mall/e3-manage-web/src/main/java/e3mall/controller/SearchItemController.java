package e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import e3mall.common.utils.E3Result;
import e3mall.search.service.SearchItenService;

//导入商品数据到索引库

@Controller
public class SearchItemController {
	
	@Autowired
	private SearchItenService searchItemService;
	
	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result importItem() {
		E3Result e3result = searchItemService.importAllItems();
		return e3result;
	}

}
