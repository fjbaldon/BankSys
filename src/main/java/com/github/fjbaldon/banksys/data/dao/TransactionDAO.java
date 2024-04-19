package com.github.fjbaldon.banksys.data.dao;

import com.github.fjbaldon.banksys.business.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class TransactionDAO {

    public Transaction createTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO Transaction (transaction_type, amount, description, account_id, from_account_id, to_account_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, transaction.transactionType().toString());
            stmt.setBigDecimal(2, transaction.amount());
            stmt.setString(3, transaction.description());
            stmt.setLong(4, transaction.accountId());
            stmt.setLong(5, transaction.fromAccountId());
            stmt.setLong(6, transaction.toAccountId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                return getTransactionById(rs.getLong(1));
            return null;
        }
    }

    public Transaction getTransactionById(long id) throws SQLException {
        String sql = "SELECT * FROM Transaction WHERE transaction_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return new Transaction(
                        rs.getLong("transaction_id"),
                        Transaction.TransactionType.valueOf(rs.getString("transaction_type")),
                        rs.getBigDecimal("amount"),
                        rs.getString("description"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getLong("account_id"),
                        rs.getLong("from_account_id"),
                        rs.getLong("to_account_id"));
        }
        return null;
    }

    public List<Transaction> getTransactions() throws SQLException {
        String sql = "SELECT * FROM Transaction";
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
        }
        return transactions;
    }

    public void updateTransaction(Transaction transaction) throws SQLException {
        String sql = "UPDATE Transaction SET transaction_type = ?, amount = ?, description = ?, created_at = ?, account_id = ?, from_account_id = ?, to_account_id = ? WHERE transaction_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, transaction.transactionType().toString());
            stmt.setBigDecimal(2, transaction.amount());
            stmt.setString(3, transaction.description());
            stmt.setTimestamp(4, Timestamp.valueOf(transaction.createdAt()));
            stmt.setLong(5, transaction.accountId());
            stmt.setLong(6, transaction.fromAccountId());
            stmt.setLong(7, transaction.toAccountId());
            stmt.setLong(8, transaction.transactionId());
            stmt.executeUpdate();
        }
    }

    public void deleteTransaction(Transaction transaction) throws SQLException {
        String sql = "DELETE FROM Transaction WHERE transaction_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, transaction.transactionId());
            stmt.executeUpdate();
        }
    }

    public TransactionDAO(Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

    private final Connection connection;
}
