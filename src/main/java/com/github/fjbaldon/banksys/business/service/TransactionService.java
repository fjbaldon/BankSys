package com.github.fjbaldon.banksys.business.service;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.business.model.Transaction;
import com.github.fjbaldon.banksys.data.dao.TransactionDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * The TransactionService class provides methods for retrieving a list of transactions
 * associated with a specific account. It communicates with the TransactionDAO to
 * perform database queries related to transactions.
 *
 * Note: Error handling is minimal in this example for brevity. In a real-world application,
 * robust error handling and logging should be implemented.
 *
 * @author Francis John Baldon
 * @version 1.0
 * @since December 2023
 */
public class TransactionService {
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
