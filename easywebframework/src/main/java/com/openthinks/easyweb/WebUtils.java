package com.openthinks.easyweb;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import com.openthinks.easyweb.annotation.process.WebProcesser;
import com.openthinks.easyweb.annotation.process.objects.WebContainer;
import com.openthinks.easyweb.annotation.process.objects.WebMethod;
import com.openthinks.easyweb.context.RequestSuffix;
import com.openthinks.easyweb.context.WebContexts;
import com.openthinks.libs.utilities.Checker;

/**
 * Web utilities for path, encode
 * @author dailey.yet@outlook.com
 *
 */
public class WebUtils {

	/**
	 * get the instance of {@link WebMethod} by the {@link HttpServletRequest}
	 * @param req HttpServletRequest
	 * @return WebMethod
	 */
	public static WebMethod getWebMethod(HttpServletRequest req) {
		String path = req.getRequestURI();
		String mappingPath = WebUtils.convertToRequestMapingPath(path, WebContexts.get().getWebConfigure()
				.getRequestSuffix());
		WebContainer container = WebContexts.get().getWebContainer();
		WebMethod webMethod = container.lookup(mappingPath);
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
		while (rp.indexOf(WebProcesser.PATH_SPLITER) == 0) {
			rp = rp.substring(1);
		}
		String fp = fullPath;
		if (fp.lastIndexOf(WebProcesser.PATH_SPLITER) < 1) {
			if (WebProcesser.PATH_SPLITER.equals(fp.trim())) {
				// Fix Bug for root path is "/" and
				// controller's root path is default
				fp = "";
			}
		} else {
			while (fp.lastIndexOf(WebProcesser.PATH_SPLITER) == fp.length() - 1) {
				fp = fp.substring(0, fp.length() - 1);
			}
		}
		return fp + WebProcesser.PATH_SPLITER + rp;

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
	 * get the full URL path by the EasyWeb controller method path
	 * @param requestMappingPath String
	 * @param suffix {@link RequestSuffix}
	 * @return String
	 */
	public static String convertToRequestURI(String requestMappingPath, RequestSuffix suffix) {
		String requestURI = requestMappingPath + suffix.options()[0];
		return requestURI;
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
}
