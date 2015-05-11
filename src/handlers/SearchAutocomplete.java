package handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvcController.HttpRequestHandler;
import net.sf.json.JSONArray;

public class SearchAutocomplete implements HttpRequestHandler {

	/* max number of suggestions shown */
	@SuppressWarnings("unused")
	private static final int MAX_SHOWING = 10;
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response)
			throws ParseException, ServletException, IOException,
			NumberFormatException {
		PrintWriter out;
		try {
			response.setContentType("text/html");
			response.setHeader("Cache-control", "no-cache, no-store");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Expires", "-1");

			out = response.getWriter();

			String term = request.getParameter("term");
			//System.out.println(term);
			
			JSONArray array = getProperArray(term);
			
			out.println(array.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

	public SearchAutocomplete() {
		super();
	}
	
	private static JSONArray getProperArray(String term) {
		JSONArray array = new JSONArray();
		ArrayList<String> m1 = new ArrayList<String>();
		/*
		int count = 0;
		
		// TODO: sort movies
		
		for (Movie m : movies) {
			if ((m.getTitle().toLowerCase().startsWith(term) ||
					m.getTitle().toLowerCase().startsWith("the " + term)) &&
					m.getStreamingUrl() != null) {
				m1.add(m.getPair());
				++count;
				if (count >= MAX_SHOWING) break;
			}
		}
		*/
		
		m1.add("hello");
		m1.add("world");
		
		// sort lists in alphabetical order
		if (!m1.isEmpty()) Collections.sort(m1);
		
		// add titles to JSONArray
		for (String s : m1) {
			array.add(s);
		}
		
		System.out.println(array.toString());
		return array;
	}
}
