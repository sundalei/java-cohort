package com.wileyedge.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Meeting {

    private int id;
    private String name;
    private LocalDateTime time;
    Room room;
    List<Employee> attendees = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Employee> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Employee> attendees) {
        this.attendees = attendees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return id == meeting.id && name.equals(meeting.name) && time.equals(meeting.time) && room.equals(meeting.room) && attendees.equals(meeting.attendees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, time, room, attendees);
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", room=" + room +
                ", attendees=" + attendees +
                '}';
    }
}
