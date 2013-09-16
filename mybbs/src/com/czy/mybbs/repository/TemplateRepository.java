package com.czy.mybbs.repository;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class TemplateRepository {

	private static Map cache = new HashMap();

	public static void load(String filename) {
		FileInputStream fis = null;
		try {
			Properties p = new Properties();
			fis = new FileInputStream(filename);
			p.load(fis);
			for (Iterator iter = p.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				cache.put(key, p.getProperty(key));
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getTempladteName(String key) {
		return (String) cache.get(key);
	}

}
