package com.openthinks.easyweb.context;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RequestSuffix {
	private final Set<String> suffixes = new HashSet<String>();
	public final static String SUFFIX_MULTI_SPLIT = ",";

	public static RequestSuffix build(String suffixStr) {
		String[] suffixArray = suffixStr.split(SUFFIX_MULTI_SPLIT);
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

	@Override
	public String toString() {
		return Arrays.toString(suffixes.toArray());
	}
}
