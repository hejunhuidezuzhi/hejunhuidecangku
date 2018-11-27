package cn.appsys.service.appinfo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appinfo.AppinfoMapper;
import cn.appsys.pojo.Appinfo;

@Service("appinfoService")
public class AppinfoServiceImpl implements AppinfoService {
	@Resource
	private AppinfoMapper appinfoMapper;
	@Override
	public int GetAppinfoCount(String querySoftwareName, String queryStatus, String queryCategoryLevel1,
			String queryCategoryLevel2, String queryCategoryLevel3, String queryFlatformId) {
		int count=0;
		try {
			count=appinfoMapper.GetAppinfoCount(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public List<Appinfo> GetAppinfoList(String querySoftwareName, String queryStatus, String queryCategoryLevel1,
			String queryCategoryLevel2, String queryCategoryLevel3, String queryFlatformId, int currentPageNo,
			int pagesize) {
		
		List<Appinfo> list=new ArrayList<>();
		try {
			currentPageNo=(currentPageNo-1)*pagesize;
			list=appinfoMapper.GetAppinfoList(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, currentPageNo, pagesize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public Appinfo GetByAPKName(String APKName) {
		Appinfo appinfo=null;
		try {
			appinfo=appinfoMapper.GetByAPKName(APKName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appinfo;
	}
	@Override
	public int Addappinfo(Appinfo appinfo) {
		int count=0;
		try {
			count=appinfoMapper.Addappinfo(appinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public Appinfo GetByappId(String id) {
		Appinfo appinfo=null;
		try {
			appinfo=appinfoMapper.GetByappId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appinfo;
	}
	@Override
	public int updateAppinfo(Appinfo appinfo) {
		int count=0;
		try {
			count=appinfoMapper.updateAppinfo(appinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public int deleteAppLogo(String id) {
		int count=0;
		try {
			count=appinfoMapper.deleteAppLogo(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public int deleteApp(String id) {
		int count=0;
		try {
			count=appinfoMapper.deleteApp(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public int updateversonId(Integer id, Integer versionId) {
		int count=0;
		try {
			count=appinfoMapper.updateversonId(id, versionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public int updatestatus(String status, String id) {
		int count=0;
		try {
			count=appinfoMapper.updatestatus(status, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

}
