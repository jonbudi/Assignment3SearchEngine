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
		
		System.out.println("In DisplayPage.java");
		
		String initialQuery = request.getParameter("query").trim().toLowerCase();
		String[] querys = initialQuery.split(" "); 
		for (String query : querys) {
			// TODO: implement this!
			System.out.println(query);
		}

	};

	public DisplayPage() {
		super();
	}
}
