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
* @Title: AutoComponent.java 
* @Package com.openthinks.easyweb.annotation 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Jul 12, 2016
* @version V1.0   
*/
package com.openthinks.easyweb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represent a autowire field, and autowire by {@code KeyType}, which support bean name and bean class;
 * date: 2016/7/12
 * @author dailey.yet@outlook.com
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoComponent {
	/**
	 * identity how to generate the injection instance object
	 * @return KeyType
	 */
	KeyType value() default KeyType.CLASS;

	/**
	 * get bean name when {@link KeyType} is {@link KeyType#BEAN_NAME}
	 * @return bean name
	 */
	String beanName() default "";

	/**
	 * get instance class type when {@link KeyType} is {@link KeyType#CLASS}
	 * @return instance class type
	 */
	Class<?> implementClass() default Void.class;

	public enum KeyType {
		BEAN_NAME, CLASS;
	}

}
