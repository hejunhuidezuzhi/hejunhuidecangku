package cn.appsys.dao.appcategory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Appcategory;


public interface AppcategoryMapper {
	/**
	 * ≤È—Ø∑÷¿‡
	 * @param parentId
	 * @return
	 */
	public List<Appcategory> getByparentId(@Param("parentId")String parentId);
}
