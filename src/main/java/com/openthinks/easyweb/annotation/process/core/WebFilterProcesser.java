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
* @Title: WebFilterProcesser.java 
* @Package com.openthinks.easyweb.annotation.process.core 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 25, 2016
* @version V1.0   
*/
package com.openthinks.easyweb.annotation.process.core;

import java.lang.reflect.Method;

import com.openthinks.easyweb.WebStatic;
import com.openthinks.easyweb.annotation.Mapping;
import com.openthinks.easyweb.annotation.process.objects.WebFilter;
import com.openthinks.easyweb.annotation.process.objects.WebMethod;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class WebFilterProcesser extends AbstractProcesser<WebFilter> {

	@Override
	public WebFilter process() {
		Object instance = this.getPropertie(WebStatic.WEB_FILTER);
		WebFilter filter = new WebFilter(instance);
		if (!filter.isValid())
			return filter;
		WebMethodProcesser processer = new WebMethodProcesser();

		for (Method method : instance.getClass().getDeclaredMethods()) {
			if (method.getAnnotation(Mapping.class) == null)
				continue;
			processer.setPropertie(WebStatic.WEB_METHOD, method);
			WebMethod child = processer.process();
			filter.add(child);
		}
		return filter;
	}

}
