package com.github.fjbaldon.banksys.data.dao;

import com.github.fjbaldon.banksys.business.model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class AccountDAO {

    public Account createAccount(Account account) throws SQLException {
        String sql = "INSERT INTO Account (account_number, account_type, balance, interest_rate, customer_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, account.accountNumber());
            stmt.setString(2, account.accountType().toString());
            stmt.setBigDecimal(3, account.balance());
            stmt.setBigDecimal(4, account.interestRate());  // Assuming interest_rate is BigDecimal
            stmt.setLong(5, account.customerId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                return new Account(
                        rs.getLong("account_id"),
                        account.accountNumber(),
                        account.accountType(),
                        account.balance(),
                        account.interestRate(),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        account.customerId());
            return null;
        }
    }

    public Account getAccountById(Long id) throws SQLException {
        String sql = "SELECT * FROM Account WHERE account_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return new Account(
                        rs.getLong("account_id"),
                        rs.getString("account_number"),
                        Account.AccountType.valueOf(rs.getString("account_type")),
                        rs.getBigDecimal("balance"),
                        rs.getBigDecimal("interest_rate"),  // Assuming interest_rate is BigDecimal
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getLong("customer_id"));
        }
        return null;
    }

    public List<Account> getAccounts() throws SQLException {
        String sql = "SELECT * FROM Account";
        List<Account> accounts = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
                        rs.getLong("customer_id")));
        }
        return accounts;
    }

    public void updateAccount(Account account) throws SQLException {
        String sql = "UPDATE Account SET account_number = ?, account_type = ?, balance = ?, interest_rate = ?, customer_id = ? WHERE account_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, account.accountNumber());
            stmt.setString(2, account.accountType().toString());
            stmt.setBigDecimal(3, account.balance());
            stmt.setBigDecimal(4, account.interestRate());  // Assuming interest_rate is BigDecimal
            stmt.setLong(5, account.customerId());
            stmt.setLong(6, account.customerId());
            stmt.executeUpdate();
        }
    }

    public void deleteAccount(Account account) throws SQLException {
        String sql = "DELETE FROM Account WHERE account_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, account.accountId());
            stmt.executeUpdate();
        }
    }

    public AccountDAO(Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

    private final Connection connection;
}
