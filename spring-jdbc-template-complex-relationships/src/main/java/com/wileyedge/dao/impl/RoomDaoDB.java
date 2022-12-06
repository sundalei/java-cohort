package com.wileyedge.dao.impl;

import com.wileyedge.entity.Room;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wileyedge.dao.RoomDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RoomDaoDB implements RoomDao {

    private final JdbcTemplate jdbcTemplate;

    public RoomDaoDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static final class RoomMapper implements RowMapper<Room> {

        @Override
        public Room mapRow(ResultSet rs, int rowNum) throws SQLException {

            Room room = new Room();
            room.setId(rs.getInt("id"));
            room.setName(rs.getString("name"));
            room.setDescription(rs.getString("description"));
            return room;
        }
    }

    @Override
    public List<Room> getAllRooms() {
        final String SELECT_ALL_ROOMS = "SELECT * FROM room";
        return jdbcTemplate.query(SELECT_ALL_ROOMS, new RoomMapper());
    }

    @Override
    public Room getRoomById(int id) {
        try {
            final String SELECT_ROOM_BY_ID = "SELECT * FROM room WHERE id = ?";
            return jdbcTemplate.queryForObject(SELECT_ROOM_BY_ID, new RoomMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public Room addRoom(Room room) {
        final String INSERT_ROOM = "INSERT INTO room(name,description) VALUES(?,?)";
        jdbcTemplate.update(INSERT_ROOM, room.getName(), room.getDescription());

        Integer newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        if (newId != null) {
            room.setId(newId);
        }
        return room;
    }

    @Override
    public void updateRoom(Room room) {
        final String UPDATE_ROOM = "UPDATE room SET name = ?, description = ? WHERE id = ?";
        jdbcTemplate.update(UPDATE_ROOM, room.getName(), room.getDescription(), room.getId());
    }

    @Override
    @Transactional
    public void deleteRoomById(int id) {
        final String DELETE_MEETING_EMPLOYEE_BY_ROOM = "DELETE me.* FROM meeting_employee me "
                + "JOIN meeting m ON me.meetingId = m.id WHERE m.roomId = ?";
        jdbcTemplate.update(DELETE_MEETING_EMPLOYEE_BY_ROOM, id);

        final String DELETE_MEETING_BY_ROOM = "DELETE FROM meeting WHERE roomId = ?";
        jdbcTemplate.update(DELETE_MEETING_BY_ROOM, id);

        final String DELETE_ROOM = "DELETE FROM room WHERE id = ?";
        jdbcTemplate.update(DELETE_ROOM, id);
    }
}
