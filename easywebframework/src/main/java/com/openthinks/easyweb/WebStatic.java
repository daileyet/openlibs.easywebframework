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
* @Title: WebStatic.java 
* @Package com.openthinks.easyweb 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Jul 19, 2016
* @version V1.0   
*/
package com.openthinks.easyweb;

import java.io.File;

/**
 * @Description: All static const variables and property
 * @author dailey.yet@outlook.com
 */
public final class WebStatic {

	public static final String WEB_ROOT = "EasyWeb_Container";
	public static final String WEB_CONFIGURE = "EasyWeb_Configure";
	public static final String WEB_CONTROLLER = "EasyWeb_Controller";
	public static final String WEB_FILTER = "EasyWeb_Filter";
	public static final String WEB_METHOD = "EasyWeb_Method";
	/**
	 * Controller component conventional end name<BR>
	 * For example: IndexController
	 */
	public static final String CONTROLLER_FILE_SUFFIX = "Controller.class";
	public static final String FILTER_FILE_SUFFIX = "Filter.class";
	public static final String PATH_SPLITER = File.separator;
	/**
	 * Web application class directory after deploy; <BR>
	 * also can the attribute name of main servlet or init parameter of this main servlet
	 * 	<pre>
	 *	&lt;context-param>
	 *	&lt;param-name>easyweb-class-dir&lt;/param-name>
	 *	&lt;param-value>R:\to\path\target\classes&lt;/param-value>
	 *	&lt;/context-param>
	 *  </pre>
	 */
	public static final String WEB_CLASS_DIR = "easyweb-class-dir";
	public static final String WEB_REDIRECT_PATH_REFIX = "@REDIRECT::";
	public static final String WEB_FILTER_PASS_PATH_REFIX = "@FILTER::PASS::";
	public static final String WEB_FILTER_STOP_PATH_REFIX = "@FILTER::STOP::";;

}
