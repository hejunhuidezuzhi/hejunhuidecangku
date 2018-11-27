package cn.appsys.dao.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Appinfo;
import cn.appsys.pojo.Appversion;

public interface AppinfoMapper {
	
	/**
	 * ��ѯ������
	 * @param querySoftwareName
	 * @param queryStatus
	 * @param queryCategoryLevel1
	 * @param queryCategoryLevel2
	 * @param queryCategoryLevel3
	 * @param queryFlatformId
	 * @param pageIndex
	 * @return
	 */
	public int GetAppinfoCount(@Param("querySoftwareName")String querySoftwareName,
			@Param("queryStatus")String queryStatus,
			@Param("queryCategoryLevel1")String queryCategoryLevel1,
			@Param("queryCategoryLevel2")String queryCategoryLevel2,
			@Param("queryCategoryLevel3")String queryCategoryLevel3,
			@Param("queryFlatformId")String queryFlatformId);
	/**
	 * ��ҳ��ѯ
	 * @param querySoftwareName
	 * @param queryStatus
	 * @param queryCategoryLevel1
	 * @param queryCategoryLevel2
	 * @param queryCategoryLevel3
	 * @param queryFlatformId
	 * @param currentPageNo
	 * @param pagesize
	 * @return
	 */
	public List<Appinfo> GetAppinfoList(@Param("querySoftwareName")String querySoftwareName,
			@Param("queryStatus")String queryStatus,
			@Param("queryCategoryLevel1")String queryCategoryLevel1,
			@Param("queryCategoryLevel2")String queryCategoryLevel2,
			@Param("queryCategoryLevel3")String queryCategoryLevel3,
			@Param("queryFlatformId")String queryFlatformId,
			@Param("currentPageNo")Integer currentPageNo,
			@Param("pagesize")Integer pagesize);
	/**
	 * �ж�APKName�Ƿ����
	 * @param APKName
	 * @return
	 */
	public Appinfo GetByAPKName(@Param("APKName")String APKName);
	
	/**
	 * ������Ϣ
	 * @param appinfo
	 * @return
	 */
	public int Addappinfo(Appinfo appinfo);
	
	/**
	 * ����ID��ѯ��Ϣ
	 * @param id
	 * @return
	 */
	public Appinfo GetByappId(@Param("id")String id);
	
	/**
	 * �޸�APP��Ϣ
	 * @param appinfo
	 * @return
	 */
	public int updateAppinfo(Appinfo appinfo);
	
	/**
	 * ɾ��Logo
	 * @param id
	 * @return
	 */
	public int deleteAppLogo(@Param("id")String id);
	
	/**
	 * ɾ��APP
	 * @param id
	 * @return
	 */
	public int deleteApp(@Param("id")String id);
	
	/**
	 * �޸����°汾��
	 * @return
	 */
	public int updateversonId(@Param("id")Integer id,@Param("versionId")Integer versionId);
	
	public int updatestatus(@Param(value="status")String status,@Param(value="id")String id);
}
