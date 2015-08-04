/**
 * 
 */
package openthinks.easyweb.context.parser;

import openthinks.easyweb.context.Bootstrap;
import openthinks.easyweb.context.NullBootstrap;

/**
 * @author minjdai
 * 
 */
public class NullConfigureParser implements ConfigureParser {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.openthinks.easyweb.context.parser.ConfigureParser#scanPackages()
	 */
	@Override
	public String[] scanPackages() {

		return new String[0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.openthinks.easyweb.context.parser.ConfigureParser#requestSuffix()
	 */
	@Override
	public String requestSuffix() {
		return "";
	}

	@Override
	public Bootstrap bootstarp() {
		return new NullBootstrap();
	}
}
