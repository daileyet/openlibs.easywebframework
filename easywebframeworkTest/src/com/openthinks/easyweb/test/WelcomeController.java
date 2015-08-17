package com.openthinks.easyweb.test;

import openthinks.easyweb.annotation.Controller;
import openthinks.easyweb.annotation.Mapping;

@Controller("/welcome")
public class WelcomeController {

	@Mapping("/go")
	public String go() {

		return "/WEB-INF/jsp/togo.jsp";
	}
}
