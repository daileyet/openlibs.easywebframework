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
* @Title: AdvancePackageScanner.java 
* @Package com.openthinks.easyweb.annotation.process.core.scaner 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Sep 29, 2016
* @version V1.0   
*/
package com.openthinks.easyweb.annotation.process.core.scaner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.openthinks.easyweb.WebStatic;
import com.openthinks.easyweb.annotation.Controller;
import com.openthinks.easyweb.annotation.Filter;
import com.openthinks.easyweb.annotation.process.core.WebControllerProcesser;
import com.openthinks.easyweb.annotation.process.core.WebFilterProcesser;
import com.openthinks.easyweb.annotation.process.objects.WebContainer;
import com.openthinks.easyweb.annotation.process.objects.WebController;
import com.openthinks.easyweb.annotation.process.objects.WebFilter;
import com.openthinks.easyweb.context.WebContexts;
import com.openthinks.libs.utilities.logger.ProcessLogger;

/**
 * use {@link Reflections} to scan packager
 * @author dailey.yet@outlook.com
 *
 */
public class AdvancePackageScanner implements PackageScanner {

	@Override
	public void scan(WebContainer container, Set<String> packages) {
		Set<Class<?>> allControllerClazzs = new HashSet<>();
		Set<Class<?>> allFilterClazzs = new HashSet<>();
		for (String packageStr : packages) {
			Reflections reflections = new Reflections(packageStr);
			Set<Class<?>> controllerClazzs = reflections.getTypesAnnotatedWith(Controller.class);
			allControllerClazzs.addAll(controllerClazzs);
			Set<Class<?>> filterClazzs = reflections.getTypesAnnotatedWith(Filter.class);
			allFilterClazzs.addAll(filterClazzs);
		}
		processController(container, allControllerClazzs);
		processFilters(container, allFilterClazzs);
	}

	/**
	 * @param container
	 * @param allFilterClazzs
	 */
	protected void processFilters(WebContainer container, Set<Class<?>> allFilterClazzs) {
		List<Object> webFilters = getInstances(allFilterClazzs);
		WebFilterProcesser filterProcesser = new WebFilterProcesser();
		for (Object filter : webFilters) {
			filterProcesser.setPropertie(WebStatic.WEB_FILTER, filter);
			WebFilter child = filterProcesser.process();
			container.add(child);
		}
	}

	/**
	 * @param container
	 * @param allControllerClazzs
	 */
	protected void processController(WebContainer container, Set<Class<?>> allControllerClazzs) {
		List<Object> webControllers = getInstances(allControllerClazzs);
		WebControllerProcesser controllerProcesser = new WebControllerProcesser();
		for (Object controller : webControllers) {
			controllerProcesser.setPropertie(WebStatic.WEB_CONTROLLER, controller);
			WebController child = controllerProcesser.process();
			container.add(child);
		}
	}

	private List<Object> getInstances(Set<Class<?>> webInstancerClasses) {
		List<Object> allInstance = new ArrayList<>();
		for (Class<?> clazz : webInstancerClasses) {
			try {
				Object obj = WebContexts.get().lookup(clazz);
				allInstance.add(obj);
			} catch (Exception e) {
				ProcessLogger.warn(e);
			}
		}
		return allInstance;
	}
}
