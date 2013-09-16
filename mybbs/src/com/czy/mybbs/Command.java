package com.czy.mybbs;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletResponse;

import com.czy.mybbs.config.SystemGlobals;
import com.czy.mybbs.context.RequestContext;
import com.czy.mybbs.repository.TemplateRepository;

import freemarker.template.SimpleHash;
import freemarker.template.Template;

public abstract class Command {
	private static Class[] NO_ARGS_CLASS = new Class[0];
	private static Object[] NO_ARGS_OBJECT = new Object[0];

	protected RequestContext request;
	protected HttpServletResponse response;
	protected SimpleHash templadtContext;// templadte 模板使用的键值队的 context
	private String templateName;// 模版名称会对应到具体的页面模版

	public abstract void list();
	
	public void setTemplateName(String templateNameKey){
		this.templateName = TemplateRepository.getTempladteName(templateNameKey);
	}

	public Template process(RequestContext request,
			HttpServletResponse response, SimpleHash templadtContext) {
		System.out.println(this.getClass() + " : process");
		this.request = request;
		this.response = response;
		this.templadtContext = templadtContext;
		String action = this.request.getAction();
		if(action == null){
			this.list();
		}else{
			try {
				// 在方法执行的过程中
				this.getClass().getMethod(action, NO_ARGS_CLASS)
						.invoke(this, NO_ARGS_OBJECT);
			} catch (NoSuchMethodException e) {
				this.list();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			return BBSExecuteContext.templateConfig().getTemplate(
					new StringBuffer(SystemGlobals.getValue("template.dir"))// 此处获取的dir
																			// 是
																			// 在template下的default
																			// 或其他模版
							.append('/').append(this.templateName).toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
