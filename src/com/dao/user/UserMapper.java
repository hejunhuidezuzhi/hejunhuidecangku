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
	 * �ж������Ƿ���ȷ
	 * @param id
	 * @param userPassword
	 * @return
	 */
	public User getUserBypwd(@Param("id")Integer id,@Param("userPassword")String userPassword);
	/**
	 * �޸�����
	 * @param id
	 * @param userPassword
	 * @return
	 */
	public Integer updatepwd(@Param("id")Integer id,@Param("userPassword")String userPassword);
	
	/**
	 * �����û�
	 * @param user
	 * @return
	 */
	public int adduser(User user);
	/**
	 * ɾ���û�
	 * @param id
	 * @return
	 */
	public int deleteByid(@Param("id")Integer id);
	/**
	 * �޸��û�
	 * @param user
	 * @return
	 */
	public int updateuser(User user);
	
}
