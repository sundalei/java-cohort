package com.wileyedge.controller;

import java.util.List;

import org.springframework.stereotype.Component;

import com.wileyedge.dao.EmployeeDao;
import com.wileyedge.dao.MeetingDao;
import com.wileyedge.dao.RoomDao;
import com.wileyedge.entity.Meeting;
import com.wileyedge.view.MeetingView;

@Component
public class MeetingController {

    private final MeetingView view;
    private final EmployeeDao employeeDao;
    private final MeetingDao meetingDao;
    private final RoomDao roomDao;

    public MeetingController(MeetingView view,
            EmployeeDao employeeDao,
            MeetingDao meetingDao,
            RoomDao roomDao) {
        this.view = view;
        this.employeeDao = employeeDao;
        this.meetingDao = meetingDao;
        this.roomDao = roomDao;
    }

    public void run() {

        while (true) {
            view.displayProgramBanner();
            view.displayMenu();

            int choice = view.getMenuChoice(4);
            switch (choice) {
                case 1: // Meetings
                    handleMeetings();
                    break;
                case 2: // Rooms
                    // handleRooms();
                    break;
                case 3: // Employees
                    // handleEmployees();
                    break;
                case 4: // Exit
                    view.exit();
                    System.exit(0);
            }
        }
    }

    public void handleMeetings() {
        while (true) {
            view.displayMeetingBanner();
            view.displayMeetingMenu();

            int choice = view.getMenuChoice(5);

            switch (choice) {
                case 1: // List Meeting
                    listMeetings();
                    break;
                case 5: // Return to Main Menu
                    // view.returnToMainMenu();
                    return;
            }
        }
    }

    private void listMeetings() {
        view.listMeetingsBanner();
        List<Meeting> meetings = meetingDao.getAllMeetings();
        System.out.println("hello world");
    }
}
