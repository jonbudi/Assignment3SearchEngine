package handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

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
		
		Map<Integer, Double> scores = new TreeMap<Integer, Double>();
		Map<Integer, Integer> map;
		
		double score;
		for (String query : querys) {
			System.out.println(query);
			
			try {
				map = DatabaseCall.getTFIDF(query);
				System.out.println(map.size());
				for (int docId : map.keySet()) {
					score = map.get(docId);
					if (scores.containsKey(docId)) {
						scores.put(docId, scores.get(docId) * Math.log(score));
					} else {
						scores.put(docId, Math.log(score));
					}
				}
				
			} catch (SQLException e) {
				//e.printStackTrace();
			}
			
		}

		double highest = -1;
		int index = -1;
		for (int key: scores.keySet()) {
			if (scores.get(key) > highest) {
				highest = scores.get(key);
				index = key;
			}
			System.out.println(key + " " + scores.get(key));
		}
		
		System.out.println(index + " " + highest);
		
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
