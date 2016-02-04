package com.openthinks.easyweb.test;

import com.openthinks.easyweb.annotation.Controller;
import com.openthinks.easyweb.annotation.Mapping;

@Controller("/welcome")
public class WelcomeController {

	@Mapping("/go")
	public String go() {

		return "/WEB-INF/jsp/togo.jsp";
	}
}
