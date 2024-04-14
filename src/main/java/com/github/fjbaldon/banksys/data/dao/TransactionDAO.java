package com.github.fjbaldon.banksys.data.dao;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.business.model.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The TransactionDAO class provides methods for interacting with the database
 * to perform CRUD (Create, Read, Update, Delete) operations related to financial transactions.
 * It communicates with the database to retrieve transaction information, save new transactions,
 * and delete transactions. The class utilizes the AccountDAO to establish associations
 * between transactions and their associated accounts.
 *
 * Note: Error handling is minimal in this example for brevity. In a real-world application,
 * robust error handling and logging should be implemented. Additionally, methods for date
 * formatting and parsing have been added to handle the conversion between LocalDateTime and
 * the SQL TIMESTAMP data type.
 *
 * @author Francis John Baldon
 * @version 1.0
 * @since December 2023
 */
public class TransactionDAO {
    public static TransactionDAO create(AccountDAO accountDAO,
                                        Connection connection) {
        return new TransactionDAO(accountDAO, connection);
    }

    public List<Transaction> getTransactionsOf(Account account) {
        var query = "SELECT * FROM transactions WHERE account_number = ?";
        var transactions = new ArrayList<Transaction>();

        try (var statement = connection.prepareStatement(query)) {
            statement.setInt(1, account.getNumber());

            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    var date = parseDate(resultSet.getString("date"));
                    var description = resultSet.getString("description");
                    var amount = resultSet.getBigDecimal("amount");
                    int accountNumber = resultSet.getInt("account_number");

                    var transactionOwner = accountDAO.getAccountOf(accountNumber);

                    var transaction = Transaction.create(id, date, description, amount, transactionOwner);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return transactions;
    }

    public Transaction getTransactionBy(int transactionID) {
        var query = "SELECT * FROM transactions WHERE id = ?";
        try (var statement = connection.prepareStatement(query)) {
            statement.setInt(1, transactionID);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    var date = parseDate(resultSet.getString("date"));
                    var description = resultSet.getString("description");
                    var amount = resultSet.getBigDecimal("amount");
                    int accountNumber = resultSet.getInt("account_number");

                    var account = accountDAO.getAccountOf(accountNumber);

                    return Transaction.create(id, date, description, amount, account);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public void save(Transaction transaction) {
        var query = "INSERT INTO transactions(id, date, description, amount, account_number) VALUES (?, ?, ?, ?, ?)";
        try (var statement = connection.prepareStatement(query)) {
            statement.setInt(1, transaction.getID());
            statement.setString(2, formatDate(transaction.getDate()));
            statement.setString(3, transaction.getDescription());
            statement.setBigDecimal(4, transaction.getAmount());
            statement.setInt(5, transaction.getAccount().getNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void delete(Transaction transaction) {
        var query = "DELETE FROM transactions WHERE id = ?";
        try (var statement = connection.prepareStatement(query)) {
            statement.setInt(1, transaction.getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private final AccountDAO accountDAO;
    private final Connection connection;

    private String formatDate(LocalDateTime dateTime) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        return dateTime.format(formatter);
    }

    private LocalDateTime parseDate(String dateString) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        return LocalDateTime.parse(dateString, formatter);
    }

    private TransactionDAO(AccountDAO accountDAO,
                           Connection connection) {
        this.accountDAO = accountDAO;
        this.connection = connection;
    }
}
