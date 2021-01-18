package e3mall.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import e3mall.common.jedis.JedisClient;
import e3mall.common.utils.E3Result;
import e3mall.common.utils.JsonUtils;
import e3mall.domain.TbUser;
import e3mall.domain.TbUserExample;
import e3mall.domain.TbUserExample.Criteria;
import e3mall.mapper.TbUserMapper;
import e3mall.sso.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;

	//参数是用户名和密码   
	//业务逻辑 1.判断用户名和密码是否正确  2.如果不正确返回登陆失败  3.如果正确生成token 4.将用户信息写入redis key:token value :用户信息
	//设置key的过期时间，相当于session的过期时间
	//将token返回
	//返回值E3result 其中包含token
	public E3Result userLogin(String username, String password) {
		//1.判断用户名和密码是否正确 
		//根据用户名查询用户信息
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		//执行查询
		List<TbUser> list = userMapper.selectByExample(example);
		if(list == null || list.size()==0) {
			//返回登陆失败
			return E3Result.build(400, "用户名或者密码错误");
		}
		//获取用户信息
		TbUser user = list.get(0);
		String  wo = user.getPassword();
		//判断密码是否正确
		if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(wo)){
			//返回登陆失败
			return E3Result.build(400, "用户名或者密码错误");
		}
		//3.如果正确生成token
		String token = UUID.randomUUID().toString();
		//4.将用户信息写入redis key:token value :用户信息
		user.setPassword(null);
		jedisClient.set("SESSION:"+token, JsonUtils.objectToJson(user));
		//设置key的过期时间，相当于session的过期时间 30分钟
		jedisClient.expire("SESSION:"+token, 1800);
		
		
		return E3Result.ok(token);
	}

}
