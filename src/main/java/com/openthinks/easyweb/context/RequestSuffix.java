package com.openthinks.easyweb.context;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The request suffix; for example:
 * <ul>
 * <li> http://localhost:8080/easywebexample/index.do	- .do</li>
 * <li> http://localhost:8080/easywebexample/index.htm	- .htm</li>
 * </ul>
 * @author dailey.yet@outlook.com
 *
 */
public class RequestSuffix {
	private final Set<String> suffixes = new HashSet<>();
	/**
	 * the split token between multiple suffix
	 */
	public final static String SUFFIX_MULTI_SPLIT = ",";

	public static RequestSuffix build(String suffixStr) {
		String[] suffixArray = new String[0];
		if (suffixStr != null) {
			suffixArray = suffixStr.split(SUFFIX_MULTI_SPLIT);
		}
		RequestSuffix rs = new RequestSuffix();
		for (String suffix : suffixArray) {
			if (suffix != null && !"".equals(suffix.trim())) {
				rs.option(suffix.trim());
			}
		}
		return rs;
	}

	private void option(String suffix) {
		suffixes.add(suffix.toLowerCase());
	}

	public boolean contains(String suffix) {
		if (suffix != null)
			return suffixes.contains(suffix.toLowerCase());
		return false;
	}

	public String[] options() {
		return suffixes.toArray(new String[0]);
	}

	public boolean isEmpty() {
		return suffixes.isEmpty();
	}

	@Override
	public String toString() {
		return Arrays.toString(suffixes.toArray());
	}
}
