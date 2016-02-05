/**
 * 
 */
package com.openthinks.easyweb.context;

import com.openthinks.easyweb.context.parser.ConfigureParser;
import com.openthinks.easyweb.context.parser.WebConfigureAnnoationParser;

/**
 * Used in {@link ConfigureParser}<BR>
 * 
 * @see {@link WebConfigureAnnoationParser}
 * @author minjdai
 * 
 */
public interface Bootstrap {
	/**
	 * initial setting; such as:
	 * <ul>
	 * <li> database connection configuration
	 * <li> file i/o
	 * </ul>
	 */
	public void initial();

	public void cleanUp();
}
