package com.czy.mybbs.config;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigLoader {

	/**
	 * 读取主配在文件 SystemGlobals.properties
	 * 
	 * @param path
	 *            整个项目部署的绝对路径  servlet.getRealPath();
	 */
	public static void loadSystemGlobals(String path) {
		SystemGlobals.init(path, path
				+ "/WEB-INF/config/SystemGlobals.properties");
	}

	public static Properties loadModuleMapping(String baseDir) {
		FileInputStream fis = null;
		Properties moduleMapping = new Properties();
		try {
			fis = new FileInputStream(baseDir + "/moduleMapping.properties");
			moduleMapping.load(fis);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return moduleMapping;
	}
}
