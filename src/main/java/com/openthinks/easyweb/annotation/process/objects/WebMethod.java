/**
 * 
 */
package com.openthinks.easyweb.annotation.process.objects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.Mapping;
import com.openthinks.easyweb.context.handler.WebAttributers;

/**
 * The web method unit
 * date: 2013-6-6
 * @author minjdai
 */
public class WebMethod implements WebUnit {

	private final Method method;
	private WebInstancer parent;

	public WebMethod(Method method) {
		super();
		this.method = method;
		mapping = this.method.getAnnotation(Mapping.class);
	}

	private final Mapping mapping;

	@Override
	public String getRelativePath() {
		return mapping.value();
	}

	/**
	 * get relative path include parent {@link WebInstancer#getRelativePath()}
	 * @return String
	 */
	public String getRelativePath2() {
		return WebUtils.contactPath(parent().getRelativePath(), this.getRelativePath());
	}

	@SuppressWarnings("unchecked")
	@Override
	public WebInstancer parent() {
		return this.parent;
	}

	@Override
	public Set<WebUnit> children() {
		return Collections.emptySet();
	}

	void parent(WebInstancer parent) {
		this.parent = parent;
	}

	@Override
	public String getName() {
		return method.getName();
	}

	@Override
	public String getFullPath() {
		return WebUtils.contactPath(parent().getFullPath(), this.getRelativePath());
	}

	@Override
	public boolean isValid() {
		return mapping != null;
	}

	/**
	 * get method response reference
	 * @return WebMethodResponse
	 */
	public WebMethodResponse getMethodResponse() {
		return WebMethodResponse.build(method);
	}

	/**
	 * invoke this web method
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 * @param filterChain {@linkplain FilterChain}
	 * @return execution result return  
	 * @throws IllegalArgumentException if the method is an instance method and the specified object argument is not an instance of the class or interface declaring the underlying method (or of a subclass or implementor thereof); if the number of actual and formal parameters differ; if an unwrapping conversion for primitive arguments fails; or if, after possible unwrapping, a parameter value cannot be converted to the corresponding formal parameter type by a method invocation conversion.
	 * @throws IllegalAccessException  if this Method object is enforcing Java language access control and the underlying method is inaccessible.
	 * @throws InvocationTargetException  if the underlying method throws an exception.
	 */
	public Object invoke(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class<?>[] parametersType = method.getParameterTypes();
		Object[] parameterValues = new Object[parametersType.length];
		int index = 0;

		for (Class<?> type : parametersType) {
			if (type == WebAttributers.class) {
				parameterValues[index] = new WebAttributers(req, resp);
			} else if (type == HttpServletRequest.class) {
				parameterValues[index] = req;
			} else if (type == HttpServletResponse.class) {
				parameterValues[index] = resp;
			} else if (type == FilterChain.class) {
				parameterValues[index] = filterChain;
			} else {
				parameterValues[index] = null;
			}
			index++;
		}
		return method.invoke(parent.getInstance(), parameterValues);

	}
}
