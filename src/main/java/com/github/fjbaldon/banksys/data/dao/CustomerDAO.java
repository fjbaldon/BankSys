package com.github.fjbaldon.banksys.data.dao;

import com.github.fjbaldon.banksys.business.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class CustomerDAO {

    public Customer createCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customer (first_name, last_name, middle_initial, date_of_birth, email, phone_number, address) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, customer.firstName());
            stmt.setString(2, customer.lastName());
            stmt.setString(3, customer.middleInitial());
            stmt.setDate(4, java.sql.Date.valueOf(customer.dateOfBirth()));  // Convert Java Date to SQL Date
            stmt.setString(5, customer.email());
            stmt.setString(6, customer.phoneNumber());
            stmt.setString(7, customer.address());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                return new Customer(
                        rs.getLong(1),
                        customer.firstName(),
                        customer.lastName(),
                        customer.middleInitial(),
                        customer.dateOfBirth(),
                        customer.email(),
                        customer.phoneNumber(),
                        customer.address(),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime());
            return null;
        }
    }

    public Customer getCustomerById(long id) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return new Customer(
                        rs.getLong("customer_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("middle_initial"),
                        rs.getDate("date_of_birth").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime());
        }
        return null;
    }

    public List<Customer> getCustomers() throws SQLException {
        String sql = "SELECT * FROM Customer";
        List<Customer> customers = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
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
                        rs.getTimestamp("updated_at").toLocalDateTime()));
            }
        }
        return customers;
    }

    public void updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE Customer SET first_name = ?, last_name = ?, middle_initial = ?, date_of_birth = ?, email = ?, phone_number = ?, address = ? WHERE customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, customer.firstName());
            stmt.setString(2, customer.lastName());
            stmt.setString(3, customer.middleInitial());
            stmt.setDate(4, java.sql.Date.valueOf(customer.dateOfBirth()));  // Convert Java Date to SQL Date
            stmt.setString(5, customer.email());
            stmt.setString(6, customer.phoneNumber());
            stmt.setString(7, customer.address());
            stmt.setLong(8, customer.customerId());
            stmt.executeUpdate();
        }
    }

    public void deleteCustomer(Customer customer) throws SQLException {
        String sql = "DELETE FROM Customer WHERE customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, customer.customerId());
            stmt.executeUpdate();
        }
    }

    public CustomerDAO(Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

    private final Connection connection;
}