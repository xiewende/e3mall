package e3mall.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;

import e3mall.common.jedis.JedisClient;
import e3mall.common.utils.E3Result;
import e3mall.common.utils.JsonUtils;
import e3mall.domain.TbUser;
import e3mall.sso.service.TokenService;
//根据token取用户信息
@Service
public class TokenServiceImpl implements TokenService {
	@Autowired
	private JedisClient jedisClient;

	@Override
	public E3Result getUserByToken(String token) {
		//根据token取到redis中的用户信息
		String json = jedisClient.get("SESSION:"+token);
		//娶不到用户信息，登陆已经过期，返回登陆过期
		if(StringUtils.isBlank(token)) {
			return E3Result.build(201, "用户登陆已经过期");
		}
		//取到用户信息更新token的过期时间
		jedisClient.expire("SESSION:"+token, 1800);
		//返回结果  E3result包含TbUser
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		return E3Result.ok(user);
	}

}
