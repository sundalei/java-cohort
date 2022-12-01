package com.wileyedge.view;

import org.springframework.stereotype.Component;

@Component
public class MeetingView {

    private final UserIO io;

    public MeetingView(UserIO io) {
        this.io = io;
    }

    public void printError(Exception ex) {
        io.print("ERROR: " + ex.getMessage());
        ex.printStackTrace();
    }
    
    public void displayProgramBanner() {
        io.print("Meeting Manager");
    }

    public void displayMenu() {
        io.print("1. Meetings");
        io.print("2. Rooms");
        io.print("3. Employees");
        io.print("4. Exit");
    }

    public int getMenuChoice(int maxChoice) {
        return io.readInt("Enter menu selection", 1, maxChoice);
    }

    public void exit() {
        io.print("Existing Meeting Manager");
    }

    public void displayMeetingBanner() {
        io.print("");
        io.print("Meeting Menu");
    }

    public void displayMeetingMenu() {
        io.print("1. List Meetings");
        io.print("2. Add Meeting");
        io.print("3. Update Meeting");
        io.print("4. Delete Meeting");
        io.print("5. Return to Main Menu");
    }

    public void listMeetingsBanner() {
        io.print("All Meetings");
    }
}
