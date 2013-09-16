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
	protected SimpleHash templadtContext;// templadte ģ��ʹ�õļ�ֵ�ӵ� context
	private String templateName;// ģ�����ƻ��Ӧ�������ҳ��ģ��

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
				// �ڷ���ִ�еĹ�����
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
					new StringBuffer(SystemGlobals.getValue("template.dir"))// �˴���ȡ��dir
																			// ��
																			// ��template�µ�default
																			// ������ģ��
							.append('/').append(this.templateName).toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
