/**
 * 
 */
package com.openthinks.easyweb.context;

import com.openthinks.easyweb.context.parser.ConfigureParser;
import com.openthinks.easyweb.context.parser.WebConfigureAnnoationParser;

/**
 * Used in {@link ConfigureParser}
 * 
 * @author minjdai
 * @see WebConfigureAnnoationParser
 * 
 */
public interface Bootstrap {
	/**
	 * initial setting; such as:
	 * <ul>
	 * <li> database connection configuration</li>
	 * <li> file i/o</li>
	 * </ul>
	 */
	public void initial();

	/**
	 * clear and release resources
	 */
	public void cleanUp();
}
