package com.github.fjbaldon.banksys.data.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum ConnectionManager {
    INSTANCE;

    public Connection getConnection() throws SQLException {
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed())
            connection.close();
    }

    private final Connection connection;

    ConnectionManager() {
        try {
            String url = "jdbc:mariadb://localhost:3306/BankSys";
            String username = "banksys";
            String password = "sysknab";

            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish database connection", e);
        }

        var createLoginTableQ = "CREATE TABLE IF NOT EXISTS Login ("
                + "login_id INT UNSIGNED AUTO_INCREMENT, "
                + "username VARCHAR(255) UNIQUE NOT NULL, "
                + "password_hash CHAR(64) NOT NULL, "
                + "created_at DATETIME DEFAULT CURRENT_TIMESTAMP, "
                + "updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, "
                + "customer_id INT UNSIGNED UNIQUE NOT NULL, "
                + "PRIMARY KEY (login_id), "
                + "FOREIGN KEY (customer_id) references Customer(customer_id)";

        var createCustomerTableQ = "CREATE TABLE IF NOT EXISTS Customer ("
                + "name VARCHAR(255) PRIMARY KEY UNIQUE, "
                + "password CHAR(64) NOT NULL)";

        var createAccountTableQ = "CREATE TABLE IF NOT EXISTS Account ("
                + "number INTEGER PRIMARY KEY UNIQUE, "
                + "balance DECIMAL(18, 2) NOT NULL, "
                + "owner VARCHAR(255) NOT NULL, "
                + "FOREIGN KEY (owner) REFERENCES users(name))";

        var createTransactionTableQ = "CREATE TABLE IF NOT EXISTS Transaction ("
                + "id INTEGER PRIMARY KEY UNIQUE, "
                + "date TEXT NOT NULL, "
                + "description TEXT NOT NULL, "
                + "amount DECIMAL(18, 2) NOT NULL, "
                + "account_number INTEGER NOT NULL, "
                + "FOREIGN KEY (account_number) REFERENCES accounts(number))";

        try (var statement =  connection.prepareStatement(createLoginTableQ);
             var statement1 = connection.prepareStatement(createCustomerTableQ);
             var statement2 = connection.prepareStatement(createAccountTableQ);
             var statement3 = connection.prepareStatement(createTransactionTableQ)) {
            statement.execute();
            statement1.execute();
            statement2.execute();
            statement3.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database tables", e);
        }
    }
}
