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
	 * 增加
	 * @param provider
	 * @return
	 */
	public int addProvider(Provider provider);
	
	public Provider getByid(@Param("id")String id);
	/**
	 * 修改供应商
	 * @param provider
	 * @return
	 */
	public int updateProvider(Provider provider);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int delProvider(@Param("id")Integer id);
	/**
	 * 根据供应商id查询订单表记录条数
	 * @param id
	 * @return
	 */
	public int getbillByproviderid(@Param("id")Integer id);
	/**
	 * 根据供应商id查询删除订单表信息
	 * @param id
	 * @return
	 */
	public int delbillByproviderid(@Param("id")Integer id);
	
	public List<Provider> providerlistall();
	
}	
