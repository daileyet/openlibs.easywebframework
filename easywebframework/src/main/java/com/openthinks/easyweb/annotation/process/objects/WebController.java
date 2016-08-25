package com.openthinks.easyweb.annotation.process.objects;

import com.openthinks.easyweb.annotation.Controller;

/**
 * The web controller unit
 * @author dailey.yet@outlook.com
 * @see Controller
 */
public class WebController extends WebInstancer {
	private final Controller controller;

	public WebController(Object instance) {
		super(instance);
		this.controller = this.type.getAnnotation(Controller.class);
		this.name = calculateName();
	}

	@Override
	protected String calculateName() {
		if (controller == null)
			return this.type.getName();
		String controllerName = controller.name();
		if (controllerName == null || "".equals(controllerName)) {
			controllerName = this.type.getSimpleName();
			controllerName = controllerName.substring(0, 1).toLowerCase().concat(controllerName.substring(1));
		}
		return controllerName;
	}

	@Override
	public String getRelativePath() {
		return controller.value();
	}

	@Override
	public boolean isValid() {
		return controller != null;
	}

}
