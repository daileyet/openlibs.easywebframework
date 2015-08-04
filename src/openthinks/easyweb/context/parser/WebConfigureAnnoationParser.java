package openthinks.easyweb.context.parser;

import openthinks.easyweb.annotation.configure.BootstarpClass;
import openthinks.easyweb.annotation.configure.EasyConfigure;
import openthinks.easyweb.annotation.configure.RequestSuffixs;
import openthinks.easyweb.annotation.configure.ScanPackages;
import openthinks.easyweb.context.Bootstrap;
import openthinks.easyweb.context.NullBootstrap;
import openthinks.easyweb.exception.CheckNoPassException;

public class WebConfigureAnnoationParser implements ConfigureParser {
	private final Class<?> configureClass;

	public WebConfigureAnnoationParser(String configureClassName)
			throws ClassNotFoundException {
		configureClass = Class.forName(configureClassName);
		checkNecessaryAnnoation();
	}

	private void checkNecessaryAnnoation() throws CheckNoPassException {
		if (configureClass.getAnnotation(EasyConfigure.class) == null)
			throw new CheckNoPassException(getClass(),
					"checkNecessaryAnnoation");
		if (configureClass.getAnnotation(ScanPackages.class) == null)
			throw new CheckNoPassException(getClass(),
					"checkNecessaryAnnoation");
		if (configureClass.getAnnotation(RequestSuffixs.class) == null)
			throw new CheckNoPassException(getClass(),
					"checkNecessaryAnnoation");
	}

	@Override
	public String[] scanPackages() {
		ScanPackages packages = configureClass
				.getAnnotation(ScanPackages.class);
		return packages == null ? new String[] {} : packages.value();
	}

	@Override
	public String requestSuffix() {
		RequestSuffixs suffixs = configureClass
				.getAnnotation(RequestSuffixs.class);
		return suffixs == null ? "" : suffixs.value();
	}

	@Override
	public Bootstrap bootstarp() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		Class<?> bootstarp = this.configureClass;
		BootstarpClass bootAnnotation = configureClass
				.getAnnotation(BootstarpClass.class);
		if (bootAnnotation != null) {
			bootstarp = Class.forName(bootAnnotation.value());
		}
		if (bootstarp != null) {
			Object bootObj = bootstarp.newInstance();
			if (bootObj instanceof Bootstrap) {
				return (Bootstrap) bootObj;
			}
		}
		return new NullBootstrap();
	}

}
