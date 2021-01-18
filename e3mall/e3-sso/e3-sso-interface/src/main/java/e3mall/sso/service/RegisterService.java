package e3mall.sso.service;

import e3mall.common.utils.E3Result;
import e3mall.domain.TbUser;

public interface RegisterService {
	
	E3Result checkData(String parm , int type);
	E3Result register(TbUser user);

}
