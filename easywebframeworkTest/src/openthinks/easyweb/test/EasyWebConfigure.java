package openthinks.easyweb.test;

import openthinks.easyweb.annotation.configure.EasyConfigure;
import openthinks.easyweb.annotation.configure.RequestSuffixs;
import openthinks.easyweb.annotation.configure.ScanPackages;

@EasyConfigure
@ScanPackages({ "openthinks.easyweb.test" })
@RequestSuffixs(".do,.htm")
public class EasyWebConfigure {

}
