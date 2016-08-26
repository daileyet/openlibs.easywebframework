package com.openthinks.easyweb.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.openthinks.easyweb.WebStatic;
import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.Controller;
import com.openthinks.easyweb.annotation.process.objects.WebContainer;
import com.openthinks.easyweb.annotation.process.objects.WebController;
import com.openthinks.easyweb.annotation.process.objects.WebFilter;
import com.openthinks.easyweb.annotation.process.objects.WebInstancer;
import com.openthinks.easyweb.annotation.process.objects.WebMethod;
import com.openthinks.easyweb.context.RequestSuffix;
import com.openthinks.easyweb.context.WebContexts;

/**
 * Servlet implementation class WebProcessMonitor<BR>
 * Simple monitor for all the configured {@link Controller}
 */
public class WebProcessMonitor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String TEMPLATE_RESOURCE = "monitor.template.html";
	public static final String TEMPLATE_RESOURCE_CSS = "bootstrap.min.css";
	public static final String TEMPLATE_RESOURCE_CONFIG = "template.porperties";
	public static final String TEMPLATE_CONFIG_PAGE_NAME = "TEMPLATE_PAGE_NAME";
	public static final String TEMPLATE_CONFIG_ERROR_PAGE_NAME = "TEMPLATE_ERROR_PAGE_NAME";
	public static final String TEMPLATE_CONFIG_FILE_NAME = "TEMPLATE_CSS_FILE_NAME";
	private Properties templateConfig = new Properties();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WebProcessMonitor() {
		super();
		try {
			templateConfig.load(getClass().getResourceAsStream(TEMPLATE_RESOURCE_CONFIG));
		} catch (IOException e) {
			templateConfig.putIfAbsent(TEMPLATE_CONFIG_PAGE_NAME, TEMPLATE_RESOURCE);
			templateConfig.putIfAbsent(TEMPLATE_CONFIG_FILE_NAME, TEMPLATE_RESOURCE_CSS);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean enable = false;
		String remoteEnable = getInitParameter(WebStatic.WEB_MONITOR_INIT_PARAM_ENABLE_REMOTE);
		if (remoteEnable != null) {
			enable = Boolean.valueOf(remoteEnable);
		}
		if (!enable && !isLocalAddress(request)) {
			generateErrorPage(request, response);
		} else {
			generatePage(request, response);
		}
	}

	private void generateErrorPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		StringBuffer buffer = readFromSource(templateConfig.getProperty(TEMPLATE_CONFIG_ERROR_PAGE_NAME));
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		String page = buffer.toString();
		page = resolveCssOffline(page);
		out.print(page);
		out.flush();
	}

	private boolean isLocalAddress(HttpServletRequest request) {
		String host = WebUtils.getRemoteIP(request);
		boolean isLocal = false;
		try {
			InetAddress inetAddress = InetAddress.getByName(host);
			isLocal = inetAddress.isAnyLocalAddress() || inetAddress.isLoopbackAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return isLocal;
	}

	/**
	 * @throws IOException 
	 * 
	 */
	protected void generatePage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		WebContainer webContainer = WebContexts.get().getWebContainer();
		StringBuffer buffer = readFromSource(templateConfig.getProperty(TEMPLATE_CONFIG_PAGE_NAME));
		Set<WebController> controllers = webContainer.getWebControllers();
		Set<WebFilter> filters = webContainer.getWebFilters();
		sortInstancers(controllers);
		sortInstancers(filters);
		StringBuffer dynamicContent = new StringBuffer();
		if (!controllers.isEmpty()) {
			dynamicContent.append("<tr>");
			dynamicContent.append("<td colspan='4'><b>WebController</b></td>");
			dynamicContent.append("</tr>");
		}
		for (WebController controller : controllers) {
			generateAndAppendRow(dynamicContent, controller, true);
		}
		if (!filters.isEmpty()) {
			dynamicContent.append("<tr>");
			dynamicContent.append("<td colspan='4'><b>WebFilter</b></td>");
			dynamicContent.append("</tr>");
		}
		for (WebFilter filter : filters) {
			generateAndAppendRow(dynamicContent, filter, false);
		}
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		String page = buffer.toString().replace("{{tbody.content}}", dynamicContent.toString());
		page = resolveCssOffline(page);
		out.print(page);
		out.flush();
	}

	/**
	 * @param dynamicContent
	 * @param controller
	 */
	protected void generateAndAppendRow(StringBuffer dynamicContent, WebInstancer instancer, boolean needLink) {
		dynamicContent.append("<tr>");
		dynamicContent.append("<td>" + instancer.getName() + "</td>");
		dynamicContent.append("<td>" + instancer.getType().getName() + "</td>");
		dynamicContent.append("<td>" + instancer.getFullPath() + "</td>");
		//dynamicContent.append("<td>" + instancer.getRelativePath() + "</td>");
		dynamicContent.append("<td style=\"padding: 0\">");
		if (instancer.getSize() > 0) {
			dynamicContent.append("<div class=\"list-group\" style=\"margin: 0\">");
			for (WebMethod method : instancer.children()) {
				if (needLink)
					dynamicContent.append("<a class=\"list-group-item\" title=\"" + method.getFullPath() + "\" href=\""
							+ getLink(method) + "\">" + method.getName() + "</a>");
				else
					dynamicContent.append("<span class=\"list-group-item\" title=\"" + method.getFullPath() + "\">"
							+ method.getName() + "</span>");
			}
			dynamicContent.append("</div>");
		} else {
			dynamicContent.append("<span>no data found</span>");
		}
		dynamicContent.append("</td>");
		dynamicContent.append("</tr>");
	}

	/**
	 * @param webContainer
	 * @return
	 */
	protected void sortInstancers(Set<? extends WebInstancer> instancers) {
		instancers.stream().sorted((inst1, inst2) -> {
			return inst1.getName().compareTo(inst2.getName());
		});
	}

	private StringBuffer readFromSource(String templateResource) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(getClass().getResourceAsStream(templateResource)));
		String line;
		StringBuffer buffer = new StringBuffer();
		do {
			line = bufferedReader.readLine();
			if (line != null)
				buffer.append(line);
		} while (line != null);
		bufferedReader.close();
		return buffer;
	}

	private String resolveCssOffline(String page) throws IOException {
		StringBuffer cssBuffer = readFromSource(templateConfig.getProperty(TEMPLATE_CONFIG_FILE_NAME));
		return page.replace("{{bootstrap.min.css}}", cssBuffer.toString());
	}

	private String getLink(WebMethod method) {
		RequestSuffix requestSuffix = WebContexts.get().getWebConfigure().getRequestSuffix();
		return method.getFullPath() + requestSuffix.options()[0];
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
