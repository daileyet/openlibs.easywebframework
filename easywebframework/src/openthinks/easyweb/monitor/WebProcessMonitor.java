package openthinks.easyweb.monitor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import openthinks.easyweb.annotation.Controller;
import openthinks.easyweb.annotation.process.objects.WebContainer;
import openthinks.easyweb.annotation.process.objects.WebController;
import openthinks.easyweb.annotation.process.objects.WebMethod;
import openthinks.easyweb.context.WebContexts;

/**
 * Servlet implementation class WebProcessMonitor<BR>
 * Simple monitor for all the configured {@link Controller}
 */
public class WebProcessMonitor extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WebProcessMonitor() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
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
			for (WebController controller : container.children()) {
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
