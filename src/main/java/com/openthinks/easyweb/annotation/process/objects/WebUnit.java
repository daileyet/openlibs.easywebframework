package com.openthinks.easyweb.annotation.process.objects;

import java.util.Set;

/**
 * The interface of easyweb unit
 * @author dailey.yet@outlook.com
 * @see WebContainer
 * @see WebController
 * @see WebMethod
 */
public interface WebUnit {

	/**
	 * relative path for this unit
	 * @return relative web path
	 */
	public String getRelativePath();

	/**
	 * full path for this unit
	 * @return full web path
	 */
	public String getFullPath();

	/**
	 * unit name
	 * @return name of unit
	 */
	public String getName();

	/**
	 * parent unit
	 * @param <T> WebUnit
	 * @return T parent unit
	 */
	public <T extends WebUnit> T parent();

	/**
	 * get children set
	 * @param <T> WebUnit
	 * @return Set children units
	 */
	public <T extends WebUnit> Set<T> children();

	/**
	 * the unit is valid or not
	 * @return boolean
	 */
	public boolean isValid();

}
