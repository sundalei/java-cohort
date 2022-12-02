package com.wileyedge.dao;

import java.util.List;

import com.wileyedge.entity.Employee;
import com.wileyedge.entity.Meeting;
import com.wileyedge.entity.Room;

public interface MeetingDao {
    
    List<Meeting> getAllMeetings();
    
    List<Meeting> getMeetingForRoom(Room room);

    List<Meeting> getMeetingsForEmployee(Employee employee);

    Meeting getMeetingById(int id);

    void updateMeeting(Meeting meeting);
}
