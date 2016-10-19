package com.openthinks.easyweb.annotation.process.objects;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

import com.openthinks.easyweb.annotation.process.filter.path.FilterPathMatcher;
import com.openthinks.easyweb.context.RequestSuffix;
import com.openthinks.libs.utilities.Checker;

/**
 * Represent all related objects of easyweb framework
 * @author dailey.yet@outlook.com
 *
 */
public class WebContainer implements WebUnit {
	private final ServletContext context;
	private final Set<WebController> controllerChildern;
	private final Map<String, WebMethod> controllerMapping;
	private final Set<WebFilter> filterChildern;
	private final Map<String, WebMethod> filterMapping;

	public WebContainer(ServletContext context) {
		super();
		this.context = context;
		this.controllerChildern = new HashSet<>();
		this.controllerMapping = new ConcurrentHashMap<>();
		this.filterChildern = new HashSet<>();
		this.filterMapping = new ConcurrentHashMap<>();
	}

	/**
	 * reset {@link WebContainer}, clear all processed children and relationship
	 */
	public void reset() {
		this.controllerChildern.clear();
		this.controllerMapping.clear();
		this.filterChildern.clear();
		this.filterMapping.clear();
	}

	/**
	 * add an child object of WebController
	 * @param child WebController
	 */
	public void add(WebController child) {
		if (child.isValid()) {
			boolean isSuccess = false;
			isSuccess = controllerChildern.add(child);
			if (isSuccess) {
				child.parent(this);
			}
		}
	}

	/**
	 * add an child object of WebFilter
	 * @param child WebFilter
	 */
	public void add(WebFilter child) {
		if (child.isValid()) {
			boolean isSuccess = false;
			isSuccess = filterChildern.add(child);
			if (isSuccess) {
				child.parent(this);
			}
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
	public Set<WebInstancer> children() {
		Set<WebInstancer> set = new HashSet<>();
		set.addAll(controllerChildern);
		set.addAll(filterChildern);
		return Collections.unmodifiableSet(set);
	}

	public Set<WebController> getWebControllers() {
		return controllerChildern;
	}

	public Set<WebFilter> getWebFilters() {
		return filterChildern;
	}

	@Override
	public String getName() {
		return getRelativePath();
	}

	@Override
	public String getFullPath() {
		return getRelativePath();
	}

	@Override
	public boolean isValid() {
		return true;
	}

	/**
	 * find {@link WebMethod} by controllerMapping path which equals {@link WebMethod#getFullPath()}
	 * 
	 * @param requestMapingPath
	 *            String request mapping path without {@link RequestSuffix} etc. /web/test/index
	 * @return {@link WebMethod}
	 */
	public WebMethod lookup(String requestMapingPath) {
		Checker.require(requestMapingPath).notNull();
		if (!requestMapingPath.startsWith(getRelativePath())) {// BUG on SAE, need full path
			requestMapingPath = getRelativePath() + requestMapingPath;
		}
		return this.controllerMapping.get(requestMapingPath);
	}

	/**
	 * find {@link WebMethod} by filterMapping path which equals {@link WebMethod#getFullPath()}
	 * @param requestMapingPath  request mapping path without {@link RequestSuffix} etc. /web/test/index
	 * @return {@link WebMethod}
	 */
	public WebMethod lookupFilter(String requestMapingPath) {
		Checker.require(requestMapingPath).notNull();
		if (!requestMapingPath.startsWith(getRelativePath())) {// BUG on SAE, need full path
			requestMapingPath = getRelativePath() + requestMapingPath;
		}
		WebMethod webMethod = this.filterMapping.get(requestMapingPath);
		if (webMethod == null) {//if not found, try the closet path
			Optional<String> closetPath = FilterPathMatcher.configDefaultStrategy().findMatch(requestMapingPath,
					this.filterMapping.keySet());
			if (closetPath.isPresent()) {
				webMethod = this.filterMapping.get(closetPath.get());
			}
		}
		return webMethod;
	}

	/**
	 * rebuild web request mapping
	 */
	public void buildMapping() {
		controllerMapping.clear();
		for (WebController c : this.controllerChildern) {
			for (WebMethod m : c.children()) {
				controllerMapping.put(m.getFullPath(), m);
			}
		}
		filterMapping.clear();
		for (WebFilter f : this.filterChildern) {
			for (WebMethod m : f.children()) {
				filterMapping.put(m.getFullPath(), m);
			}
		}

	}
}
