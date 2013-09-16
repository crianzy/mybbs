package com.czy.mybbs.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class SystemGlobals {
	public static Logger logger = Logger.getLogger(SystemGlobals.class);
	public static SystemGlobals systemGlobals = new SystemGlobals();// ��������

	private static String configPath;// �����ļ�Ŀ¼ config ��Ŀ¼
	private static String applicationPath;//��Ŀ����ľ���·��
	private Properties systemProperties = new Properties();// SystemGlobals.properties
															// ��Ӧ��properties

	// ˽�й����� ����
	private SystemGlobals() {
	}

	/**
	 * ��ʼ��SystemGlobals ��ȡSystemGlobals.properties
	 * 
	 * @param path
	 *            �����ļ�Ŀ¼ config ��·��
	 * @param filepath
	 *            ����������ļ�·��
	 */
	@SuppressWarnings("static-access")
	public static void init(String applicationPath, String filepath) {
		systemGlobals.configPath = applicationPath+"/WEB-INF/config";
		systemGlobals.applicationPath = applicationPath;
		FileInputStream fin;
		// ��ȡ�����ļ�
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
	 * ��ȡ�����ļ��� ��ֵ �������ⲿ����
	 * 
	 * @param key
	 *            �����ļ��е�key
	 * @return �����ļ��е�key����Ӧ��ֵ
	 */
	public static String getValue(String key) {
		return systemGlobals.getVaruableValue(key);
	}

	/**
	 * ��ȡ�����ļ��еļ�ֵ �ڲ�����
	 * 
	 * @param key
	 *            �����ļ��е�key
	 * @return �����ļ��е�key����Ӧ��ֵ
	 */
	public String getVaruableValue(String key) {
		return systemGlobals.systemProperties.getProperty(key);
	}

}
