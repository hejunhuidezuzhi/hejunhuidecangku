package com.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.entity.Bill;
import com.entity.Role;
import com.service.role.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {
	@Resource
	private RoleService roleService;
	
	@RequestMapping("/rolelist.html")
	public String rolelist(Model model) {
		List<Role> list=roleService.GetRolelist();
		System.out.println(list.size());
		model.addAttribute("roleList", list);
		return "rolelist";
	}
	
	@RequestMapping("/addrole.html")
	public String addrolet() {
		return "roleadd";
	}
	
	@RequestMapping("/addsave.html")
	public String addsave(Role role) {
		if (roleService.addRole(role)>0) {
			return "redirect:/role/rolelist.html";
		}
		return "roleadd";
	}
	
	@RequestMapping(value="/rcexist.json",method=RequestMethod.GET)
	@ResponseBody
	public Object rcexist(@RequestParam String roleCode) {
		
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if (roleService.getreleCode(roleCode)>0) {
			resultMap.put("roleCode", "exist");
		} else {
			resultMap.put("roleCode", "false");
		}
		return JSONArray.toJSONString(resultMap);
	}
	
	@RequestMapping(value = "/modify/{roleid}", method = RequestMethod.GET)
	public String roleview(@PathVariable String roleid, Model model) {
		Role role=roleService.getroleByid(roleid);
		model.addAttribute("role", role);
		return "rolemodify";
	}
	
	@RequestMapping("/modifysave.html")
	public String modifysave(Role role) {
		if (roleService.updaterole(role)>0) {
			return "redirect:/role/rolelist.html";
		}
		return "rolemodify";
	}
	
	@RequestMapping(value="/delrole.json",method=RequestMethod.GET)
	@ResponseBody
	public Object delrole(@RequestParam String id) {
		
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if (roleService.delrole(id)>0) {
			resultMap.put("delResult", "true");
		} else {
			resultMap.put("delResult", "false");
		}
		return JSONArray.toJSONString(resultMap);
	}
}
