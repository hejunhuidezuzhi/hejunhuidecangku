package cn.appsys.dao.datadictionary;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Datadictionary;

public interface DatadictionaryMapper {
	/**
	 * ���ݱ����ѯ��Ϣ
	 * @param typeCode
	 * @return
	 */
	public List<Datadictionary> getBytypeCode(@Param("typeCode")String typeCode);
}
