package com.openthinks.easyweb.annotation.process.core;

import java.lang.reflect.Method;

import com.openthinks.easyweb.WebStatic;
import com.openthinks.easyweb.annotation.Mapping;
import com.openthinks.easyweb.annotation.process.objects.WebController;
import com.openthinks.easyweb.annotation.process.objects.WebMethod;

/**
 * Web controller processor
 * @author dailey.yet@outlook.com
 */
class WebControllerProcesser extends AbstractProcesser<WebController> {
	@Override
	public WebController process() {
		Object instance = this.getPropertie(WebStatic.WEB_CONTROLLER);
		WebController controller = new WebController(instance);
		if (!controller.isValid())
			return controller;
		WebMethodProcesser processer = new WebMethodProcesser();

		for (Method method : instance.getClass().getDeclaredMethods()) {
			if (method.getAnnotation(Mapping.class) == null)
				continue;
			processer.setPropertie(WebStatic.WEB_METHOD, method);
			WebMethod child = processer.process();
			controller.add(child);
		}
		return controller;
	}
}