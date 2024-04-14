package com.github.fjbaldon.banksys.service;

import com.github.fjbaldon.banksys.model.Account;
import com.github.fjbaldon.banksys.model.Transaction;
import com.github.fjbaldon.banksys.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * The TransactionService class provides methods for retrieving a list of transactions
 * associated with a specific account. It communicates with the TransactionRepository to
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
    public static TransactionService create(TransactionRepository transactionRepository) {
        return new TransactionService(transactionRepository);
    }

    public List<Transaction> listTransactionsOf(Account account) {
        return (account == null) ? new ArrayList<>() : transactionRepository.getTransactionsOf(account);
    }

    private final TransactionRepository transactionRepository;

    private TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
