package com.openthinks.easyweb.utils.json;

import com.google.gson.Gson;

public class OperationJson {
	public static final String ERROR_TYEP = "ERROR";
	public static final String SUCESS_TYEP = "SUCESS";
	private String type;
	private String msg;
	private Object other;

	public Object getOther() {
		return other;
	}

	public OperationJson setOther(Object other) {
		this.other = other;
		return this;
	}

	public static OperationJson build() {
		return new OperationJson();
	}

	public String getType() {
		return type;
	}

	public OperationJson error() {
		setType(ERROR_TYEP);
		return this;
	}

	public OperationJson sucess() {
		setType(SUCESS_TYEP);
		return this;
	}

	public OperationJson error(String msg) {
		setType(ERROR_TYEP);
		setMsg(msg);
		return this;
	}

	public OperationJson sucess(String msg) {
		setType(SUCESS_TYEP);
		setMsg(msg);
		return this;
	}

	public OperationJson setType(String type) {
		this.type = type;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public OperationJson setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	@Override
	public String toString() {
		return toJson(this);
	}

	public static String toJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

}
