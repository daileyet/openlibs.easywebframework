package openthinks.easyweb.context.parser;

import openthinks.easyweb.context.Bootstrap;

public interface ConfigureParser {

	String[] scanPackages();

	String requestSuffix();

	Bootstrap bootstarp() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException;

}
