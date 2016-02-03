/**
 * 
 */
package com.openthinks.easyweb.annotation.process.objects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.Mapping;
import com.openthinks.easyweb.context.handler.WebAttributers;

/**
 * The web method unit
 * @author minjdai
 * @date 2013-6-6
 */
public class WebMethod implements WebUnit {

	private final Method method;
	private WebController parent;

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

	@SuppressWarnings("unchecked")
	@Override
	public WebController parent() {
		return this.parent;
	}

	@Override
	public Set<WebUnit> children() {
		return Collections.emptySet();
	}

	void parent(WebController parent) {
		this.parent = parent;
	}

	@Override
	public String getName() {
		return getRelativePath();
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
	 * @return Object
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Object invoke(HttpServletRequest req, HttpServletResponse resp) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
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
			} else {
				parameterValues[index] = null;
			}
			index++;
		}
		return method.invoke(parent.getInstance(), parameterValues);

	}
}
