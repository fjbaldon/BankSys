-- Database creation
CREATE DATABASE IF NOT EXISTS BankSys;  -- Replace with your desired database name
USE BankSys;

-- Customer table
CREATE TABLE Customer (
                          customer_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                          first_name VARCHAR(255) NOT NULL,
                          last_name VARCHAR(255) NOT NULL,
                          middle_initial CHAR(1) DEFAULT NULL,
                          date_of_birth DATE NOT NULL,
                          email VARCHAR(255) UNIQUE NOT NULL,
                          phone_number CHAR(15) UNIQUE,
                          address VARCHAR(255),
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                          updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Login table
CREATE TABLE Login (
                       login_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       password_hash CHAR(64) NOT NULL,  -- Secure hash of password
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       customer_id INT UNSIGNED UNIQUE NOT NULL,
                       FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

-- Account table
CREATE TABLE Account (
                         account_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                         account_number CHAR(15) UNIQUE NOT NULL,
                         account_type ENUM('checking', 'savings', 'loan') NOT NULL,
                         balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
                         interest_rate DECIMAL(5,2) DEFAULT NULL,
                         created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                         updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         customer_id INT UNSIGNED NOT NULL,
                         FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

-- Transaction table
CREATE TABLE Transaction (
                             transaction_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                             transaction_type ENUM('deposit', 'withdrawal', 'transfer', 'payment', 'fee') NOT NULL,
                             amount DECIMAL(15,2) NOT NULL,
                             description VARCHAR(255),
                             created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                             account_id INT UNSIGNED NOT NULL,
                             from_account_id INT UNSIGNED DEFAULT NULL,
                             to_account_id INT UNSIGNED DEFAULT NULL,
                             FOREIGN KEY (account_id) REFERENCES Account(account_id),
                             FOREIGN KEY (from_account_id) REFERENCES Account(account_id),
                             FOREIGN KEY (to_account_id) REFERENCES Account(account_id)
);

-- Grant permissions (adjust based on your needs)
GRANT ALL PRIVILEGES ON BankSys.* TO 'banksys'@'127.0.0.1' IDENTIFIED BY 'sysknab';  -- Replace with your credentials
FLUSH PRIVILEGES;

-- Sample data (optional)
-- You can add sample data after table creation using INSERT statements

ALTER TABLE Customer AUTO_INCREMENT = 1000;  -- Set auto increment starting value (optional)
ALTER TABLE Account AUTO_INCREMENT = 1000;  -- Set auto increment starting value (optional)
ALTER TABLE Transaction AUTO_INCREMENT = 1000;  -- Set auto increment starting value (optional)
ALTER TABLE Login AUTO_INCREMENT = 1000;  -- Set auto increment starting value (optional)