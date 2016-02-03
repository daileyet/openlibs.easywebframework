package com.openthinks.easyweb.annotation.configure;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The request url suffixes, likes: <dl>http://www.openthinks.com/api/add.do</dl>
 * ".do" is the request url suffixes, it will be used to configure easy web framework.
 * @author minjdai
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RequestSuffixs {
	String value();
}
