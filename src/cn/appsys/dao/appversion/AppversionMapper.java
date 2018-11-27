package cn.appsys.dao.appversion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Appversion;

public interface AppversionMapper {
	/**
	 * ����appId��ѯ�汾��Ϣ
	 * @param id
	 * @return
	 */
	public List<Appversion> getByappId(@Param("id")String id);
	
	
	
	/**
	 * ���Ӱ汾��Ϣ
	 * @param appversion
	 * @return
	 */
	public int Addappversion(Appversion appversion);
	
	/**
	 * ɾ��APP�汾��Ϣ
	 * @param id
	 * @return
	 */
	public int deleteAppversion(@Param("id")String id);
	
	/**
	 * ����APPID��ѯ���°汾
	 * @param appId
	 * @return
	 */
	public Appversion GetByappidDate(@Param("appId")Integer appId);
	
	/**
	 * ɾ��apk�ļ�
	 * @param id
	 * @return
	 */
	public int deleteApkFile(@Param("id")String id);
	
	/**
	 * �޸İ汾��Ϣ
	 * @param appversion
	 * @return
	 */
	public int updateAppversion(Appversion appversion);
	
}
