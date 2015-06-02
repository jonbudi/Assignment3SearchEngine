package handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Database;

import mvcController.HttpRequestHandler;
import net.sf.json.JSONArray;

public class SearchAutocomplete implements HttpRequestHandler {

	/** max number of suggestions shown **/
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
	
	/** get autocomplete suggestions array **/
	private static JSONArray getProperArray(String query) {
		JSONArray array = new JSONArray();
		List<String> list = new ArrayList<String>();
				
		Database.connect();
		ResultSet rs;
		int count = 0;
		
		String prefix = "";
		String fixedQuery = query;
		
		try {
			if (query.lastIndexOf(' ') != -1) {
				prefix = query.substring(0, query.lastIndexOf(' '));
				fixedQuery = query.substring(query.lastIndexOf(' ') + 1); 
			}
			fixedQuery = fixedQuery.trim();
			rs = Database.executeQuery("SELECT * FROM icsdump.termidtoterm WHERE term LIKE '" + fixedQuery + "%'");
			while (rs.next() && count++ < MAX_SHOWING) {
				list.add(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// sort lists in alphabetical order
		if (!list.isEmpty()) {
			Collections.sort(list);
		}
		
		// add titles to JSONArray
		for (String s : list) {
			array.add(prefix + " " + s);
		}
		
		//System.out.println(array.toString());
		return array;
	}
}
