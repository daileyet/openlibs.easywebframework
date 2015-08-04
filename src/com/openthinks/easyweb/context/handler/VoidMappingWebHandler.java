/**
 * 
 */
package com.openthinks.easyweb.context.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.process.objects.WebMethod;

/**
 * @author minjdai
 * 
 */
public class VoidMappingWebHandler implements WebHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.openthinks.easyweb.context.handler.WebHandler#handle(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void handle(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		WebMethod webMethod = WebUtils.getWebMethod(req);
		try {
			webMethod.invoke(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
