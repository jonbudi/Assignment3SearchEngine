package database;

public class SQLQueries {
	public static final String autocomplete = "SELECT term, termFrequency "
			+ "FROM icsdump.termidtoterm NATURAL JOIN icsdump.termidtotermfrequency "
			+ "WHERE term LIKE '%s%%' ORDER BY termFrequency DESC";
}
