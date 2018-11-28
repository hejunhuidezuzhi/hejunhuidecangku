package com.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.Provider;
import com.entity.User;

public interface UserMapper {
	
	public List<User> GetUserList();
	
	public User getlogin(@Param("userCode")String userCode);
	
	public int getUserCount(@Param("queryUserRole")String queryUserRole,
			@Param("queryUsername")String queryUsername);
	
	public List<User> getUserlist(@Param("queryUserRole")String queryUserRole,
			@Param("queryUsername")String queryUsername,@Param("currentPageNo")int currentPageNo,
			@Param("pagesize")int pagesize);
	
	public User getUserByid(@Param("id")Integer id);
	
	/***
	 * 判断密码是否正确
	 * @param id
	 * @param userPassword
	 * @return
	 */
	public User getUserBypwd(@Param("id")Integer id,@Param("userPassword")String userPassword);
	/**
	 * 修改密码
	 * @param id
	 * @param userPassword
	 * @return
	 */
	public Integer updatepwd(@Param("id")Integer id,@Param("userPassword")String userPassword);
	
	/**
	 * 增加用户
	 * @param user
	 * @return
	 */
	public int adduser(User user);
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	public int deleteByid(@Param("id")Integer id);
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	public int updateuser(User user);
	
}
