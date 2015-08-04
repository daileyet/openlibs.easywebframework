package openthinks.easyweb.annotation.process.objects;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

/**
 * Represent all related objects of easyweb framework
 * @author dailey.yet@outlook.com
 *
 */
public class WebContainer implements WebUnit {
	private final ServletContext context;
	private final Set<WebController> childern;
	private final Map<String, WebMethod> mapping;

	public WebContainer(ServletContext context) {
		super();
		this.context = context;
		this.childern = new HashSet<WebController>();
		this.mapping = new ConcurrentHashMap<String, WebMethod>();
	}

	/**
	 * add an child object of WebController
	 * @param child WebController
	 */
	public void add(WebController child) {
		if (child.isValid() && childern.add(child)) {
			child.parent(this);
		}
	}

	@Override
	public String getRelativePath() {
		return context.getContextPath();
	}

	@Override
	public <T extends WebUnit> T parent() {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<WebController> children() {
		return childern;
	}

	@Override
	public String getName() {
		return getRelativePath();
	}

	// public static final String WEB_ROOT = "http://localhost:8080";

	@Override
	public String getFullPath() {
		return getRelativePath();
	}

	@Override
	public boolean isValid() {
		return true;
	}

	/**
	 * find {@link WebMethod} by mapping path which equals {@link
	 * WebMethod.getFullPath()}
	 * 
	 * @param path
	 *            String etc. /web/test/index.do
	 * @return
	 */
	public WebMethod lookup(String path) {
		// TODO path is not null
		if (!path.startsWith(getRelativePath())) {// BUG on SAE, need full path
			path = getRelativePath() + path;
		}
		return this.mapping.get(path);
	}

	/**
	 * build
	 */
	public void buildMapping() {
		mapping.clear();
		for (WebController c : this.childern) {
			for (WebMethod m : c.children()) {
				mapping.put(m.getFullPath(), m);
			}
		}
	}

}
