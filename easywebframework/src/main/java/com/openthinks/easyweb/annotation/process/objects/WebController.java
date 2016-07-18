package com.openthinks.easyweb.annotation.process.objects;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.AutoComponent;
import com.openthinks.easyweb.annotation.AutoComponent.KeyType;
import com.openthinks.easyweb.annotation.Controller;
import com.openthinks.easyweb.context.WebContexts;
import com.openthinks.easyweb.exception.AutowireFailedException;

/**
 * The web controller unit
 * @author dailey.yet@outlook.com
 * @see Controller
 */
public class WebController implements WebUnit {
	private final Object instance;
	private final Controller controller;
	private final Class<?> type;
	private final Set<WebMethod> childern;
	private WebContainer parent;
	private final String name;

	public WebController(Object instance) {
		super();
		this.instance = instance;
		this.type = this.instance.getClass();
		this.controller = this.type.getAnnotation(Controller.class);
		this.childern = new HashSet<WebMethod>();
		this.name = calculate();
		this.prepareAutoComponment();
	}

	private void prepareAutoComponment() {
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

	Object getInstance() {
		return instance;
	}

	private String calculate() {
		if (controller == null)
			return this.type.getName();
		String controllerName = controller.name();
		if (controllerName == null || "".equals(controllerName)) {
			controllerName = this.type.getSimpleName();
			controllerName = controllerName.substring(0, 1).toLowerCase().concat(controllerName.substring(1));
		}
		return controllerName;
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
	public String getRelativePath() {
		return controller.value();
	}

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

	@Override
	public String getFullPath() {
		return WebUtils.contactPath(parent().getFullPath(), this.getRelativePath());
	}

	@Override
	public boolean isValid() {
		return controller != null;
	}

	public Class<?> getType() {
		return type;
	}

}
