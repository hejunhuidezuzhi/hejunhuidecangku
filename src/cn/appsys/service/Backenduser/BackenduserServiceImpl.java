package cn.appsys.service.Backenduser;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.Backenduser.BackenduserMapper;
import cn.appsys.pojo.Backenduser;

@Service("backenduserService")
public class BackenduserServiceImpl implements BackenduserService {
	@Resource
	private BackenduserMapper backenduserMapper;
	@Override
	public Backenduser Backendlogin(String devCode, String devPassword) {
		Backenduser backenduser=null;
		try {
			backenduser=backenduserMapper.Backendlogin(devCode, devPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return backenduser;
	}

}
