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
* @Title: Versions.java 
* @author dailey.yet@outlook.com  
* @date Aug 20, 2015
* @version V1.1   
*/
package com.openthinks.easyweb;

import javax.servlet.http.HttpServletRequest;

import com.openthinks.easyweb.annotation.AutoComponent;
import com.openthinks.easyweb.annotation.Controller;
import com.openthinks.easyweb.annotation.Filter;
import com.openthinks.easyweb.annotation.Jsonp;
import com.openthinks.easyweb.annotation.process.filter.path.FilterPathMatchStrategy;
import com.openthinks.easyweb.annotation.process.filter.path.MixedFilterPathMatchStrategy;
import com.openthinks.easyweb.annotation.process.filter.path.RegExrFilterPathMatchStrategy;
import com.openthinks.easyweb.annotation.process.objects.WebContainer;
import com.openthinks.easyweb.annotation.process.objects.WebController;
import com.openthinks.easyweb.annotation.process.objects.WebFilter;
import com.openthinks.easyweb.annotation.process.objects.WebMethod;
import com.openthinks.easyweb.context.EasyWebFilterContext;
import com.openthinks.easyweb.context.handler.FilterHandler;
import com.openthinks.easyweb.context.handler.StringMappingWebHandler;
import com.openthinks.easyweb.monitor.WebProcessMonitor;
import com.openthinks.libs.utilities.version.AppVersion;
import com.openthinks.libs.utilities.version.VersionCenter;

/**
 * openthinks.easyweb version class
 * @author dailey.yet@outlook.com
 *
 */
@AppVersion("1.2")
public class Versions extends VersionCenter {

	/**
	 * @change
	 * <ul>
	 * 		<li> Add feature {@link EasyWebFilter}
	 * 		<ol>
	 * 			<li> Add annotation {@link Filter} which like {@link Controller}
	 * 			<li> Add interface {@link FilterHandler}
	 * 			<li> Add {@link WebFilter} which like {@link WebController}
	 * 			<li> Add {@link EasyWebFilterContext} to reference {@link EasyWebFilter}
	 * 			<li> Add "enableRemote" option for {@link WebProcessMonitor} 
	 * 			<li> Add {@link FilterPathMatchStrategy} for {@link WebContainer#lookupFilter(String)}
	 * 			<li> Add {@link RegExrFilterPathMatchStrategy},{@link MixedFilterPathMatchStrategy}
	 * 		</ol>
	 * </ul>
	 */
	String v_1_2;
	/**
	 * @change
	 * <ul>
	 * 	<li>Add annotation {@link Jsonp} and support JSONP callback
	 * 	<li>Add annotation {@link AutoComponent} and support autowire in Controller component
	 *  <li>Enhance {@link WebProcessMonitor} and add bootstrap css
	 * 	<li>Add EL function definition file {@linkplain easyweb.tld} and support function in <BR>
	 * 	<ol>
	 * 		<li> {@link WebUtils#path(String)}
	 * 		<li> {@link WebUtils#pathS(String)}
	 * 	</ol>
	 * 	<li>Add redirect feature in {@link StringMappingWebHandler}, {@link WebUtils#redirect(String)} 
	 * </ul>
	 */
	String v_1_1;
	/**
	 * @base
	 * <dl>
	 * <dt>request mapping path</dt><dd>the path which mapping to {@link WebMethod}, also equals to {@link WebMethod#getFullPath()}</dd>
	 * <dt>full request mapping path</dt><dd>the path which mapping to {@link WebMethod} and it include web context root path, equals to {@link WebMethod#getFullPath()}</dd>
	 * <dt>short request mapping path</dt><dd>the path which mapping to {@link WebMethod} and it not include web context root path, equals to {@link WebMethod#getRelativePath2()}</dd>
	 * <dt>request URI</dt><dd>equals to {@link HttpServletRequest#getRequestURI()}</dd>
	 * </dl>
	 */
	String v_1_0;
}
