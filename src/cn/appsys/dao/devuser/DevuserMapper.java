package cn.appsys.dao.devuser;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Devuser;

public interface DevuserMapper {
	/**
	 * Ç°Ì¨µÇÂ¼
	 * @param devCode
	 * @param devPassword
	 * @return
	 */
	public Devuser login(@Param("devCode")String devCode,@Param("devPassword")String devPassword);
	
	
}
