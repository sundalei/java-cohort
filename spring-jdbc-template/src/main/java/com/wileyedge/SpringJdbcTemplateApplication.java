package com.wileyedge;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@SpringBootApplication
public class SpringJdbcTemplateApplication {

	@Autowired
	private JdbcTemplate jdbc;

	private final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		SpringApplication.run(SpringJdbcTemplateApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			do {
				System.out.println("To-Do List");
				System.out.println("1. Display List");
				System.out.println("2. Add Item");
				System.out.println("3. Update Item");
				System.out.println("4. Remove Item");
				System.out.println("5. Exit");

				System.out.println("Enter an option:");
				String option = scanner.nextLine();

				switch (option) {
					case "1":
						displayList();
						break;
					case "2":
						addItem();
						break;
					case "3":
						updateItem();
						break;
					case "4":
						removeItem();
						break;
					case "5":
						scanner.close();
						System.out.println("Existing");
						System.exit(0);
				}

			} while (true);
		};
	}

	private void displayList() {
		List<ToDo> toDos = jdbc.query("SELECT * FROM todo", new ToDoMapper());

		toDos.forEach(todo -> System.out.printf("%s: %s -- %s -- %s\n",
				todo.getId(),
				todo.getTodo(),
				todo.getNote(),
				todo.isFinished()));
		System.out.println("");
	}

	private void addItem() {
		System.out.println("Add Item");
		System.out.println("What is the task?");
		String task  = scanner.nextLine();
		System.out.println("Any additional notes?");
		String note = scanner.nextLine();

		jdbc.update("INSERT INTO todo(todo, note) VALUES (?, ?)", task, note);
		System.out.println("Add Complete");
	}

	private void updateItem() {
		System.out.println("Update Item");
		System.out.println("Which item do you want to update?");
		String itemId = scanner.nextLine();
		ToDo item = jdbc.queryForObject("SELECT * FROM todo WHERE id = ?", new ToDoMapper(), itemId);

		System.out.println("1. ToDo - " + item.getTodo());
		System.out.println("2. Note - " + item.getNote());
		System.out.println("3. Finished - " + item.isFinished());
		System.out.println("What would you like to change?");
		String choice = scanner.nextLine();
		switch (choice) {
			case "1":
				System.out.println("Enter new ToDo:");
				String todo = scanner.nextLine();
				item.setTodo(todo);
				break;
			case "2":
				System.out.println("Enter new Note:");
				String note = scanner.nextLine();
				item.setNote(note);
				break;
			case "3":
				System.out.println("Toggling Finished to " + !item.isFinished());
				item.setFinished(!item.isFinished());
				break;
			default:
				System.out.println("No change made");
				return;
		}

		jdbc.update("UPDATE todo SET todo = ?, note = ?, finished = ? WHERE id = ?",
				item.getTodo(),
				item.getNote(),
				item.isFinished(),
				item.getId());
		System.out.println("Update Complete");
	}

	private void removeItem() {
		System.out.println("Remove Item");
		System.out.println("Which item would you like to remove?");
		String itemId = scanner.nextLine();
		jdbc.update("DELETE FROM todo WHERE id = ?", itemId);
		System.out.println("Remove Complete");
	}

	private static final class ToDoMapper implements RowMapper<ToDo> {

		@Override
		public ToDo mapRow(ResultSet rs, int rowNum) throws SQLException {
			ToDo toDo = new ToDo();
			toDo.setId(rs.getInt("id"));
			toDo.setTodo(rs.getString("todo"));
			toDo.setNote(rs.getString("note"));
			toDo.setFinished(rs.getBoolean("finished"));
			return toDo;
		}
	}

}
