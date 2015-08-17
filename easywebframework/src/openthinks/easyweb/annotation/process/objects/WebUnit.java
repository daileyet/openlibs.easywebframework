package openthinks.easyweb.annotation.process.objects;

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
	 * @return String
	 */
	public String getRelativePath();

	/**
	 * full path for this unit
	 * @return String
	 */
	public String getFullPath();

	/**
	 * unit name
	 * @return String
	 */
	public String getName();

	/**
	 * parent unit
	 * @param <T> WebUnit
	 * @return T
	 */
	public <T extends WebUnit> T parent();

	/**
	 * get children set
	 * @param <T> WebUnit
	 * @return Set<T>
	 */
	public <T extends WebUnit> Set<T> children();

	/**
	 * the unit is valid or not
	 * @return boolean
	 */
	public boolean isValid();

}
