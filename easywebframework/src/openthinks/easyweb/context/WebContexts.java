/**
 * 
 */
package openthinks.easyweb.context;

import javax.servlet.ServletContext;

/**
 * EasyWeb context entry
 * @author minjdai
 * 
 */
public class WebContexts {
	protected static final String EASY_WEB_CONTEXT = "~!#$%@^&*()-+_=_EASY_WEB_CONTEXT_*$%&_+$#@!&(()%$#@@#^^)+~!@`1";
	private static ServletContext servletContext;

	/**
	 * cache servlet container context
	 * @param servletContext ServletContext
	 */
	protected static void initServletContext(ServletContext servletContext) {
		WebContexts.servletContext = servletContext;
	}

	/**
	 * get servlet container context
	 * @return ServletContext
	 */
	public static ServletContext getServletContext() {
		return servletContext;
	}

	/**
	 * get exist EasyWeb context instance
	 * @param <T> {@link SharedContext}
	 * @return T {@link SharedContext}
	 */
	@SuppressWarnings("unchecked")
	public static <T extends SharedContext> T get() {
		if (servletContext == null)
			return (T) new NullSharedContext();
		return (T) servletContext.getAttribute(EASY_WEB_CONTEXT);
	}

	/**
	 * create a new instance of EasyWeb instance
	 * @return {@link SharedContext}
	 */
	protected static SharedContext newContext() {
		return new DefaultWebContextImpl();
	}

	/**
	 * clear the EasyWeb context
	 */
	static void cleanUp() {
		SharedContext context = get();
		context.cleanUp();
		servletContext.setAttribute(EASY_WEB_CONTEXT, null);
	}

}
