package com.wileyedge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.TimeZone;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

public class ToDoListMain {

    private static Scanner scanner;
    private static DataSource dataSource;

    public static void main(String[] args) {

        scanner = new Scanner(System.in);

        try {
            dataSource = getDataSource();
        } catch (SQLException ex) {
            System.out.println("Error connecting to database");
            System.out.println(ex.getMessage());
            System.exit(0);
        }

        do {
            System.out.println("To-Do List");
            System.out.println("1. Display List");
            System.out.println("2. Add Item");
            System.out.println("3. Update Item");
            System.out.println("4. Remove Item");
            System.out.println("5. Exit");

            System.out.println("Enter an option:");
            String option = scanner.nextLine();

            try {

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
                        System.out.println("Existing");
                        System.exit(0);
                }
            } catch (SQLException ex) {
                System.out.println("Error communicating with database.");
                System.out.println(ex.getMessage());
                System.exit(0);
            }

        } while (true);
    }

    private static void displayList() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM todo");
            while (resultSet.next()) {
                System.out.printf("%s: %s -- %s -- %s\n",
                        resultSet.getString("id"),
                        resultSet.getString("todo"),
                        resultSet.getString("note"),
                        resultSet.getBoolean("finished"));
            }
            System.out.println("");
        }
    }

    private static void addItem() throws SQLException {
        System.out.println("Add Item");
        System.out.println("What is the task?");
        String task = scanner.nextLine();
        System.out.println("Any additional notes?");
        String note = scanner.nextLine();

        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO todo(todo, note) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, task);
            preparedStatement.setString(2, note);
            preparedStatement.executeUpdate();
            System.out.println("Add Complete");
        }
    }

    private static void updateItem() throws SQLException {
        System.out.println("Update Item");
        System.out.println("Which item do you want to update?");
        String itemId = scanner.nextLine();

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM todo WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, itemId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            ToDo toDo = new ToDo();
            toDo.setId(resultSet.getInt("id"));
            toDo.setTodo(resultSet.getString("todo"));
            toDo.setNote(resultSet.getString("note"));
            toDo.setFinished(resultSet.getBoolean("finished"));

            System.out.println("1. ToDo - " + toDo.getTodo());
            System.out.println("2. Note - " + toDo.getNote());
            System.out.println("3. Finished - " + toDo.isFinished());
            System.out.println("What would you like to change?");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Enter new ToDo:");
                    String todo = scanner.nextLine();
                    toDo.setTodo(todo);
                    break;
                case "2":
                    System.out.println("Enter new Note:");
                    String note = scanner.nextLine();
                    toDo.setNote(note);
                    break;
                case "3":
                    System.out.println("Toggling Finished to " + !toDo.isFinished());
                    toDo.setFinished(!toDo.isFinished());
                    break;
                default:
                    System.out.println("No change made");
                    return;
            }
            String updateSql = "UPDATE todo SET todo = ?, note = ?, finished = ? WHERE id = ?";
            PreparedStatement updatePStmt = connection.prepareCall(updateSql);
            updatePStmt.setString(1, toDo.getTodo());
            updatePStmt.setString(2, toDo.getNote());
            updatePStmt.setBoolean(3, toDo.isFinished());
            updatePStmt.setInt(4, toDo.getId());
            updatePStmt.executeUpdate();
            System.out.println("Update Complete");
        }
    }

    private static void removeItem() throws SQLException {
        System.out.println("Remove Item");
        System.out.println("Which item would you like to remove?");
        String itemId = scanner.nextLine();

        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM todo WHERE id = ?";
            PreparedStatement pStmt = conn.prepareCall(sql);
            pStmt.setString(1, itemId);
            pStmt.executeUpdate();
            System.out.println("Remove Complete");
        }
    }

    private static DataSource getDataSource() throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setDatabaseName("todoDB");
        dataSource.setUser("root");
        dataSource.setPassword("Fnst*1234");
        dataSource.setServerTimezone(TimeZone.getDefault().getID());
        dataSource.setUseSSL(false);
        dataSource.setAllowPublicKeyRetrieval(true);

        return dataSource;
    }
}
