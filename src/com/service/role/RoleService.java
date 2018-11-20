package com.service.role;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.Role;

public interface RoleService {
	public List<Role> GetRolelist();
	
	public int addRole(Role role);
	
	public int getreleCode(String roleCode);
	
	public Role getroleByid(String roleid);
	
public int updaterole(Role role);
	
	public int delrole(@Param("roleid")String roleid);
}
