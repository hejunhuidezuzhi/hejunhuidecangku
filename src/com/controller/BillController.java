package com.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.entity.Bill;
import com.entity.PageSupport;
import com.entity.Provider;
import com.entity.User;
import com.mysql.jdbc.StringUtils;
import com.service.bill.BillService;
import com.service.provider.ProviderService;

@Controller
@RequestMapping("/bill")
public class BillController extends BaseController {
	@Resource
	private ProviderService providerService;
	@Resource
	private BillService billService;
	@RequestMapping(value="/bill.html")
	public String bill(Model model) {
		List<Provider> list=providerService.providerlistall();
		model.addAttribute("providerList", list);
		return "billlist";
	}
	@RequestMapping(value="/billlist.html")
	public String billlist(@RequestParam(value = "queryProductName", required = false) String queryProductName,
			@RequestParam(value = "queryProviderId", required = false) String queryProviderId,
			@RequestParam(value = "queryIsPayment", required = false) String queryIsPayment,
			@RequestParam(value = "pageIndex", required = false) String pageIndex, Model model) {
		List<Bill> list = new ArrayList<Bill>();
		List<Provider> list2=providerService.providerlistall();
		model.addAttribute("providerList", list2);
		int pagesize = 5;
		int currentPageNo = 1;
		if (queryProductName == null) {
			queryProductName = "";
		}
		if (queryProviderId == null) {
			queryProviderId = "";
		}
		System.out.println(queryProductName);
		if (queryProviderId =="0") {
			queryProviderId = "";
			System.out.println("mmp");
		}
		
		if (queryIsPayment == null) {
			queryIsPayment = "";
		}
		if (pageIndex != null) {
			currentPageNo = Integer.parseInt(pageIndex);
		}
		int totalCount=billService.getBillCount();
		// ×ÜÒ³Êý
		PageSupport pageSupport = new PageSupport();
		pageSupport.setCurrentPageNo(currentPageNo);
		pageSupport.setPageSize(pagesize);
		pageSupport.setTotalCount(totalCount);
		int totalPageCount = pageSupport.getTotalPageCount();
		if (currentPageNo < 1) {
			currentPageNo = 1;
		} else if (currentPageNo > totalPageCount) {
			currentPageNo = totalPageCount;
		}
		list=billService.QueryBillList(queryProductName, queryProviderId, queryIsPayment, currentPageNo, pagesize);
		model.addAttribute("billList", list);
		model.addAttribute("queryProductName", queryProductName);
		model.addAttribute("queryProviderId", queryProviderId);
		model.addAttribute("queryIsPayment", queryIsPayment);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("currentPageNo", currentPageNo);
		return "billlist";
	}
	
	@RequestMapping(value = "/view/{billid}", method = RequestMethod.GET)
	public String billview(@PathVariable String billid, Model model) {
		Bill bill=billService.getBillByid(billid);
		model.addAttribute("bill", bill);
		return "billview";
	}
	
	@RequestMapping(value = "/addbill.html")
	public String addbill(Model model) {
		
		return "billadd";
	}
	
	@RequestMapping(value="/providerlist.html",method=RequestMethod.GET)
	@ResponseBody
	public Object providerlist() {
		List<Provider> list=providerService.providerlistall();
		return JSONArray.toJSONString(list);
	}
	
	@RequestMapping(value="/billadd.html",method=RequestMethod.POST)
	public String billadd(Bill bill) {
		int count=billService.addBill(bill);
		if (count>0) {
			return "redirect:/bill/billlist.html";
		}
		return "billadd";
	}
	
	@RequestMapping(value="/del.html",method=RequestMethod.GET)
	@ResponseBody
	public Object delbill(@RequestParam String billid) {
		
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if (billService.delBill(billid)>0) {
			resultMap.put("delResult", "true");
		} else {
			resultMap.put("delResult", "false");
		}
		return JSONArray.toJSONString(resultMap);
	}
	
	@RequestMapping(value = "/billmodify/{billid}", method = RequestMethod.GET)
	public String billmodify(@PathVariable String billid, Model model) {
		Bill bill=billService.getBillByid(billid);
		model.addAttribute("bill", bill);
		return "billmodify";
	}
	
	@RequestMapping(value="/billupdate.html",method=RequestMethod.POST)
	public String billupdate(Bill bill) {
		int count=billService.updateBill(bill);
		if (count>0) {
			return "redirect:/bill/billlist.html";
		}
		return "billmodify";
	}
}
