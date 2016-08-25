package com.openthinks.easyweb.context.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.openthinks.easyweb.WebStatic;
import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.Jsonp;
import com.openthinks.easyweb.annotation.ResponseReturn;
import com.openthinks.easyweb.annotation.process.objects.WebMethod;
import com.openthinks.easyweb.annotation.process.objects.WebMethodResponse;
import com.openthinks.libs.utilities.CommonUtilities;
import com.openthinks.libs.utilities.logger.ProcessLogger;

/**
 * Default handler, handle {@link WebMethod} return String type value
 * 
 * @author minjdai
 * 
 */
public class StringMappingWebHandler implements WebHandler {

	@Override
	public void handle(HttpServletRequest req, HttpServletResponse resp) {
		WebMethod webMethod = WebUtils.getControllerWebMethod(req);
		process(req, resp, null, webMethod);
	}

	@Override
	public void handle(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException {
		WebMethod webMethod = WebUtils.getFilterWebMethod(request);
		process(request, response, filterChain, webMethod);
	}

	/**
	 * @param req
	 * @param resp
	 * @param webMethod
	 */
	protected void process(ServletRequest req, ServletResponse resp, FilterChain filterChain, WebMethod webMethod) {
		try {
			String responseValue = (String) webMethod.invoke((HttpServletRequest) req, (HttpServletResponse) resp,
					filterChain);
			// TODO make responseValue more flexible
			// add '/' before responseValue
			WebMethodResponse methodResponse = webMethod.getMethodResponse();
			if (methodResponse.isDirectResponse()) {
				ResponseReturn responseReturn = methodResponse.getDirectResponseAnnotation();
				resp.setCharacterEncoding(responseReturn.charset());
				resp.setContentType(responseReturn.contentType() + "; charset=" + responseReturn.charset());
				PrintWriter writer = resp.getWriter();
				if (methodResponse.isJsonpResponse()) {
					Jsonp jsonp = methodResponse.getJsonpAnnotation();
					String callbackValue = req.getParameter(jsonp.value());
					if (callbackValue != null && !callbackValue.isEmpty()) {
						responseValue = callbackValue + "(" + responseValue + ");";
					}
				}
				writer.print(responseValue);
				writer.flush();
				return;
			} else {
				if (isRedirectPath(responseValue)) {// redirect flag
					((HttpServletResponse) resp).sendRedirect(getRedirectPath(responseValue));
				} else if (isFilterPassPath(responseValue)) {
					if (filterChain != null)
						filterChain.doFilter(req, resp);
				} else if (isFilterPassPath(responseValue)) {  
					//DO NOTHING
				}else {//forward flag
					responseValue = WebUtils.contactPath("", responseValue);
					req.getRequestDispatcher(responseValue).forward(req, resp);
				}
			}
		} catch (Exception e) {
			ProcessLogger.error(CommonUtilities.getCurrentInvokerMethod(), e);
		}
	}

	private boolean isFilterPassPath(String responseValue) {
		if (responseValue != null && responseValue.startsWith(WebStatic.WEB_FILTER_PASS_PATH_REFIX)) {
			return true;
		}
		return false;
	}

	private String getRedirectPath(String responseValue) {
		String newWebPath = responseValue.substring(WebStatic.WEB_REDIRECT_PATH_REFIX.length());
		return WebUtils.path(newWebPath);
	}

	private boolean isRedirectPath(String responseValue) {
		if (responseValue != null && responseValue.startsWith(WebStatic.WEB_REDIRECT_PATH_REFIX)) {
			return true;
		}
		return false;
	}
}