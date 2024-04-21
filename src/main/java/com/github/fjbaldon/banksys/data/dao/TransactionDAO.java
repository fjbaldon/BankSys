package com.github.fjbaldon.banksys.data.dao;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.business.model.Transaction;
import com.github.fjbaldon.banksys.data.connection.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum TransactionDAO {

    INSTANCE;
    private final ConnectionManager connectionManager = ConnectionManager.INSTANCE;

    public void createTransaction(Transaction transaction) {
        String sql = "INSERT INTO Transaction (transaction_type, amount, description, account_id, from_account_id, to_account_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, transaction.getTransactionType().toString());
            stmt.setBigDecimal(2, transaction.getAmount());
            stmt.setString(3, transaction.getDescription());
            stmt.setLong(4, transaction.getAccountId());
            stmt.setLong(5, transaction.getFromAccountId());
            stmt.setLong(6, transaction.getToAccountId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Transaction> getTransactionById(long id) {
        String sql = "SELECT * FROM Transaction WHERE transaction_id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return Optional.of(new Transaction(
                        rs.getLong("transaction_id"),
                        Transaction.TransactionType.valueOf(rs.getString("transaction_type")),
                        rs.getBigDecimal("amount"),
                        rs.getString("description"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getLong("account_id"),
                        rs.getLong("from_account_id"),
                        rs.getLong("to_account_id")
                ));
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> getTransactionsByAccount(Account account) {
        String sql = "SELECT * FROM Transaction WHERE account_id = ?";
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                transactions.add(new Transaction(
                        rs.getLong("transaction_id"),
                        Transaction.TransactionType.valueOf(rs.getString("transaction_type")),
                        rs.getBigDecimal("amount"),
                        rs.getString("description"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getLong("account_id"),
                        rs.getLong("from_account_id"),
                        rs.getLong("to_account_id")
                ));
            return transactions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> getTransactions() {
        String sql = "SELECT * FROM Transaction";
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                transactions.add(new Transaction(
                        rs.getLong("transaction_id"),
                        Transaction.TransactionType.valueOf(rs.getString("transaction_type")),
                        rs.getBigDecimal("amount"),
                        rs.getString("description"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getLong("account_id"),
                        rs.getLong("from_account_id"),
                        rs.getLong("to_account_id")
                ));
            return transactions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTransaction(Transaction transaction) {
        String sql = "UPDATE Transaction SET transaction_type = ?, amount = ?, description = ?, created_at = ?, account_id = ?, from_account_id = ?, to_account_id = ? WHERE transaction_id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, transaction.getTransactionType().toString());
            stmt.setBigDecimal(2, transaction.getAmount());
            stmt.setString(3, transaction.getDescription());
            stmt.setTimestamp(4, Timestamp.valueOf(transaction.getCreatedAt()));
            stmt.setLong(5, transaction.getAccountId());
            stmt.setLong(6, transaction.getFromAccountId());
            stmt.setLong(7, transaction.getToAccountId());
            stmt.setLong(8, transaction.getTransactionId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTransaction(Transaction transaction) {
        String sql = "DELETE FROM Transaction WHERE transaction_id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, transaction.getTransactionId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
