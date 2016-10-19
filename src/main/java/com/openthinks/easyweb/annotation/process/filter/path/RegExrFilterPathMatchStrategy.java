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
* @Title: RegExrFilterPathMatchStrategy.java 
* @Package com.openthinks.easyweb.annotation.process.filter.path 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Sep 21, 2016
* @version V1.0   
*/
package com.openthinks.easyweb.annotation.process.filter.path;

import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.context.RequestSuffix;
import com.openthinks.libs.utilities.logger.ProcessLogger;

/**
 * Regular expressions strategy, this will treat parameter filterPaths in {@link #findMatch(String, Set)} as regular expressions
 * @author dailey.yet@outlook.com
 *
 */
public class RegExrFilterPathMatchStrategy implements FilterPathMatchStrategy {

	/**
	 * find the match filter path
	 * @param originalPath  String request URI without {@link RequestSuffix} etc. /web/test/index
	 * @param filterPaths Set container register filter path, and will treat as regular expressions
	 */
	@Override
	public Optional<String> findMatch(String originalPath, Set<String> filterPaths) {
		final String outerPath = originalPath;
		Set<String> closetPaths = filterPaths.stream().filter((regxPath) -> {
			if (outerPath.equals(regxPath)) {
				return true;
			}
			try {
				return Pattern.matches(regxPath, outerPath);
			} catch (Exception e) {
				ProcessLogger.warn(e);
				return false;
			}
		}).collect(Collectors.toSet());
		Optional<String> closetPath = closetPaths.stream().sorted(WebUtils::comparePathByLongest).findFirst();
		return closetPath;
	}
}
