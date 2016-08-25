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
* @Title: AuthorFilter.java 
* @Package com.openthinks.easyweb.test.filter 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 25, 2016
* @version V1.0   
*/
package com.openthinks.easyweb.test.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.Filter;
import com.openthinks.easyweb.annotation.Mapping;
import com.openthinks.easyweb.context.handler.WebAttributers;

/**
 * @author dailey.yet@outlook.com
 *
 */
@Filter
public class AuthorFilter {

//	@Mapping("/index")
//	public String author(WebAttributers was) {
//		Object sessionInfo = was.getSession("LOGIN_NAME");
//		if (sessionInfo == null) {
//			return WebUtils.redirect("/login");
//		}
//		return WebUtils.filterPass();
//	}
//	
//	
//	@Mapping("/index2")
//	public String author2(WebAttributers was, FilterChain filterChain) {
//		Object sessionInfo = was.getSession("LOGIN_NAME");
//		if (sessionInfo == null) {
//			return WebUtils.redirect("/login");
//		}
//		return WebUtils.filterPass();
//	}
	
	@Mapping("/")
	public String authorAll(WebAttributers was) {
		Object sessionInfo = was.getSession("LOGIN_NAME");
		if (sessionInfo == null) {
			return WebUtils.redirect("/login");
		}
		return WebUtils.filterPass();
	}
	
	@Mapping("/login")
	public String passToLogin(){
		return WebUtils.filterPass();
	}
	
	@Mapping("/login/action")
	public void passDoLogin(HttpServletRequest req, HttpServletResponse reps,FilterChain filterChain) throws IOException, ServletException{
		filterChain.doFilter(req, reps);
	}
}
