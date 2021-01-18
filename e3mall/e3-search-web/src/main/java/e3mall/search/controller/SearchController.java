package e3mall.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import e3mall.common.domain.SearchResult;
import e3mall.search.service.SearchService;

//商品搜索
@Controller
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/search")
	public String searchItemList(String keyword,@RequestParam(defaultValue="1")Integer page,Model model) throws Exception {
		keyword = new String(keyword.getBytes("iso-8859-1"),"utf-8");
		//查询商品列表
		Integer rows = 60;
		SearchResult result = searchService.search(keyword, page, rows);
		//把结果传递给界面
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", result.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("recourdCount", result.getRecourdCount());
		model.addAttribute("itemList", result.getItemList());
		//返回逻辑视图
		return "search";
	}

}
