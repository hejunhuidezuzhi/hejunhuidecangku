package cn.appsys.service.devuser;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.devuser.DevuserMapper;
import cn.appsys.pojo.Devuser;

@Service("dev_userService")
public class DevuserServiceImpl implements DevuserService {
	@Resource
	private DevuserMapper dev_userMapper;
	@Override
	public Devuser login(String devCode, String devPassword) {
		Devuser dev_user=null;
		try {
			dev_user=dev_userMapper.login(devCode, devPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dev_user;
	}

}
