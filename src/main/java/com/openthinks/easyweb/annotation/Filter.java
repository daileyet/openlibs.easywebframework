/**   
 *  Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
* @Title: Filter.java 
* @Package com.openthinks.easyweb.annotation 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 25, 2016
* @version V1.0   
*/
package com.openthinks.easyweb.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Represent a Filter class, those marked this annotation class need to make their name end with <code>Filter</code>
 * date: 2016/8/25
 * @author dailey.yet@outlook.com
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface Filter {
	/**
	 * filter request path mapping<BR>
	 * default value will parse to "/"
	 * @return filter relative request path 
	 */
	public String value() default "";

	/**
	 * filter name<BR>
	 * default value will named the filter name as class name which lower case the first character 
	 * @return filter name
	 */
	public String name() default "";
}
