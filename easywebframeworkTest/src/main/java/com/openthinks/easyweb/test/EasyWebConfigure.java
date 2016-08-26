package com.openthinks.easyweb.test;

import com.openthinks.easyweb.annotation.configure.EasyConfigure;
import com.openthinks.easyweb.annotation.configure.RequestSuffixs;
import com.openthinks.easyweb.annotation.configure.ScanPackages;
import com.openthinks.easyweb.context.Bootstrap;
import com.openthinks.easyweb.context.EasyWebFilterContexts;
import com.openthinks.easyweb.context.WebContexts;

@EasyConfigure
@ScanPackages({ "com.openthinks.easyweb.test" })
@RequestSuffixs(".do,.htm")
public class EasyWebConfigure implements Bootstrap {

	@Override
	public void cleanUp() {

	}

	@Override
	public void initial() {
		System.out.println("---------------------------------------------");
		System.out.println(WebContexts.getServletContext().getRealPath(""));
		System.out.println("---------------------------------------------");

		EasyWebFilterContexts.get().addInitListener((ctx) -> {
			System.out.println("init filter:" + ctx.getFilterConfig().getFilterName());
		});

		EasyWebFilterContexts.get().addDestoryListener((ctx) -> {
			System.out.println("destory filter:" + ctx.getFilterConfig().getFilterName());
		});
	}

}
