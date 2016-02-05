package com.openthinks.easyweb.context;

import com.openthinks.easyweb.annotation.process.core.WebProcesser;
import com.openthinks.easyweb.annotation.process.objects.WebContainer;
import com.openthinks.libs.utilities.InstanceUtilities.InstanceWrapper;

/**
 * Default EasyWeb context implementation
 * @author dailey.yet@outlook.com
 *
 */
public class DefaultWebContextImpl extends SharedContext {
	public DefaultWebContextImpl() {
	}

	@Override
	public WebConfigure getWebConfigure() {
		return lookup(WebConfigure.class, InstanceWrapper.build(WebContextConfige.class));
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