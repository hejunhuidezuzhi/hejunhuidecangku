package com.service.user;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.user.UserMapper;
import com.entity.Provider;
import com.entity.User;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;
	
	@Override
	public List<User> GetUserList() {
		List<User> list=new ArrayList<User>();
		try {
			list=userMapper.GetUserList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public User getlogin(String userCode) {
		User user=null;
		try {
			user=userMapper.getlogin(userCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public int getUserCount(String queryUserRole, String queryUsername) {
		int count=0;
		try {
			count=userMapper.getUserCount(queryUserRole, queryUsername);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<User> getProviderlist(String queryUserRole, String queryUsername, int currentPageNo, int pagesize) {
		List<User> list=new ArrayList<User>();
		try {
			currentPageNo=(currentPageNo-1)*pagesize;
			list=userMapper.getUserlist(queryUserRole, queryUsername, currentPageNo, pagesize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public User getUserByid(Integer id) {
		User user=null;
		try {
			user=userMapper.getUserByid(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User getUserBypwd(Integer id, String userPassword) {
		User user=null;
		try {
			user=userMapper.getUserBypwd(id, userPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public Integer updatepwd(Integer id, String userPassword) {
		Integer count=0;
		try {
			count=userMapper.updatepwd(id, userPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int adduser(User user) {
		int count=0;
		try {
			count=userMapper.adduser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Boolean deleteByid(Integer id) {
		Boolean flg=true;
		try {
			User user=userMapper.getUserByid(id);
			if (user.getIdPicPath()!=null && user.getIdPicPath().equals("")) {
				File file = new File(user.getIdPicPath());
				if (file.exists()) {
					flg=file.delete();
				}
			}
			if (user.getWorkPicPath()!=null && user.getWorkPicPath().equals("")) {
				File file = new File(user.getWorkPicPath());
				if (file.exists()) {
					flg=file.delete();
				}
			}
			if (flg) {
				userMapper.deleteByid(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flg;
	}

	@Override
	public int updateuser(User user) {
		int count=0;
		try {
			count=userMapper.updateuser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

}
