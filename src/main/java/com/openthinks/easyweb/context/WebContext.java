package com.openthinks.easyweb.context;

import com.openthinks.easyweb.annotation.process.core.WebProcesser;
import com.openthinks.easyweb.annotation.process.objects.WebContainer;

/**
 * Context for holding {@link WebConfigure}, {@link WebProcesser}, {@link WebContainer}
 * 
 * @author minjdai
 * 
 */
public interface WebContext {

	/**
	 * get EasyWeb configuration
	 * @return WebConfigure {@link WebConfigure}
	 */
	public WebConfigure getWebConfigure();

	/**
	 * get EasyWeb processor
	 * @return WebProcesser {@link WebProcesser}
	 */
	public WebProcesser getWebProcesser();

	/**
	 * get EasyWeb all objects
	 * @return WebContainer {@link WebContainer}
	 */
	public WebContainer getWebContainer();
}
