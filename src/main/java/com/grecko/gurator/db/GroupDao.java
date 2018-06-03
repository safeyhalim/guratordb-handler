package com.grecko.gurator.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDao extends BaseDao {
	private static final String GROUPS_RATED_FIVE_RESTAURANTS = "select group_id from guratorapp_grouprestaurantsurvey group by group_id having count(group_id) >= 5";
	private static final String GROUP_PARTICIPANTS = "select participant_id from guratorapp_groupparticipant where group_id = %d";

	public GroupDao(String databasePath) {
		super(databasePath);
	}

	/**
	 * Returns the groups that completed the rating of five restaurants and each member of the group rated all other members
	 * @throws SQLException
	 */
	public String getCompletedGroups() throws SQLException {
		ResultSet rs = DbConnector.execute(databasePath, GROUPS_RATED_FIVE_RESTAURANTS);
		List<Integer> groupIds = getGroupIds(rs);
		StringBuilder strBuilder = new StringBuilder();
		for (int groupId : groupIds) {
			List<Integer> participantIds = getGroupParticipants(groupId);
			UserSurveyDao userSurveyDao = new UserSurveyDao(databasePath);
			if (userSurveyDao.hasParticipantsRatedEachOther(participantIds)) {
				strBuilder.append("Group ID: ").append(groupId).append(" - Participants: {")
				.append(listAsCommaSeparatedStrings(participantIds)).append("}").append(System.getProperty("line.separator"));
			}
		}
		return strBuilder.toString();
	}
	
	private List<Integer> getGroupIds(ResultSet rs) throws SQLException {
		List<Integer> groupIds = new ArrayList<Integer>();
		while(rs.next()) {
			groupIds.add(rs.getInt("group_id"));
		}
		return groupIds;
	}
	
	/**
	 * Returns the list of participants in a certain group
	 * @param groupId group Id of the group for which we get the list of participants
	 * @throws SQLException
	 */
	public List<Integer> getGroupParticipants(int groupId) throws SQLException {
		ResultSet rs = DbConnector.execute(databasePath, String.format(GROUP_PARTICIPANTS, groupId));
		List<Integer> participantIds = new ArrayList<Integer>();
		while(rs.next()) {
			participantIds.add(rs.getInt("participant_id"));
		}
		return participantIds;
	}
}
