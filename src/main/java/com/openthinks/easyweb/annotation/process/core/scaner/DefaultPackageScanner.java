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
* @Title: DefaultPackageScanner.java 
* @Package com.openthinks.easyweb.annotation.process.core.scaner 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Sep 29, 2016
* @version V1.0   
*/
package com.openthinks.easyweb.annotation.process.core.scaner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.openthinks.easyweb.WebStatic;
import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.process.core.WebControllerProcesser;
import com.openthinks.easyweb.annotation.process.core.WebFilterProcesser;
import com.openthinks.easyweb.annotation.process.filter.file.ControllerFileFilterVistor;
import com.openthinks.easyweb.annotation.process.filter.file.FilterFileFilterVistor;
import com.openthinks.easyweb.annotation.process.objects.WebContainer;
import com.openthinks.easyweb.annotation.process.objects.WebController;
import com.openthinks.easyweb.annotation.process.objects.WebFilter;
import com.openthinks.easyweb.context.WebContexts;
import com.openthinks.libs.utilities.logger.ProcessLogger;

/**
 * file folder support way: deploy as webapp by folder or war
 * @author dailey.yet@outlook.com
 *
 */
public class DefaultPackageScanner implements PackageScanner {

	/* (non-Javadoc)
	 * @see com.openthinks.easyweb.annotation.process.core.scaner.PackageScanner#scan(com.openthinks.easyweb.annotation.process.objects.WebContainer, java.util.Set)
	 */
	@Override
	public void scan(WebContainer container, Set<String> packages) {
		for (String pack : packages) {
			String path = WebUtils.getPackPath(pack);
			scanPackageFilter(container, path);
			scanPackageController(container, path);
		}
	}

	/**
	 * @param container
	 * @param filterProcesser
	 * @param path
	 */
	protected void scanPackageFilter(WebContainer container, String path) {
		WebFilterProcesser filterProcesser = new WebFilterProcesser();
		List<Class<?>> filterClasses = filterFilterClass(path);
		List<Object> filters = getFilterInstances(filterClasses);
		for (Object filter : filters) {
			filterProcesser.setPropertie(WebStatic.WEB_FILTER, filter);
			WebFilter child = filterProcesser.process();
			container.add(child);
		}
	}

	/**
	 * @param container
	 * @param controllerProcesser
	 * @param path
	 */
	protected void scanPackageController(WebContainer container, String path) {
		WebControllerProcesser controllerProcesser = new WebControllerProcesser();
		List<Class<?>> controllerClasses = filterControllerClass(path);
		List<Object> controllers = getControllerInstances(controllerClasses);
		for (Object controller : controllers) {
			controllerProcesser.setPropertie(WebStatic.WEB_CONTROLLER, controller);
			WebController child = controllerProcesser.process();
			container.add(child);
		}
	}

	private List<Object> getFilterInstances(List<Class<?>> filterClasses) {
		List<Object> filterInstance = new ArrayList<>();
		for (Class<?> clazz : filterClasses) {
			try {
				//filterInstance.add(clazz.newInstance());
				Object obj = WebContexts.get().lookup(clazz);
				filterInstance.add(obj);
			} catch (Exception e) {
				ProcessLogger.warn(e);
			}
		}
		return filterInstance;
	}

	private List<Class<?>> filterFilterClass(String packPath) {
		File dir = new File(packPath);
		List<Class<?>> filterClasss = new ArrayList<>();
		dir.listFiles(new FilterFileFilterVistor(filterClasss));
		return filterClasss;
	}

	private List<Object> getControllerInstances(List<Class<?>> controllerClasses) {
		List<Object> controllerInstance = new ArrayList<>();
		for (Class<?> clazz : controllerClasses) {
			try {
				//controllerInstance.add(clazz.newInstance());
				Object obj = WebContexts.get().lookup(clazz);
				controllerInstance.add(obj);
			} catch (Exception e) {
				ProcessLogger.warn(e);
			}
		}
		return controllerInstance;
	}

	private List<Class<?>> filterControllerClass(String packPath) {
		File dir = new File(packPath);
		List<Class<?>> controllerClasss = new ArrayList<>();
		dir.listFiles(new ControllerFileFilterVistor(controllerClasss));
		return controllerClasss;
	}
}
