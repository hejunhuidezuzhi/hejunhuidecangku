package com.tools;

import java.sql.Connection;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.entity.User;

import com.service.user.UserService;
import com.service.user.UserServiceImpl;

public class UserDaoTest {
	public static void main(String[] args) {
		Chaxun();
	}

	public static void Chaxun() {
		ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext*.xml");
		UserService userService =(UserService) context.getBean("userService");
		
		BaseDao.getConnection();
		ConfigManager.getInstance();
		
		List<User> list=userService.GetUserList();
		for (User user : list) {
			System.out.println(user.getUserName());
		}
	}
}
