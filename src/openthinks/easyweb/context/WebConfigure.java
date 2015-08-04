package openthinks.easyweb.context;

import java.util.Set;

public interface WebConfigure {
	String CONFIGURE_FILE_LOCATION = "configureLocation";
	String CONFIGURE_CLASS_NAME = "configureClassName";

	Set<String> getScanPackages();

	RequestSuffix getRequestSuffix();

	Bootstrap getBootstarp();

}
