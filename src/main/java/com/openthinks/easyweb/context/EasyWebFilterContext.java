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
* @Title: EasyWebFilterContext.java 
* @Package com.openthinks.easyweb.context 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 26, 2016
* @version V1.0   
*/
package com.openthinks.easyweb.context;

import javax.servlet.FilterConfig;

import com.openthinks.easyweb.EasyWebFilter;
import com.openthinks.easyweb.EasyWebFilterListener;

/**
 * Context used in {@link EasyWebFilter}
 * date:	2016/8/26
 * 
 * @author dailey.yet@outlook.com
 */
public interface EasyWebFilterContext {

	/**
	 * invoke in {@link EasyWebFilter#init(FilterConfig)}
	 * @param filterConfig FilterConfig
	 */
	void fireInit(FilterConfig filterConfig);

	/**
	 * invoke in {@link EasyWebFilter#destroy()}
	 */
	void fireDestroy();

	/**
	 * get {@link FilterConfig} instance from current {@link EasyWebFilter}
	 * @return FilterConfig
	 */
	FilterConfig getFilterConfig();

	void deleteDestoryListeners();

	void deleteInitListeners();

	void deleteDestoryListener(EasyWebFilterListener filterListener);

	void deleteInitListener(EasyWebFilterListener filterListener);

	/**
	 * add listener for {@link EasyWebFilter#destroy()}, will be fire by {@link #fireDestroy}
	 * @param filterListener EasyWebFilterListener
	 */
	void addDestoryListener(EasyWebFilterListener filterListener);

	/**
	 * add listener for {@link EasyWebFilter#init(FilterConfig)}, will be fire by {@link #fireInit(FilterConfig)}
	 * @param filterListener EasyWebFilterListener
	 */
	void addInitListener(EasyWebFilterListener filterListener);

}
