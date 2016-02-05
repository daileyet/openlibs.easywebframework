package com.openthinks.easyweb.annotation.process.filter;

import java.io.File;
import java.util.List;

import com.openthinks.easyweb.WebStatic;
import com.openthinks.easyweb.annotation.Controller;

public class ControllerFileFilterVistor extends FileFilterVisitor {

	public ControllerFileFilterVistor(List<Class<?>> controllerClasss) {
		super(controllerClasss);
	}

	@Override
	public boolean acceptClassName(File file) {
		if (file == null)
			return false;
		return file.getName().toUpperCase().endsWith(WebStatic.CONTROLLER_FILE_SUFFIX.toUpperCase());
	}

	@Override
	public boolean acceptClassType(Class<?> clazz) {
		Controller controllerAnnotation = clazz.getAnnotation(Controller.class);
		return controllerAnnotation != null;
	}
}