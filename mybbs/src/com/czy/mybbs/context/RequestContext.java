package com.czy.mybbs.context;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.constraints.Length;

import com.czy.mybbs.config.SystemGlobals;

public class RequestContext {

	private HttpServletRequest request;
	private String extension = SystemGlobals.getValue("url.extension");

	public RequestContext(HttpServletRequest request) {
		this.request = request;
		this.request.getContextPath();
		try {
			this.request.setCharacterEncoding(SystemGlobals
					.getValue("encoding"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("request.getContextPath() = "
				+ this.request.getContextPath());
		System.out.println("request.getRequestURI() = "
				+ this.request.getRequestURI());
		extractRequestUri(this.request.getRequestURI(),
				this.request.getContextPath());
	}

	/**
	 * 解析 uri 提取出 module action method
	 */
	public void extractRequestUri(String requestUri, String contextPath) {
		// 把uri 中的项目名去掉
		if (contextPath != null && contextPath.length() > 0) {
			requestUri = requestUri.substring(contextPath.length()+1,
					requestUri.length());
			System.out.println("requesturi = " + requestUri);
		}
		requestUri = requestUri.substring(0, requestUri.indexOf(extension));
		String url[] = requestUri.split("/");
		setAttribute("module", url[0]);
		if (url.length > 1) {
			setAttribute("action", url[1]);
		}

	}

	public String getModule() {
		return (String) getAttibute("module");
	}

	public String getAction() {
		return (String) getAttibute("action");
	}

	public String getParameter(String parameter) {
		return this.request.getParameter(parameter);
	}

	public void setAttribute(String name, Object value) {
		this.request.setAttribute(name, value);
	}

	public Object getAttibute(String name) {
		return this.request.getAttribute(name);
	}
}
