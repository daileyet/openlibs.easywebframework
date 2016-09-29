package com.openthinks.easyweb.context.parser;

import com.openthinks.easyweb.annotation.configure.BootstrapClass;
import com.openthinks.easyweb.annotation.configure.EasyConfigure;
import com.openthinks.easyweb.annotation.configure.RequestSuffixs;
import com.openthinks.easyweb.annotation.configure.ScanPackages;
import com.openthinks.easyweb.annotation.configure.ScanWay;
import com.openthinks.easyweb.annotation.configure.ScanWay.ScanWayEnum;
import com.openthinks.easyweb.context.Bootstrap;
import com.openthinks.easyweb.context.NullBootstrap;
import com.openthinks.libs.utilities.InstanceUtilities;
import com.openthinks.libs.utilities.exception.CheckerNoPassException;

/**
 * Use annotation configure for EasyWeb configuration
 * @author dailey.yet@outlook.com
 *
 */
public class WebConfigureAnnoationParser implements ConfigureParser {
	private final Class<?> configureClass;

	public WebConfigureAnnoationParser(String configureClassName) throws ClassNotFoundException {
		configureClass = Class.forName(configureClassName);
		checkNecessaryAnnoation();
	}

	private void checkNecessaryAnnoation() throws CheckerNoPassException {
		if (configureClass.getAnnotation(EasyConfigure.class) == null)
			throw new CheckerNoPassException(getClass(), "checkNecessaryAnnoation");
		if (configureClass.getAnnotation(ScanPackages.class) == null)
			throw new CheckerNoPassException(getClass(), "checkNecessaryAnnoation");
		if (configureClass.getAnnotation(RequestSuffixs.class) == null)
			throw new CheckerNoPassException(getClass(), "checkNecessaryAnnoation");
	}

	@Override
	public String[] scanPackages() {
		ScanPackages packages = configureClass.getAnnotation(ScanPackages.class);
		return packages == null ? new String[] {} : packages.value();
	}

	@Override
	public String requestSuffix() {
		RequestSuffixs suffixs = configureClass.getAnnotation(RequestSuffixs.class);
		return suffixs == null ? "" : suffixs.value();
	}

	@Override
	public Bootstrap bootstarp() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> bootstarp = this.configureClass;
		BootstrapClass bootAnnotation = configureClass.getAnnotation(BootstrapClass.class);
		if (bootAnnotation != null) {
			bootstarp = Class.forName(bootAnnotation.value());
		}
		if (bootstarp != null && Bootstrap.class.isAssignableFrom(bootstarp)) {
			@SuppressWarnings("unchecked")
			Class<? extends Bootstrap> bootstarpClass = (Class<? extends Bootstrap>) bootstarp;
			Bootstrap bootObj = InstanceUtilities.create(bootstarpClass, null);
			return bootObj;
		}
		return new NullBootstrap();
	}

	@Override
	public ScanWayEnum getScanWay() {
		ScanWay scanWay = configureClass.getAnnotation(ScanWay.class);
		if (scanWay == null) {
			return ScanWayEnum.FILE_PATH;
		}
		return scanWay.value();
	}

}
