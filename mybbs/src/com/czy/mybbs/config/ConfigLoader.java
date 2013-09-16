package com.czy.mybbs.config;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigLoader {

	/**
	 * ��ȡ�������ļ� SystemGlobals.properties
	 * 
	 * @param path
	 *            ������Ŀ����ľ���·�� servlet.getRealPath();
	 */
	public static void loadSystemGlobals(String path) {
		SystemGlobals.init(path, path + ConfigKeys.SYSTEMGLOBALS_PATH);
	}

	/**
	 * ��ȡ moduleMapping.properties
	 * @param baseDir config ��Ŀ¼
	 * @return	���ض�ȡ���properties����
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
