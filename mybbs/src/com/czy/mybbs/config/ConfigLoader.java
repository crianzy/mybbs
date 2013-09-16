package com.czy.mybbs.config;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigLoader {

	/**
	 * 读取主配在文件 SystemGlobals.properties
	 * 
	 * @param path
	 *            整个项目部署的绝对路径 servlet.getRealPath();
	 */
	public static void loadSystemGlobals(String path) {
		SystemGlobals.init(path, path + ConfigKeys.SYSTEMGLOBALS_PATH);
	}

	/**
	 * 读取 moduleMapping.properties
	 * @param baseDir config 的目录
	 * @return	返回读取后的properties对象
	 */
	public static Properties loadModuleMapping(String baseDir) {
		FileInputStream fis = null;
		Properties moduleMapping = new Properties();
		try {
			fis = new FileInputStream(baseDir + ConfigKeys.MODULE_MAPPING_PATH);
			moduleMapping.load(fis);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return moduleMapping;
	}
}
