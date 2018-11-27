package cn.appsys.service.appcategory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Appcategory;

public interface AppcategoryService {
	public List<Appcategory> getByparentId(String parentId);
}
