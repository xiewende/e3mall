package e3mall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import e3mall.common.utils.FastDFSClient;
import e3mall.common.utils.JsonUtils;
import e3mall.common.utils.UploadFile;

@Controller
public class PictureController {
	
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	
	@RequestMapping(value="/pic/upload",produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")	
	public @ResponseBody
	String uploadPicture(MultipartFile uploadFile) {
		try {
			//把图片上传到服务器
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
			//取文件扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			System.out.println("文件全名字是："+originalFilename);//文件全名字是：456.jpg
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			//上传到服务器后得到一个图片的地址和文件名/group1/M00/00/00/wKgZhVxFNMCAbejtAAC0L5weuz0016.jpg
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			System.out.println("返回来的url是;"+url);
			//补充为完整的url http://192.168.25.133/group1/M00/00/00/wKgZhVxFNMCAbejtAAC0L5weuz0016.jpg
			url = IMAGE_SERVER_URL+url;
			System.out.println("完整的url="+url);
			//封装到map集合json格式返回   页面需要
			Map result = new HashMap<>();
			result.put("error", 0);
			result.put("url", url);
			return JsonUtils.objectToJson(result);
			//UploadFile upload = new UploadFile();
			//upload.setError(0);
			//upload.setUrl(url);
			//return upload;
		} catch (Exception e) {
			e.printStackTrace();
			Map result = new HashMap<>();
			result.put("error", 1);
			result.put("message", "上传图片失败！");
			return JsonUtils.objectToJson(result);
		}		
	}

}
