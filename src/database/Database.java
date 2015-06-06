package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	private static final String DATABASE = "jdbc:mysql:///icsdump";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "Password1";

	protected static boolean isConnected;
	protected static Connection connection;

	/** connect to database with given username and password **/
	public static void connect() {
		if (!isConnected) {
			try {
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				connection = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			isConnected = true;
		}
	}

	/** close the database **/
	public static void close() {
		if (isConnected) {
			try {
				connection.close();
				isConnected = false;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/** executes select queries **/
	public static ResultSet executeQuery(String query) throws SQLException {
		Statement select = connection.createStatement();
		return select.executeQuery(query);
	}

	/** executes update/delete queries **/
	public static int executeUpdate(String query) throws SQLException {
		Statement select = connection.createStatement();
		return select.executeUpdate(query);
	}

	public static Connection getConnection() {
		return connection;
	}
}
