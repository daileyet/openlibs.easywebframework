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
* @Title: FilterFileFilterVistor.java 
* @Package com.openthinks.easyweb.annotation.process.core 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 25, 2016
* @version V1.0   
*/
package com.openthinks.easyweb.annotation.process.filter.file;

import java.io.File;
import java.util.List;

import com.openthinks.easyweb.WebStatic;
import com.openthinks.easyweb.annotation.Filter;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class FilterFileFilterVistor extends FileFilterVisitor {

	public FilterFileFilterVistor(List<Class<?>> filterClasss) {
		super(filterClasss);
	}

	@Override
	public boolean acceptClassType(Class<?> clazz) {
		Filter filterAnnotation = clazz.getAnnotation(Filter.class);
		return filterAnnotation != null;
	}

	@Override
	public boolean acceptClassName(File file) {
		if (file == null)
			return false;
		return file.getName().toUpperCase().endsWith(WebStatic.FILTER_FILE_SUFFIX.toUpperCase());
	}

}
