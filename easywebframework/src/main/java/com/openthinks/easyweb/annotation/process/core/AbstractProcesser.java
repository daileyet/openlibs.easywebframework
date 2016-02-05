package com.openthinks.easyweb.annotation.process.core;

import java.util.HashMap;
import java.util.Map;

import com.openthinks.easyweb.annotation.process.Processer;
import com.openthinks.easyweb.annotation.process.objects.WebUnit;

abstract class AbstractProcesser<T extends WebUnit> implements Processer<T> {
	private final Map<String, Object> properties = new HashMap<String, Object>();

	public void setPropertie(String key, Object value) {
		properties.put(key, value);
	}

	@SuppressWarnings({ "unchecked" })
	public <U extends Object> U getPropertie(String key) {
		return (U) properties.get(key);
	}

	@Override
	public abstract T process();

}