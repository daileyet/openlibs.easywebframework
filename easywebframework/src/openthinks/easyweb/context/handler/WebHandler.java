package openthinks.easyweb.context.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import openthinks.easyweb.annotation.process.objects.WebMethod;

/**
 * handle for different {@link WebMethod}
 * 
 * @author minjdai
 * 
 */
public interface WebHandler {

	void handle(HttpServletRequest req, HttpServletResponse resp)
			throws IOException;

}
