package cn.appsys.dao.devuser;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Devuser;

public interface DevuserMapper {
	/**
	 * ǰ̨��¼
	 * @param devCode
	 * @param devPassword
	 * @return
	 */
	public Devuser login(@Param("devCode")String devCode,@Param("devPassword")String devPassword);
	
	
}
