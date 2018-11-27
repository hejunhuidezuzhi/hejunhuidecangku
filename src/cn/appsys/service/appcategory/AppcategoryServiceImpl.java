package cn.appsys.service.appcategory;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appcategory.AppcategoryMapper;
import cn.appsys.pojo.Appcategory;
@Service("app_category")
public class AppcategoryServiceImpl implements AppcategoryService {
	@Resource
	private AppcategoryMapper app_categoryMapper;
	@Override
	public List<Appcategory> getByparentId(String parentId) {
		List<Appcategory> list= new ArrayList<>();
		try {
			list=app_categoryMapper.getByparentId(parentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
