package com.openthinks.easyweb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author minjdai
 * @since 2013-9-26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseReturn {

	String charset() default "UTF-8";

	//TODO make a enumn type
	String contentType() default "text/plain";
}
