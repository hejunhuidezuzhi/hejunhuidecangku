package cn.appsys.service.datadictionary;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Datadictionary;

public interface DatadictionaryService {
	public List<Datadictionary> getBytypeCode(@Param("typeCode")String typeCode);
}
