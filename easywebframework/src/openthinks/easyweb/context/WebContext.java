package openthinks.easyweb.context;

import openthinks.easyweb.annotation.process.WebProcesser;
import openthinks.easyweb.annotation.process.objects.WebContainer;

/**
 * Context for holding {@link WebConfigure}, {@link WebProcesser}, {@link WebContainer}
 * 
 * @author minjdai
 * 
 */
public interface WebContext {

	/**
	 * get EasyWeb configuration
	 * @return WebConfigure
	 */
	public WebConfigure getWebConfigure();

	/**
	 * get EasyWeb processor
	 * @return WebProcesser
	 */
	public WebProcesser getWebProcesser();

	/**
	 * get EasyWeb all objects
	 * @return WebContainer
	 */
	public WebContainer getWebContainer();
}
