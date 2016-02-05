package com.openthinks.easyweb.utils.json;


public class OperationJsonTest {

	public void testOutput() {
		OperationJson json = OperationJson.build().error("it's error");
		String actual = json.toString();
		System.out.println(actual);
	}

}
