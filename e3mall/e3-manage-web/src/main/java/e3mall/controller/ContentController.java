package e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import e3mall.common.domain.EasyUIDataGridResult;
import e3mall.common.utils.E3Result;
import e3mall.content.service.ContentService;
import e3mall.domain.TbContent;

//内容管理
@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	//查询某叶子节点的所有内容
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentList(Integer page , Integer rows,Long categoryId ) {
		//调用服务获取结果集
		EasyUIDataGridResult result = contentService.getContentList(page, rows,categoryId);
		return result;
	}
	
	//新增一个内荣
	@RequestMapping(value="/content/save",method=RequestMethod.POST)	
	public @ResponseBody
	E3Result addContent(TbContent content) {
		E3Result e3Result = contentService.addContent(content);
		return e3Result;
	}

}
