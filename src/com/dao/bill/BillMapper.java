package com.dao.bill;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.Bill;

public interface BillMapper {
	
	public List<Bill> QueryBillList(@Param("queryProductName")String queryProductName,
			@Param("queryProviderId")String queryProviderId,
			@Param("queryIsPayment")String queryIsPayment,
			@Param("currentPageNo")int currentPageNo,
			@Param("pagesize")int pagesize);
	
	/**
	 * 查询总记录条数
	 * @return
	 */
	public int getBillCount();
	
	public Bill getBillByid(@Param("billid")String billid);
	
	/**
	 * 添加订单
	 * @param bill
	 * @return
	 */
	public int addBill(Bill bill);
	/**
	 * 删除订单
	 * @param billid
	 * @return
	 */
	public int delBill(@Param("billid")String billid);
	
	/**
	 * 修改订单
	 * @param bill
	 * @return
	 */
	public int updateBill(Bill bill);
}
