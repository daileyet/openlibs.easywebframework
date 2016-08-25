package com.openthinks.easyweb.utils.json;

import java.util.Arrays;

import com.openthinks.easyweb.WebUtils;

public class StringSetSortTest {

	public static void main(String[] args) {
		String[] paths = { "/easywebtest/welcome/welcome/go", "/easywebtest/welcome/welcome/go/1",
				"/easywebtest/welcome/welcome/go/1/2", "/easywebtest/welcome/welcome/go/1/2/3",
				"/easywebtest/task/index", "/easywebtest/", "/" };
		String target = "/easywebtest/hello";
		Arrays.asList(paths).stream().sorted(WebUtils::comparePathByLongest).forEach(System.out::println);
		System.out.println();
		System.out.println(Arrays.asList(paths).stream().sorted(WebUtils::comparePathByLongest).filter(target::contains)
				.findFirst());

		//		Arrays.asList(paths).stream().sorted(WebUtils::comparePathByLongest).filter(target::contains)
		//				.forEach(System.out::println);
		
		System.out.println("".equals("/".substring(1)));
	}

}
