package cn.appsys.dao.Backenduser;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Backenduser;


public interface BackenduserMapper {
	/**
	 * ��̨��¼
	 * @param devCode
	 * @param devPassword
	 * @return
	 */
	public Backenduser Backendlogin(@Param("userCode")String devCode,@Param("userPassword")String devPassword);
}
