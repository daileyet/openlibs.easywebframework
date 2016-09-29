/**
 * 
 */
package com.openthinks.easyweb.context;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.openthinks.easyweb.annotation.configure.ScanWay.ScanWayEnum;
import com.openthinks.easyweb.context.parser.ConfigureParser;
import com.openthinks.easyweb.context.parser.WebConfigureAnnoationParser;
import com.openthinks.easyweb.context.parser.WebConfigureFileParser;
import com.openthinks.libs.utilities.CommonUtilities;
import com.openthinks.libs.utilities.logger.ProcessLogger;

/**
 * The implementation of {@link WebConfigure}
 * @author minjdai
 * 
 */
public class WebContextConfige implements WebConfigure {
	private final ConfigureParser parser;

	public WebContextConfige() throws ClassNotFoundException {
		String configureLocation = WebContexts.getServletContext().getInitParameter(CONFIGURE_FILE_LOCATION);
		if (configureLocation != null) {
			parser = new WebConfigureFileParser(configureLocation);
		} else {
			String configureClassName = WebContexts.getServletContext().getInitParameter(CONFIGURE_CLASS_NAME);
			parser = new WebConfigureAnnoationParser(configureClassName);
		}
	}

	@Override
	public Set<String> getScanPackages() {
		String[] packages = parser.scanPackages();
		return new HashSet<>(Arrays.asList(packages));
	}

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
			ProcessLogger.error(CommonUtilities.getCurrentInvokerMethod(), e);
		}
		return new NullBootstrap();
	}

	@Override
	public ScanWayEnum getScanWay() {
		return parser.getScanWay();
	}

}
