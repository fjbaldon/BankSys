package com.github.fjbaldon.banksys.business.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Account class represents a bank account, containing information such as
 * account number, balance, owner, and a list of transactions. It provides methods
 * for deposit, withdrawal, and transfer operations, as well as accessors for
 * retrieving account details. The class follows the immutability principle
 * by providing read-only access to its internal state and using a builder-like
 * factory method for instantiation.
 *
 * The deposit, withdraw, and transfer methods enforce business logic and
 * transaction recording. They throw a RuntimeException with the message "FATAL_ERR"
 * if the provided parameters are invalid. The class also ensures that the
 * list of transactions is unmodifiable externally to maintain data integrity.
 *
 * @author Francis John Baldon
 * @version 1.0
 * @since December 2023
 */
public class Account {
    public static Account create(int number,
                                 BigDecimal balance,
                                 User owner) {
        return new Account(number, balance, owner);
    }

    public int getNumber() {
        return number;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public User getOwner() {
        return owner;
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = new ArrayList<>(transactions);
    }

    public void deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new RuntimeException("FATAL_ERR");

        balance = balance.add(amount);
        transactions.add(Transaction.create(Transaction.NEW_ID, LocalDateTime.now(), "Deposit", amount, this));
    }

    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0 || balance.compareTo(amount) < 0)
            throw new RuntimeException("FATAL_ERR");

        balance = balance.subtract(amount);
        transactions.add(Transaction.create(Transaction.NEW_ID, LocalDateTime.now(), "Withdrawal", amount.negate(), this));
    }

    public void transfer(Account destination, BigDecimal amount) {
        if (amount == null || destination == null || amount.compareTo(BigDecimal.ZERO) <= 0 || balance.compareTo(amount) < 0)
            throw new RuntimeException("FATAL_ERR");

        withdraw(amount);
        destination.deposit(amount);

        transactions.add(Transaction.create(Transaction.NEW_ID, LocalDateTime.now(), "Transfer", amount.negate(), this));
    }

    private final int number;
    private BigDecimal balance;
    private final User owner;
    private List<Transaction> transactions;

    private Account(int number,
                    BigDecimal balance,
                    User owner) {
        this.number = number;
        this.balance = balance;
        this.owner = owner;
        this.transactions = new ArrayList<>();
    }
}
