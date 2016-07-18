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
* @Title: AutowireFailedException.java 
* @Package com.openthinks.easyweb.annotation.process.objects 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Jul 12, 2016
* @version V1.0   
*/
package com.openthinks.easyweb.exception;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class AutowireFailedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7537990619932538902L;

	/**
	 * 
	 */
	public AutowireFailedException() {
	}

	/**
	 * @param message
	 */
	public AutowireFailedException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public AutowireFailedException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AutowireFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public AutowireFailedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
