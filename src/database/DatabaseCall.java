package database;

import java.sql.ResultSet;
import java.sql.SQLException;

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

		return termId;
	}
}
