package com.github.fjbaldon.banksys.data.dao;

import com.github.fjbaldon.banksys.business.model.Login;
import com.github.fjbaldon.banksys.data.connection.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum LoginDAO {

    INSTANCE;
    private final ConnectionManager connectionManager = ConnectionManager.INSTANCE;

    public void createLogin(Login login) {
        String sql = "INSERT INTO Login (username, password_hash, customer_id) VALUES (?, ?, ?)";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login.getUsername());
            stmt.setString(2, login.getPasswordHash());  // Assuming passwordHash is already securely hashed
            stmt.setLong(3, login.getCustomerId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Login> getLoginById(long id) {
        String sql = "SELECT * FROM Login WHERE login_id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return Optional.of(new Login(
                        rs.getLong("login_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getLong("customer_id")
                ));
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Login> getLoginByUsername(String username) {
        String sql = "SELECT * FROM Login WHERE username = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return Optional.of(new Login(
                        rs.getLong("login_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getLong("customer_id")
                ));
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Login> getLogins() {
        String sql = "SELECT * FROM Login";
        List<Login> logins = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                logins.add(new Login(
                        rs.getLong("login_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getLong("customer_id")
                ));
            return logins;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateLogin(Login login) {
        String sql = "UPDATE Login SET username = ?, password_hash = ?, customer_id = ? WHERE login_id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login.getUsername());
            stmt.setString(2, login.getPasswordHash());  // Assuming passwordHash is already securely hashed
            stmt.setLong(3, login.getCustomerId());
            stmt.setLong(4, login.getLoginId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteLogin(Login login) {
        String sql = "DELETE FROM Login WHERE login_id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, login.getLoginId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
