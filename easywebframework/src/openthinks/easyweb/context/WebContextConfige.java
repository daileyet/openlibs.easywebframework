/**
 * 
 */
package openthinks.easyweb.context;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import openthinks.easyweb.context.parser.ConfigureParser;
import openthinks.easyweb.context.parser.WebConfigureAnnoationParser;
import openthinks.easyweb.context.parser.WebConfigureFileParser;

/**
 * @author minjdai
 * 
 */
public class WebContextConfige implements WebConfigure {
	private final ConfigureParser parser;

	public WebContextConfige() throws ClassNotFoundException {
		String configureLocation = WebContexts.getServletContext()
				.getInitParameter(CONFIGURE_FILE_LOCATION);
		if (configureLocation != null) {
			parser = new WebConfigureFileParser(configureLocation);
		} else {
			String configureClassName = WebContexts.getServletContext()
					.getInitParameter(CONFIGURE_CLASS_NAME);
			parser = new WebConfigureAnnoationParser(configureClassName);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.openthinks.easyweb.context.WebConfigure#getScanPackages()
	 */
	@Override
	public Set<String> getScanPackages() {
		String[] packages = parser.scanPackages();
		return new HashSet<String>(Arrays.asList(packages));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.openthinks.easyweb.context.WebConfigure#getRequestSuffix()
	 */
	@Override
	public RequestSuffix getRequestSuffix() {
		String suffix = parser.requestSuffix();
		return RequestSuffix.build(suffix);
	}

	@Override
	public Bootstrap getBootstarp() {
		try {
			return parser.bootstarp();
		} catch (Exception e) {
			// TODO log
			e.printStackTrace();
		}
		return new NullBootstrap();
	}

}
