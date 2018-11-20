package com.service.provider;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.provider.ProviderMapper;
import com.entity.Provider;

@Service("providerService")
public class ProviderServiceImpl implements ProviderService {
	@Resource
	private ProviderMapper providerMapper;
	
	@Override
	public int getProvideCount(String queryProCode, String queryProName) {
		int count=0;
		try {
			count=providerMapper.getProvideCount(queryProCode, queryProName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<Provider> getProviderlist(String queryProCode, String queryProName, int currentPageNo,
			int pagesize) {
		List<Provider> list=new ArrayList<Provider>();
		try {
			currentPageNo=(currentPageNo-1)*pagesize;
			list=providerMapper.getProviderlist(queryProCode, queryProName, currentPageNo, pagesize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Boolean addProvider(Provider provider) {
		Boolean flg=false;
		try {
			if (providerMapper.addProvider(provider)>0) {
				flg=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flg;
	}

	@Override
	public Provider getByid(String id) {
		Provider provider=null;
		try {
			
			provider=providerMapper.getByid(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return provider;
	}

	@Override
	public Boolean updateProvider(Provider provider) {
		Boolean flg=false;
		try {
			if (providerMapper.updateProvider(provider)>0) {
				flg=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flg;
	}

	@Override
	public int delProvider(int id) {
		int count=0;
		try {
			if (providerMapper.getbillByproviderid(id)>0) {
				providerMapper.delbillByproviderid(id);
			}
			count=providerMapper.delProvider(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int getbillByproviderid(int id) {
		int count=0;
		try {
			count=providerMapper.getbillByproviderid(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int delbillByproviderid(int id) {
		int count=0;
		try {
			count=providerMapper.delbillByproviderid(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<Provider> providerlistall() {
		List<Provider> list=new ArrayList<Provider>();
		try {
			list=providerMapper.providerlistall();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
