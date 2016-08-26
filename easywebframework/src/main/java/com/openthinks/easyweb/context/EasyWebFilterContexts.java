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
* @Title: EasyWebFilterContexts.java 
* @Package com.openthinks.easyweb.context 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 26, 2016
* @version V1.0   
*/
package com.openthinks.easyweb.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.FilterConfig;

import com.openthinks.easyweb.EasyWebFilter;
import com.openthinks.easyweb.EasyWebFilterListener;
import com.openthinks.libs.utilities.Checker;

/**
 * Container for {@link EasyWebFilterContext}
 * @author dailey.yet@outlook.com
 * @date	2016/8/26
 */
public final class EasyWebFilterContexts {
	private static final Map<Class<? extends EasyWebFilter>, EasyWebFilterContext> ctxBook = new ConcurrentHashMap<>();
	private static final Lock lock = new ReentrantLock();

	/**
	 * find {@link EasyWebFilterContext} instance by reference Class {@link EasyWebFilter}
	 * @param webFilterClazz Class<? extends EasyWebFilter>
	 * @return EasyWebFilterContext
	 */
	public static EasyWebFilterContext get(Class<? extends EasyWebFilter> webFilterClazz) {
		EasyWebFilterContext filterCtx = ctxBook.get(webFilterClazz);
		if (filterCtx == null) {
			lock.lock();
			try {
				if (filterCtx == null) {
					filterCtx = new EasyWebFilterContextImpl();
					ctxBook.put(webFilterClazz, filterCtx);
				}
			} finally {
				lock.unlock();
			}
		}
		return filterCtx;
	}

	/**
	 * get context for default filter {@link EasyWebFilter} 
	 * @return EasyWebFilterContext
	 */
	public static EasyWebFilterContext get() {
		return get(EasyWebFilter.class);
	}

	static class EasyWebFilterContextImpl implements EasyWebFilterContext {
		private FilterConfig filterConfig;
		private final List<EasyWebFilterListener> easyWebFilterInitListeners = Collections
				.synchronizedList(new ArrayList<>());
		private final List<EasyWebFilterListener> easyWebFilterDestoryListeners = Collections
				.synchronizedList(new ArrayList<>());

		@Override
		public void fireInit(FilterConfig filterConfig) {
			this.filterConfig = filterConfig;
			for (EasyWebFilterListener listener : easyWebFilterInitListeners)
				listener.on(this);
		}

		@Override
		public void fireDestroy() {
			for (EasyWebFilterListener listener : easyWebFilterDestoryListeners)
				listener.on(this);
		}

		@Override
		public FilterConfig getFilterConfig() {
			return filterConfig;
		}

		@Override
		public synchronized void addInitListener(EasyWebFilterListener filterListener) {
			Checker.require(filterListener).notNull();
			if (!easyWebFilterInitListeners.contains(filterListener)) {
				easyWebFilterInitListeners.add(filterListener);
			}
		}

		@Override
		public synchronized void addDestoryListener(EasyWebFilterListener filterListener) {
			Checker.require(filterListener).notNull();
			if (!easyWebFilterDestoryListeners.contains(filterListener)) {
				easyWebFilterDestoryListeners.add(filterListener);
			}
		}

		@Override
		public synchronized void deleteInitListener(EasyWebFilterListener filterListener) {
			easyWebFilterInitListeners.remove(filterListener);
		}

		@Override
		public synchronized void deleteDestoryListener(EasyWebFilterListener filterListener) {
			easyWebFilterDestoryListeners.remove(filterListener);
		}

		@Override
		public synchronized void deleteInitListeners() {
			easyWebFilterInitListeners.clear();
		}

		@Override
		public synchronized void deleteDestoryListeners() {
			easyWebFilterDestoryListeners.clear();
		}
	}
}
