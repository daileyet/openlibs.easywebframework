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
* @Title: FilterPathMatchStrategy.java 
* @Package com.openthinks.easyweb.annotation.process.objects 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 26, 2016
* @version V1.0   
*/
package com.openthinks.easyweb.annotation.process.filter.path;

import java.util.Optional;
import java.util.Set;

import com.openthinks.easyweb.annotation.process.objects.WebFilter;
import com.openthinks.easyweb.context.RequestSuffix;

/**
 * Strategy for {@link WebFilter} path
 * @author dailey.yet@outlook.com
 * @date 2016/8/26
 */
public interface FilterPathMatchStrategy {

	/**
	 * find the match filter path
	 * @param originalPath String request URI without {@link RequestSuffix} etc. /web/test/index
	 * @param filterPaths Set<String> registered filter path
	 * @return Optional<String>
	 */
	Optional<String> findMatch(String originalPath, Set<String> filterPaths);

}
