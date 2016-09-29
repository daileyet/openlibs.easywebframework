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
* @Title: ScanWay.java 
* @Package com.openthinks.easyweb.annotation.configure 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Sep 29, 2016
* @version V1.0   
*/
package com.openthinks.easyweb.annotation.configure;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dailey.yet@outlook.com
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ScanWay {
	ScanWayEnum value() default ScanWayEnum.FILE_PATH;

	public enum ScanWayEnum {
		/**
		 * this way will base on file path;it is suit for web application, which deploy as folder or war
		 */
		FILE_PATH,
		/**
		 * this way will use third party library(reflections);it is support java application, which deploy as jar file
		 */
		ADVANCE;
	}
}
