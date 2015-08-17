package openthinks.easyweb.context;

import openthinks.easyweb.annotation.process.WebProcesser;
import openthinks.easyweb.annotation.process.objects.WebContainer;

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
