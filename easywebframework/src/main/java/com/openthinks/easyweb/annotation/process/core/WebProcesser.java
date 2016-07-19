package com.openthinks.easyweb.annotation.process.core;

/**
 * The easyweb processor for annotation configure approach 
 * @author dailey.yet@outlook.com
 *
 */
public class WebProcesser {


	public void process() {
		AbstractProcesser<?> processer = new WebContainerProcesser();
		processer.process();
	}

}
