/**
 * 
 */
package com.openthinks.easyweb.context.parser;

import com.openthinks.easyweb.context.Bootstrap;
import com.openthinks.easyweb.context.NullBootstrap;

/**
 * @author minjdai
 * 
 */
public class NullConfigureParser implements ConfigureParser {

	@Override
	public String[] scanPackages() {

		return new String[0];
	}

	@Override
	public String requestSuffix() {
		return "";
	}

	@Override
	public Bootstrap bootstarp() {
		return new NullBootstrap();
	}
}
