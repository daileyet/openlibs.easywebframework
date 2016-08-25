package com.openthinks.easyweb.test.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.openthinks.easyweb.WebUtils;
import com.openthinks.easyweb.annotation.AutoComponent;
import com.openthinks.easyweb.annotation.Controller;
import com.openthinks.easyweb.annotation.Jsonp;
import com.openthinks.easyweb.annotation.Mapping;
import com.openthinks.easyweb.annotation.ResponseReturn;
import com.openthinks.easyweb.annotation.ResponseReturn.ResponseReturnType;
import com.openthinks.easyweb.context.handler.WebAttributers;
import com.openthinks.easyweb.test.service.HelloService;
import com.openthinks.easyweb.utils.json.OperationJson;

@Controller
public class HelloController {

	/**
	 * auto initialize {@link HelloService}
	 */
	@AutoComponent
	HelloService helloService;

	/**
	 * Usage 1: with String return value
	 * forward to hello.jsp
	 * @param ws
	 * @return
	 */
	@Mapping("/index")
	public String index() {
		return "hello.jsp";
	}

	/**
	 * Usage 2: without return value
	 * response output by self
	 * @param webAttributes
	 */
	@Mapping("/index2")
	public void index2(WebAttributers webAttributes) {
		HttpServletResponse res = webAttributes.getResponse();
		res.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter writer = webAttributes.getResponse().getWriter();
			writer.print("hello,welcome");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Usage 3: with String return value 
	 * take return value as response output
	 * @return
	 */
	@Mapping("/index3")
	@ResponseReturn(contentType = ResponseReturnType.TEXT_XML)
	public String index3() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <welcome-file-list><welcome-file>index.do</welcome-file></welcome-file-list>";
	}

	@Mapping("/login")
	public String toLogin() {
		return "login.jsp";
	}

	/**
	 * Usage 4: get request parameters
	 * forward to next page
	 * @param ws
	 * @return
	 */
	@Mapping("/login/action")
	public String doLogin(WebAttributers ws) {
		String username = (String) ws.get("username");
		String userpass = (String) ws.get("pass");

		if (helloService.isValidate(username, userpass)) {
			ws.storeSession("LOGIN_NAME", username);
			return "hello.jsp";
		}
		return toLogin();
	}

	/**
	 * Usage 5: get request parameters
	 * redirect to next page
	 * @param ws
	 * @return
	 */
	@Mapping("/login/action2")
	public String doLogin2(WebAttributers ws) {
		String username = (String) ws.get("username");
		String userpass = (String) ws.get("pass");

		if (helloService.isValidate(username, userpass)) {
			ws.storeSession("LOGIN_NAME", username);
			return WebUtils.redirect("/index");
		}
		return WebUtils.redirect("/login");
	}

	/**
	 * Usage 6: with String return value 
	 * take return value as special response output for JSONP
	 * default JSONP like : callback(return_json_value)
	 * @param ws
	 * @return
	 */
	@Mapping("/login/action3")
	@ResponseReturn(contentType = ResponseReturnType.TEXT_JAVASCRIPT)
	@Jsonp
	public String doLogin3(WebAttributers ws) {
		String username = (String) ws.get("username");
		String userpass = (String) ws.get("pass");

		if (helloService.isValidate(username, userpass)) {
			ws.storeSession("LOGIN_NAME", username);
			return OperationJson.build().sucess().toString();
		}
		return OperationJson.build().error().toString();
	}
}
