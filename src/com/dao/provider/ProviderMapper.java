package com.dao.provider;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.Provider;

public interface ProviderMapper {
	
	public int getProvideCount(@Param("queryProCode")String queryProCode,
			@Param("queryProName")String queryProName);
	
	
	public List<Provider> getProviderlist(@Param("queryProCode")String queryProCode,
			@Param("queryProName")String queryProName,@Param("currentPageNo")int currentPageNo,
			@Param("pagesize")int pagesize);
	
	/**
	 * ����
	 * @param provider
	 * @return
	 */
	public int addProvider(Provider provider);
	
	public Provider getByid(@Param("id")String id);
	/**
	 * �޸Ĺ�Ӧ��
	 * @param provider
	 * @return
	 */
	public int updateProvider(Provider provider);
	
	/**
	 * ɾ��
	 * @param id
	 * @return
	 */
	public int delProvider(@Param("id")Integer id);
	/**
	 * ���ݹ�Ӧ��id��ѯ�������¼����
	 * @param id
	 * @return
	 */
	public int getbillByproviderid(@Param("id")Integer id);
	/**
	 * ���ݹ�Ӧ��id��ѯɾ����������Ϣ
	 * @param id
	 * @return
	 */
	public int delbillByproviderid(@Param("id")Integer id);
	
	public List<Provider> providerlistall();
	
}	
