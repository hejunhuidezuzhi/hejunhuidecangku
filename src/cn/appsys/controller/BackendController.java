package cn.appsys.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.sun.javafx.sg.prism.NGShape.Mode;

import cn.appsys.pojo.Appcategory;
import cn.appsys.pojo.Appinfo;
import cn.appsys.pojo.Appversion;
import cn.appsys.pojo.Backenduser;
import cn.appsys.pojo.Datadictionary;
import cn.appsys.service.Backenduser.BackenduserService;
import cn.appsys.service.appcategory.AppcategoryService;
import cn.appsys.service.appinfo.AppinfoService;
import cn.appsys.service.appversion.AppversionService;
import cn.appsys.service.datadictionary.DatadictionaryService;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping("/backend")
public class BackendController {
	@Resource
	private BackenduserService backenduserService;
	@Resource
	private AppinfoService appinfoService;
	@Resource
	private DatadictionaryService DatadictionaryService;
	@Resource
	private AppcategoryService appcategoryService;
	@Resource
	private AppversionService appversionService;
	
	/**
	 * 跳转至后台登录
	 * @return
	 */
	@RequestMapping(value="/login.html")
	public String login() {
		return "backendlogin";
	}
	
	/**
	 * 后台登录
	 * @return
	 */
	@RequestMapping(value="/dologin")
	public String dologin(String userCode,String userPassword,HttpSession session) {
		Backenduser userSession=backenduserService.Backendlogin(userCode, userPassword);
		if (userSession!=null) {
			session.setAttribute("userSession", userSession);
			return "backend/main";
		}
		return "redirect:/backend/login.html";
	}
	
	@RequestMapping(value="/logout")
	public void logout(HttpSession session,HttpServletResponse response) throws IOException {
		session.invalidate();
		response.sendRedirect("../index.jsp");
	}
	
	@RequestMapping(value="/list")
	public String list(Model model,
			@RequestParam(value="querySoftwareName",required=false) String querySoftwareName,
			@RequestParam(value="queryCategoryLevel1",required=false) String queryCategoryLevel1,
			@RequestParam(value="queryCategoryLevel2",required=false) String queryCategoryLevel2,
			@RequestParam(value="queryCategoryLevel3",required=false) String queryCategoryLevel3,
			@RequestParam(value="queryFlatformId",required=false) String queryFlatformId,
			@RequestParam(value="pageIndex",required=false) String pageIndex) {
		
		//页面容量
		int pagesize=5;
		//当前页码
		int currentPageNo = 1;
		if (pageIndex!=null) {
			currentPageNo=Integer.parseInt(pageIndex);
		}		
		
		Integer _queryCategoryLevel1 = null;
		if(queryCategoryLevel1 != null && !queryCategoryLevel1.equals("")){
			_queryCategoryLevel1 = Integer.parseInt(queryCategoryLevel1);
		}
		Integer _queryCategoryLevel2 = null;
		if(queryCategoryLevel2 != null && !queryCategoryLevel2.equals("")){
			_queryCategoryLevel2 = Integer.parseInt(queryCategoryLevel2);
		}
		Integer _queryCategoryLevel3 = null;
		if(queryCategoryLevel3 != null && !queryCategoryLevel3.equals("")){
			_queryCategoryLevel3 = Integer.parseInt(queryCategoryLevel3);
		}
		Integer _queryFlatformId = null;
		if(queryFlatformId != null && !queryFlatformId.equals("")){
			_queryFlatformId = Integer.parseInt(queryFlatformId);
		}
		
		//总数量
		int totalCount=appinfoService.GetAppinfoCount(querySoftwareName, "1", queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId);
		PageSupport pages=new PageSupport();
		pages.setPageSize(pagesize);
		pages.setTotalCount(totalCount);
		pages.setCurrentPageNo(currentPageNo);
		int totalPageCount = pages.getTotalPageCount();
		if (currentPageNo < 1) {
			currentPageNo = 1;
		} else if (currentPageNo > totalPageCount) {
			currentPageNo = totalPageCount;
		}	
		
		List<Appinfo> appInfoList=appinfoService.GetAppinfoList(querySoftwareName, "1", queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, currentPageNo, pagesize);
		
		model.addAttribute("appInfoList", appInfoList);
		model.addAttribute("pages", pages);
		//所属平台
		List<Datadictionary> flatFormList=DatadictionaryService.getBytypeCode("APP_FLATFORM");
		model.addAttribute("flatFormList", flatFormList);
		//一级菜单
		List<Appcategory> categoryLevel1List=appcategoryService.getByparentId(null);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		
		model.addAttribute("querySoftwareName", querySoftwareName);
		model.addAttribute("queryCategoryLevel1", _queryCategoryLevel1);
		model.addAttribute("queryCategoryLevel2", _queryCategoryLevel2);
		model.addAttribute("queryCategoryLevel3", _queryCategoryLevel3);
		model.addAttribute("queryFlatformId", _queryFlatformId);
		//二级分类列表和三级分类列表---回显
		if(queryCategoryLevel2 != null && !queryCategoryLevel2.equals("")){
			List<Appcategory> categoryLevel2List = appcategoryService.getByparentId(queryCategoryLevel1.toString());
			model.addAttribute("categoryLevel2List", categoryLevel2List);
		}
		if(queryCategoryLevel3 != null && !queryCategoryLevel3.equals("")){
			List<Appcategory> categoryLevel3List = appcategoryService.getByparentId(queryCategoryLevel2.toString());
			model.addAttribute("categoryLevel3List", categoryLevel3List);
		}
		return "backend/applist";
	}
	
	/**
	 * 加载二级三级菜单
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/categorylevellist.json",method=RequestMethod.GET)
	@ResponseBody
	public Object categorylevellist(@RequestParam String pid) {
		System.out.println("进来了cnm");
		if (pid.equals("")) {
			pid=null;
		}
		List<Appcategory> list=appcategoryService.getByparentId(pid);
		return JSONArray.toJSON(list);
	}
	
	@RequestMapping(value="/check")
	public String check(@RequestParam String aid,@RequestParam String vid,Model model) {
		Appinfo appInfo=appinfoService.GetByappId(aid);
		model.addAttribute("appInfo", appInfo);
		Appversion appVersion=appversionService.GetByappidDate(Integer.parseInt(aid));
		model.addAttribute("appVersion", appVersion);
		return "backend/appcheck";
	}
	
	@RequestMapping(value="/checksave")
	public String checksave(Appinfo appinfo) {
		if (appinfoService.updateAppinfo(appinfo)>0) {
			return "redirect:/backend/list";
		}
		return "backend/applist";
	}
	
}
