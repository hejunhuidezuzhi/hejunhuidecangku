package cn.appsys.service.appversion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Appversion;

public interface AppversionService {
	public List<Appversion> getByappId(String id);
	
	
	public int Addappversion(Appversion appversion);
	
	public int deleteAppversion(String id);
	
	public Appversion GetByappidDate(Integer appId);
	
	public int deleteApkFile(String id);
	
	public int updateAppversion(Appversion appversion);
}
