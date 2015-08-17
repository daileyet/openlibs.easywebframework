/**
 * 
 */
package openthinks.easyweb.context;

import openthinks.easyweb.annotation.process.WebProcesser;
import openthinks.easyweb.annotation.process.objects.WebContainer;

/**
 * @author minjdai
 *
 */
public class NullSharedContext extends SharedContext {

	@Override
	public WebConfigure getWebConfigure() {
		return null;
	}

	@Override
	public WebProcesser getWebProcesser() {
		return null;
	}

	@Override
	public WebContainer getWebContainer() {
		return null;
	}

}
