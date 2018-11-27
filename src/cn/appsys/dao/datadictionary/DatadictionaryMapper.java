package cn.appsys.dao.datadictionary;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Datadictionary;

public interface DatadictionaryMapper {
	/**
	 * 根据编码查询信息
	 * @param typeCode
	 * @return
	 */
	public List<Datadictionary> getBytypeCode(@Param("typeCode")String typeCode);
}
