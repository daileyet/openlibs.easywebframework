package com.openthinks.easyweb.context.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Null handler, handle the request path not in mapping book
 * 
 * @author minjdai
 * 
 */
public class NullWebHandler implements WebHandler {

	@Override
	public void handle(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// TODO make it can use error page
		resp.setContentType("text/plain");
		PrintWriter writer = resp.getWriter();
		writer.println("Bad request:" + req.getRequestURI());
		writer.flush();
	}
}