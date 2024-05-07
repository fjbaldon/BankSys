CREATE DATABASE IF NOT EXISTS BankSys;
USE BankSys;

CREATE TABLE Customer
(
    customer_id    INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    first_name     VARCHAR(255)        NOT NULL,
    last_name      VARCHAR(255)        NOT NULL,
    middle_initial CHAR(1)  DEFAULT NULL,
    date_of_birth  DATE                NOT NULL,
    email          VARCHAR(255) UNIQUE NOT NULL,
    phone_number   CHAR(15) UNIQUE,
    address        VARCHAR(255),
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_deleted     BOOLEAN  DEFAULT FALSE
);

CREATE TABLE Login
(
    login_id      INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username      VARCHAR(255) UNIQUE NOT NULL,
    password_hash CHAR(64)            NOT NULL,
    created_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    customer_id   INT UNSIGNED UNIQUE NOT NULL,
    is_deleted    BOOLEAN  DEFAULT FALSE,
    FOREIGN KEY (customer_id) REFERENCES Customer (customer_id)
);

CREATE TABLE Account
(
    account_id     INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    account_number CHAR(15) UNIQUE                      NOT NULL,
    account_type   ENUM ('Checking', 'Savings', 'Loan') NOT NULL,
    balance        DECIMAL(15, 2)                       NOT NULL DEFAULT 0.00,
    interest_rate  DECIMAL(5, 2)                                 DEFAULT NULL,
    created_at     DATETIME                                      DEFAULT CURRENT_TIMESTAMP,
    updated_at     DATETIME                                      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    customer_id    INT UNSIGNED                         NOT NULL,
    is_deleted     BOOLEAN                                       DEFAULT FALSE,
    FOREIGN KEY (customer_id) REFERENCES Customer (customer_id)
);

CREATE TABLE Transaction
(
    transaction_id   INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    transaction_type ENUM ('Deposit', 'Withdrawal', 'Transfer', 'Payment', 'Fee') NOT NULL,
    amount           DECIMAL(15, 2)                                               NOT NULL,
    description      VARCHAR(255),
    created_at       DATETIME     DEFAULT CURRENT_TIMESTAMP,
    account_id       INT UNSIGNED                                                 NOT NULL,
    from_account_id  INT UNSIGNED DEFAULT NULL,
    to_account_id    INT UNSIGNED DEFAULT NULL,
    is_deleted       BOOLEAN      DEFAULT FALSE,
    FOREIGN KEY (account_id) REFERENCES Account (account_id),
    FOREIGN KEY (from_account_id) REFERENCES Account (account_id),
    FOREIGN KEY (to_account_id) REFERENCES Account (account_id)
);

GRANT ALL PRIVILEGES ON BankSys.* TO 'banksys'@'127.0.0.1' IDENTIFIED BY 'sysknab'; -- Replace with your credentials
FLUSH PRIVILEGES;

ALTER TABLE Customer
    AUTO_INCREMENT = 1000;
ALTER TABLE Account
    AUTO_INCREMENT = 1000;
ALTER TABLE Transaction
    AUTO_INCREMENT = 1000;
ALTER TABLE Login
    AUTO_INCREMENT = 1000;