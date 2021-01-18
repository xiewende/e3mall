package e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import e3mall.common.domain.EasyUITreeNode;
import e3mall.service.ItemCatService;

//商品分类管理界面
@Controller
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	//查询分类的结构层次
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCat(
			@RequestParam(name="id",defaultValue="0")Long parentId){
		//调用服务查询节点列表
		List<EasyUITreeNode> list = itemCatService.getItemCatlist(parentId);
		return list;
	}

}
