package com.service.provider;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.Provider;

public interface ProviderService {
	
	public int getProvideCount(String queryProCode,String queryProName);
	
	public List<Provider> getProviderlist(String queryProCode,String queryProName,int currentPageNo,int pagesize);
	
	public Boolean addProvider(Provider provider);
	
	public Provider getByid(String id);
	
	public Boolean updateProvider(Provider provider);
	
	public int delProvider(int id);
	/**
	 * ���ݹ�Ӧ��id��ѯ�������¼����
	 * @param id
	 * @return
	 */
	public int getbillByproviderid(int id);
	/**
	 * ���ݹ�Ӧ��id��ѯɾ����������Ϣ
	 * @param id
	 * @return
	 */
	public int delbillByproviderid(int id);
	
	public List<Provider> providerlistall();
}
