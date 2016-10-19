package com.openthinks.easyweb.context.handler;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.openthinks.easyweb.EasyWebDispatcher;
import com.openthinks.easyweb.EasyWebFilter;
import com.openthinks.easyweb.annotation.process.objects.WebMethod;

/**
 * handle for different {@link WebMethod}; used in {@link EasyWebDispatcher}
 * 
 * @author minjdai
 * 
 */
public interface WebHandler extends FilterHandler {

	/**
	 * handle action for {@link WebMethod} in {@link EasyWebDispatcher}
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws IOException occurs when io error
	 */
	void handle(HttpServletRequest request, HttpServletResponse response) throws IOException;

	/**
	 * handle action for {@link WebMethod} in {@link EasyWebFilter}
	 * @param request ServletRequest
	 * @param response ServletResponse
	 * @param chain FilterChain
	 * @throws IOException occurs when io error
	 * @throws ServletException occurs when servlet error
	 */
	@Override
	void handle(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException;
}
