package com.openthinks.easyweb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represent the response trait for the HTTP request
 * date: 2013-9-26
 * @author minjdai
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseReturn {

	/**
	 * the HTTP response charset
	 * @return charset
	 */
	String charset() default "UTF-8";

	/**
	 * the HTTP response content type
	 * @return {@link ResponseReturnType}
	 */
	ResponseReturnType contentType() default ResponseReturnType.TEXT_PLAN;

	/**
	 * response return type enum
	 *
	 */
	public enum ResponseReturnType {
		TEXT_PLAN("text/plain"), TEXT_HTML("text/html"), TEXT_XML("text/xml"), TEXT_JSON("text/json"), TEXT_JAVASCRIPT(
				"text/javascript");
		private String value;

		private ResponseReturnType(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}
	}
}
