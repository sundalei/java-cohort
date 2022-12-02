package com.wileyedge.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.wileyedge.dao.MeetingDao;
import com.wileyedge.entity.Employee;
import com.wileyedge.entity.Meeting;
import com.wileyedge.entity.Room;

@Repository
public class MeetingDaoDB implements MeetingDao {

	private final JdbcTemplate jdbcTemplate;

	public MeetingDaoDB(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public static final class MeetingMapper implements RowMapper<Meeting> {

		@Override
		@Nullable
		public Meeting mapRow(ResultSet rs, int rowNum) throws SQLException {
			Meeting meeting = new Meeting();
			meeting.setId(rs.getInt("id"));
			meeting.setName(rs.getString("name"));
			meeting.setTime(rs.getTimestamp("time").toLocalDateTime());
			return meeting;
		}
		
	}

    @Override
    public List<Meeting> getAllMeetings() {
        return null;
    }

	@Override
	public List<Meeting> getMeetingForRoom(Room room) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meeting> getMeetingsForEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meeting getMeetingById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		
	}
    
}
