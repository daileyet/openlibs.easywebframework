/**
 * 
 */
package com.openthinks.easyweb.context.parser;

import com.openthinks.easyweb.annotation.configure.ScanWay.ScanWayEnum;
import com.openthinks.easyweb.context.Bootstrap;

/**
 * Get EasyWeb configuration from file
 * @author minjdai
 * 
 */
public class WebConfigureFileParser implements ConfigureParser {
	private final String configureLocation;

	public WebConfigureFileParser(String configureLocation) {
		this.configureLocation = configureLocation;
		System.out.println(this.configureLocation);
	}

	/**
	 * TODO
	 */
	@Override
	public String[] scanPackages() {

		return new String[] { "com.openthinks.easyweb.test" };
	}

	/**
	 * TODO
	 */
	@Override
	public String requestSuffix() {

		return ".do,.htm";
	}

	/**
	 * TODO
	 */
	@Override
	public Bootstrap bootstarp() {

		return null;
	}

	@Override
	public ScanWayEnum getScanWay() {
		return ScanWayEnum.FILE_PATH;
	}

}
