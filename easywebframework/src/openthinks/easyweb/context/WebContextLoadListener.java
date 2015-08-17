package openthinks.easyweb.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class WebContextLoadListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		WebContexts.cleanUp();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		final ServletContext servletContext = arg0.getServletContext();

		WebContexts.initServletContext(servletContext);
		//save WebContexts attribute 
		servletContext.setAttribute(WebContexts.EASY_WEB_CONTEXT,
				WebContexts.newContext());
		// initial all base objects
		WebContexts.get().getWebConfigure();
		WebContexts.get().getWebConfigure().getBootstarp().initial();
		WebContexts.get().getWebProcesser().process();
	}
}
