package com.openthinks.easyweb.context.parser;

import com.openthinks.easyweb.annotation.configure.ScanWay.ScanWayEnum;
import com.openthinks.easyweb.context.Bootstrap;
import com.openthinks.easyweb.context.RequestSuffix;

/**
 * EasyWeb configuration parser
 * @author dailey.yet@outlook.com
 *
 */
public interface ConfigureParser {

	/**
	 * get the scanned packages configure item
	 * @return String[]
	 */
	String[] scanPackages();

	/**
	 * get the request suffix configure item
	 * @return one request suffix
	 * @see RequestSuffix
	 */
	String requestSuffix();

	/**
	 * get {@link Bootstrap} instance
	 * @return Bootstrap instance
	 * @throws ClassNotFoundException occurs when class not found
	 * @throws InstantiationException occurs when new instance by Class
	 * @throws IllegalAccessException occurs when illegal access happen
	 */
	Bootstrap bootstarp() throws ClassNotFoundException, InstantiationException, IllegalAccessException;

	/**
	 * get the EasyWeb controller package scan way
	 * @return {@link ScanWayEnum}
	 */
	ScanWayEnum getScanWay();
}
