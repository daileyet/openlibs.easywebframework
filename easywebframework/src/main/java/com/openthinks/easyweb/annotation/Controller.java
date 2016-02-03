package com.openthinks.easyweb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represent a controller class, those marked this annotation class need to make their name end with <code>Controller</code> 
 * @author dailey.yet@outlook.com
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {

	/**
	 * controller request path mapping<BR>
	 * default value will parse to "/"
	 * @return String 
	 */
	public String value() default "";

	/**
	 * controller name<BR>
	 * default value will named the controller name as class name which lower case the first character 
	 * @return String
	 */
	public String name() default "";
}
