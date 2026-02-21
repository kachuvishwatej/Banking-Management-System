package com.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/banking_system?useSSL=false&serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASSWORD = "Vishwa@12";

    private static Connection connection = null;

    // Get database connection
    public static Connection getConnection() {

        try {
            if (connection == null || connection.isClosed()) {

                Class.forName("com.mysql.cj.jdbc.Driver");

                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

                System.out.println("MySQL Database connection established successfully");

                createTables();
            }

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();

        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
        }

        return connection;
    }

    // Create tables if they don't exist
    private static void createTables() {

        try (Statement stmt = connection.createStatement()) {

            // Create Accounts table
            String createAccountTable =
                    "CREATE TABLE IF NOT EXISTS accounts (" +
                    "account_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "account_number VARCHAR(20) UNIQUE NOT NULL, " +
                    "account_holder_name VARCHAR(100) NOT NULL, " +
                    "account_type VARCHAR(20) NOT NULL, " +
                    "balance DECIMAL(15,2) DEFAULT 0, " +
                    "email VARCHAR(100), " +
                    "phone VARCHAR(15), " +
                    "address TEXT, " +
                    "pin VARCHAR(4) NOT NULL, " +
                    "created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "is_active BOOLEAN DEFAULT TRUE" +
                    ")";

            stmt.execute(createAccountTable);

            // Create Transactions table
            String createTransactionsTable =
                    "CREATE TABLE IF NOT EXISTS transactions (" +
                    "transaction_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "account_number VARCHAR(20) NOT NULL, " +
                    "transaction_type VARCHAR(20) NOT NULL, " +
                    "amount DECIMAL(15,2) NOT NULL, " +
                    "balance_after DECIMAL(15,2) NOT NULL, " +
                    "description TEXT, " +
                    "recipient_account VARCHAR(20), " +
                    "transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (account_number) REFERENCES accounts(account_number) " +
                    "ON DELETE CASCADE" +
                    ")";

            stmt.execute(createTransactionsTable);

            System.out.println("MySQL tables created/verified successfully");

        } catch (SQLException e) {
            System.err.println("Error creating tables");
            e.printStackTrace();
        }
    }

    // Close database connection
    public static void closeConnection() {

        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
