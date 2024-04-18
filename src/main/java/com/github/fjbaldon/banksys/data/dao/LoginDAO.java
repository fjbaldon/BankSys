package com.github.fjbaldon.banksys.data.dao;

import com.github.fjbaldon.banksys.business.model.Login;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class LoginDAO {

    public Login createLogin(Login login) throws SQLException {
        String sql = "INSERT INTO Login (username, password_hash, customer_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, login.username());
            stmt.setString(2, login.passwordHash());  // Assuming passwordHash is already securely hashed
            stmt.setLong(3, login.customerId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                return new Login(
                        rs.getLong("login_id"),
                        login.username(),
                        login.passwordHash(),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        login.customerId());
            return null;
        }
    }

    public Login getLoginById(long id) throws SQLException {
        String sql = "SELECT * FROM Login WHERE login_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return new Login(
                        rs.getLong("login_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getLong("customer_id")
                );
        }
        return null;
    }

    public Login getLoginByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM Login WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return new Login(
                        rs.getLong("login_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getLong("customer_id"));
        }
        return null;
    }

    public List<Login> getLogins() throws SQLException {
        String sql = "SELECT * FROM Login";
        List<Login> logins = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
        }
        return logins;
    }

    public void updateLogin(Login login) throws SQLException {
        String sql = "UPDATE Login SET username = ?, password_hash = ?, customer_id = ? WHERE login_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login.username());
            stmt.setString(2, login.passwordHash());  // Assuming passwordHash is already securely hashed
            stmt.setLong(3, login.customerId());
            stmt.setLong(4, login.loginId());
            stmt.executeUpdate();
        }
    }

    public void deleteLogin(Login login) throws SQLException {
        String sql = "DELETE FROM Login WHERE login_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, login.loginId());
            stmt.executeUpdate();
        }
    }

    public LoginDAO(Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

    private final Connection connection;
}
