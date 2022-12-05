package com.wileyedge.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wileyedge.dao.MeetingDao;
import com.wileyedge.dao.impl.EmployeeDaoDB.EmployeeMapper;
import com.wileyedge.dao.impl.RoomDaoDB.RoomMapper;
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
		final String SELECT_ALL_MEETINGS = "SELECT * FROM meeting";
		List<Meeting> meetings = jdbcTemplate.query(SELECT_ALL_MEETINGS, new MeetingMapper());

		addRoomAndEmployeesToMeetings(meetings);

		return meetings;
	}

	@Override
	public List<Meeting> getMeetingForRoom(Room room) {
		final String SELECT_MEETINGS_FOR_ROOM = "SELECT * FROM meeting WHERE roomId = ?";
		List<Meeting> meetings = jdbcTemplate.query(SELECT_MEETINGS_FOR_ROOM, new MeetingMapper(), room.getId());

		addRoomAndEmployeesToMeetings(meetings);

		return meetings;
	}

	@Override
	public List<Meeting> getMeetingsForEmployee(Employee employee) {
		final String SELECT_MEETINGS_FOR_EMPLOYEE = "SELECT * FROM meeting m "
				+ "JOIN meeting_employee me ON m.id = me.meetingId WHERE me.employeeId = ?";
		List<Meeting> meetings = jdbcTemplate.query(SELECT_MEETINGS_FOR_EMPLOYEE, new MeetingMapper(),
				employee.getId());

		addRoomAndEmployeesToMeetings(meetings);
		return meetings;
	}

	@Override
	public Meeting getMeetingById(int id) {
		try {

			final String SELECT_MEETING_BY_ID = "SELECT * FROM meeting WHERE id = ?";
			Meeting meeting = jdbcTemplate.queryForObject(SELECT_MEETING_BY_ID, new MeetingMapper(), id);
			if (meeting != null) {
				meeting.setRoom(getRoomForMeeting(meeting));
				meeting.setAttendees(getEmployeesForMeeting(meeting));
			}
			return meeting;
		} catch (DataAccessException ex) {
			return null;
		}
	}

	@Override
	@Transactional
	public Meeting addMeeting(Meeting meeting) {
		final String INSERT_MEETING = "INSERT INTO meeting(name, time, roomId) VALUES (?, ?, ?)";
		jdbcTemplate.update(INSERT_MEETING, meeting.getName(), Timestamp.valueOf(meeting.getTime()),
				meeting.getRoom().getId());
		Integer newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
		if (newId != null) {
			meeting.setId(newId);
		}

		insertMeetingEmployee(meeting);

		return meeting;
	}

	@Override
	@Transactional
	public void updateMeeting(Meeting meeting) {
		final String UPDATE_MEETING = "UPDATE meeting SET name = ?, time = ?, roomId = ? WHERE id = ?";
		jdbcTemplate.update(UPDATE_MEETING, meeting.getName(), Timestamp.valueOf(meeting.getTime()),
				meeting.getRoom().getId(), meeting.getId());

		final String DELETE_MEETING_EMPLOYEE = "DELETE FROM meeting_employee WHERE meetingId = ?";
		jdbcTemplate.update(DELETE_MEETING_EMPLOYEE, meeting.getId());
		insertMeetingEmployee(meeting);

	}

	private Room getRoomForMeeting(Meeting meeting) {
		final String SELECT_ROOM_FOR_MEETING = "SELECT r.* FROM room r "
				+ "JOIN meeting m ON r.id = m.roomId WHERE m.id = ?";
		return jdbcTemplate.queryForObject(SELECT_ROOM_FOR_MEETING, new RoomMapper(), meeting.getId());
	}

	private List<Employee> getEmployeesForMeeting(Meeting meeting) {
		final String SELECT_EMPLOYEES_FOR_MEETING = "SELECT e.* FROM employee e "
				+ "JOIN meeting_employee me ON e.id = me.employeeId WHERE me.meetingId = ?";
		return jdbcTemplate.query(SELECT_EMPLOYEES_FOR_MEETING, new EmployeeMapper(), meeting.getId());
	}

	private void addRoomAndEmployeesToMeetings(List<Meeting> meetings) {
		meetings.forEach(meeting -> {
			meeting.setRoom(getRoomForMeeting(meeting));
			meeting.setAttendees(getEmployeesForMeeting(meeting));
		});
	}

	private void insertMeetingEmployee(Meeting meeting) {
		final String INSERT_MEETING_EMPLOYEE = "INSERT INTO meeting_employee"
				+ "(meetingId, employeeId) VALUES (?, ?)";
		for (Employee employee : meeting.getAttendees()) {
			jdbcTemplate.update(INSERT_MEETING_EMPLOYEE, meeting.getId(), employee.getId());
		}
	}

	@Override
	public void deleteMeetingById(int id) {
		final String DELETE_MEETING_EMPLOYEE = "DELETE FROM meeting_employee WHERE meetingId = ?";
		jdbcTemplate.update(DELETE_MEETING_EMPLOYEE, id);

		final String DELETE_MEETING = "DELETE FROM meeting WHERE id = ?";
		jdbcTemplate.update(DELETE_MEETING, id);
	}

}
