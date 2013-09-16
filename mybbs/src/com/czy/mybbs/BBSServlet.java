package com.czy.mybbs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.czy.mybbs.config.SystemGlobals;
import com.czy.mybbs.context.RequestContext;
import com.czy.mybbs.repository.ModuleRepository;

import freemarker.template.SimpleHash;
import freemarker.template.Template;

public class BBSServlet extends BaseServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println(SystemGlobals.getValue("test") + "bbsservlet");
		// TODO ��زֿ� ����
		BBSExecuteContext ex = BBSExecuteContext.get();
		// ex.setConnection(conn);
		BBSExecuteContext.set(ex);
	}

	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			Writer out = null;
			String encoding = SystemGlobals.getValue("encoding");
			RequestContext request = new RequestContext(req);

			BBSExecuteContext ex = BBSExecuteContext.get();
			ex.set(ex);

			String module = request.getModule();
			String moduleClass = module != null ? ModuleRepository
					.getModuleClass(module) : null;
			if (moduleClass != null) {
				Command command = retrieveCommand(moduleClass);
			}
			SimpleHash templateContext = BBSExecuteContext.getTemplateContext();
			out = processCommand(out, request, resp, encoding, templateContext,
					moduleClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���� module �������� �����������ǰ��
	 * 
	 * @param out
	 *            ����� response.getWriter();
	 * @param request
	 *            �Լ���װ��Request
	 * @param response
	 *            HttpServletReesponse
	 * @param encoding
	 *            �����ʽ
	 * @param context
	 *            templateContext
	 * @param moduleClass
	 * @return �����������ǰ��
	 * @throws Exception
	 */
	private Writer processCommand(Writer out, RequestContext request,
			HttpServletResponse response, String encoding, SimpleHash context,
			String moduleClass) throws Exception {
		// ���� module ��ClassName new ��һ��ʵ����
		Command c = this.retrieveCommand(moduleClass);
		// new������ʵ�� ִ����������ڷ����л�ִ�� url �е�action ����ִ��list �õ������templateģ���ļ�
		Template template = c.process(request, response, context);
		// ��������� ��contentType
		String contentType = "text/html; charset=" + encoding;
		response.setContentType(contentType);
		// ͨ�� freemarke ��ģ�嵼�뵽�������
		out = new BufferedWriter(new OutputStreamWriter(
				response.getOutputStream(), encoding));
		template.process(BBSExecuteContext.getTemplateContext(), out);
		out.flush();
		return out;
	}

	public Command retrieveCommand(String moduleClass) throws Exception {
		return (Command) Class.forName(moduleClass).newInstance();
	}

}
