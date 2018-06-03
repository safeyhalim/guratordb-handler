package com.grecko.gurator.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserSurveyDao extends BaseDao {

	private static final String PARTICIPANTS_RATED_EACH_OTHER = "select from_participant_id, to_participant_id from guratorapp_usersurvey where from_participant_id in (%s) order by from_participant_id";
	
	public UserSurveyDao(String databasePath) {
		super(databasePath);
	}
	
	public boolean hasParticipantsRatedEachOther(List<Integer> participantIds) throws SQLException {
		ResultSet rs = DbConnector.execute(databasePath, String.format(PARTICIPANTS_RATED_EACH_OTHER, listAsCommaSeparatedStrings(participantIds)));
		List<Map<String, Object>> results = resultSetToList(rs);
		
		for (Map<String, Object> record : results) {
			int fromId = (Integer)record.get("from_participant_id");
			List<Integer> tos = createRatingList(fromId, results);
			if (participantIds.size() != tos.size() +1) {
				return false;
			}
			if (!participantIds.containsAll(tos)) {
				return false;
			}
		}
		return true;
	}
	
	private List<Integer> createRatingList(int fromId, List<Map<String, Object>> ratings) {
		List<Integer> results = new ArrayList<Integer>();
		for (Map<String, Object> record : ratings) {
			if ((Integer)record.get("from_participant_id") != fromId) {
				continue;
			}
			results.add((Integer)record.get("to_participant_id"));
		}
		return results;
	}
}
