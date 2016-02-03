package com.openthinks.easyweb.annotation.process.objects;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.openthinks.easyweb.annotation.ResponseReturn;

/**
 * wrapper {@link WebMethod} response
 * 
 * @author minjdai
 * 
 */
public class WebMethodResponse {

	private final Class<?> returnType;
	private final Annotation[] returnAnnotations;

	private WebMethodResponse(Class<?> returnType, Annotation... annotations) {
		this.returnType = returnType;
		this.returnAnnotations = annotations;
	}

	private WebMethodResponse(final Method method) {
		this.returnType = method.getReturnType();
		this.returnAnnotations = method.getAnnotations();
	}

	public static WebMethodResponse build(Method method) {
		WebMethodResponse response = new WebMethodResponse(method);
		return response;
	}

	public static WebMethodResponse build(Class<?> returnType, Annotation... annotations) {
		WebMethodResponse response = new WebMethodResponse(returnType, annotations);
		return response;
	}

	public Class<?> getReturnType() {
		return returnType;
	}

	public Annotation[] getReturnAnnotations() {
		return returnAnnotations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((returnType == null) ? 0 : returnType.hashCode());
		return result;
	}

	/**
	 * the annotations on this method include the special type annotation {@link ResponseReturn} or not
	 * @return boolean
	 */
	public boolean isDirectResponse() {
		if (returnAnnotations != null) {
			for (Annotation anno : returnAnnotations) {
				if (anno instanceof ResponseReturn) {
					return true;
				}
			}
		}
		return false;
	}

	public ResponseReturn getDirectResponseAnnotation() {
		if (returnAnnotations != null) {
			for (Annotation anno : returnAnnotations) {
				if (anno instanceof ResponseReturn) {
					return (ResponseReturn) anno;
				}
			}
		}
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WebMethodResponse other = (WebMethodResponse) obj;
		if (returnType == null) {
			if (other.returnType != null)
				return false;
		} else if (!returnType.equals(other.returnType))
			return false;
		// if (returnAnnotations == null) {
		// if (other.returnAnnotations != null)
		// return false;
		// } else if (isDirectResponse() != other.isDirectResponse())
		// return false;
		return true;
	}

}
