package com.czy.mybbs;

import java.io.File;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.czy.mybbs.config.ConfigKeys;
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
			// 项目部署的真实路径，相对于磁盘的 D：//。。。 之类的 不是url
			String applicationPath = config.getServletContext().getRealPath("");
			// 加载主配置问价 SystemGlobals.properties
			ConfigLoader.loadSystemGlobals(applicationPath);
			
			// template 模版引擎配置
			Configuration templateCfg = new Configuration();
			templateCfg.setTemplateUpdateDelay(2);
			templateCfg.setSetting("number_format", "#");
			templateCfg.setSharedVariable("startupTime",
					new Long(new Date().getTime()));
			//此处路径为 webroot下的templates文件夹
			String templatePath = applicationPath + ConfigKeys.TEMPLATE_BASE_DIR;
			FileTemplateLoader templateLoader = new FileTemplateLoader(
					new File(templatePath));
			templateCfg.setTemplateLoader(templateLoader);
			//将加载好的template放入 ExecuteContext 中，静态变量
			BBSExecuteContext.setTemplateConfig(templateCfg);
			
			// 初始化 moduleMapping 模块
			ModuleRepository.init(SystemGlobals.getValue(ConfigKeys.CONFIG_PATH));
			
			// TODO 加载相关配置文件
			this.loadConfigStuff();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载一些其他东西
	 */
	protected void loadConfigStuff() {
		// 加载模版 templateMapping.properties
		TemplateRepository.load(SystemGlobals.getValue(ConfigKeys.CONFIG_PATH) + "/"
				+ SystemGlobals.getValue(ConfigKeys.TEMPLATES_MAPPING));
	}

}
