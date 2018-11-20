package com.tools;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	private static Properties properties;
	private static ConfigManager configManager;
	
	private ConfigManager() {
		String configFile = "database.properties";
		properties = new Properties();
		InputStream is = ConfigManager.class.getClassLoader().getResourceAsStream(configFile);
		try {
			properties.load(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//全局访问点   
		//懒汉模式：给方法上锁synchronized，但是代价太高，每次获取都要同步
		public static  ConfigManager getInstance(){
			if(configManager == null){
				configManager = new ConfigManager();
			}
			return configManager;
		}
		
		public String getValue(String key){
			return properties.getProperty(key);
		}
}
