package com.openthinks.easyweb.annotation.process.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.openthinks.easyweb.WebStatic;
import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.process.filter.ControllerFileFilterVistor;
import com.openthinks.easyweb.annotation.process.objects.WebContainer;
import com.openthinks.easyweb.annotation.process.objects.WebController;
import com.openthinks.easyweb.context.WebConfigure;
import com.openthinks.easyweb.context.WebContexts;

/**
 * Web container processor 
 * @author dailey.yet@outlook.com
 *
 */
class WebContainerProcesser extends AbstractProcesser<WebContainer> {

	@Override
	public WebContainer process() {
		WebContainer container = WebContexts.get().getWebContainer();
		container.reset();
		WebConfigure configure = WebContexts.get().getWebConfigure();

		WebControllerProcesser processer = new WebControllerProcesser();
		for (String pack : configure.getScanPackages()) {
			String path = WebUtils.getPackPath(pack);
			List<Class<?>> controllerClasses = filterControllerClass(path);
			List<Object> controllers = getControllerInstances(controllerClasses);

			for (Object controller : controllers) {
				processer.setPropertie(WebStatic.WEB_CONTROLLER, controller);
				WebController child = processer.process();
				container.add(child);
			}
		}
		container.buildMapping();

		return container;
	}

	private List<Object> getControllerInstances(List<Class<?>> controllerClasses) {
		List<Object> controllerInstance = new ArrayList<Object>();
		for (Class<?> clazz : controllerClasses) {
			try {
				controllerInstance.add(clazz.newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return controllerInstance;
	}

	private List<Class<?>> filterControllerClass(String packPath) {
		File dir = new File(packPath);
		List<Class<?>> controllerClasss = new ArrayList<Class<?>>();
		dir.listFiles(new ControllerFileFilterVistor(controllerClasss));
		return controllerClasss;
	}

}