package com.openthinks.easyweb.context.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.openthinks.easyweb.annotation.process.objects.WebMethodResponse;

public class WebHandlerFactory {
	private static Map<WebMethodResponse, WebHandler> private_lookups = new ConcurrentHashMap<WebMethodResponse, WebHandler>();
	private static Map<WebMethodResponse, WebHandler> open_lookups = new ConcurrentHashMap<WebMethodResponse, WebHandler>();
	// inner private lookups
	static {
		private_lookups.put(WebMethodResponse.build(void.class), new VoidMappingWebHandler());
		private_lookups.put(WebMethodResponse.build(String.class), new StringMappingWebHandler());
	}

	/**
	 * only used for customized handler
	 * 
	 * @param methodResponse WebMethodResponse
	 * @param webHandler WebHandler
	 */
	public static void register(WebMethodResponse methodResponse, WebHandler webHandler) {
		open_lookups.put(methodResponse, webHandler);
	}

	/**
	 * create {@link WebHandler} by {@link WebMethodResponse}
	 * @param methodResponse WebMethodResponse
	 * @return WebHandler
	 */
	public static WebHandler createHandler(WebMethodResponse methodResponse) {
		WebHandler handler = null;
		if (methodResponse != null) {
			handler = private_lookups.get(methodResponse);
			if (handler == null) {
				handler = open_lookups.get(methodResponse);
			}
		}
		if (handler == null) {
			handler = new NullWebHandler();
		}
		return handler;
	}
}