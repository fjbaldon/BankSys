package com.github.fjbaldon.banksys.business.service;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.business.model.Transaction;
import com.github.fjbaldon.banksys.data.dao.TransactionDAO;

import java.math.BigDecimal;
import java.util.List;

public enum TransactionService {

    INSTANCE;
    private final TransactionDAO transactionDAO = TransactionDAO.INSTANCE;

    public void createTransaction(Transaction.TransactionType transactionType, BigDecimal amount,
                                  String description, Long accountId, Long fromAccountId, Long toAccountId) {
        Transaction transaction = new Transaction(
                null,
                transactionType,
                amount,
                description,
                null,
                accountId,
                fromAccountId,
                toAccountId
        );
        transactionDAO.createTransaction(transaction);
    }

    public List<Transaction> getTransactionByAccount(Account account) {
        return transactionDAO.getTransactionsByAccount(account);
    }

    public void updateTransaction(Transaction transaction) {
        transactionDAO.updateTransaction(transaction);
    }

    public void deleteTransaction(Transaction transaction) {
        transactionDAO.deleteTransaction(transaction);
    }
}