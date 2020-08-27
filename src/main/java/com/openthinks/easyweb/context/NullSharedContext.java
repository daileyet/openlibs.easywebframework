/**
 * 
 */
package com.openthinks.easyweb.context;

import com.openthinks.easyweb.annotation.process.core.WebProcesser;
import com.openthinks.easyweb.annotation.process.objects.WebContainer;

/**
 * @author minjdai
 *
 */
public class NullSharedContext extends SharedContext{

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
