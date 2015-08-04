package com.openthinks.easyweb.annotation.process.objects;

import java.util.HashSet;
import java.util.Set;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.Controller;

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
			controllerName = controllerName.substring(0, 1).toLowerCase()
					.concat(controllerName.substring(1));
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

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getFullPath() {
		return WebUtils.contactPath(parent().getFullPath(),
				this.getRelativePath());
	}

	@Override
	public boolean isValid() {
		return controller != null;
	}

	public Class<?> getType() {
		return type;
	}

}
