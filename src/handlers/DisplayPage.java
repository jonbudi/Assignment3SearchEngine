package handlers;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvcController.HttpRequestHandler;

public class DisplayPage implements HttpRequestHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response)
			throws ParseException, ServletException, IOException, NumberFormatException {

		String query = request.getParameter("query").trim().toLowerCase();

		System.out.println("In DisplayPage.java");
		System.out.println(query);

	};

	public DisplayPage() {
		super();
	}
}
