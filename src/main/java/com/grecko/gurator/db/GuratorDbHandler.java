package com.grecko.gurator.db;

import java.sql.SQLException;

public class GuratorDbHandler {
	public static void main(String[] args) throws SQLException {
		// TODO: Command line parser
		GroupDao groupDao = new GroupDao(args[0]);
		System.out.println(groupDao.getCompletedGroups());
	}
}
