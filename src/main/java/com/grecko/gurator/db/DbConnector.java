package com.grecko.gurator.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnector {
	private static Connection connection = null;
	private static final String DRIVER_BASE = "jdbc:sqlite:";
	private static final int FIVE_SECONDS = 5;
	
	private static void connect(String databasePath) throws SQLException {
		if (connection != null && connection.isValid(FIVE_SECONDS)) {
			return;
		}
		if (databasePath != null) {
			connection = DriverManager.getConnection(DRIVER_BASE + databasePath);
		}
	}
	
	public static ResultSet execute(String databasePath, String query) throws SQLException {
		connect(databasePath);
		Statement statement = connection.createStatement();  
        ResultSet resultSet = statement.executeQuery(query);  
        return resultSet;
	}
}
