package e3mall.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import e3mall.common.utils.CookieUtils;
import e3mall.common.utils.E3Result;
import e3mall.sso.service.LoginService;

//登陆处理
@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping("/page/login")
	public String showLogin() {
		return "login";
	}
	
	@RequestMapping(value="/user/login" , method=RequestMethod.POST)
	@ResponseBody
	public E3Result login(String username,String password,
			HttpServletRequest request,HttpServletResponse response) {
		E3Result result = loginService.userLogin(username, password);
		//判断是否登陆成功
		if(result.getStatus() == 200) {//成功将token写入kooie
			String token = result.getData().toString();
			//登陆成功需要将token写入cookie
			CookieUtils.setCookie(request, response, "token", token);
		}
		return result;
	}

}
