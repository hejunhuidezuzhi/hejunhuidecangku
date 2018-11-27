package cn.appsys.service.datadictionary;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.datadictionary.DatadictionaryMapper;
import cn.appsys.pojo.Datadictionary;

@Service("data_dictionary")
public class DatadictionaryServiceImpl implements DatadictionaryService {
	@Resource
	private DatadictionaryMapper data_dictionaryMapper;
	@Override
	public List<Datadictionary> getBytypeCode(String typeCode) {
		List<Datadictionary> list=new ArrayList<>();
		try {
			list=data_dictionaryMapper.getBytypeCode(typeCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
