package com.openthinks.easyweb.test;

import com.openthinks.easyweb.annotation.configure.EasyConfigure;
import com.openthinks.easyweb.annotation.configure.RequestSuffixs;
import com.openthinks.easyweb.annotation.configure.ScanPackages;
import com.openthinks.easyweb.context.Bootstrap;
import com.openthinks.easyweb.context.WebContexts;

@EasyConfigure
@ScanPackages({ "com.openthinks.easyweb.test" })
@RequestSuffixs(".do,.htm")
public class EasyWebConfigure implements Bootstrap {

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initial() {
		System.out.println("---------------------------------------------");
		System.out.println(WebContexts.getServletContext().getRealPath(""));
		System.out.println("---------------------------------------------");
	}

}
