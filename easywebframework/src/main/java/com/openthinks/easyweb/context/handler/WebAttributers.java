package com.openthinks.easyweb.context.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Wrapped of HTTP request and HTTPP response
 * @author minjdai
 * 
 */
public class WebAttributers {

	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private final Map<String, Object> error;
	public final static String ERROR_NAME = "webError";

	public enum WebScope {
		PAGE, REQUEST, SESSION, APPLICATION
	}

	public WebAttributers(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
		this.error = new HashMap<>();
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public HttpSession getSession() {
		return request.getSession();
	}

	public ServletContext getApplication() {
		return request.getSession().getServletContext();
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> T get(String parameterName) {
		return (T) request.getParameter(parameterName);
	}

	public void storeRequest(String attributeName, Object attributeValue) {
		addAttribute(attributeName, attributeValue, WebScope.REQUEST);
	}

	public void storeSession(String attributeName, Object attributeValue) {
		addAttribute(attributeName, attributeValue, WebScope.SESSION);
	}

	public <T extends Object> T getSession(String attributeName) {
		return getAttribute(attributeName, WebScope.SESSION);
	}

	public void storeApplication(String attributeName, Object attributeValue) {
		addAttribute(attributeName, attributeValue, WebScope.APPLICATION);
	}

	public <T extends Object> T getApplication(String attributeName) {
		return getAttribute(attributeName, WebScope.APPLICATION);
	}

	public void addAttribute(String attributeName, Object attributeValue, WebScope scope) {

		switch (scope) {
		case PAGE:
		case REQUEST:
			request.setAttribute(attributeName, attributeValue);
			break;
		case SESSION:
			request.getSession().setAttribute(attributeName, attributeValue);
		case APPLICATION:
			request.getSession().getServletContext().setAttribute(attributeName, attributeValue);
			break;
		}
	}

	public void addError(String attributeName, Object attributeValue, WebScope scope) {
		error.put(attributeName, attributeValue);
		switch (scope) {
		case PAGE:
		case REQUEST:
			request.setAttribute(ERROR_NAME, error);
			break;
		case SESSION:
			request.getSession().setAttribute(ERROR_NAME, error);
		case APPLICATION:
			request.getSession().getServletContext().setAttribute(ERROR_NAME, error);
			break;
		}
	}

	public void removeAttribute(String attributeName, WebScope scope) {
		switch (scope) {
		case PAGE:
		case REQUEST:
			request.removeAttribute(attributeName);
			break;
		case SESSION:
			request.getSession().removeAttribute(attributeName);
		case APPLICATION:
			request.getSession().getServletContext().removeAttribute(attributeName);
			break;
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends Object> T getAttribute(String attributeName, WebScope scope) {
		switch (scope) {
		case PAGE:
		case REQUEST:

			return (T) request.getAttribute(attributeName);
		case SESSION:
			return (T) request.getSession().getAttribute(attributeName);
		case APPLICATION:
			return (T) request.getSession().getServletContext().getAttribute(attributeName);
		default:
			return null;
		}
	}

}
