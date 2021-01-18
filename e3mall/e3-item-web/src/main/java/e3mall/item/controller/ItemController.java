package e3mall.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import e3mall.domain.TbItem;
import e3mall.domain.TbItemDesc;
import e3mall.service.ItemService;

//商品详情页面
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable Long itemId,Model model) {
		//调用服务查询商品基本信息
		TbItem tbItem = itemService.getItemById(itemId);
		Item item = new Item(tbItem);
		//取商品描述信息
		TbItemDesc des = itemService.getItemDescById(itemId);
		//将信息传递给页面
		model.addAttribute("item",item);
		model.addAttribute("itemDesc", des);
		return "item";
	}

}
