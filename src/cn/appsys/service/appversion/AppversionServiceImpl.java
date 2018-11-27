package cn.appsys.service.appversion;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appversion.AppversionMapper;
import cn.appsys.pojo.Appversion;

@Service("appversionService")
public class AppversionServiceImpl implements AppversionService {
	@Resource
	private AppversionMapper appversionMapper;

	@Override
	public List<Appversion> getByappId(String id) {
		List<Appversion> list=new ArrayList<>();
		try {
			list=appversionMapper.getByappId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int Addappversion(Appversion appversion) {
		int count=0;
		try {
			count=appversionMapper.Addappversion(appversion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int deleteAppversion(String id) {
		int count=0;
		try {
			count=appversionMapper.deleteAppversion(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
		
	}

	@Override
	public Appversion GetByappidDate(Integer appId) {
		Appversion appversion=null;
		try {
			appversion=appversionMapper.GetByappidDate(appId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appversion;
	}

	@Override
	public int deleteApkFile(String id) {
		int count=0;
		try {
			count=appversionMapper.deleteApkFile(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int updateAppversion(Appversion appversion) {
		int count=0;
		try {
			count=appversionMapper.updateAppversion(appversion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}


}
