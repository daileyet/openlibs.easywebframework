package com.openthinks.easyweb.context;

import java.util.Set;

import com.openthinks.easyweb.WebStatic;
import com.openthinks.easyweb.annotation.configure.ScanWay.ScanWayEnum;

/**
 * The EasyWeb configuration
 * @author dailey.yet@outlook.com
 *
 */
public interface WebConfigure {
	String CONFIGURE_FILE_LOCATION = WebStatic.WEB_CONFIGURE_FILE_LOCATION;
	String CONFIGURE_CLASS_NAME = WebStatic.WEB_CONFIGURE_CLASS_NAME;

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

	/**
	 * get the EasyWeb controller package scan way
	 * @return ScanWayEnum
	 */
	ScanWayEnum getScanWay();

}
