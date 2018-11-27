package cn.appsys.service.devuser;


import cn.appsys.pojo.Devuser;

public interface DevuserService {
	public Devuser login(String devCode,String devPassword);
}	
