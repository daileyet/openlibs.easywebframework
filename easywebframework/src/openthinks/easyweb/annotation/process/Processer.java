package openthinks.easyweb.annotation.process;

import openthinks.easyweb.annotation.process.objects.WebUnit;

public interface Processer<T extends WebUnit> {
	T process();
}
