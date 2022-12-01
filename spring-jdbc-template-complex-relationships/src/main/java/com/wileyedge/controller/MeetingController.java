package com.wileyedge.controller;

import java.util.List;

import org.springframework.stereotype.Component;

import com.wileyedge.dao.EmployeeDao;
import com.wileyedge.dao.MeetingDao;
import com.wileyedge.dao.RoomDao;
import com.wileyedge.entity.Meeting;
import com.wileyedge.entity.Room;
import com.wileyedge.view.MeetingView;

@Component
public class MeetingController {

	private final MeetingView view;
	private final EmployeeDao employeeDao;
	private final MeetingDao meetingDao;
	private final RoomDao roomDao;

	public MeetingController(MeetingView view, EmployeeDao employeeDao, MeetingDao meetingDao, RoomDao roomDao) {
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
				handleRooms();
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

	private void handleMeetings() {
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

	private void handleRooms() {
		while (true) {
			view.displayRoomBanner();
			view.displayRoomMenu();

			int choice = view.getMenuChoice(6);

			switch (choice) {
			case 1: // List Room
				listRooms();
				break;
			case 2: // Add Room
				addRoom();
				break;
			case 3: // Update Room
				updateRoom();
				break;
			case 4: // Delete Room
				deleteRoom();
				break;
			case 5: // List Meetings for Room
				listMeetingsForRoom();
				break;
			case 6: // Return to Main Menu
				view.returnToMainMenu();
				return;
			}
		}
	}

	private void listMeetings() {
		view.listMeetingsBanner();
		List<Meeting> meetings = meetingDao.getAllMeetings();
		System.out.println("hello world");
	}

	private void listRooms() {
		view.listRoomsBanner();
		List<Room> rooms = roomDao.getAllRooms();
		view.displayRooms(rooms);
	}
	
	private void addRoom() {
		view.addRoomBanner();
		String name = view.getRoomName();
		String description = view.getRoomDescription();
		Room room = new Room();
		room.setName(name);
		room.setDescription(description);
		room = roomDao.addRoom(room);
		view.addRoomSuccess();
	}
	
	private void updateRoom() {
		view.updateRoomBanner();
		int id = view.getRoomId();
		Room room = roomDao.getRoomById(id);
		if (room != null) {
			view.displayUpdateInstructions();
			String name = view.updateField("Name", room.getName());
			String description = view.updateField("Description", room.getDescription());
			room.setName(name);
			room.setDescription(description);
			roomDao.updateRoom(room);
			view.updateRoomSuccess();
		} else {
			view.invalidRoom();
		}
	}
	
	private void deleteRoom() {
		view.deleteRoomBanner();
		int id = view.getRoomId();
		Room room = roomDao.getRoomById(id);
		if (room != null) {
			roomDao.deleteRoomById(id);
			view.deleteRoomSuccess();
		} else {
			view.invalidRoom();
		}
	}
	
	private void listMeetingsForRoom() {
		view.listMeetingsForRoomBanner();
		int id = view.getRoomId();
		Room room = roomDao.getRoomById(id);
		if (room != null) {
			view.displayRoom(room);
			List<Meeting> meetings = meetingDao.getMeetingForRoom(room);
			view.displayMeetings(meetings);
		} else {
			view.invalidRoom();
		}
	}
}
