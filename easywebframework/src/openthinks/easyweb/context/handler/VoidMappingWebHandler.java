/**
 * 
 */
package openthinks.easyweb.context.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import openthinks.easyweb.WebUtils;
import openthinks.easyweb.annotation.process.objects.WebMethod;
import openthinks.libs.utilities.CommonUtilities;
import openthinks.libs.utilities.ProcessLogger;

/**
 * @author minjdai
 * 
 */
public class VoidMappingWebHandler implements WebHandler {

	@Override
	public void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		WebMethod webMethod = WebUtils.getWebMethod(req);
		try {
			webMethod.invoke(req, resp);
		} catch (Exception e) {
			ProcessLogger.error(CommonUtilities.getCurrentInvokerMethod(), e.getMessage());
		}

	}

}
