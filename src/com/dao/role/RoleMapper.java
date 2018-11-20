package com.dao.role;

import com.entity.Role;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
	
	public List<Role> GetRolelist();
	
	public int addRole(Role role);
	
	public int getreleCode(@Param("roleCode")String roleCode);
	
	public Role getroleByid(@Param("roleid")String roleid);
	
	public int updaterole(Role role);
	
	public int delrole(@Param("roleid")String roleid);
}
