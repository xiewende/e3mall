package e3mall.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import e3mall.content.service.ContentService;
import e3mall.domain.TbContent;

//首页展示

@Controller
public class IndexController {
	
	@Value("${CONTENT_LUNBO_ID}")
	private Long CONTENT_LUNBO_ID;
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/index")
	public String showIndex(Model model) {
		List<TbContent> list =  contentService.getContentListByCid(CONTENT_LUNBO_ID);
		model.addAttribute("ad1List", list);
		return "index";
	}

}
