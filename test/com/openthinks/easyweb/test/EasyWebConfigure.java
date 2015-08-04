package com.openthinks.easyweb.test;

import com.openthinks.easyweb.annotation.configure.EasyConfigure;
import com.openthinks.easyweb.annotation.configure.RequestSuffixs;
import com.openthinks.easyweb.annotation.configure.ScanPackages;

@EasyConfigure
@ScanPackages({ "com.openthinks.easyweb.test" })
@RequestSuffixs(".do,.htm")
public class EasyWebConfigure {

}
