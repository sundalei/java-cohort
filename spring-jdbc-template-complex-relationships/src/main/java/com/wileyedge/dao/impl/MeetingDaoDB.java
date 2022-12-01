package com.wileyedge.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wileyedge.dao.MeetingDao;
import com.wileyedge.entity.Meeting;
import com.wileyedge.entity.Room;

@Repository
public class MeetingDaoDB implements MeetingDao {

    @Override
    public List<Meeting> getAllMeetings() {
        return null;
    }

	@Override
	public List<Meeting> getMeetingForRoom(Room room) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
