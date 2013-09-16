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
	 * 获取 BBSExecuteContext
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
	 * BBSExecuteContext 做出改变后再设置回去
	 * 
	 * @param ex
	 */
	public static void set(BBSExecuteContext ex) {
		userData.set(ex);
	}

	/**
	 * 设置 template 模版配置
	 * 
	 * @param config
	 */
	public static void setTemplateConfig(Configuration config) {
		templateConfig = config;
	}

	/**
	 * 获取template 模版配置
	 * 
	 * @return
	 */
	public static Configuration templateConfig() {
		return templateConfig;
	}

	/**
	 * 获取当线程的 templateContext
	 * 
	 * @return
	 */
	public static SimpleHash getTemplateContext() {
		//用userdate 该线程第一次获取对象时是返回null
		BBSExecuteContext bbsExecuteContext = (BBSExecuteContext) userData
				.get();
		// if (bbsExecuteContext == null) {
		// bbsExecuteContext = new BBSExecuteContext();
		// userData.set(bbsExecuteContext);
		// }
		return bbsExecuteContext.templateContext;
	}

}
