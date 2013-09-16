package com.czy.mybbs.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class SystemGlobals {
	public static Logger logger = Logger.getLogger(SystemGlobals.class);
	public static SystemGlobals systemGlobals = new SystemGlobals();// 单粒对象

	private static String configPath;// 配置文件目录 config 的目录
	private static String applicationPath;//项目部署的绝对路径
	private Properties systemProperties = new Properties();// SystemGlobals.properties
															// 对应的properties

	// 私有构造器 单粒
	private SystemGlobals() {
	}

	/**
	 * 初始化SystemGlobals 读取SystemGlobals.properties
	 * 
	 * @param path
	 *            配置文件目录 config 的路径
	 * @param filepath
	 *            具体的配置文件路径
	 */
	@SuppressWarnings("static-access")
	public static void init(String applicationPath, String filepath) {
		systemGlobals.configPath = applicationPath+"/WEB-INF/config";
		systemGlobals.applicationPath = applicationPath;
		FileInputStream fin;
		// 读取配置文件
		try {
			fin = new FileInputStream(new File(filepath));
			systemGlobals.systemProperties.load(fin);
			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		systemGlobals.systemProperties.put("configPath", configPath);
		systemGlobals.systemProperties.put("applicationPath",applicationPath);
	}

	/**
	 * 获取配置文件中 键值 单粒，外部调用
	 * 
	 * @param key
	 *            配置文件中的key
	 * @return 配置文件中的key所对应的值
	 */
	public static String getValue(String key) {
		return systemGlobals.getVaruableValue(key);
	}

	/**
	 * 获取配置文件中的键值 内部调用
	 * 
	 * @param key
	 *            配置文件中的key
	 * @return 配置文件中的key所对应的值
	 */
	public String getVaruableValue(String key) {
		return systemGlobals.systemProperties.getProperty(key);
	}

}
