package handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DatabaseCall;
import domain.SearchResult;

import mvcController.HttpRequestHandler;

public class DisplayPage implements HttpRequestHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response)
			throws ParseException, ServletException, IOException, NumberFormatException {
		
		System.out.println("In DisplayPage.java");
		
		List<SearchResult> results = new ArrayList<SearchResult>();
		
		String initialQuery = request.getParameter("query").trim().toLowerCase();
		String[] querys = initialQuery.split(" "); 
		
		int termId;
		for (String query : querys) {
			// TODO: implement this!
			System.out.println(query);
			try {
				termId = DatabaseCall.getTermId(query);
				System.out.println(termId);
			} catch (SQLException e) {
				//e.printStackTrace();
			}
		}
		
		results.add(new SearchResult(0, "Google", "http://www.google.com"));
		results.add(new SearchResult(100, "Yahoo", "http://www.yahoo.com"));
		
		try {
			request.setAttribute("results", results);
			request.getRequestDispatcher("JSP/searchResults.jsp").forward(request,response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	};

	public DisplayPage() {
		super();
	}
}
