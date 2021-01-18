package e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import e3mall.common.domain.EasyUIDataGridResult;
import e3mall.common.utils.E3Result;
import e3mall.domain.TbItem;
import e3mall.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemservice;
	
	@RequestMapping("/item")
	public 	@ResponseBody
	TbItem getItemById() {
		TbItem item = itemservice.getItemById(860275);
		System.out.println("laileme");
		return item;
	}
	
	@RequestMapping("/item/list")
	public @ResponseBody
	EasyUIDataGridResult getItemList(Integer page , Integer rows) {
		//调用服务获取结果集
		EasyUIDataGridResult result = itemservice.getItemList(page, rows);
		return result;
	}
	
	//添加方法
	@RequestMapping(value="/item/save", method=RequestMethod.POST)
	@ResponseBody
	public E3Result addItem(TbItem item,String desc) {
		E3Result e3 = itemservice.addItem(item, desc);
		return e3;
	}
	

}
