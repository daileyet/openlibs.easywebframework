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
* @Title: FilterPathMatcher.java 
* @Package com.openthinks.easyweb.annotation.process.objects 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 26, 2016
* @version V1.0   
*/
package com.openthinks.easyweb.annotation.process.filter.path;

import java.util.Optional;
import java.util.Set;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.context.RequestSuffix;
import com.openthinks.easyweb.context.WebConfigure;
import com.openthinks.easyweb.context.WebContexts;

/**
 * date: 2016/8/26
 * @author dailey.yet@outlook.com
 */
public final class FilterPathMatcher {
	public static final FilterPathMatchStrategy REGEXR = new RegExrFilterPathMatchStrategy();
	public static final FilterPathMatchStrategy SIMPLE = new SimpleFilterPathMatchStrategy();
	public static final FilterPathMatchStrategy MIXED = new MixedFilterPathMatchStrategy();
	private FilterPathMatchStrategy strategy = null;

	public static FilterPathMatcher configDefaultStrategy() {
		FilterPathMatcher filterPathMatcher = new FilterPathMatcher();
		filterPathMatcher.strategy = MIXED;
		return filterPathMatcher;
	}

	public static FilterPathMatcher configStrategy(final FilterPathMatchStrategy strategy) {
		FilterPathMatcher filterPathMatcher = new FilterPathMatcher();
		filterPathMatcher.strategy = strategy;
		return filterPathMatcher;
	}

	/**
	 * 
	 * simple {@link FilterPathMatchStrategy} implementation;<BR>
	 * <li>firstly sort parameter filterPaths by {@link WebUtils#comparePathByLongest(String, String)}
	 * <li>process {@link Predicate&lt;String&gt;} and do special when {@link RequestSuffix} not empty in {@link WebConfigure}
	 */
	static class SimpleFilterPathMatchStrategy implements FilterPathMatchStrategy {

		@Override
		public Optional<String> findMatch(String originalPath, Set<String> filterPaths) {
			final String outerPath = originalPath;
			final boolean hasNoSuffix = WebContexts.get().getWebConfigure().getRequestSuffix().isEmpty();
			Optional<String> closetPath = filterPaths.stream().sorted(WebUtils::comparePathByLongest)
					.filter((filterPath) -> {
						String requestPath = outerPath, filterTempPath = filterPath;
						if (!hasNoSuffix && !outerPath.endsWith("/")) {
							requestPath = outerPath.concat(".");
						}
						if (!hasNoSuffix && !filterPath.endsWith("/")) {
							filterTempPath = filterPath.concat(".");
						}
						return requestPath.contains(filterTempPath);
					}).findFirst();
			return closetPath;

		}

	}

	/**
	 * @see FilterPathMatchStrategy#findMatch(String, Set)
	 * @param originalPath String
	 * @param filterPaths set of filter paths
	 * @return Optional
	 */
	public Optional<String> findMatch(String originalPath, Set<String> filterPaths) {
		if (this.strategy == null) {
			return Optional.empty();
		}
		return this.strategy.findMatch(originalPath, filterPaths);
	}

}
