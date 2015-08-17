package openthinks.easyweb.annotation.process;

import openthinks.easyweb.annotation.process.objects.WebUnit;

/**
 * Parsed the easyweb configure and process the base action
 * @author dailey.yet@outlook.com
 *
 * @param <T> WebUnit
 */
public interface Processer<T extends WebUnit> {

	/**
	 * do the process action 
	 * @return T
	 */
	T process();
}
