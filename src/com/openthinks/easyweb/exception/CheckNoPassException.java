package com.openthinks.easyweb.exception;

import java.lang.reflect.Method;

public class CheckNoPassException extends RuntimeException {

	private static final long serialVersionUID = -617754292131339683L;

	public CheckNoPassException() {
	}

	public CheckNoPassException(Class<?> throwInClass, String throwInMethod) {
		super(generateDetailMessage(throwInClass, throwInMethod));
	}

	public CheckNoPassException(Class<?> throwInClass, String throwInMethod,
			Throwable cause) {
		super(generateDetailMessage(throwInClass, throwInMethod), cause);
	}

	/**
	 * @reconfigurable
	 * @param throwInClass
	 * @param throwInMethod
	 * @return String
	 */
	private static String generateDetailMessage(Class<?> throwInClass,
			String throwInMethod) {
		if (throwInClass != null) {
			for (Method m : throwInClass.getDeclaredMethods()) {
				if (m.getName().equals(throwInMethod)) {
					return m.toString();
				}
			}
		}
		return "";
	}

	public CheckNoPassException(String message) {
		super(message);
	}

}
