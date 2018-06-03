package com.grecko.gurator.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseDao {
	protected String databasePath;
	
	public BaseDao(String databasePath) {
		//TODO: Verify path
		this.databasePath = databasePath;
	}
	
	protected List<Map<String, Object>> resultSetToList(ResultSet rs) throws SQLException {
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		ResultSetMetaData metaData = rs.getMetaData();
		while (rs.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				map.put(metaData.getColumnName(i), rs.getObject(i));
			}
			results.add(map);
		}
		return results;
	}
	
	protected String listAsCommaSeparatedStrings(List<?> list) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(list.get(0));
		for (int i = 1; i < list.size(); i++){
			strBuilder.append(",");
			strBuilder.append(list.get(i).toString());
		}
		return strBuilder.toString();
	}
}
