/**
 * 
 */
package com.openthinks.easyweb.context.handler;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.process.objects.WebMethod;
import com.openthinks.libs.utilities.CommonUtilities;
import com.openthinks.libs.utilities.logger.ProcessLogger;

/**
 * @author minjdai
 * 
 */
public class VoidMappingWebHandler implements WebHandler {

	@Override
	public void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		WebMethod webMethod = WebUtils.getControllerWebMethod(req);
		process(req, resp, null, webMethod);
	}

	@Override
	public void handle(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException {
		WebMethod webMethod = WebUtils.getFilterWebMethod(request);
		process((HttpServletRequest) request, (HttpServletResponse) response, filterChain, webMethod);
	}

	/**
	 * @param req
	 * @param resp
	 * @param webMethod
	 */
	protected void process(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain,
			WebMethod webMethod) {
		try {
			webMethod.invoke(req, resp,filterChain);
		} catch (Exception e) {
			ProcessLogger.error(CommonUtilities.getCurrentInvokerMethod(), e);
		}
	}

}
