package openthinks.easyweb.context.handler;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import openthinks.easyweb.WebUtils;
import openthinks.easyweb.annotation.ResponseReturn;
import openthinks.easyweb.annotation.process.objects.WebMethod;
import openthinks.easyweb.annotation.process.objects.WebMethodResponse;
import openthinks.libs.utilities.CommonUtilities;
import openthinks.libs.utilities.ProcessLogger;

/**
 * Default handler, handle {@link WebMethod} return String type value
 * 
 * @author minjdai
 * 
 */
public class StringMappingWebHandler implements WebHandler {

	@Override
	public void handle(HttpServletRequest req, HttpServletResponse resp) {
		WebMethod webMethod = WebUtils.getWebMethod(req);
		try {
			String responseValue = (String) webMethod.invoke(req, resp);
			// TODO make responseValue more flexible
			// add '/' before responseValue
			WebMethodResponse methodResponse = webMethod.getMethodResponse();
			if (methodResponse.isDirectResponse()) {
				ResponseReturn responseReturn = methodResponse.getDirectResponseAnnotation();
				resp.setCharacterEncoding(responseReturn.charset());
				resp.setContentType(responseReturn.contentType() + "; charset=" + responseReturn.charset());
				PrintWriter writer = resp.getWriter();
				writer.print(responseValue);
				writer.flush();
				return;
			} else {
				responseValue = WebUtils.contactPath("", responseValue);
				req.getRequestDispatcher(responseValue).forward(req, resp);
			}
		} catch (Exception e) {
			ProcessLogger.error(CommonUtilities.getCurrentInvokerMethod(), e.getMessage());
		}
	}
}