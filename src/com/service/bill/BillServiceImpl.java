package com.service.bill;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.bill.BillMapper;
import com.entity.Bill;

@Service("billService")
public class BillServiceImpl implements BillService {
	@Resource
	private BillMapper billMapper;
	@Override
	public int getBillCount() {
		int count=0;
		try {
			count=billMapper.getBillCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public List<Bill> QueryBillList(String queryProductName, String queryProviderId, String queryIsPayment,
			int currentPageNo, int pagesize) {
		List<Bill> list=new ArrayList<Bill>();
		try {
			currentPageNo=(currentPageNo-1)*pagesize;
			list=billMapper.QueryBillList(queryProductName, queryProviderId, queryIsPayment, currentPageNo, pagesize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public Bill getBillByid(String billid) {
		Bill bill=null;
		try {
			bill=billMapper.getBillByid(billid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bill;
	}
	@Override
	public int addBill(Bill bill) {
		int count=0;
		try {
			count=billMapper.addBill(bill);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public int delBill(String billid) {
		int count=0;
		try {
			count=billMapper.delBill(billid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public int updateBill(Bill bill) {
		int count=0;
		try {
			count=billMapper.updateBill(bill);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
}
