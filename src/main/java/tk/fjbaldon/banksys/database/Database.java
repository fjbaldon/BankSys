package tk.fjbaldon.banksys.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The Database class provides a singleton instance representing the connection to the
 * SQLite database for the banking system. It encapsulates methods to retrieve the
 * database connection, close the connection, and initialize the necessary tables.
 * The class follows the Singleton pattern to ensure a single instance of the database
 * connection throughout the application.
 *
 * The database is initialized with three tables: 'users,' 'accounts,' and 'transactions.'
 * Each table is created if it does not exist, and the necessary foreign key relationships
 * are established between them. The database connection is established during the
 * initialization of the singleton instance.
 *
 * Note: Error handling is minimal in this example for brevity. In a real-world
 * application, robust error handling and logging should be implemented.
 *
 * @author Francis John Baldon
 * @version 1.0
 * @since December 2023
 */
public class Database {
    public static Database instance() {
        return INSTANCE;
    }

    private static final String DATABASE_URL = "jdbc:sqlite:src/main/resources/bank.db";
    private static final Database INSTANCE = new Database();

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private Connection connection;

    private Database() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        var createUsersQuery = "CREATE TABLE IF NOT EXISTS users ("
                + "name VARCHAR(255) PRIMARY KEY UNIQUE, "
                + "password CHAR(64) NOT NULL)";

        var createAccountsQuery = "CREATE TABLE IF NOT EXISTS accounts ("
                + "number INTEGER PRIMARY KEY UNIQUE, "
                + "balance DECIMAL(18, 2) NOT NULL, "
                + "owner VARCHAR(255) NOT NULL, "
                + "FOREIGN KEY (owner) REFERENCES users(name))";

        var createTransactionsQuery = "CREATE TABLE IF NOT EXISTS transactions ("
                + "id INTEGER PRIMARY KEY UNIQUE, "
                + "date TEXT NOT NULL, "
                + "description TEXT NOT NULL, "
                + "amount DECIMAL(18, 2) NOT NULL, "
                + "account_number INTEGER NOT NULL, "
                + "FOREIGN KEY (account_number) REFERENCES accounts(number))";

        try (var statement = connection.prepareStatement(createUsersQuery);
             var statement1 = connection.prepareStatement(createAccountsQuery);
             var statement2 = connection.prepareStatement(createTransactionsQuery)) {
            statement.execute();
            statement1.execute();
            statement2.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
