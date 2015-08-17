/**
 * 
 */
package openthinks.easyweb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represent a mapping between URL relative path and the method in controller  
 * @author minjdai
 * @date 2013-6-6
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {
	/**
	 * mapping URL path
	 * @return String
	 */
	public String value() default "";
}
