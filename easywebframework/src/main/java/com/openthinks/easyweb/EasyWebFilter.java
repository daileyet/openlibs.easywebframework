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
* @Title: EasyWebFilter.java 
* @Package com.openthinks.easyweb 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 25, 2016
* @version V1.0   
*/
package com.openthinks.easyweb;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.openthinks.easyweb.context.EasyWebFilterContext;
import com.openthinks.easyweb.context.EasyWebFilterContexts;
import com.openthinks.easyweb.context.handler.FilterHandler;
import com.openthinks.easyweb.context.handler.Handlers;

/**
 * Main filter for easywebframework to main servlet
 * @author dailey.yet@outlook.com
 * @date	2016/8/25
 */
public class EasyWebFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		EasyWebFilterContext webFilterCtx = EasyWebFilterContexts.get(this.getClass());
		webFilterCtx.fireInit(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		FilterHandler handler = Handlers.getFilterHandler(request, request.getServletContext());
		handler.handle(request, response, chain);
	}

	@Override
	public void destroy() {
		EasyWebFilterContext webFilterCtx = EasyWebFilterContexts.get(this.getClass());
		webFilterCtx.fireDestroy();
	}

}
