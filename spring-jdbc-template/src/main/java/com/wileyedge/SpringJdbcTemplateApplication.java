package com.wileyedge;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class SpringJdbcTemplateApplication {

	@Autowired
	private JdbcTemplate jdbc;

	public static void main(String[] args) {
		SpringApplication.run(SpringJdbcTemplateApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {

			Scanner scanner = new Scanner(System.in);

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
						// addItem();
						break;
					case "3":
						// updateItem();
						break;
					case "4":
						// removeItem();
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
		List<ToDo> toDos = jdbc.query("SELECT * FROM todo", (resultSet, index) -> {
			ToDo td = new ToDo();
			td.setId(resultSet.getInt("id"));
			td.setTodo(resultSet.getString("todo"));
			td.setNote(resultSet.getString("note"));
			td.setFinished(resultSet.getBoolean("finished"));
			return td;
		});

		toDos.forEach(todo -> System.out.printf("%s: %s -- %s -- %s\n",
				todo.getId(),
				todo.getTodo(),
				todo.getNote(),
				todo.isFinished()));
		System.out.println("");
	}

}
