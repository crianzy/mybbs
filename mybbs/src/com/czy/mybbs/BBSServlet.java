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
		// TODO 相关仓库 缓存
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
	 * 更具 module 处理命令 返回输出流道前端
	 * 
	 * @param out
	 *            输出流 response.getWriter();
	 * @param request
	 *            自己封装的Request
	 * @param response
	 *            HttpServletReesponse
	 * @param encoding
	 *            编码格式
	 * @param context
	 *            templateContext
	 * @param moduleClass
	 * @return 返回输出流道前端
	 * @throws Exception
	 */
	private Writer processCommand(Writer out, RequestContext request,
			HttpServletResponse response, String encoding, SimpleHash context,
			String moduleClass) throws Exception {
		// 根据 module 的ClassName new 出一个实例来
		Command c = this.retrieveCommand(moduleClass);
		// new出来的实例 执行命令方法，在方法中会执行 url 中德action 否则执行list 得到具体的template模版文件
		Template template = c.process(request, response, context);
		// 设置输出流 的contentType
		String contentType = "text/html; charset=" + encoding;
		response.setContentType(contentType);
		// 通过 freemarke 把模板导入到输出流中
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
