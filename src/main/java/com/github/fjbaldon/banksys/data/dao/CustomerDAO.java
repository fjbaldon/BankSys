package com.github.fjbaldon.banksys.data.dao;

import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.data.connection.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum CustomerDAO {

    INSTANCE;
    private final ConnectionManager connectionManager = ConnectionManager.INSTANCE;

    public void createCustomer(Customer customer) {
        String sql = "INSERT INTO Customer (first_name, last_name, middle_initial, date_of_birth, email, phone_number, address) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getMiddleInitial());
            stmt.setDate(4, java.sql.Date.valueOf(customer.getDateOfBirth()));  // Convert Java Date to SQL Date
            stmt.setString(5, customer.getEmail());
            stmt.setString(6, customer.getPhoneNumber());
            stmt.setString(7, customer.getAddress());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Customer> getCustomerById(Long id) {
        String sql = "SELECT * FROM Customer WHERE customer_id = ? AND is_deleted = FALSE";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return Optional.of(new Customer(
                        rs.getLong("customer_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("middle_initial"),
                        rs.getDate("date_of_birth").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getBoolean("is_deleted")
                ));
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        String sql = "SELECT * FROM Customer WHERE email = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return Optional.of(new Customer(
                        rs.getLong("customer_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("middle_initial"),
                        rs.getDate("date_of_birth").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getBoolean("is_deleted")
                ));
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Customer> getCustomerByPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM Customer WHERE phone_number = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, phoneNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return Optional.of(new Customer(
                        rs.getLong("customer_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("middle_initial"),
                        rs.getDate("date_of_birth").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getBoolean("is_deleted")
                ));
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Customer> getCustomerByAccountNumber(String accountNumber) {
        String sql = "SELECT c.* FROM Customer c " +
                "INNER JOIN Account a ON c.customer_id = a.customer_id " +
                "WHERE a.account_number = ? AND c.is_deleted = FALSE";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return Optional.of(new Customer(
                        rs.getLong("customer_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("middle_initial"),
                        rs.getDate("date_of_birth").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getBoolean("is_deleted")
                ));
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Customer> getCustomers() {
        String sql = "SELECT * FROM Customer WHERE is_deleted = FALSE";
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                customers.add(new Customer(
                        rs.getLong("customer_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("middle_initial"),
                        rs.getDate("date_of_birth").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getBoolean("is_deleted")
                ));
            return customers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCustomer(Customer customer) {
        String sql = "UPDATE Customer SET first_name = ?, last_name = ?, middle_initial = ?, date_of_birth = ?, email = ?, phone_number = ?, address = ? WHERE customer_id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getMiddleInitial());
            stmt.setDate(4, java.sql.Date.valueOf(customer.getDateOfBirth()));
            stmt.setString(5, customer.getEmail());
            stmt.setString(6, customer.getPhoneNumber());
            stmt.setString(7, customer.getAddress());
            stmt.setLong(8, customer.getCustomerId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCustomerById(Long id) {
        String sql = "UPDATE Customer SET is_deleted = TRUE WHERE customer_id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}