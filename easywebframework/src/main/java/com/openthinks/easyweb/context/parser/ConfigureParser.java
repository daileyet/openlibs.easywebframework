package com.openthinks.easyweb.context.parser;

import com.openthinks.easyweb.annotation.configure.ScanWay.ScanWayEnum;
import com.openthinks.easyweb.context.Bootstrap;

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
	 * @return String
	 */
	String requestSuffix();

	/**
	 * 
	 * @return Bootstrap
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	Bootstrap bootstarp() throws ClassNotFoundException, InstantiationException, IllegalAccessException;

	/**
	 * get the EasyWeb controller package scan way
	 * @return ScanWayEnum
	 */
	ScanWayEnum getScanWay();
}
