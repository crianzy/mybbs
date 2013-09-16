package com.czy.mybbs.repository;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.czy.mybbs.config.ConfigLoader;

public class ModuleRepository {
	private static final Logger logger = Logger
			.getLogger(ModuleRepository.class);

	private static Properties modulaMapping;

	/**
	 * 初始化module 仓库，
	 * 
	 * @param basedir
	 *            配置文件目录的路径 config 的地址
	 */
	public static void init(String basedir) {
		modulaMapping = ConfigLoader.loadModuleMapping(basedir);
	}

	public static String getModuleClass(String module) {
		if (modulaMapping == null) {
			logger.error("Null modules ");
		}
		return modulaMapping.getProperty(module);
	}

}
