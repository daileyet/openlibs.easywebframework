package openthinks.easyweb.annotation.configure;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import openthinks.easyweb.annotation.Controller;

/**
 * tell the easyweb which package should take it as the {@link Controller}
 * @author dailey.yet@outlook.com
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ScanPackages {

	/**
	 * the array for need scanned package name, the easyweb framework will search the {@link Controller} and initial them at these packages
	 * @return String[]
	 */
	String[] value();
}
