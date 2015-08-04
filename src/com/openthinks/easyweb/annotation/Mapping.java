/**
 * 
 */
package com.openthinks.easyweb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author minjdai
 * @date 2013-6-6
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {
	public String value() default "";
}
