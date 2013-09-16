package com.czy.mybbs;

import java.io.File;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.czy.mybbs.config.ConfigLoader;
import com.czy.mybbs.config.SystemGlobals;
import com.czy.mybbs.repository.ModuleRepository;
import com.czy.mybbs.repository.TemplateRepository;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;

public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1433313860699859943L;

	public static Logger logger = Logger.getLogger(BaseServlet.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			// ��Ŀ�������ʵ·��������ڴ��̵� D��//������ ֮��� ����url
			String applicationPath = config.getServletContext().getRealPath("");
			// �����������ʼ� SystemGlobals.properties
			ConfigLoader.loadSystemGlobals(applicationPath);
			// ��ʼ�� moduleMapping ģ��
			ModuleRepository.init(SystemGlobals.getValue("configPath"));
			// template ģ����������
			Configuration templateCfg = new Configuration();
			templateCfg.setTemplateUpdateDelay(2);
			templateCfg.setSetting("number_format", "#");
			templateCfg.setSharedVariable("startupTime",
					new Long(new Date().getTime()));
			String templatePath = applicationPath + "/templates";
			FileTemplateLoader templateLoader = new FileTemplateLoader(
					new File(templatePath));
			templateCfg.setTemplateLoader(templateLoader);
			BBSExecuteContext.setTemplateConfig(templateCfg);
			// TODO ������������ļ�
			this.loadConfigStuff();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ����һЩ��������
	 */
	protected void loadConfigStuff() {
		// ����ģ�� templateMapping.properties
		TemplateRepository.load(SystemGlobals.getValue("configPath") + "/"
				+ SystemGlobals.getValue("templates.mapping"));
	}

}
