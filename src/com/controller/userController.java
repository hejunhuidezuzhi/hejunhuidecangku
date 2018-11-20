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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dao.user.UserMapper;
import com.entity.PageSupport;
import com.entity.Provider;
import com.entity.Role;
import com.entity.User;
import com.mysql.jdbc.StringUtils;
import com.service.role.RoleService;
import com.service.user.UserService;



@Controller
@RequestMapping("/user")
public class userController extends BaseController {
	@Resource
	private UserService UserService;
	@Resource
	private RoleService roleService;
	
	@RequestMapping("/login.html")
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="/dologin.html",method=RequestMethod.POST)
	public String doLogin(@RequestParam String userCode,@RequestParam String userPassword
			,HttpSession session,HttpServletRequest request) {
		User user=UserService.getlogin(userCode);
		if (user!=null) {
			if (userPassword.equals(user.getUserPassword())) {
				session.setAttribute("user", user);
				return "redirect:/user/main.html";
			} else {
				throw new RuntimeException("密码输入错误！");
			}
			
		} else {
			throw new RuntimeException("用户名不存在！");
			//request.setAttribute("error", "用户或密码不正确");
			//return "login";
		}
	}
	
//	@ExceptionHandler(value= {RuntimeException.class})
//	public String handlerException(RuntimeException e,HttpServletRequest request) {
//		request.setAttribute("error", e);
//		return "login";
//	}
	
	@RequestMapping("/main.html")
	public String main(HttpSession session) {
		if (session.getAttribute("user")==null) {
			return "redirect:/user/login.html";
		}
		return "frame";
	}
	
	@RequestMapping("/logout.html")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
	
	@RequestMapping("/userlist.html")
	public String getuserlist(@RequestParam(value = "queryUserRole", required = false) String queryUserRole,
			@RequestParam(value = "queryUsername", required = false) String queryUsername,
			@RequestParam(value = "pageIndex", required = false) String pageIndex, Model model) {
		
		List<User> list = new ArrayList<User>();
		List<Role> rolelist=new ArrayList<Role>();
		int pagesize = 5;
		System.out.println("fadfasdfasdf"+queryUserRole);
		int currentPageNo = 1;
		if (queryUserRole == null) {
			queryUserRole = "";
		}
		if (queryUserRole.equals("0")) {
			queryUserRole = "";
		}
		if (queryUsername == null) {
			queryUsername = "";
		}
		if (pageIndex != null) {
			currentPageNo = Integer.parseInt(pageIndex);
		}
		int totalCount=UserService.getUserCount(queryUserRole, queryUsername);
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
		list=UserService.getProviderlist(queryUserRole, queryUsername, currentPageNo, pagesize);
		rolelist=roleService.GetRolelist();
		System.out.println(list.size());
		model.addAttribute("userList", list);
		model.addAttribute("roleList", rolelist);
		model.addAttribute("queryUserRole", queryUserRole);
		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("currentPageNo", currentPageNo);
		return "userlist";
	}
	
	@RequestMapping(value = "/view/{userid}", method = RequestMethod.GET)
	public String userview(@PathVariable String userid, Model model, HttpServletRequest request) {
		User user=UserService.getUserByid(Integer.parseInt(userid));
		model.addAttribute("user", user);
		return "userview";
	}
	
	@RequestMapping(value = "/pwdmodify.html")
	public String pwdmodify() {
		return "pwdmodify";
	}
	
	@RequestMapping(value="/pwd.html",method=RequestMethod.GET)
	@ResponseBody
	public Object pwdxxx(@RequestParam String oldpassword,HttpSession session) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(null == session.getAttribute("user") ){//session过期
			resultMap.put("result", "sessionerror");
		}else if(StringUtils.isNullOrEmpty(oldpassword)){//旧密码输入为空
			resultMap.put("result", "error");
		}else{
			String sessionPwd = ((User)session.getAttribute("user")).getUserPassword();
			if(oldpassword.equals(sessionPwd)){
				resultMap.put("result", "true");
			}else{//旧密码输入不正确
				resultMap.put("result", "false");
			}
		}
		return JSONArray.toJSONString(resultMap);
		
	}
	
	@RequestMapping(value="/pwdsave.html")
	public String pwdsave(@RequestParam String newpassword,HttpSession session) {
		int id=((User) session.getAttribute("user")).getId();
		Integer count=UserService.updatepwd(id, newpassword);
		if (count>0) {
			return "login";
		}
		return "pwdmodify";
	}
	
	@RequestMapping(value="/useradd.html",method=RequestMethod.GET)
	public String useradd(@ModelAttribute("user") User user) {
		return "useradd";
	}
	
	@RequestMapping(value="/rolelist",method=RequestMethod.GET)
	@ResponseBody
	public Object getrolelist() {
		String cjson="";
		List<Role> list=new ArrayList<Role>();
		try {
			list=roleService.GetRolelist();
			cjson=JSON.toJSONString(list);
		} catch (Exception e) {
			e.printStackTrace();
			return "sessionerror";
		}
		return cjson;
	}
	
	@RequestMapping(value="/userCode",method=RequestMethod.GET)
	@ResponseBody
	public Object getuserCode(@RequestParam String userCode,HttpSession session) {
		String cjson="";
		User user=new User();
		if (null==userCode || "".equals(userCode)) {
			return "error";
		} else {
			try {
				user=UserService.getlogin(userCode);
				cjson=JSON.toJSONString(user);
				System.out.println(cjson);
			} catch (Exception e) {
				e.printStackTrace();
				return "sessionerror";
			}
			return cjson;
		}
	}
	
	@RequestMapping(value="/addsave.html", method = RequestMethod.POST)
	public String useraddsave(User user,HttpSession session,HttpServletRequest request,
			 @RequestParam(value ="attachs", required = false) MultipartFile[] attachs) {

		String a_idPicPath = null;
		String a_workPicPath = null;
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
						a_idPicPath = path + File.separator + fileName;
					} else if (i == 1) {
						a_workPicPath = path + File.separator + fileName;
					}

				} else {
					request.setAttribute("uploadFileError", "*上传图片格式不正确");
					return "provideradd";
				}
			}
		}

		
		user.setCreatedBy(((User) session.getAttribute("user")).getId());
		user.setCreationDate(new Date());
		user.setIdPicPath(a_idPicPath);
		user.setWorkPicPath(a_workPicPath);
		if (UserService.adduser(user)>0) {
			return "redirect:/user/userlist.html";
		}
		return "useradd";

	}
	
	@RequestMapping(value="/deluser.json",method=RequestMethod.GET)
	@ResponseBody
	public Object deluser(@RequestParam String uid) {
		System.out.println("cnmcnmncmncmcncmc");
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(StringUtils.isNullOrEmpty(uid)){
			resultMap.put("delResult", "notexist");
		}else{
			if(UserService.deleteByid(Integer.parseInt(uid)))
				resultMap.put("delResult", "true");
			else
				resultMap.put("delResult", "false");
		}
		return JSONArray.toJSONString(resultMap);
	}
	
	@RequestMapping(value = "/usermodif/{userid}", method = RequestMethod.GET)
	public String getProviderById(@PathVariable String userid, Model model) {
		User user=UserService.getUserByid(Integer.parseInt(userid));
		model.addAttribute("user", user);
		return "usermodify";
	}
	
	@RequestMapping(value="/modifysave.html", method = RequestMethod.POST)
	public String modifysave(User user,HttpSession session,HttpServletRequest request,
			 @RequestParam(value ="attachs", required = false) MultipartFile[] attachs) {

		String a_idPicPath = null;
		String a_workPicPath = null;
		String errorInfo = null;
		Boolean flag = true;
		String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
		if (attachs!=null) {
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
							a_idPicPath = path + File.separator + fileName;
						} else if (i == 1) {
							a_workPicPath = path + File.separator + fileName;
						}

					} else {
						request.setAttribute("uploadFileError", "*上传图片格式不正确");
						return "usermodify";
					}
				}
			}
		}
		

		
		user.setCreatedBy(((User) session.getAttribute("user")).getId());
		user.setCreationDate(new Date());
		user.setIdPicPath(a_idPicPath);
		user.setWorkPicPath(a_workPicPath);
		if (UserService.updateuser(user)>0) {
			return "redirect:/user/userlist.html";
		}
		return "usermodify";

	}
}
