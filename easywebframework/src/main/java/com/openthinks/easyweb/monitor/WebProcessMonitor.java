package com.openthinks.easyweb.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		generatePage(request, response);
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
			dynamicContent.append("<td colspan='5'><b>WebController</b></td>");
			dynamicContent.append("</tr>");
		}
		for (WebController controller : controllers) {
			generateAndAppendRow(dynamicContent, controller);
		}
		if (!filters.isEmpty()) {
			dynamicContent.append("<tr>");
			dynamicContent.append("<td colspan='5'><b>WebFilter</b></td>");
			dynamicContent.append("</tr>");
		}
		for (WebFilter filter : filters) {
			generateAndAppendRow(dynamicContent, filter);
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
	protected void generateAndAppendRow(StringBuffer dynamicContent, WebInstancer instancer) {
		dynamicContent.append("<tr>");
		dynamicContent.append("<td>" + instancer.getName() + "</td>");
		dynamicContent.append("<td>" + instancer.getType().getName() + "</td>");
		dynamicContent.append("<td>" + instancer.getFullPath() + "</td>");
		dynamicContent.append("<td>" + instancer.getRelativePath() + "</td>");
		dynamicContent.append("<td style=\"padding: 0\">");
		if (instancer.getSize() > 0) {
			dynamicContent.append("<div class=\"list-group\" style=\"margin: 0\">");
			for (WebMethod method : instancer.children()) {
				dynamicContent.append(
						"<a class=\"list-group-item\" href=\"" + getLink(method) + "\">" + method.getName() + "</a>");
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
		//generatePageSimple(response);
		doGet(request, response);
	}

	/**
	 * @param response
	 * @throws IOException
	 */
	protected void generatePageSimple(HttpServletResponse response) throws IOException {
		WebContainer container = WebContexts.get().getWebContainer();
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		if (container == null)
			return;
		if (container.children().size() > 0) {
			out.print("<span>Web Container Objects List:</span></br></br>");
			out.print("<table border=1 width=100%>");
			out.print("<tr>");
			out.print("<th>Controller Name</th>");
			out.print("<th>Class Type</th>");
			out.print("<th>Request Mapping</th>");
			out.print("<th>Path</th>");
			out.print("<th>Web Method Count</th>");
			out.print("<th>Web Method Details</th>");
			out.print("</tr>");
			for (WebInstancer controller : container.children()) {
				out.print("<tr>");
				out.print("<td>");
				out.print(controller.getName());
				out.print("</td>");
				out.print("<td>");
				out.print(controller.getType().getName());
				out.print("</td>");
				out.print("<td>");
				out.print(controller.getFullPath());
				out.print("</td>");
				out.print("<td>");
				out.print(controller.getRelativePath());
				out.print("</td>");
				out.print("<td>");
				out.print(controller.children().size());
				out.print("</td>");

				out.print("<td>");
				if (controller.children().size() > 0) {
					out.print("<table border=1 width=100%>");
					out.print("<tr>");
					out.print("<th>Method Name</th>");
					out.print("<th>Request Mapping</th>");
					out.print("</tr>");
					for (WebMethod method : controller.children()) {
						out.print("<tr>");
						out.print("<td width=30%>");
						out.print(method.getName());
						out.print("</td>");
						out.print("<td>");
						out.print(method.getFullPath());
						out.print("</td>");
						out.print("</tr>");
					}
					out.print("</table>");
				} else {
					out.print("no data found");
				}
				out.print("</td>");
				out.print("</tr>");
			}
			out.print("</table>");
		} else {
			out.print("no data found");
		}
		out.flush();
	}

}
