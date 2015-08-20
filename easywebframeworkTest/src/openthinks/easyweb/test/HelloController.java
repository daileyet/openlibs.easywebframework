package openthinks.easyweb.test;

import java.io.IOException;
import java.io.PrintWriter;

import openthinks.easyweb.annotation.Controller;
import openthinks.easyweb.annotation.Mapping;
import openthinks.easyweb.annotation.ResponseReturn;
import openthinks.easyweb.context.handler.WebAttributers;

@Controller
public class HelloController {

	@Mapping("/index")
	public String index(WebAttributers ws) {
		return "hello.jsp";
	}

	@Mapping("/index2")
	public void index2(WebAttributers webAttributes) {
		webAttributes.getResponse().setContentType("text/html;charset=utf-8");
		try {
			PrintWriter writer = webAttributes.getResponse().getWriter();
			writer.print("hello,welcome");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Mapping("/index3")
	@ResponseReturn(contentType = "text/xml")
	public String index3() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <welcome-file-list><welcome-file>index.do</welcome-file></welcome-file-list>";
	}

	@Mapping("/login")
	public String toLogin() {
		return "login.jsp";
	}

	@Mapping("/login/action")
	public String doLogin(WebAttributers ws) {
		String username = (String) ws.get("username");
		String userpass = (String) ws.get("pass");

		if ("admin".equals(username) && "admin".equals(userpass)) {
			ws.storeSession("LOGIN_NAME", username);
			return "hello.jsp";
		}

		return toLogin();
	}
}
