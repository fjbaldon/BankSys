package com.github.fjbaldon.banksys.business.service;

import com.github.fjbaldon.banksys.business.model.Transaction;
import com.github.fjbaldon.banksys.data.dao.AccountDAO;
import com.github.fjbaldon.banksys.data.dao.TransactionDAO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Objects;

public final class TransactionService {

    public Transaction createTransaction(Transaction.TransactionType transactionType, BigDecimal amount,
                                         String description, Long accountId, Long fromAccountId, Long toAccountId) throws SQLException {
        Transaction transaction = new Transaction.Builder()
                .transactionType(transactionType)
                .amount(amount)
                .description(description)
                .accountId(accountId)
                .fromAccountId(fromAccountId)
                .toAccountId(toAccountId)
                .build();
        transactionDAO.createTransaction(transaction);
        return transaction;
    }

    public Transaction getTransactionById(Long transactionId) throws SQLException {
        Transaction transaction = transactionDAO.getTransactionById(transactionId);
        return transaction;
    }

    public void updateTransaction(Transaction transaction) throws SQLException {
        transactionDAO.updateTransaction(transaction);
    }

    public void deleteTransaction(Transaction transaction) throws SQLException {
        transactionDAO.deleteTransaction(transaction);
    }

    public TransactionService(TransactionDAO transactionDAO) {
        this.transactionDAO = Objects.requireNonNull(transactionDAO);
    }

    private final TransactionDAO transactionDAO;
}