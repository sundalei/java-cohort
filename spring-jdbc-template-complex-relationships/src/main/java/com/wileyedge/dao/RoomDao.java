package com.wileyedge.dao;

import java.util.List;

import com.wileyedge.entity.Room;

public interface RoomDao {
    
    List<Room> getAllRooms();
    Room getRoomById(int id);
    Room addRoom(Room room);
    void updateRoom(Room room);
    void deleteRoomById(int id);
}
