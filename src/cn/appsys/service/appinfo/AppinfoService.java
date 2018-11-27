package cn.appsys.service.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Appinfo;

public interface AppinfoService {
	public int GetAppinfoCount(String querySoftwareName,String queryStatus,
								String queryCategoryLevel1,String queryCategoryLevel2,
								String queryCategoryLevel3,String queryFlatformId);
	
	public List<Appinfo> GetAppinfoList(String querySoftwareName,String queryStatus,
			String queryCategoryLevel1,String queryCategoryLevel2,
			String queryCategoryLevel3,String queryFlatformId,
			int currentPageNo,int pagesize);
	
	public Appinfo GetByAPKName(String APKName);
	
	public int Addappinfo(Appinfo appinfo);
	
	public Appinfo GetByappId(String id);
	
	public int updateAppinfo(Appinfo appinfo);
	
	public int deleteAppLogo(String id);
	
	public int deleteApp(String id);
	
	public int updateversonId(Integer id,Integer versionId);
	
	public int updatestatus(String status,String id);
}
