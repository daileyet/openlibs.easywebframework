package com.openthinks.easyweb.context;

import com.openthinks.easyweb.annotation.process.WebProcesser;
import com.openthinks.easyweb.annotation.process.objects.WebContainer;

/**
 * Context for holding
 * 
 * @author minjdai
 * 
 */
public interface WebContext {

	public WebConfigure getWebConfigure();

	public WebProcesser getWebProcesser();

	public WebContainer getWebContainer();
}
