package com.czy.mybbs;

import org.apache.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;

public class BBSExecuteContext {
	private static ThreadLocal userData = new ThreadLocal();
	private static Logger logger = Logger.getLogger(BBSExecuteContext.class);
	private static Configuration templateConfig;

	private SimpleHash templateContext = new SimpleHash(
			ObjectWrapper.BEANS_WRAPPER);

	/**
	 * ��ȡ BBSExecuteContext
	 * 
	 * @return
	 */
	public static BBSExecuteContext get() {
		BBSExecuteContext ex = (BBSExecuteContext) userData.get();

		if (ex == null) {
			ex = new BBSExecuteContext();
			userData.set(ex);
		}

		return ex;
	}

	/**
	 * BBSExecuteContext �����ı�������û�ȥ
	 * 
	 * @param ex
	 */
	public static void set(BBSExecuteContext ex) {
		userData.set(ex);
	}

	/**
	 * ���� template ģ������
	 * 
	 * @param config
	 */
	public static void setTemplateConfig(Configuration config) {
		templateConfig = config;
	}

	/**
	 * ��ȡtemplate ģ������
	 * 
	 * @return
	 */
	public static Configuration templateConfig() {
		return templateConfig;
	}

	/**
	 * ��ȡ���̵߳� templateContext
	 * 
	 * @return
	 */
	public static SimpleHash getTemplateContext() {
		//��userdate ���̵߳�һ�λ�ȡ����ʱ�Ƿ���null
		BBSExecuteContext bbsExecuteContext = (BBSExecuteContext) userData
				.get();
		// if (bbsExecuteContext == null) {
		// bbsExecuteContext = new BBSExecuteContext();
		// userData.set(bbsExecuteContext);
		// }
		return bbsExecuteContext.templateContext;
	}

}
