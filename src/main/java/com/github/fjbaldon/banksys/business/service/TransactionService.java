package com.github.fjbaldon.banksys.business.service;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.business.model.Transaction;
import com.github.fjbaldon.banksys.business.service.exception.TransactionServiceException;
import com.github.fjbaldon.banksys.data.dao.AccountDAO;
import com.github.fjbaldon.banksys.data.dao.TransactionDAO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public enum TransactionService {

    INSTANCE;
    private final TransactionDAO transactionDAO = TransactionDAO.INSTANCE;
    private final AccountDAO accountDAO = AccountDAO.INSTANCE;

    public void createDeposit(Long accountId, BigDecimal amount, String description) throws TransactionServiceException {
        validateAccountExists(accountId);
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionServiceException("Deposit amount must be positive.");
        }

        Transaction transaction = new Transaction(
                null,
                Transaction.TransactionType.Deposit,
                amount,
                description,
                null,
                accountId,
                null,
                null,
                null
        );
        Optional<Account> account = accountDAO.getAccountById(accountId);
        if (account.isEmpty())
            throw new IllegalStateException();
        account.get().setBalance(account.get().getBalance().add(amount));

        accountDAO.updateAccount(account.get());
        transactionDAO.createTransaction(transaction);
    }

    public void createWithdrawal(Long accountId, BigDecimal amount, String description) throws TransactionServiceException {
        validateAccountExists(accountId);
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionServiceException("Withdrawal amount must be positive.");
        }

        Optional<Account> account = accountDAO.getAccountById(accountId);
        if (account.isEmpty())
            throw new TransactionServiceException("Account with ID " + accountId + " does not exist.");

        if (account.get().getBalance().compareTo(amount) < 0)
            throw new TransactionServiceException("Insufficient funds for withdrawal.");

        Transaction transaction = new Transaction(
                null,
                Transaction.TransactionType.Withdrawal,
                amount.negate(),
                description,
                null,
                accountId,
                null,
                null,
                null
        );
        account.get().setBalance(account.get().getBalance().subtract(amount));

        accountDAO.updateAccount(account.get());
        transactionDAO.createTransaction(transaction);
    }

    public void createTransfer(Long fromAccountId, Long toAccountId, BigDecimal amount, String description) throws TransactionServiceException {
        validateAccountExists(fromAccountId);
        validateAccountExists(toAccountId);
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new TransactionServiceException("Transfer amount must be positive.");

        Optional<Account> fromAccount = accountDAO.getAccountById(fromAccountId);
        if (fromAccount.isEmpty())
            throw new TransactionServiceException("Account with ID " + fromAccountId + " does not exist.");

        if (fromAccount.get().getBalance().compareTo(amount) < 0)
            throw new TransactionServiceException("Insufficient funds for transfer.");

        Transaction fromTransaction = new Transaction(
                null,
                Transaction.TransactionType.Transfer,
                amount.negate(),
                description,
                null,
                fromAccountId,
                null,
                toAccountId,
                null
        );
        Transaction toTransaction = new Transaction(
                null,
                Transaction.TransactionType.Transfer,
                amount,
                description,
                null,
                toAccountId,
                fromAccountId,
                null,
                null
        );

        Optional<Account> updatedFromAccount = accountDAO.getAccountById(fromAccountId);
        if (updatedFromAccount.isEmpty())
            throw new IllegalStateException();
        updatedFromAccount.get().setBalance(updatedFromAccount.get().getBalance().subtract(amount));

        Optional<Account> updatedToAccount = accountDAO.getAccountById(toAccountId);
        if (updatedToAccount.isEmpty())
            throw new IllegalStateException();
        updatedToAccount.get().setBalance(updatedToAccount.get().getBalance().add(amount));

        accountDAO.updateAccount(updatedFromAccount.get());
        accountDAO.updateAccount(updatedToAccount.get());
        transactionDAO.createTransaction(fromTransaction);
        transactionDAO.createTransaction(toTransaction);
    }

    public void createPayment(Long accountId, BigDecimal amount, String description) throws TransactionServiceException {
        throw new UnsupportedOperationException("Payment functionality not implemented yet.");
    }

    public void createFee(Long accountId, BigDecimal amount, String description) throws TransactionServiceException {
        validateAccountExists(accountId);
        if (amount.compareTo(BigDecimal.ZERO) >= 0)
            throw new TransactionServiceException("Fee amount must be negative.");

        Transaction transaction = new Transaction(
                null,
                Transaction.TransactionType.Fee,
                amount,
                description,
                null,
                accountId,
                null,
                null,
                null
        );
        transactionDAO.createTransaction(transaction);
    }

    private void validateAccountExists(Long accountId) throws TransactionServiceException {
        if (accountDAO.getAccountById(accountId).isEmpty())
            throw new TransactionServiceException("Account with ID " + accountId + " does not exist.");
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionDAO.getTransactionById(id);
    }

    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionDAO.getTransactionsByAccountId(accountId);
    }

    public List<Transaction> getTransactionsByFromAccountId(Long fromAccountId) {
        return transactionDAO.getTransactionsByFromAccountId(fromAccountId);
    }

    public List<Transaction> getTransactionsByToAccountId(Long toAccountId) {
        return transactionDAO.getTransactionsByToAccountId(toAccountId);
    }

    public void updateTransaction(Transaction transaction) {
        transactionDAO.updateTransaction(transaction);
    }

    public void deleteTransactionById(Long id) {
        transactionDAO.deleteTransactionById(id);
    }

    public void deleteTransactionsByAccountId(Long accountId) {
        transactionDAO.deleteTransactionsByAccountId(accountId);
    }
}