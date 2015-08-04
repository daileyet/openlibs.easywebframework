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

	/* (non-Javadoc)
	 * @see com.openthinks.easyweb.context.WebContext#getWebConfigure()
	 */
	@Override
	public WebConfigure getWebConfigure() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.openthinks.easyweb.context.WebContext#getWebProcesser()
	 */
	@Override
	public WebProcesser getWebProcesser() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.openthinks.easyweb.context.WebContext#getWebContainer()
	 */
	@Override
	public WebContainer getWebContainer() {
		// TODO Auto-generated method stub
		return null;
	}

}
