package cn.appsys.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;

import cn.appsys.pojo.Appcategory;
import cn.appsys.pojo.Appinfo;
import cn.appsys.pojo.Appversion;
import cn.appsys.pojo.Datadictionary;
import cn.appsys.pojo.Devuser;
import cn.appsys.service.appcategory.AppcategoryService;
import cn.appsys.service.appinfo.AppinfoService;
import cn.appsys.service.appversion.AppversionService;
import cn.appsys.service.datadictionary.DatadictionaryService;
import cn.appsys.service.devuser.DevuserService;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping("/devuser")
public class DevuserController {
	@Resource
	private DevuserService devuserService;
	@Resource
	private DatadictionaryService DatadictionaryService;
	@Resource
	private AppcategoryService appcategoryService;
	@Resource
	private AppinfoService appinfoService;
	@Resource
	private AppversionService appversionService;
	
	@RequestMapping(value="/login.html")
	public String login() {
		return "devlogin";
	}
	
	@RequestMapping(value="/dologin.html",method=RequestMethod.POST)
	public String dologin(String devCode,String devPassword,HttpSession session) {
		Devuser dev_user=devuserService.login(devCode, devPassword);
		if (dev_user!=null) {
			session.setAttribute("devUserSession", dev_user);
			return "developer/main";
		}
		return "redirect:/devuser/login.html";
	}
	
	@RequestMapping(value="/logout.html")
	public void logout(HttpSession session,HttpServletResponse response) throws IOException {
		session.invalidate();
		response.sendRedirect("../index.jsp");
	}
	
	@RequestMapping(value="/list.html")
	public String appinfolist(Model model,
			@RequestParam(value="querySoftwareName",required=false) String querySoftwareName,
			@RequestParam(value="queryStatus",required=false) String queryStatus,
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
		int totalCount=appinfoService.GetAppinfoCount(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId);
		
		
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
		List<Appinfo> appInfoList=appinfoService.GetAppinfoList(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, currentPageNo, pagesize);
		
		model.addAttribute("appInfoList", appInfoList);
		model.addAttribute("pages", pages);
		//APP状态
		List<Datadictionary> statusList=DatadictionaryService.getBytypeCode("APP_STATUS");
		model.addAttribute("statusList", statusList);
		//所属平台
		List<Datadictionary> flatFormList=DatadictionaryService.getBytypeCode("APP_FLATFORM");
		model.addAttribute("flatFormList", flatFormList);
		//一级菜单
		List<Appcategory> categoryLevel1List=appcategoryService.getByparentId(null);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		
		model.addAttribute("queryStatus", queryStatus);
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
		return "developer/appinfolist";
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
	
	@RequestMapping(value="/addappinfo.html")
	public String addappinfo() {
		return "developer/appinfoadd";
	}
	
	/**
	 * 加载所属平台
	 * @param tcode
	 * @return
	 */
	@RequestMapping(value="/datadictionarylist.json",method=RequestMethod.GET)
	@ResponseBody
	public Object datadictionarylist(@RequestParam String tcode) {
		System.out.println("进来了cnm");
		
		List<Datadictionary> statusList=DatadictionaryService.getBytypeCode(tcode);
		return JSONArray.toJSON(statusList);
	}
	
	/**
	 * 验证APKName是否存在
	 * @param APKName
	 * @return
	 */
	@RequestMapping(value="/apkexist.json",method=RequestMethod.GET)
	@ResponseBody
	public Object apkexist(@RequestParam String APKName) {
		
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if (APKName.equals("")) {
			resultMap.put("APKName", "empty");
		}else {
			Appinfo appinfo=appinfoService.GetByAPKName(APKName);
			if (appinfo==null) {
				resultMap.put("APKName", "noexist");
			} else {
				resultMap.put("APKName", "exist");
			}
		}
		
		return JSONArray.toJSON(resultMap);
	}
	
	/**
	 * 新增应用信息
	 * @param appinfo
	 * @param session
	 * @param request
	 * @param attact
	 * @return
	 */
	@RequestMapping(value="/appinfoaddsave.html",method=RequestMethod.POST)
	public String appinfoadd(Appinfo appinfo,HttpSession session,HttpServletRequest request,
			@RequestParam(value="a_logoPicPath",required=false) MultipartFile attact) {
		String logoPicPath=null;
		String logoLocPath=null;
		System.out.println(attact.getOriginalFilename());
		if (!attact.isEmpty()) {
			String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName=attact.getOriginalFilename();//原文件名
			String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀
			int filesize=500000;
			if (attact.getSize()>filesize) {
				return "developer/appinfoadd";
			} else if (prefix.equalsIgnoreCase("jpg")
					||prefix.equalsIgnoreCase("png")
					||prefix.equalsIgnoreCase("jpeg")
					||prefix.equalsIgnoreCase("pneg")) {
				appinfo.setLogoLocPath(oldFileName);
				String fileName = appinfo.getLogoLocPath();
				File targetFile=new File(path,fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				try {
					System.out.println(targetFile);
					attact.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					return "developer/appinfoadd";
				}
				 logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
				 logoLocPath = path+File.separator+fileName;
			}
		} else {
			return "developer/appinfoadd";
		}
		appinfo.setCreatedBy(((Devuser)session.getAttribute("devUserSession")).getId());
		appinfo.setCreationDate(new Date());
		appinfo.setLogoPicPath(logoPicPath);
		appinfo.setLogoLocPath(logoLocPath);
		appinfo.setDevId(((Devuser)session.getAttribute("devUserSession")).getId());
		appinfo.setStatus(1);
		if (appinfoService.Addappinfo(appinfo)>0) {
			return "redirect:/devuser/list.html";
		}
		return "developer/appinfoadd";
	}
	
	/**
	 * 查询历史版本信息
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/appversionadd.html")
	public String appversionadd(String id,Model model) {
		List<Appversion> appVersionList=appversionService.getByappId(id);
		Appversion appVersion =new Appversion();
		appVersion.setAppId(Integer.parseInt(id));
		model.addAttribute("appVersion", appVersion);
		model.addAttribute("appVersionList", appVersionList);
		return "developer/appversionadd";
	}
	
	/**
	 * 新增版本信息
	 * @param appversion
	 * @param session
	 * @param request
	 * @param attact
	 * @return
	 */
	@RequestMapping(value="/addversionsave.html",method=RequestMethod.POST)
	public String addversionsave(Appversion appversion,HttpSession session,HttpServletRequest request,
			@RequestParam(value="a_downloadLink",required=false) MultipartFile attact) {
		String apkFileName=null;
		String apkLocPath=null;
		System.out.println(attact.getOriginalFilename());
//		if (!attact.isEmpty()) {
			System.out.println("cnm");
			String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName=attact.getOriginalFilename();//原文件名
			String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀
			if (prefix.equalsIgnoreCase("apk")) {
				appversion.setApkFileName(oldFileName);
				String fileName = appversion.getApkFileName() + ".apk";
				File targetFile=new File(path,fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				try {
					attact.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("第二个错误");
					return "developer/appversionadd";
				}
				apkFileName =fileName;
				apkLocPath = path+File.separator+fileName;
			}
//		} else {
//			System.out.println("第三个错误");
//			return "developer/appversionadd";
//		}
		appversion.setCreatedBy(((Devuser)session.getAttribute("devUserSession")).getId());
		appversion.setCreationDate(new Date());
		appversion.setModifyDate(new Date());
		appversion.setApkFileName(apkFileName);
		appversion.setApkLocPath(apkLocPath);
		appversion.setDownloadLink(request.getContextPath()+"/statics/uploadfiles/"+appversion.getApkFileName());
		if (appversionService.Addappversion(appversion)>0) {
			Appversion appversion2=appversionService.GetByappidDate(appversion.getAppId());
			
			appinfoService.updateversonId(appversion.getAppId(), appversion2.getId());
			return "redirect:/devuser/list.html";
		}
		System.out.println("第一四个错误");
		return "developer/appversionadd";
	}
	
	/**
	 * 查看APP信息
	 * @param appinfoid
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/appview/{appinfoid}",method=RequestMethod.GET)
	public String appview(@PathVariable String appinfoid,Model model) {
		Appinfo appInfo=appinfoService.GetByappId(appinfoid);
		model.addAttribute("appInfo", appInfo);
		List<Appversion> appVersionList=appversionService.getByappId(appinfoid);
		model.addAttribute("appVersionList", appVersionList);
		return "developer/appinfoview";
	}
	
	/**
	 * 跳转修改页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/appinfomodify.html")
	public String appinfomodify(String id,Model model) {
		Appinfo appInfo=appinfoService.GetByappId(id);
		model.addAttribute("appInfo", appInfo);
		return "developer/appinfomodify";
	}
	
	/**
	 * 删除APP图片
	 * @param id
	 * @param flag
	 * @return
	 */
	@RequestMapping(value="/delfile.json",method=RequestMethod.GET)
	@ResponseBody
	public Object delfile(String id,String flag) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if (appinfoService.deleteAppLogo(id)>0) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "failed");
		}
		return JSONArray.toJSON(resultMap);
	}
	
	/**
	 * 修改APP信息
	 * @param appinfo
	 * @param session
	 * @param request
	 * @param attact
	 * @return
	 */
	@RequestMapping(value="/appinfomodifysave.html",method=RequestMethod.POST)
	public String appinfomodifysave(Appinfo appinfo,HttpSession session,HttpServletRequest request,
			@RequestParam(value="attach",required=false) MultipartFile attact) {
		String logoPicPath=null;
		String logoLocPath=null;
		System.out.println(attact.getOriginalFilename());
		if (!attact.isEmpty()) {
			String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName=attact.getOriginalFilename();//原文件名
			String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀
			int filesize=500000;
			if (attact.getSize()>filesize) {
				return "developer/appinfomodify";
			} else if (prefix.equalsIgnoreCase("jpg")
					||prefix.equalsIgnoreCase("png")
					||prefix.equalsIgnoreCase("jpeg")
					||prefix.equalsIgnoreCase("pneg")) {
				appinfo.setLogoLocPath(oldFileName);
				String fileName = appinfo.getLogoLocPath();
				File targetFile=new File(path,fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				try {
					System.out.println(targetFile);
					attact.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					return "developer/appinfomodify";
				}
				 logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
				 logoLocPath = path+File.separator+fileName;
			}
		} else {
			return "developer/appinfomodify";
		}
		appinfo.setCreatedBy(((Devuser)session.getAttribute("devUserSession")).getId());
		appinfo.setCreationDate(new Date());
		appinfo.setLogoPicPath(logoPicPath);
		appinfo.setLogoLocPath(logoLocPath);
		appinfo.setDevId(((Devuser)session.getAttribute("devUserSession")).getId());
		appinfo.setStatus(1);
		if (appinfoService.updateAppinfo(appinfo)>0) {
			return "redirect:/devuser/list.html";
		}
		return "developer/appinfomodify";
	}
	
	/**
	 * 删除APP
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delapp.json",method=RequestMethod.GET)
	@ResponseBody
	public Object delapp(String id) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if (id.equals("")) {
			resultMap.put("delResult", "notexist");
		} else {
			if (appversionService.getByappId(id).size()>0) {
				appversionService.deleteAppversion(id);
			} 
			if (appinfoService.deleteApp(id)>0) {
				resultMap.put("delResult", "true");
			}else {
				resultMap.put("delResult", "false");
			}
		}
		return JSONArray.toJSON(resultMap);
	}
	
	/***
	 * 跳转修改版本页面
	 * @param vid
	 * @param aid
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/appversionmodify.html")
	public String appversionmodify(String vid,String aid,Model model) {
		List<Appversion> appVersionList=appversionService.getByappId(aid);
		model.addAttribute("appVersionList", appVersionList);
		Appversion appVersion=appversionService.GetByappidDate(Integer.parseInt(aid));
		model.addAttribute("appVersion", appVersion);
		return "developer/appversionmodify";
	}
	
	/**
	 * 删除apk文件
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delapkfile.json",method=RequestMethod.GET)
	@ResponseBody
	public Object delapkfile(String id) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if (appversionService.deleteApkFile(id)>0) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "failed");
		}
		return JSONArray.toJSON(resultMap);
	}
	
	/**
	 * 修改版本信息
	 * @param appversion
	 * @param session
	 * @param request
	 * @param attact
	 * @return
	 */
	@RequestMapping(value="/appversionmodifysave.html",method=RequestMethod.POST)
	public String appversionmodifysave(Appversion appversion,HttpSession session,HttpServletRequest request,
			@RequestParam(value="attach",required=false) MultipartFile attact) {
		String apkFileName=null;
		String apkLocPath=null;
		System.out.println(attact.getOriginalFilename());
			System.out.println("cnm");
			String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName=attact.getOriginalFilename();//原文件名
			String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀
			if (prefix.equalsIgnoreCase("apk")) {
				appversion.setApkFileName(oldFileName);
				String fileName = appversion.getApkFileName() + ".apk";
				File targetFile=new File(path,fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				try {
					attact.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("第二个错误");
					return "developer/appversionadd";
				}
				apkFileName =fileName;
				apkLocPath = path+File.separator+fileName;
			}
		appversion.setCreatedBy(((Devuser)session.getAttribute("devUserSession")).getId());
		appversion.setCreationDate(new Date());
		appversion.setModifyDate(new Date());
		appversion.setApkFileName(apkFileName);
		appversion.setApkLocPath(apkLocPath);
		appversion.setDownloadLink(request.getContextPath()+"/statics/uploadfiles/"+appversion.getApkFileName());
		if (appversionService.updateAppversion(appversion)>0) {
//			Appversion appversion2=appversionService.GetByappidDate(appversion.getAppId());
//			appinfoService.updateversonId(appversion.getAppId(), appversion2.getId());
			return "redirect:/devuser/list.html";
		}
		System.out.println("第一四个错误");
		return "developer/appversionadd";
	}
	
	/**
	 * 上架、下架
	 * @param appId
	 * @param saleSwitch
	 * @return
	 */
	@RequestMapping(value="/sale.json",method=RequestMethod.GET)
	@ResponseBody
	public Object sale(String appId,String saleSwitch) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		System.out.println(saleSwitch);
		resultMap.put("errorCode", "0");
		if (saleSwitch.equals("open")) {
			String status="4";
			if (appinfoService.updatestatus(status, appId)>0) {
				resultMap.put("resultMsg", "success");
			} else {
				resultMap.put("resultMsg", "failed");
			}
		} 
		if (saleSwitch.equals("close")) {
			String status="5";
			if (appinfoService.updatestatus(status, appId)>0) {
				resultMap.put("resultMsg", "success");
			} else {
				resultMap.put("resultMsg", "failed");
			}
		}
		return JSONArray.toJSON(resultMap);
	}
}
