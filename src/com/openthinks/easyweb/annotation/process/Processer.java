package com.openthinks.easyweb.annotation.process;

import com.openthinks.easyweb.annotation.process.objects.WebUnit;

public interface Processer<T extends WebUnit> {
	T process();
}
