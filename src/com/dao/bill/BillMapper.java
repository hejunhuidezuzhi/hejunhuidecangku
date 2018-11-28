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
	 * ��ѯ�ܼ�¼����
	 * @return
	 */
	public int getBillCount();
	
	public Bill getBillByid(@Param("billid")String billid);
	
	/**
	 * ��Ӷ���
	 * @param bill
	 * @return
	 */
	public int addBill(Bill bill);
	/**
	 * ɾ������
	 * @param billid
	 * @return
	 */
	public int delBill(@Param("billid")String billid);
	
	/**
	 * �޸Ķ���
	 * @param bill
	 * @return
	 */
	public int updateBill(Bill bill);
}
