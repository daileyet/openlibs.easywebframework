package com.openthinks.easyweb.annotation.process.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.openthinks.easyweb.WebStatic;
import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.process.filter.file.ControllerFileFilterVistor;
import com.openthinks.easyweb.annotation.process.filter.file.FilterFileFilterVistor;
import com.openthinks.easyweb.annotation.process.objects.WebContainer;
import com.openthinks.easyweb.annotation.process.objects.WebController;
import com.openthinks.easyweb.annotation.process.objects.WebFilter;
import com.openthinks.easyweb.context.WebConfigure;
import com.openthinks.easyweb.context.WebContexts;
import com.openthinks.libs.utilities.logger.ProcessLogger;

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
		for (String pack : configure.getScanPackages()) {
			String path = WebUtils.getPackPath(pack);
			scanPackageFilter(container, path);
			scanPackageController(container, path);
		}
		container.buildMapping();
		return container;
	}

	/**
	 * @param container
	 * @param filterProcesser
	 * @param path
	 */
	protected void scanPackageFilter(WebContainer container, String path) {
		WebFilterProcesser filterProcesser = new WebFilterProcesser();
		List<Class<?>> filterClasses = filterFilterClass(path);
		List<Object> filters = getFilterInstances(filterClasses);
		for (Object filter : filters) {
			filterProcesser.setPropertie(WebStatic.WEB_FILTER, filter);
			WebFilter child = filterProcesser.process();
			container.add(child);
		}
	}

	/**
	 * @param container
	 * @param controllerProcesser
	 * @param path
	 */
	protected void scanPackageController(WebContainer container, String path) {
		WebControllerProcesser controllerProcesser = new WebControllerProcesser();
		List<Class<?>> controllerClasses = filterControllerClass(path);
		List<Object> controllers = getControllerInstances(controllerClasses);
		for (Object controller : controllers) {
			controllerProcesser.setPropertie(WebStatic.WEB_CONTROLLER, controller);
			WebController child = controllerProcesser.process();
			container.add(child);
		}
	}

	private List<Object> getFilterInstances(List<Class<?>> filterClasses) {
		List<Object> filterInstance = new ArrayList<>();
		for (Class<?> clazz : filterClasses) {
			try {
				filterInstance.add(clazz.newInstance());
			} catch (InstantiationException e) {
				ProcessLogger.warn(e);
			} catch (IllegalAccessException e) {
				ProcessLogger.warn(e);
			}
		}
		return filterInstance;
	}

	private List<Class<?>> filterFilterClass(String packPath) {
		File dir = new File(packPath);
		List<Class<?>> filterClasss = new ArrayList<>();
		dir.listFiles(new FilterFileFilterVistor(filterClasss));
		return filterClasss;
	}

	private List<Object> getControllerInstances(List<Class<?>> controllerClasses) {
		List<Object> controllerInstance = new ArrayList<>();
		for (Class<?> clazz : controllerClasses) {
			try {
				controllerInstance.add(clazz.newInstance());
			} catch (InstantiationException e) {
				ProcessLogger.warn(e);
			} catch (IllegalAccessException e) {
				ProcessLogger.warn(e);
			}
		}
		return controllerInstance;
	}

	private List<Class<?>> filterControllerClass(String packPath) {
		File dir = new File(packPath);
		List<Class<?>> controllerClasss = new ArrayList<>();
		dir.listFiles(new ControllerFileFilterVistor(controllerClasss));
		return controllerClasss;
	}

}