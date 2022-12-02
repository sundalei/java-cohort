package com.wileyedge.view;

import java.util.List;

import org.springframework.stereotype.Component;

import com.wileyedge.entity.Employee;
import com.wileyedge.entity.Meeting;
import com.wileyedge.entity.Room;

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

    public void displayRoomBanner() {
        io.print("");
        io.print("Room Menu");
    }

    public void displayRoomMenu() {
        io.print("1. List Rooms");
        io.print("2. Add Room");
        io.print("3. Update Room");
        io.print("4. Delete Room");
        io.print("5. View Meetings for Room");
        io.print("6. Return to Main Menu");
    }

    public void listRoomsBanner() {
        io.print("All Rooms");
    }
    
    public void displayRooms(List<Room> rooms) {
    	for (Room room : rooms) {
    		StringBuilder builder = new StringBuilder();
    		builder.append(room.getId());
    		builder.append(" -- ");
    		builder.append(room.getName());
    		builder.append(" -- ");
    		builder.append(room.getDescription());
    		io.print(builder.toString());
    	}
    	io.print("");
    }
    
    public void addRoomBanner() {
    	io.print("Adding Room");
    }
    
    public String getRoomName() {
    	return io.readString("Enter room name: ");
    }
    
    public String getRoomDescription() {
    	return io.readString("Enter room description");
    }
    
    public void addRoomSuccess() {
    	io.print("Room added successfully");
    }
    
    public void updateRoomBanner() {
    	io.print("Updating Room");
    }
    
    public int getRoomId() {
    	return io.readInt("Enter ID of room: ");
    }
    
    public void displayUpdateInstructions() {
    	io.print("Hit enter to keep original value.");
    }
    
    public String updateField(String fieldName, String original) {
    	String update = io.readString("Update " + fieldName + " (" + original + ") ");
    	if (update.trim().isEmpty()) {
    		return original;
    	}
    	return update;
    }
    
    public void updateRoomSuccess() {
    	io.print("Room updated successfully");
    }
    
    public void invalidRoom() {
    	io.print("No room with that ID");
    }
    
    public void deleteRoomBanner() {
    	io.print("Deleting Room");
    }
    
    public void deleteRoomSuccess() {
    	io.print("Room deleted successfully");
    }
    
    public void listMeetingsForRoomBanner() {
    	io.print("Listing meetings for room");
    }
    
    public void displayRoom(Room room) {
    	StringBuilder builder = new StringBuilder();
		builder.append(room.getId());
		builder.append(" -- ");
		builder.append(room.getName());
		builder.append(" -- ");
		builder.append(room.getDescription());
		io.print(builder.toString());
    }
    
    public void displayMeetings(List<Meeting> meetings) {
    	io.print("");
    	for (Meeting meeting: meetings) {
    		StringBuilder builder = new StringBuilder();
    		builder.append(meeting.getId());
    		builder.append(" -- ");
    		builder.append(meeting.getName());
    		builder.append(" -- ");
    		builder.append(meeting.getTime());
    		builder.append(" -- ");
    		builder.append(meeting.getRoom().getName());
    		builder.append(" -- ");
    		builder.append("# of Attendees: ");
    		builder.append(meeting.getAttendees().size());
    		io.print(builder.toString());
    	}
    }
    
    public void returnToMainMenu() {
    	io.print("Returning to Main Menu");
    	io.print("");
    }

    public void displayEmployeesBanner() {
        io.print("");
        io.print("Employee Menu");
    }

    public void displayEmployeesMenu() {
        io.print("1. List Employees");
        io.print("2. Add Employee");
        io.print("3. Update Employee");
        io.print("4. Delete Employee");
        io.print("5. View Meetings for Employee");
        io.print("6. Add Employee to Meeting");
        io.print("7. Return to Main Menu");
    }

    public void listEmployeesBanner() {
        io.print("All Employees");
    }

    public void listEmployees(List<Employee> employees) {
        for (Employee employee : employees) {
            StringBuilder builder = new StringBuilder();
            builder.append(employee.getId());
            builder.append(" -- ");
            builder.append(employee.getFirstName());
            builder.append(" -- ");
            builder.append(employee.getLastName());
            io.print(builder.toString());
        }
        io.print("");
    }

    public void addEmployeeBanner() {
        io.print("Adding Employee");
    }

    public String getEmployeeFirstName() {
        return io.readString("Enter employee first name: ");
    }

    public String getEmployeeLastName() {
        return io.readString("Enter employee last name: ");
    }

    public void addEmployeeSuccess() {
        io.print("Employee added successfully");
    }
}
