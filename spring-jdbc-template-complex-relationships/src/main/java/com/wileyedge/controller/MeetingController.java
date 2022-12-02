package com.wileyedge.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.wileyedge.dao.EmployeeDao;
import com.wileyedge.dao.MeetingDao;
import com.wileyedge.dao.RoomDao;
import com.wileyedge.entity.Employee;
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
					handleEmployees();
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
				case 2: // Add Meeting
					addMeeting();
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

	private void handleEmployees() {
		while (true) {
			view.displayEmployeesBanner();
			view.displayEmployeesMenu();

			int choice = view.getMenuChoice(7);

			switch (choice) {
				case 1: // List Employees
					listEmployees();
					break;
				case 2: // Add Employee
					addEmployee();
					break;
				case 3: // Update Employee
					updateEmployee();
					break;
				case 4: // Delete Employee
					deleteEmployee();
					break;
				case 5: // List Meetings for Employee
					listMeetingsForEmployee();
					break;
				case 6:
					addEmployeeToMeeting();
					break;
				case 7: // Return to Main Menu
					view.returnToMainMenu();
					return;

			}
		}
	}

	private void listMeetings() {
		view.listMeetingsBanner();
		List<Meeting> meetings = meetingDao.getAllMeetings();
		view.displayMeetings(meetings);
	}

	private void addMeeting() {
		view.addMeetingBanner();
		String name = view.getMeetingName();
		LocalDateTime time = view.getMeetingDateTime();
		List<Room> rooms = roomDao.getAllRooms();
		view.displayRooms(rooms);
		int id = view.getMeetingRoomId();
		Room room = roomDao.getRoomById(id);
		Meeting meeting = new Meeting();
		meeting.setName(name);
		meeting.setRoom(room);
		meeting.setTime(time);
		meetingDao.addMeeting(meeting);
		view.addMeetingSuccess();
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

	private void listEmployees() {
		view.listEmployeesBanner();
		List<Employee> employees = employeeDao.getAllEmployees();
		view.listEmployees(employees);
	}

	private void addEmployee() {
		view.addEmployeeBanner();
		String firstName = view.getEmployeeFirstName();
		String lastName = view.getEmployeeLastName();

		Employee employee = new Employee();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee = employeeDao.addEmployee(employee);
		view.addEmployeeSuccess();
	}

	private void updateEmployee() {
		view.updateEmployeeBanner();
		int id = view.getEmployeeId();
		Employee employee = employeeDao.getEmployeeById(id);
		if (employee != null) {
			view.displayUpdateInstructions();
			String firstName = view.updateField("First Name", employee.getFirstName());
			String lastName = view.updateField("Last Name", employee.getLastName());
			employee.setFirstName(firstName);
			employee.setLastName(lastName);
			employeeDao.updateEmployee(employee);
			view.updateEmployeeSuccess();
		} else {
			view.invalidEmployee();
		}
	}

	private void deleteEmployee() {
		view.deleteEmployeeBanner();
		int id = view.getEmployeeId();
		Employee employee = employeeDao.getEmployeeById(id);
		if (employee != null) {
			employeeDao.deleteEmployeeById(id);
			view.deleteEmployeeSuccess();
		} else {
			view.invalidEmployee();
		}
	}

	private void listMeetingsForEmployee() {
		view.listMeetingsForEmployeeBanner();
		int id = view.getEmployeeId();
		Employee employee = employeeDao.getEmployeeById(id);
		if (employee != null) {
			view.displayEmployee(employee);
			List<Meeting> meetings = meetingDao.getMeetingsForEmployee(employee);
			view.displayMeetings(meetings);
		} else {
			view.invalidEmployee();
		}
	}

	private void addEmployeeToMeeting() {
		view.addEmployeeToMeetingBanner();
		int id = view.getEmployeeId();
		Employee employee = employeeDao.getEmployeeById(id);
		if (employee != null) {
			List<Meeting> meetings = meetingDao.getAllMeetings();
			view.displayMeetings(meetings);
			int meetingId = view.getMeetingIdToJoin();
			Meeting meeting = meetingDao.getMeetingById(meetingId);
			if (!meeting.getAttendees().contains(employee)) {
				meeting.getAttendees().add(employee);
				meetingDao.updateMeeting(meeting);
			}
			view.addEmployeeToMeetingSuccess();
		} else {
			view.invalidEmployee();
		}
	}
}
