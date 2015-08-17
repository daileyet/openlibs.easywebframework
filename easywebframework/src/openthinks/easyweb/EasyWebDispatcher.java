package openthinks.easyweb;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import openthinks.easyweb.context.handler.Handlers;
import openthinks.easyweb.context.handler.WebHandler;

/**
 * Main servlet for easywebframework to dispatch http request
 * @author minjdai
 * @date 2013-6-6
 */
public class EasyWebDispatcher extends HttpServlet {
	private static final long serialVersionUID = -3908723323659757112L;

	/**
	 * dispatch all http request to this servlet to the special {@link WebHandler} to handle
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	void dispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		WebHandler handler = Handlers.getHandler(req, req.getSession().getServletContext());
		handler.handle(req, resp);
	}

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		dispatch(arg0, arg1);
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}
}
