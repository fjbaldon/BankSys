package com.github.fjbaldon.banksys.data.dao;

import com.github.fjbaldon.banksys.business.model.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The UserDAO class provides methods for interacting with the database to perform
 * CRUD (Create, Read, Update, Delete) operations related to user accounts. It communicates
 * with the database to retrieve user information, save new users, and delete users.
 *
 * Note: Error handling is minimal in this example for brevity. In a real-world application,
 * robust error handling and logging should be implemented.
 *
 * @author Francis John Baldon
 * @version 1.0
 * @since December 2023
 */
public class UserDAO {
    public static UserDAO create(Connection connection) {
        return new UserDAO(connection);
    }

    public User getUserOf(String username) {
        var query = "SELECT * FROM users WHERE name = ?";
        try (var statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    var name = resultSet.getString("name");
                    var password = resultSet.getString("password");
                    return User.create(name, password);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public void save(User user) {
        var query = "INSERT INTO users(name, password) VALUES (?, ?)";
        try (var statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void delete(User user) {
        var query = "DELETE FROM users WHERE name = ?";
        try (var statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private final Connection connection;

    private UserDAO(Connection connection) {
        this.connection = connection;
    }
}