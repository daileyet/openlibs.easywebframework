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
* @Title: WebFilter.java 
* @Package com.openthinks.easyweb.annotation.process.objects 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 25, 2016
* @version V1.0   
*/
package com.openthinks.easyweb.annotation.process.objects;

import com.openthinks.easyweb.annotation.Filter;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class WebFilter extends WebInstancer {
	private final Filter fiter;

	public WebFilter(Object instance) {
		super(instance);
		this.fiter = this.type.getAnnotation(Filter.class);
		this.name = calculateName();
	}

	@Override
	public String getRelativePath() {
		return fiter.value();
	}

	@Override
	public boolean isValid() {
		return fiter != null;
	}

	@Override
	protected String calculateName() {
		if (fiter == null)
			return this.type.getName();
		String fiterName = fiter.name();
		if (fiterName == null || "".equals(fiterName)) {
			fiterName = this.type.getSimpleName();
			fiterName = fiterName.substring(0, 1).toLowerCase().concat(fiterName.substring(1));
		}
		return fiterName;
	}

}
