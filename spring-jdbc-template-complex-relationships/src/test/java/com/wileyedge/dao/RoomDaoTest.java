package com.wileyedge.dao;

import com.wileyedge.TestApplicationConfiguration;
import com.wileyedge.entity.Employee;
import com.wileyedge.entity.Meeting;
import com.wileyedge.entity.Room;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

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

    /**
     * Test of getAllRooms method, of class RoomDao.
     */
    @Test
    public void testGetAllRooms() {
    }

    /**
     * Test of getRoomById method, of class RoomDao.
     */
    @Test
    public void testGetRoomById() {
    }

    /**
     * Test of addRoom method, of class RoomDao.
     */
    @Test
    public void testAddRoom() {
    }

    /**
     * Test of updateRoom method, of class RoomDao.
     */
    @Test
    public void testUpdateRoom() {
    }

    /**
     * Test of deleteRoomById method, of class RoomDao.
     */
    @Test
    public void testDeleteRoomById() {
    }
}
