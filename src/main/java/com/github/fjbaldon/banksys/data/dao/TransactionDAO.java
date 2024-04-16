package com.github.fjbaldon.banksys.data.dao;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.business.model.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class TransactionDAO {
    public Transaction createTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO Transaction (transaction_type, amount, description, account_id, from_account_id, to_account_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, transaction.transactionType().toString());
            stmt.setBigDecimal(2, transaction.amount());
            stmt.setString(3, transaction.description());
            stmt.setLong(4, transaction.accountId());
            stmt.setLong(5, transaction.fromAccountId());
            stmt.setLong(6, transaction.toAccountId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                return new Transaction(
                        rs.getLong("transaction_id"),
                        transaction.transactionType(),
                        transaction.amount(),
                        transaction.description(),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        transaction.accountId(),
                        transaction.fromAccountId(),
                        transaction.toAccountId());
            return null;
        }
    }

    public Transaction getTransactionById(int id) throws SQLException {
        String sql = "SELECT * FROM Transaction WHERE transaction_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
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
        try (Connection connection = getConnection();
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
        }
        return transactions;
    }

    private Connection getConnection() throws SQLException {
        return ConnectionManager.INSTANCE.getConnection();
    }
}
