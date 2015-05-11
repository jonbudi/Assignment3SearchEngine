package mvcController;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MvcController
 */
@WebServlet("/MvcController")
public class MvcController extends HttpServlet {

	static final long serialVersionUID = 1L;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */

	@SuppressWarnings("rawtypes")
	private Map handlers;

	public MvcController() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String mvcProps = getServletContext().getRealPath(
				"/WEB-INF/mvc.properties");
		try {
			this.handlers = MvcUtil.buildHandlers(mvcProps);
		} catch (MvcException e) {
			throw new ServletException(
					"Unable to configure controller servlet", e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = request.getServletPath();
		String key = url.substring(1, url.lastIndexOf('.'));

		HttpRequestHandler handler = (HttpRequestHandler) handlers.get(key);

		if (handler != null) {
			try {
				handler.handle(request, response);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} else {
			throw new ServletException("No matching handler");
		}
	}
}