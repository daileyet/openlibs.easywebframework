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
* @Title: Scanners.java 
* @Package com.openthinks.easyweb.annotation.process.core.scaner 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Sep 29, 2016
* @version V1.0   
*/
package com.openthinks.easyweb.annotation.process.core.scaner;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.openthinks.easyweb.annotation.configure.ScanWay.ScanWayEnum;
import com.openthinks.libs.utilities.InstanceUtilities;
import com.openthinks.libs.utilities.logger.ProcessLogger;

/**
 * 
 * {@link PackageScanner} factory
 * @author dailey.yet@outlook.com
 *
 */
public class Scanners {
	//	DefaultPackageScanner
	private static final Map<ScanWayEnum, String> REGISTERMAP = new ConcurrentHashMap<>();
	private static final PackageScanner DEFAULT = new DefaultPackageScanner();
	static {
		REGISTERMAP.put(ScanWayEnum.FILE_PATH,
				"com.openthinks.easyweb.annotation.process.core.scaner.DefaultPackageScanner");
		REGISTERMAP.put(ScanWayEnum.ADVANCE,
				"com.openthinks.easyweb.annotation.process.core.scaner.AdvancePackageScanner");
	}

	@SuppressWarnings("unchecked")
	public static PackageScanner get(ScanWayEnum scanWay) {
		try {
			String className = REGISTERMAP.get(scanWay);
			Class<PackageScanner> clazz = (Class<PackageScanner>) Class.forName(className);
			return InstanceUtilities.create(clazz, null);
		} catch (Exception e) {
			ProcessLogger.warn(e);
		}
		return DEFAULT;
	}

}
