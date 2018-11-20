package com.service.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.Provider;
import com.entity.User;

public interface UserService {
	public List<User> GetUserList();
	
	public User getlogin(String userCode);
	
	public int getUserCount(String queryUserRole,String queryUsername);
	
	public List<User> getProviderlist(String queryUserRole,String queryUsername,int currentPageNo,int pagesize);
	
	public User getUserByid(Integer id);
	
	public User getUserBypwd(Integer id,String userPassword);
	
	public Integer updatepwd(Integer id,String userPassword);
	
	public int adduser(User user);
	
	public Boolean deleteByid(Integer id);
	
	public int updateuser(User user);
}
