package com.openthinks.easyweb.annotation.configure;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.openthinks.easyweb.context.Bootstrap;

/**
 * For configure item for bootstrap class
 * @author dailey.yet@outlook.com
 * @see Bootstrap
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BootstrapClass {

	/**
	 * the full name of bootstrap class
	 * @return String
	 */
	String value() default "openthinks.easyweb.context.NullBootstrap";
}
