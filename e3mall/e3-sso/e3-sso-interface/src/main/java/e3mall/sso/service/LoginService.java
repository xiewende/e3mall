package e3mall.sso.service;
//登陆接口

import e3mall.common.utils.E3Result;

public interface LoginService {
	
	//参数是用户名和密码   
	//业务逻辑 1.判断用户名和密码是否正确  2.如果不正确返回登陆失败  3.如果正确生成token 4.将用户信息写入redis key:token value :用户信息
	//设置key的过期时间，相当于session的过期时间
	//将token返回
	//返回值E3result 其中包含token
	E3Result userLogin(String username,String password);

}
