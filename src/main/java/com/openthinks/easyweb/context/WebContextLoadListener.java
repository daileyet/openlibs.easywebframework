package com.openthinks.easyweb.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.openthinks.libs.utilities.logger.ProcessLogger;

/**
 * EasyWeb servlet context listener
 * @author dailey.yet@outlook.com
 *
 */
public class WebContextLoadListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		WebContexts.get().getWebConfigure().getBootstarp().cleanUp();
		WebContexts.cleanUp();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ProcessLogger.info("Easyweb WebContextLoadListener initial...");
		final ServletContext servletContext = arg0.getServletContext();
		WebContexts.initServletContext(servletContext);
		// save WebContexts attribute
		servletContext.setAttribute(WebContexts.EASY_WEB_CONTEXT, WebContexts.newContext());
		// initial all base objects
		ProcessLogger.info("Easyweb WebContextLoadListener initial WebConfigure");
		WebConfigure webConfigure=WebContexts.get().getWebConfigure();
		ProcessLogger.info("Easyweb WebContextLoadListener WebConfigure instance:{0}",webConfigure);
		ProcessLogger.info("Easyweb WebContextLoadListener initial Bootstrap");
		webConfigure.getBootstarp().initial();
		ProcessLogger.info("Easyweb WebContextLoadListener initial WebProcesser");
		WebContexts.get().getWebProcesser().process();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		WebContexts.get().getWebConfigure().getBootstarp().cleanUp();
		WebContexts.cleanUp();
	}
}
