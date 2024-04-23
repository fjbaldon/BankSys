package com.github.fjbaldon.banksys.data.dao;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.data.connection.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum AccountDAO {

    INSTANCE;
    private final ConnectionManager connectionManager = ConnectionManager.INSTANCE;

    public void createAccount(Account account) {
        String sql = "INSERT INTO Account (account_number, account_type, balance, interest_rate, customer_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, account.getAccountNumber());
            stmt.setString(2, account.getAccountType().toString());
            stmt.setBigDecimal(3, account.getBalance());
            stmt.setBigDecimal(4, account.getInterestRate());
            stmt.setLong(5, account.getCustomerId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Account> getAccountById(Long id) {
        String sql = "SELECT * FROM Account WHERE account_id = ? AND is_deleted = FALSE";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return Optional.of(new Account(
                        rs.getLong("account_id"),
                        rs.getString("account_number"),
                        Account.AccountType.valueOf(rs.getString("account_type")),
                        rs.getBigDecimal("balance"),
                        rs.getBigDecimal("interest_rate"),  // Assuming interest_rate is BigDecimal
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getLong("customer_id"),
                        rs.getBoolean("is_deleted")
                ));
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Account> getAccountByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM Account WHERE account_number = ? AND is_deleted = FALSE";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return Optional.of(new Account(
                        rs.getLong("account_id"),
                        rs.getString("account_number"),
                        Account.AccountType.valueOf(rs.getString("account_type")),
                        rs.getBigDecimal("balance"),
                        rs.getBigDecimal("interest_rate"),  // Assuming interest_rate is BigDecimal
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getLong("customer_id"),
                        rs.getBoolean("is_deleted")
                ));
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Account> getAccountsByCustomerId(Long customerId) {
        String sql = "SELECT * FROM Account where customer_id = ? AND is_deleted = FALSE";
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                accounts.add(new Account(
                        rs.getLong("account_id"),
                        rs.getString("account_number"),
                        Account.AccountType.valueOf(rs.getString("account_type")),
                        rs.getBigDecimal("balance"),
                        rs.getBigDecimal("interest_rate"),  // Assuming interest_rate is BigDecimal
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getLong("customer_id"),
                        rs.getBoolean("is_deleted")
                ));
            return accounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Account> getAccounts() {
        String sql = "SELECT * FROM Account WHERE is_deleted = FALSE";
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                accounts.add(new Account(
                        rs.getLong("account_id"),
                        rs.getString("account_number"),
                        Account.AccountType.valueOf(rs.getString("account_type")),
                        rs.getBigDecimal("balance"),
                        rs.getBigDecimal("interest_rate"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getLong("customer_id"),
                        rs.getBoolean("is_deleted")
                ));
            return accounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAccount(Account account) {
        String sql = "UPDATE Account SET account_number = ?, account_type = ?, balance = ?, interest_rate = ?, customer_id = ? WHERE account_id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, account.getAccountNumber());
            stmt.setString(2, account.getAccountType().toString());
            stmt.setBigDecimal(3, account.getBalance());
            stmt.setBigDecimal(4, account.getInterestRate());  // Assuming interest_rate is BigDecimal
            stmt.setLong(5, account.getCustomerId());
            stmt.setLong(6, account.getAccountId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAccountById(Long id) {
        String sql = "UPDATE Account SET is_deleted = TRUE WHERE account_id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAccountsByCustomerId(Long customerId) {
        String sql = "UPDATE Account SET is_deleted = TRUE WHERE customer_id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, customerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
