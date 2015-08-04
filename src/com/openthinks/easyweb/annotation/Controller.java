package com.openthinks.easyweb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {

	/**
	 * @return controller request path mapping
	 */
	public String value() default "";

	public String name() default "";
}
