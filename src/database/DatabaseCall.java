package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public class DatabaseCall {

	public static ResultSet getAutocomplete(String query) throws SQLException {
		return Database.executeQuery(String.format("SELECT term, termFrequency "
				+ "FROM icsdump.termidtoterm NATURAL JOIN icsdump.termidtotermfrequency "
				+ "WHERE term LIKE '%s%%' ORDER BY termFrequency DESC", query));
	}

	public static int getTermId(String term) throws SQLException {
		ResultSet rs = Database.executeQuery(String.format("SELECT termid "
				+ "FROM icsdump.termidtoterm WHERE term = '%s'", term));

		int termId = -1;
		while (rs.next()) {
			termId = rs.getInt(1);
			break;
		}

		System.out.println(termId);

		return termId;
	}

	public static Map<Integer, Integer> getTFIDF(String term) throws SQLException {
		Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
		int termId = getTermId(term);
		if (termId != -1) {
			int docId, value;
			
			ResultSet rs = Database.executeQuery(String.format("SELECT docId, value FROM icsdump.tfidf " + 
					"WHERE termid = '%d' ORDER BY value DESC", termId));
			
			while (rs.next()) {
				docId = rs.getInt(1);
				value = rs.getInt(2);
				map.put(docId, value);
			}
		}
		
		return map;
	}
	
	public static int getPageRank(int docId) throws SQLException {
		int score = -1;
		ResultSet rs = Database.executeQuery(String.format("SELECT value FROM icsdump.pagerank " + 
				"WHERE docid = '%d'", docId));
		
		while (rs.next()) {
			score = rs.getInt(1);
			break;
		}
		
		return score;
	}
}
