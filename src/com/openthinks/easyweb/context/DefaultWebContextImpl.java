package com.openthinks.easyweb.context;

import com.openthinks.easyweb.annotation.process.WebProcesser;
import com.openthinks.easyweb.annotation.process.objects.WebContainer;

public class DefaultWebContextImpl extends SharedContext {
	public DefaultWebContextImpl() {
	}

	@Override
	public WebConfigure getWebConfigure() {
		return lookup(WebConfigure.class,
				InstanceWrapper.build(WebContextConfige.class));
	}

	@Override
	public WebProcesser getWebProcesser() {
		return lookup(WebProcesser.class, WebContexts.getServletContext());
	}

	@Override
	public WebContainer getWebContainer() {
		return lookup(WebContainer.class, WebContexts.getServletContext());
	}

}