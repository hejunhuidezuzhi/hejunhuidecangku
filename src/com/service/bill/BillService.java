package com.service.bill;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.Bill;

public interface BillService {
	public int getBillCount();
	
	public List<Bill> QueryBillList(String queryProductName,String queryProviderId,String queryIsPayment,int currentPageNo,int pagesize);

	public Bill getBillByid(@Param("billid")String billid);
	
	public int addBill(Bill bill);
	
	public int delBill(@Param("billid")String billid);
	
	public int updateBill(Bill bill);
}
