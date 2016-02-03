package com.openthinks.easyweb.context.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.openthinks.easyweb.annotation.process.objects.WebMethod;

/**
 * handle for different {@link WebMethod}
 * 
 * @author minjdai
 * 
 */
public interface WebHandler {

	/**
	 * handle action for {@link WebMethod}
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 * @throws IOException
	 */
	void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException;

}
