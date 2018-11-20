package com.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.entity.PageSupport;
import com.entity.Provider;
import com.entity.User;
import com.mysql.jdbc.StringUtils;
import com.service.provider.ProviderService;

@Controller
@RequestMapping("/provider")
public class ProviderController {
	@Resource
	private ProviderService providerService;

	@RequestMapping("/providerlist.html")
	public String providerlist(@RequestParam(value = "queryProCode", required = false) String queryProCode,
			@RequestParam(value = "queryProName", required = false) String queryProName,
			@RequestParam(value = "pageIndex", required = false) String pageIndex, Model model) {
		List<Provider> list = new ArrayList<Provider>();
		int pagesize = 5;
		int currentPageNo = 1;
		if (queryProCode == null) {
			queryProCode = "";
		}
		if (queryProName == null) {
			queryProName = "";
		}
		if (pageIndex != null) {
			currentPageNo = Integer.parseInt(pageIndex);
		}
		int totalCount = providerService.getProvideCount(queryProCode, queryProName);

		// 总页数
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
		list = providerService.getProviderlist(queryProCode, queryProName, currentPageNo, pagesize);
		System.out.println(list.size());
		model.addAttribute("providerList", list);
		model.addAttribute("queryProCode", queryProCode);
		model.addAttribute("queryProName", queryProName);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("currentPageNo", currentPageNo);
		return "providerlist";
	}

	@RequestMapping("/provideradd.html")
	public String provideradd() {
		return "provideradd";
	}

	@RequestMapping(value = "/provideraddsave.html", method = RequestMethod.POST)
	public String provideraddsave(Provider provider, HttpSession session, HttpServletRequest request,
			@RequestParam(value = "attachs", required = false) MultipartFile attachs[]) {

		String companyLicPicPath = null;
		String orgCodePicPath = null;
		String errorInfo = null;
		Boolean flag = true;
		String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
		for (int i = 0; i < attachs.length; i++) {
			MultipartFile attach = attachs[i];

			if (!attach.isEmpty()) {

				String oldFileName = attach.getOriginalFilename();
				String prefix = FilenameUtils.getExtension(oldFileName);
				int filesize = 500000;
				if (attach.getSize() > filesize) {
					request.setAttribute("uploadFileError", "*上传大小不得超过500KB");
					flag = false;
				} else if (prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png")
						|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")) {
					String fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + "_Personal.jpg";
					File targetFile = new File(path, fileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					try {
						attach.transferTo(targetFile);
					} catch (Exception e) {
						e.printStackTrace();
						request.setAttribute("uploadFileError", "*上传失败");
						flag = false;
					}
					if (i == 0) {
						companyLicPicPath = path + File.separator + fileName;
					} else if (i == 1) {
						orgCodePicPath = path + File.separator + fileName;
					}

				} else {
					request.setAttribute("uploadFileError", "*上传图片格式不正确");
					return "provideradd";
				}
			}
		}

		User user = new User();
		user.setCreatedBy(((User) session.getAttribute("user")).getId());
		provider.setCreatedBy(user.getCreatedBy());
		provider.setCreationDate(new Date());
		provider.setCompanyLicPicPath(companyLicPicPath);
		provider.setOrgCodePicPath(orgCodePicPath);
		if (providerService.addProvider(provider)) {
			return "redirect:/provider/providerlist.html";
		}
		return "provideradd";

	}

	@RequestMapping(value = "/providerview/{proid}", method = RequestMethod.GET)
	public String providerview(@PathVariable String proid, Model model, HttpServletRequest request) {
		Provider provider = providerService.getByid(proid);
		if (provider.getCompanyLicPicPath() != null && !"".equals(provider.getCompanyLicPicPath())) {
			String[] paths = provider.getCompanyLicPicPath().split("\\" + File.separator);
			System.out.println("view companyLicPicPath paths[paths.length-1]============ " + paths[paths.length - 1]);
			provider.setCompanyLicPicPath(request.getContextPath() + "/statics/uploadfiles/" + paths[paths.length - 1]);
			System.out.println(request.getContextPath() + "/statics/uploadfiles/" + paths[paths.length - 1]);
		}
		if (provider.getOrgCodePicPath() != null && !"".equals(provider.getOrgCodePicPath())) {
			String[] paths = provider.getOrgCodePicPath().split("\\" + File.separator);
			System.out.println("view orgCodePicPath paths[paths.length-1]============ " + paths[paths.length - 1]);
			provider.setOrgCodePicPath(request.getContextPath() + "/statics/uploadfiles/" + paths[paths.length - 1]);
		}
		model.addAttribute("provider", provider);
		return "providerview";
	}

	@RequestMapping(value = "/providerById/{proid}", method = RequestMethod.GET)
	public String getProviderById(@PathVariable String proid, Model model) {
		Provider provider = providerService.getByid(proid);

		model.addAttribute("provider", provider);
		return "providermodify";
	}

	@RequestMapping(value = "/modifysave.html", method = RequestMethod.POST)
	public String modifyProviderSave(Provider provider) {
		if (providerService.updateProvider(provider)) {
			return "redirect:/provider/providerlist.html";
		}
		return "providermodify";
	}
	
	@RequestMapping(value="/delprovider.json",method=RequestMethod.GET)
	@ResponseBody
	public Object delprovider(@RequestParam String proid) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(StringUtils.isNullOrEmpty(proid)){
			resultMap.put("delResult", "notexist");
		}else{
			if(providerService.delProvider(Integer.parseInt(proid))>0)
				resultMap.put("delResult", "true");
			else
				resultMap.put("delRes1‘ult", "false");
		}
		return JSONArray.toJSONString(resultMap);
	}
}
