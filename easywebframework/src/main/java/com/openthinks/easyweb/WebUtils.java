package com.openthinks.easyweb;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.openthinks.easyweb.annotation.process.objects.WebContainer;
import com.openthinks.easyweb.annotation.process.objects.WebController;
import com.openthinks.easyweb.annotation.process.objects.WebFilter;
import com.openthinks.easyweb.annotation.process.objects.WebMethod;
import com.openthinks.easyweb.context.RequestSuffix;
import com.openthinks.easyweb.context.WebContexts;
import com.openthinks.easyweb.monitor.WebProcessMonitor;
import com.openthinks.libs.utilities.Checker;

/**
 * Web utilities for path, encode
 * @author dailey.yet@outlook.com
 *
 */
public final class WebUtils {

	/**
	 * sort two given parameter and take the longest as first
	 * @param path1 String
	 * @param path2 String
	 * @return int
	 */
	public static int comparePathByLongest(String path1, String path2) {
		int length1 = path1 == null ? 0 : path1.length();
		int length2 = path2 == null ? 0 : path2.length();
		return length2 - length1;
	}

	/**
	 * 
	 * @param subpath String Web method path
	 * @return String REDIRECT::/controller_path/method_path.htm
	 */
	public static String redirect(String subpath) {
		return WebStatic.WEB_REDIRECT_PATH_REFIX + subpath;
	}

	/**
	 * 
	 * @param subpath String Web method path
	 * @return String FILTER::PASS::
	 */
	public static String filterPass() {
		return WebStatic.WEB_FILTER_PASS_PATH_REFIX;
	}

	public static String filterStop() {
		return WebStatic.WEB_FILTER_STOP_PATH_REFIX;
	}

	/**
	 * get easyweb controller method mapping full path
	 * @param subpath String Web method path
	 * @return String which as {@link HttpServletRequest#getRequestURI()}
	 */
	public static String path(String subpath) {
		String relSubpath = getRequestURI(subpath);
		return WebContexts.getServletContext().getContextPath() + relSubpath;
	}

	/**
	 * get static web resource full path
	 * @param staticPath String not easyweb method path
	 * @return String static web resource full path
	 */
	public static String pathS(String staticPath) {
		return WebContexts.getServletContext().getContextPath() + staticPath;
	}

	/**
	 * get the instance of {@link WebMethod} by the {@link HttpServletRequest};
	 * first try to find in {@link WebController} scope, if not found will try to find in {@link WebFilter} scope
	 * @see WebUtils#getControllerWebMethod(HttpServletRequest)
	 * @see WebUtils#getFilterWebMethod(ServletRequest)
	 * @param req {@link HttpServletRequest}
	 * @return {@link WebMethod} or null
	 */
	public static WebMethod getWebMethod(HttpServletRequest req) {
		String path = req.getRequestURI();
		String mappingPath = getRequestMapingPath(path);
		WebContainer container = WebContexts.get().getWebContainer();
		WebMethod webMethod = container.lookup(mappingPath);
		if (webMethod == null) {
			webMethod = container.lookupFilter(mappingPath);
		}
		return webMethod;
	}

	/**
	 * get the instance of {@link WebMethod} which belong to {@link WebController} by the {@link HttpServletRequest}
	 * @param req {@link HttpServletRequest}
	 * @return {@link WebMethod} or null
	 */
	public static WebMethod getControllerWebMethod(HttpServletRequest req) {
		String path = req.getRequestURI();
		String mappingPath = getRequestMapingPath(path);
		WebContainer container = WebContexts.get().getWebContainer();
		WebMethod webMethod = container.lookup(mappingPath);
		return webMethod;
	}

	/**
	 * get the instance of {@link WebMethod} which belong to {@link WebFilter} by the {@link HttpServletRequest}
	 * @param req {@link HttpServletRequest}
	 * @return {@link WebMethod} or null
	 */
	public static WebMethod getFilterWebMethod(ServletRequest req) {
		String path = ((HttpServletRequest) req).getRequestURI();
		String mappingPath = getRequestMapingPath(path);
		WebContainer container = WebContexts.get().getWebContainer();
		WebMethod webMethod = container.lookupFilter(mappingPath);
		return webMethod;
	}

	@SuppressWarnings("deprecation")
	public static String encode(Object object) {
		String retval = null;
		if (object != null) {
			retval = URLEncoder.encode(object.toString());
		}
		return retval;
	}

	@SuppressWarnings("deprecation")
	public static String decode(Object object) {
		String retval = null;
		if (object != null) {
			retval = URLDecoder.decode(object.toString());
		}
		return retval;
	}

	/**
	 * Used for web path
	 * @param fullPath
	 * @param relativePath
	 * @return String
	 */
	public static String contactPath(String fullPath, String relativePath) {
		Checker.require(fullPath).notNull();
		Checker.require(relativePath).notNull();
		String rp = relativePath;
		while (rp.indexOf("/") == 0) {
			rp = rp.substring(1);
		}
		String fp = fullPath;
		if (fp.lastIndexOf("/") < 1) {
			if ("/".equals(fp.trim())) {
				// Fix Bug for root path is "/" and
				// controller's root path is default
				fp = "";
			}
		} else {
			while (fp.lastIndexOf("/") == fp.length() - 1) {
				fp = fp.substring(0, fp.length() - 1);
			}
		}
		return fp + "/" + rp;

	}

	/**
	 * Used for local file path
	 * @param fullPath
	 * @param relativePath
	 * @return String
	 */
	public static String contactFilePath(String fullPath, String relativePath) {
		Checker.require(fullPath).notNull();
		Checker.require(relativePath).notNull();
		String rp = relativePath;
		while (rp.indexOf(WebStatic.PATH_SPLITER) == 0) {
			rp = rp.substring(1);
		}
		String fp = fullPath;
		if (fp.lastIndexOf(WebStatic.PATH_SPLITER) < 1) {
			if (WebStatic.PATH_SPLITER.equals(fp.trim())) {
				// Fix Bug for root path is "/" and
				// controller's root path is default
				fp = "";
			}
		} else {
			while (fp.lastIndexOf(WebStatic.PATH_SPLITER) == fp.length() - 1) {
				fp = fp.substring(0, fp.length() - 1);
			}
		}
		return fp + WebStatic.PATH_SPLITER + rp;

	}

	/**
	 * get the EasyWeb controller method path
	 * @param requestURI String HTTP request full URL path
	 * @param suffix {@link RequestSuffix} the suffix of the URL path
	 * @return String
	 */
	public static String convertToRequestMapingPath(String requestURI, RequestSuffix suffix) {
		for (String option : suffix.options()) {
			String URI = requestURI.toLowerCase();
			if (!URI.endsWith(option)) {
				continue;
			}
			int suffixIndex = URI.lastIndexOf(option);
			return requestURI.substring(0, suffixIndex);
		}
		return requestURI;
	}

	/**
	 * get the EasyWeb controller method path
	 * @see WebUtils#convertToRequestMapingPath(String, RequestSuffix)
	 * @param requestURI String {@link HttpServletRequest#getRequestURI()}
	 * @return String the EasyWeb controller method path
	 */
	public static String getRequestMapingPath(String requestURI) {
		return convertToRequestMapingPath(requestURI, WebContexts.get().getWebConfigure().getRequestSuffix());
	}

	/**
	 * get the full URL path by the EasyWeb controller method path
	 * @param requestMappingPath String
	 * @param suffix {@link RequestSuffix}
	 * @return String String HTTP request full URL path
	 */
	public static String convertToRequestURI(String requestMappingPath, RequestSuffix suffix) {
		String requestURI = requestMappingPath + suffix.options()[0];
		return requestURI;
	}

	/**
	 * get the full URL path by the EasyWeb controller method path
	 * @see WebUtils#convertToRequestURI(String, RequestSuffix)
	 * @param requestMappingPath String the EasyWeb controller method path
	 * @return String HTTP request full URL path
	 */
	public static String getRequestURI(String requestMappingPath) {
		return convertToRequestURI(requestMappingPath, WebContexts.get().getWebConfigure().getRequestSuffix());
	}

	public static String getRemoteIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * get Web application class directory after deploy
	 * @param servletContext ServletContext
	 * @return String
	 */
	public static String getWebClassDir(final ServletContext servletContext) {
		String web_class_dir = (String) servletContext.getAttribute(WebStatic.WEB_CLASS_DIR);
		if (web_class_dir == null) {
			web_class_dir = servletContext.getInitParameter(WebStatic.WEB_CLASS_DIR);
			if (web_class_dir == null)
				web_class_dir = servletContext.getRealPath("") + WebStatic.PATH_SPLITER + "WEB-INF"
						+ WebStatic.PATH_SPLITER + "classes" + WebStatic.PATH_SPLITER;
		}
		return web_class_dir;
	}

	public static String getWebClassDir() {
		return getWebClassDir(WebContexts.getServletContext());
	}

	/**
	 * calculate class name by class full path with package and package full path
	 * @param classFileWholePath
	 * @param classPackRootDir
	 * @return
	 */
	public static String getClassName(String classFileWholePath, String classPackRootDir) {
		String cfwp = classFileWholePath.toUpperCase();
		String cprd = classPackRootDir.toUpperCase();
		//fix file path not unified
		cprd = WebUtils.contactFilePath(cprd, "");
		int index = cfwp.indexOf(cprd);
		// TODO check param1 contain param2
		String className = classFileWholePath.substring(index + cprd.length());
		return className.replace(WebStatic.PATH_SPLITER, ".").replace(".class", "");
	}

	/**
	 * get file path for given package
	 * @param pack String package full name
	 * @param servletContext ServletContext
	 * @return String package file path
	 */
	public static String getPackPath(String pack, final ServletContext servletContext) {
		String tempPack = getPackagePath(pack);
		return WebUtils.contactFilePath(getWebClassDir(servletContext), tempPack);
	}

	/**
	 * only get package path for given package not include absolute file path(which the pacakage root directory)
	 * @param pack String package full name
	 * @return
	 */
	public static String getPackagePath(String pack) {
		Checker.require(pack).notNull();
		int all_index = pack.indexOf("*");
		String tempPack = pack;
		if (all_index > 0) {// com.xxx.*
			tempPack = tempPack.substring(0, all_index);
		}
		tempPack = tempPack.replace(".", WebStatic.PATH_SPLITER);
		return tempPack;
	}

	public static String getPackPath(String pack) {
		return getPackPath(pack, WebContexts.getServletContext());
	}

	/**
	 * check {@link WebProcessMonitor} enable remote access or not 
	 * @return boolean
	 */
	public static boolean isEnableRemoteMonitor() {
		boolean enable = false;
		String remoteEnable = (String) WebContexts.getServletContext()
				.getAttribute(WebStatic.WEB_MONITOR_INIT_PARAM_ENABLE_REMOTE);
		if (remoteEnable != null) {
			enable = Boolean.valueOf(remoteEnable);
		}
		return enable;
	}
}
