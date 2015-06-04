package handlers;

import java.io.BufferedReader;
import java.io.FileReader;
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
import javax.servlet.http.HttpSession;

import database.DatabaseCall;
import domain.SearchResult;

import mvcController.HttpRequestHandler;

public class DisplayPage implements HttpRequestHandler {

	/** max number of suggestions shown **/
	private static final int MAX_SHOWING = 10;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response)
			throws ParseException, ServletException, IOException, NumberFormatException {

		System.out.println("In DisplayPage.java");

		HttpSession session = request.getSession(true);
		// get list of search results (used to get url and title)
		if (session.getAttribute("searchList") == null) {
			session.setAttribute("searchList", getSearchResultList());
		}
		@SuppressWarnings("unchecked")
		List<SearchResult> searchList = (List<SearchResult>) session.getAttribute("searchList");
		System.out.println("searchList.size() = " + searchList.size());

		// list of results to display
		List<SearchResult> results = new ArrayList<SearchResult>();

		String initialQuery = request.getParameter("query").trim().toLowerCase();
		String[] querys = initialQuery.split(" ");

		Map<Integer, Double> scores = new TreeMap<Integer, Double>();
		Map<Integer, Integer> map;

		double score = -1;
		// for each query term, calculate tdidf
		for (String query : querys) {
			System.out.println(query);
			try {
				map = DatabaseCall.getTFIDF(query);
				System.out.println("tfidf map size = " + map.size());
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

		int docId;
		SearchResult sr;
		// for each doc, determine if any query term in url or title
		for (Entry<Integer, Double> entry : scores.entrySet()) {
			docId = entry.getKey();
			sr = searchList.get(docId);
			for (String query : querys) {
				if (sr.getTitle().contains(query)) {
					scores.put(docId, entry.getValue() * 1.3);
					System.out.println(docId);
				}
				if (sr.getUrl().contains(query)) {
					scores.put(docId, entry.getValue() * 1.3);
					System.out.println(docId);
				}
			}
		}

		// sort by highest scores first
		Map<Integer, Double> temp = sortByComparator(scores);

		int count = 0;
		// for the first MAX_SHOWING entries, calculate page rank
		for (Entry<Integer, Double> entry : temp.entrySet()) {
			docId = entry.getKey();
			try {
				score = DatabaseCall.getPageRank(docId);
			} catch (SQLException e) {
				//e.printStackTrace();
			}
			if (score != -1) {
				scores.put(docId, entry.getValue() * Math.log(score));
			}

			if (count++ > MAX_SHOWING) {
				break;
			}
		}
		scores = sortByComparator(scores);

		// get the scores title and url information
		count = 0;
		for (Entry<Integer, Double> entry : scores.entrySet()) {
			docId = entry.getKey();
			System.out.println(entry.getValue());
			sr = searchList.get(docId);
			results.add(new SearchResult(docId, sr.getTitle(), sr.getUrl()));

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

	/** get list of search results **/
	private static List<SearchResult> getSearchResultList() {
		BufferedReader br = null;
		List<SearchResult> list = new ArrayList<SearchResult>();
		try {
			String currentLine;

			br = new BufferedReader(new FileReader("urltitle.csv"));
			int docId = 0;
			String[] split;
			while ((currentLine = br.readLine()) != null) {
				split = currentLine.split(",");
				if (split.length == 1) {
					list.add(new SearchResult(docId, "", split[0]));
				} else {
					list.add(new SearchResult(docId, split[1], split[0]));
				}
				++docId;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}

	/** 
	 * sorts map by desc values
	 * SOURCE: http://www.mkyong.com/java/how-to-sort-a-map-in-java/ **/
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
