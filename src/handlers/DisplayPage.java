package handlers;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Util;

import database.DatabaseCall;
import domain.FileDumpObject;
import domain.SearchResult;

import mvcController.HttpRequestHandler;

public class DisplayPage implements HttpRequestHandler {

	/** max number of suggestions shown **/
	private static final int MAX_SHOWING = 10;
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response)
			throws ParseException, ServletException, IOException, NumberFormatException {

		System.out.println("In DisplayPage.java");

		List<SearchResult> results = new ArrayList<SearchResult>();

		String initialQuery = request.getParameter("query").trim().toLowerCase();
		String[] querys = initialQuery.split(" ");

		Map<Integer, Double> scores = new TreeMap<Integer, Double>();
		Map<Integer, Integer> map;

		double score = -1;
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

		Map<Integer, Double> temp = sortByComparator(scores);
		
		double highest = -1;
		int index = -1;
		int docId;
		int count = 0;
		for (Entry<Integer, Double> entry : temp.entrySet()) {
			docId = entry.getKey();
			try {
				score = DatabaseCall.getPageRank(docId);
			} catch (SQLException e) {
				//e.printStackTrace();
			}
			if (score != -1) {
				if (scores.containsKey(docId)) {
					scores.put(docId, scores.get(docId) * score);
				} else {
					scores.put(docId, score);
				}
			}

			if (scores.get(docId) > highest) {
				highest = scores.get(docId);
				index = docId;
			}
			//System.out.println(docId + " " + scores.get(docId));
			if (count++ > MAX_SHOWING) {
				break;
			}
		}

		System.out.println(index + " " + highest);

		scores = sortByComparator(scores);
		
		count = 0;
		FileDumpObject fdo;
		for (Entry<Integer, Double> entry : scores.entrySet()) {
			docId = entry.getKey();
			fdo = Util.fileToFDO(new File("FileDump/" + docId + ".txt"));
			System.out.println(fdo.getTitle() + " " + fdo.getUrl());
			results.add(new SearchResult(docId, fdo.getTitle(), fdo.getUrl()));
			if (count++ > MAX_SHOWING) {
				break;
			}
		}

		try {
			request.setAttribute("results", results);
			request.getRequestDispatcher("JSP/searchResults.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	};

	public DisplayPage() {
		super();
	}

	private static Map<Integer, Double> sortByComparator(Map<Integer, Double> unsortMap) {

		// Convert Map to List
		List<Map.Entry<Integer, Double>> list = new LinkedList<Map.Entry<Integer, Double>>(
				unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
			public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// Convert sorted map back to a Map
		Map<Integer, Double> sortedMap = new LinkedHashMap<Integer, Double>();
		for (Iterator<Map.Entry<Integer, Double>> it = list.iterator(); it.hasNext();) {
			Map.Entry<Integer, Double> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
}
