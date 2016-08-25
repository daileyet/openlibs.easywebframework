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
* @Title: WebInstancer.java 
* @Package com.openthinks.easyweb.annotation.process.objects 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 25, 2016
* @version V1.0   
*/
package com.openthinks.easyweb.annotation.process.objects;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.AutoComponent;
import com.openthinks.easyweb.annotation.AutoComponent.KeyType;
import com.openthinks.easyweb.context.WebContexts;
import com.openthinks.easyweb.exception.AutowireFailedException;

/**
 * @author dailey.yet@outlook.com
 *
 */
public abstract class WebInstancer implements WebUnit {
	protected final Object instance;
	protected final Class<?> type;
	protected final Set<WebMethod> childern;
	protected WebContainer parent;
	protected String name;

	public WebInstancer(Object instance) {
		super();
		this.instance = instance;
		this.type = this.instance.getClass();
		this.childern = new HashSet<>();
		this.prepareAutoComponment();
	}

	protected void prepareAutoComponment() {
		for (Field field : this.type.getDeclaredFields()) {

			AutoComponent autoComponent = field.getAnnotation(AutoComponent.class);
			if (autoComponent != null) {
				Object autowireValue = null;
				KeyType keyType = autoComponent.value();
				if (keyType == KeyType.CLASS) {
					Class<?> instanceClass = (autoComponent.implementClass() == Void.class) ? field.getType()
							: autoComponent.implementClass();
					autowireValue = WebContexts.get().lookup(instanceClass);

				} else if (keyType == KeyType.BEAN_NAME) {
					String beanName = autoComponent.beanName();
					autowireValue = WebContexts.get().lookup(beanName);
				}
				if (autowireValue == null) {
					throw new NullPointerException("Could not autowire this component by @KeyType:" + keyType);
				}
				try {
					field.setAccessible(true);
					field.set(instance, autowireValue);
				} catch (IllegalArgumentException e) {
					throw new AutowireFailedException(e);
				} catch (IllegalAccessException e) {
					throw new AutowireFailedException(e);
				}
			}
		}

	}

	protected abstract String calculateName();

	Object getInstance() {
		return instance;
	}

	public void add(WebMethod child) {
		if (child.isValid() && childern.add(child)) {
			child.parent(this);
		}
	}

	public void addMethod(WebMethod method) {
		if (childern.add(method)) {
			method.parent(this);
		}
	}

	@Override
	public String getFullPath() {
		return WebUtils.contactPath(parent().getFullPath(), this.getRelativePath());
	}

	/* (non-Javadoc)
	 * @see com.openthinks.easyweb.annotation.process.objects.WebUnit#parent()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public WebContainer parent() {
		return parent;
	}

	void parent(WebContainer parent) {
		this.parent = parent;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<WebMethod> children() {
		return this.childern;
	}

	public Set<WebMethod> getWebMethods() {
		return childern;
	}

	public int getSize() {
		return this.childern == null ? 0 : this.childern.size();
	}

	@Override
	public String getName() {
		return this.name;
	}

	public Class<?> getType() {
		return type;
	}
}
