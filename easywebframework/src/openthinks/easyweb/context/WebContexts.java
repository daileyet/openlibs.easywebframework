/**
 * 
 */
package openthinks.easyweb.context;

import javax.servlet.ServletContext;

/**
 * @author minjdai
 * 
 */
public class WebContexts {
	protected static final String EASY_WEB_CONTEXT = "~!#$%@^&*()-+_=_EASY_WEB_CONTEXT_*$%&_+$#@!&(()%$#@@#^^)+~!@`1";
	private static ServletContext servletContext;

	protected static void initServletContext(ServletContext servletContext) {
		WebContexts.servletContext = servletContext;
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

	@SuppressWarnings("unchecked")
	public static <T extends SharedContext> T get() {
		if (servletContext == null)
			return (T) new NullSharedContext();
		return (T) servletContext.getAttribute(EASY_WEB_CONTEXT);
	}

	protected static SharedContext newContext() {
		return new DefaultWebContextImpl();
	}

	static void cleanUp() {
		SharedContext context = get();
		context.cleanUp();
		servletContext.setAttribute(EASY_WEB_CONTEXT, null);
	}

}
