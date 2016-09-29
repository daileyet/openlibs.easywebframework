package com.openthinks.easyweb.annotation.process.core;

import com.openthinks.easyweb.annotation.process.core.scaner.PackageScanner;
import com.openthinks.easyweb.annotation.process.core.scaner.Scanners;
import com.openthinks.easyweb.annotation.process.objects.WebContainer;
import com.openthinks.easyweb.context.WebConfigure;
import com.openthinks.easyweb.context.WebContexts;

/**
 * Web container processor 
 * @author dailey.yet@outlook.com
 *
 */
class WebContainerProcesser extends AbstractProcesser<WebContainer> {

	@Override
	public WebContainer process() {
		WebContainer container = WebContexts.get().getWebContainer();
		container.reset();
		WebConfigure configure = WebContexts.get().getWebConfigure();
		PackageScanner packageScanner = Scanners.get(configure.getScanWay());
		packageScanner.scan(container, configure.getScanPackages());
		container.buildMapping();
		return container;
	}

}