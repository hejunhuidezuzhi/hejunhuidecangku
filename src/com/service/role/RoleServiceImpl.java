package com.service.role;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.role.RoleMapper;
import com.entity.Role;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleMapper roleMapper;
	
	@Override
	public List<Role> GetRolelist() {
		List<Role> list=new ArrayList<Role>();
		try {
			list=roleMapper.GetRolelist();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int addRole(Role role) {
		int count=0;
		try {
			count=roleMapper.addRole(role);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int getreleCode(String roleCode) {
		int count=0;
		try {
			count=roleMapper.getreleCode(roleCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Role getroleByid(String roleid) {
		Role role=null;
		try {
			role=roleMapper.getroleByid(roleid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return role;
	}

	@Override
	public int updaterole(Role role) {
		int count=0;
		try {
			count=roleMapper.updaterole(role);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int delrole(String roleid) {
		int count=0;
		try {
			count=roleMapper.delrole(roleid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

}
