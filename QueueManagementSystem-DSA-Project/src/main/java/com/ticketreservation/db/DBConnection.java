package com.ticketreservation.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // creating a connection instance
    private static Connection connection;
    
    // instrcuctions to set up database:
    // 1. Install MySQL server and MySQL Workbench
    // 2. Create a new database named "ticket_reservation"
    // 3. Create a new user with username "root" and password "Password" (or update the USERNAME and PASSWORD constants below to match your MySQL credentials)
    // 4. Grant all privileges to the new user for the "ticket_reservation" database
    // 5. Run the provided SQL script to create the "tickets" table and insert sample data

    // database connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/ticket_reservation?useSSL=false&serverTimezone=UTC"; // Update the URL if your MySQL server is running on a different host or port
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Password"; // Update the password if you set a different password for your MySQL user
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            // A call to forName("X") causes the class named X to be initialize
            Class.forName(DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // get database connection
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Database connected successfully");
            }
        } catch (SQLException e) {
            System.out.println("Database connected successfully");
            e.printStackTrace();
        }
        return connection;
    }

    // closing database connection
    public static void closeConnection() {
        try {
            if (connection != null || !connection.isClosed()) {
                connection.close();
                System.out.println("Database closed successfully");
            }
        } catch (SQLException e) {
            System.out.println("Database not closed successfully");
            e.printStackTrace();
        }
    }

    public static void testConnection() {
        try {
            if (connection != null || !connection.isClosed()) {
                connection.close();
                System.out.println("Database closed successfully");
            }
        } catch (SQLException e) {
            System.out.println("Database not closed successfully");
            e.printStackTrace();
        }
    }
}