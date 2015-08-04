/**
 * 
 */
package openthinks.easyweb.context.parser;

import openthinks.easyweb.context.Bootstrap;

/**
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

}
