package openthinks.easyweb.context.handler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import openthinks.easyweb.WebUtils;
import openthinks.easyweb.annotation.process.objects.WebContainer;
import openthinks.easyweb.annotation.process.objects.WebMethod;
import openthinks.easyweb.annotation.process.objects.WebMethodResponse;
import openthinks.easyweb.context.WebContexts;

public class Handlers {

	public static WebHandler getHandler(HttpServletRequest req,
			ServletContext servletContext) {
		String path = req.getRequestURI();
		String mappingPath = WebUtils.convertToRequestMapingPath(path,
				WebContexts.get().getWebConfigure().getRequestSuffix());
		WebContainer container = WebContexts.get().getWebContainer();
		WebMethod webMethod = container.lookup(mappingPath);
		WebMethodResponse methodResponse = null;
		if (webMethod != null) {
			methodResponse = webMethod.getMethodResponse();
		}
		return WebHandlerFactory.createHandler(methodResponse);
	}

}
