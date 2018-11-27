package cn.appsys.dao.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Appinfo;
import cn.appsys.pojo.Appversion;

public interface AppinfoMapper {
	
	/**
	 * 查询总条数
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
	 * 分页查询
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
	 * 判断APKName是否存在
	 * @param APKName
	 * @return
	 */
	public Appinfo GetByAPKName(@Param("APKName")String APKName);
	
	/**
	 * 增加信息
	 * @param appinfo
	 * @return
	 */
	public int Addappinfo(Appinfo appinfo);
	
	/**
	 * 根据ID查询信息
	 * @param id
	 * @return
	 */
	public Appinfo GetByappId(@Param("id")String id);
	
	/**
	 * 修改APP信息
	 * @param appinfo
	 * @return
	 */
	public int updateAppinfo(Appinfo appinfo);
	
	/**
	 * 删除Logo
	 * @param id
	 * @return
	 */
	public int deleteAppLogo(@Param("id")String id);
	
	/**
	 * 删除APP
	 * @param id
	 * @return
	 */
	public int deleteApp(@Param("id")String id);
	
	/**
	 * 修改最新版本号
	 * @return
	 */
	public int updateversonId(@Param("id")Integer id,@Param("versionId")Integer versionId);
	
	public int updatestatus(@Param(value="status")String status,@Param(value="id")String id);
}
