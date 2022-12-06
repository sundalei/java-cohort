package com.wileyedge.dao;

import com.wileyedge.TestApplicationConfiguration;
import com.wileyedge.entity.Employee;
import com.wileyedge.entity.Meeting;
import com.wileyedge.entity.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author leosun
 */
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class RoomDaoTest {

    private final RoomDao roomDao;
    private final EmployeeDao employeeDao;
    private final MeetingDao meetingDao;

    @Autowired
    // here Autowired annotation is required, refer to 
    // <a>https://stackoverflow.com/questions/66671846/why-does-springboottest-need-autowired-in-constructor-injection</a>
    public RoomDaoTest(RoomDao roomDao,
            EmployeeDao employeeDao,
            MeetingDao meetingDao) {
        this.roomDao = roomDao;
        this.employeeDao = employeeDao;
        this.meetingDao = meetingDao;
    }

    @BeforeEach
    public void setUp() {
        List<Room> rooms = roomDao.getAllRooms();
        for (Room room : rooms) {
            roomDao.deleteRoomById(room.getId());
        }

        List<Employee> employees = employeeDao.getAllEmployees();
        for (Employee employee : employees) {
            employeeDao.deleteEmployeeById(employee.getId());
        }

        List<Meeting> meetings = meetingDao.getAllMeetings();
        for (Meeting meeting : meetings) {
            meetingDao.deleteMeetingById(meeting.getId());
        }
    }

    @Test
    public void testAddGetRoom() {
        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);

        Room fromDao = roomDao.getRoomById(room.getId());

        assertEquals(room, fromDao);
    }

    @Test
    public void testGetAllRooms() {
        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        roomDao.addRoom(room);

        Room room2 = new Room();
        room2.setName("Test Room 2");
        room2.setDescription("Test Room 2");
        roomDao.addRoom(room2);

        List<Room> rooms = roomDao.getAllRooms();

        assertEquals(2, rooms.size());
        assertTrue(rooms.contains(room));
        assertTrue(rooms.contains(room2));
    }

    @Test
    public void testUpdateRoom() {
        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);

        Room fromDao = roomDao.getRoomById(room.getId());

        assertEquals(room, fromDao);

        room.setName("Another Test Room");

        roomDao.updateRoom(room);

        assertNotEquals(room, fromDao);

        fromDao = roomDao.getRoomById(room.getId());

        assertEquals(room, fromDao);
    }

    @Test
    public void testDeleteRoomById() {
        Room room = new Room();
        room.setName("Test Room");
        room.setDescription("Test Room Description");
        room = roomDao.addRoom(room);

        Employee employee = new Employee();
        employee.setFirstName("Test First");
        employee.setLastName("Test Last");
        employee = employeeDao.addEmployee(employee);

        Meeting meeting = new Meeting();
        meeting.setName("Test Meeting");
        meeting.setTime(LocalDateTime.now());
        meeting.setRoom(room);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        meeting.setAttendees(employees);
        meetingDao.addMeeting(meeting);

        roomDao.deleteRoomById(room.getId());

        Room fromDao = roomDao.getRoomById(room.getId());
        assertNull(fromDao);
    }
}
