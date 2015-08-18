package openthinks.easyweb.context;

import java.util.Set;

/**
 * The EasyWeb configuration
 * @author dailey.yet@outlook.com
 *
 */
public interface WebConfigure {
	String CONFIGURE_FILE_LOCATION = "configureLocation";
	String CONFIGURE_CLASS_NAME = "configureClassName";

	/**
	 * get the EasyWeb controller package
	 * @return Set<String>
	 */
	Set<String> getScanPackages();

	/**
	 * get the EasyWeb request suffix
	 * @return RequestSuffix
	 */
	RequestSuffix getRequestSuffix();

	/**
	 * get the implementation of {@link Bootstrap}
	 * @return Bootstrap
	 */
	Bootstrap getBootstarp();

}
