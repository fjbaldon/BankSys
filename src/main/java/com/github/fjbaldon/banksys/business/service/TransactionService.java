package com.github.fjbaldon.banksys.business.service;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.business.model.Transaction;
import com.github.fjbaldon.banksys.data.dao.TransactionDAO;

import java.util.ArrayList;
import java.util.List;

public final class TransactionService {
    public static TransactionService create(TransactionDAO transactionDAO) {
        return new TransactionService(transactionDAO);
    }

    public List<Transaction> listTransactionsOf(Account account) {
        return (account == null) ? new ArrayList<>() : transactionDAO.getTransactionsOf(account);
    }

    private final TransactionDAO transactionDAO;

    private TransactionService(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }
}
