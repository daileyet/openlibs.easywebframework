/**
 * 
 */
package openthinks.easyweb.context;

import openthinks.easyweb.context.parser.ConfigureParser;
import openthinks.easyweb.context.parser.WebConfigureAnnoationParser;

/**
 * Used in {@link ConfigureParser}<BR>
 * 
 * @see {@link WebConfigureAnnoationParser}
 * @author minjdai
 * 
 */
public interface Bootstrap {
	public void initial();
}
