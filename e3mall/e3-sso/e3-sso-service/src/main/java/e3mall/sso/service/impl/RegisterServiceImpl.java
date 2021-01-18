package e3mall.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.common.utils.StringUtils;

import e3mall.common.utils.E3Result;
import e3mall.domain.TbUser;
import e3mall.domain.TbUserExample;
import e3mall.domain.TbUserExample.Criteria;
import e3mall.mapper.TbUserMapper;
import e3mall.sso.service.RegisterService;

//用户注册处理
@Service
public class RegisterServiceImpl implements RegisterService {
	
	@Autowired
	private TbUserMapper userMapper;

	@Override
	public E3Result checkData(String parm, int type) {
		//根据不同的查询类型   用户名 1     手机号 2    邮箱3  生成不同的查询语句
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		if(type == 1) {
			criteria.andUsernameEqualTo(parm);
		}else if (type == 2) {
			criteria.andPhoneEqualTo(parm);
		}else if (type == 3) {
			criteria.andEmailEqualTo(parm);
		}else {
			return E3Result.build(400, "数据类型错误");
		}
		//执行查询
		List<TbUser> list = userMapper.selectByExample(example);
		//判断数据中是否拥有数据，没有返回true   有返回false
		if(list != null && list.size()>0) {
			return E3Result.ok(false);
		}
		
		return E3Result.ok(true);
	}

	@Override
	public E3Result register(TbUser user) {
		//数据有效性校验
		if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword()) 
				|| StringUtils.isBlank(user.getPhone())) {
			return E3Result.build(400, "用户数据不完整，注册失败");
		}
		E3Result result = checkData(user.getUsername(), 1);
		if(!(boolean) result.getData()) {
			return E3Result.build(400, "用户名已经重复");
		}
		result = checkData(user.getPhone(), 1);
		if(!(boolean) result.getData()) {
			return E3Result.build(400, "手机号已经占用");
		}
		//补全poji属性
		user.setCreated(new Date());
		user.setUpdated(new Date());
		//对密码进行加密  spring自带的util类
		String md5 = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5);
		//将数据插入数据库
		userMapper.insert(user);
		//返回添加成功信息
		return E3Result.ok();
		
		
	}

}
