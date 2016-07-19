package com.openthinks.easyweb.annotation.process.core;

import java.lang.reflect.Method;

import com.openthinks.easyweb.WebStatic;
import com.openthinks.easyweb.annotation.process.objects.WebMethod;

/**
 * Web method processor
 * @author dailey.yet@outlook.com
 */
class WebMethodProcesser extends AbstractProcesser<WebMethod> {

	@Override
	public WebMethod process() {
		Method method = this.getPropertie(WebStatic.WEB_METHOD);
		WebMethod webMethod = new WebMethod(method);
		return webMethod;
	}

}