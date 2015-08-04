package com.openthinks.easyweb.context.parser;

import com.openthinks.easyweb.context.Bootstrap;

public interface ConfigureParser {

	String[] scanPackages();

	String requestSuffix();

	Bootstrap bootstarp() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException;

}
