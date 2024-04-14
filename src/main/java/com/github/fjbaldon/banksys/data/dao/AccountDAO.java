package com.github.fjbaldon.banksys.data.dao;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.business.model.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The AccountDAO class provides methods for interacting with the database
 * to perform CRUD (Create, Read, Update, Delete) operations related to bank accounts.
 * It communicates with the database to retrieve account information, save new accounts,
 * update existing accounts, and delete accounts. The class utilizes the UserDAO
 * to establish associations between accounts and their owners.
 *
 * Note: Error handling is minimal in this example for brevity. In a real-world
 * application, robust error handling and logging should be implemented.
 *
 * @author Francis John Baldon
 * @version 1.0
 * @since December 2023
 */
public class AccountDAO {
    public static AccountDAO create(UserDAO userDAO,
                                    Connection connection) {
        return new AccountDAO(userDAO, connection);
    }

    public List<Account> getAccountsOf(User owner) {
        var query = "SELECT * FROM accounts WHERE owner = ?";
        var accounts = new ArrayList<Account>();

        try (var statement = connection.prepareStatement(query)) {
            statement.setString(1, owner.getName());

            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int number = resultSet.getInt("number");
                    var balance = BigDecimal.valueOf(resultSet.getDouble("balance"));

                    var account = Account.create(number, balance, owner);
                    accounts.add(account);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return accounts;
    }

    public Account getAccountOf(int accountNumber) {
        var query = "SELECT * FROM accounts WHERE number = ?";
        try (var statement = connection.prepareStatement(query)) {
            statement.setInt(1, accountNumber);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int number = resultSet.getInt("number");
                    var balance = BigDecimal.valueOf(resultSet.getDouble("balance"));
                    var ownerString = resultSet.getString("owner");

                    var owner = userDAO.getUserOf(ownerString);

                    return Account.create(number, balance, owner);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public void save(Account account) {
        var query = "INSERT INTO accounts(number, balance, owner) VALUES (?, ?, ?)";
        try (var statement = connection.prepareStatement(query)) {
            statement.setInt(1, account.getNumber());
            statement.setBigDecimal(2, account.getBalance());
            statement.setString(3, account.getOwner().getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void update(Account account) {
        var updateQuery = "UPDATE accounts SET number = ?, balance = ?, owner = ? WHERE number = ?";
        try (var preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, account.getNumber());
            preparedStatement.setBigDecimal(2, account.getBalance());
            preparedStatement.setString(3, account.getOwner().getName());
            preparedStatement.setInt(4, account.getNumber());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void delete(Account account) {
        var query = "DELETE FROM accounts WHERE number = ?";
        try (var statement = connection.prepareStatement(query)) {
            statement.setInt(1, account.getNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private final UserDAO userDAO;
    private final Connection connection;

    private AccountDAO(UserDAO userDAO,
                       Connection connection) {
        this.userDAO = userDAO;
        this.connection = connection;
    }
}
