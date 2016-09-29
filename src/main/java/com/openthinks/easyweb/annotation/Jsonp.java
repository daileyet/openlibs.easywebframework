package com.openthinks.easyweb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represent the response as JSONP data format
 * @author dailey.yet@outlook.com
 * @since 2016-5-6
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Jsonp {
	/**
	 * 
	 * @return String callback function in client side parameter name
	 */
	String value() default "callback";
}
