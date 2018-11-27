package cn.appsys.dao.appversion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Appversion;

public interface AppversionMapper {
	/**
	 * 根据appId查询版本信息
	 * @param id
	 * @return
	 */
	public List<Appversion> getByappId(@Param("id")String id);
	
	
	
	/**
	 * 增加版本信息
	 * @param appversion
	 * @return
	 */
	public int Addappversion(Appversion appversion);
	
	/**
	 * 删除APP版本信息
	 * @param id
	 * @return
	 */
	public int deleteAppversion(@Param("id")String id);
	
	/**
	 * 根据APPID查询最新版本
	 * @param appId
	 * @return
	 */
	public Appversion GetByappidDate(@Param("appId")Integer appId);
	
	/**
	 * 删除apk文件
	 * @param id
	 * @return
	 */
	public int deleteApkFile(@Param("id")String id);
	
	/**
	 * 修改版本信息
	 * @param appversion
	 * @return
	 */
	public int updateAppversion(Appversion appversion);
	
}
