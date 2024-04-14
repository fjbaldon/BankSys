package com.github.fjbaldon.banksys.business.service;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.business.model.Transaction;
import com.github.fjbaldon.banksys.business.model.User;
import com.github.fjbaldon.banksys.data.dao.AccountDAO;
import com.github.fjbaldon.banksys.data.dao.TransactionDAO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The AccountService class provides business logic for managing user accounts and performing
 * financial transactions. It communicates with the AccountDAO and TransactionDAO
 * to perform operations such as listing accounts, registering new accounts, depositing funds,
 * withdrawing funds, transferring funds between accounts, and deleting accounts.
 *
 * Note: Error handling is minimal in this example for brevity. In a real-world application,
 * robust error handling and logging should be implemented. Additionally, methods for updating
 * transactions and handling account deletions have been added for completeness.
 *
 * @author Francis John Baldon
 * @version 1.0
 * @since December 2023
 */
public class AccountService {
    public static AccountService create(AccountDAO accountDAO,
                                        TransactionDAO transactionDAO) {
        return new AccountService(accountDAO, transactionDAO);
    }

    public List<Account> listAccountsOf(User user) {
        return (user == null) ? new ArrayList<>() : accountDAO.getAccountsOf(user);
    }

    public enum RegistrationStatus {
        OK,
        ACCOUNT_NUMBER_TAKEN,
        INVALID_ACCOUNT
    }

    public RegistrationStatus register(Account account) {
        if (account == null)
            return RegistrationStatus.INVALID_ACCOUNT;

        if (accountDAO.getAccountOf(account.getNumber()) != null)
            return RegistrationStatus.ACCOUNT_NUMBER_TAKEN;

        accountDAO.save(account);

        return RegistrationStatus.OK;
    }

    public enum DepositStatus {
        OK,
        INVALID_AMOUNT,
        INVALID_ARGUMENTS
    }

    public DepositStatus deposit(Account account, BigDecimal amount) {
        if (account == null || amount == null)
            return DepositStatus.INVALID_ARGUMENTS;

        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            return DepositStatus.INVALID_AMOUNT;

        account.deposit(amount);

        accountDAO.update(account);
        var transaction = account.getTransactions().get(account.getTransactions().size() - 1);
        transactionDAO.save(transaction);

        return DepositStatus.OK;
    }

    public enum WithdrawalStatus {
        OK,
        INVALID_AMOUNT,
        INSUFFICIENT_FUNDS,
        INVALID_ARGUMENTS
    }

    public WithdrawalStatus withdraw(Account account, BigDecimal amount) {
        if (account == null || amount == null)
            return WithdrawalStatus.INVALID_ARGUMENTS;

        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            return WithdrawalStatus.INVALID_AMOUNT;

        if (account.getBalance().compareTo(amount) < 0)
            return WithdrawalStatus.INSUFFICIENT_FUNDS;

        account.withdraw(amount);

        accountDAO.update(account);
        var transaction = account.getTransactions().get(account.getTransactions().size() - 1);
        transactionDAO.save(transaction);

        return WithdrawalStatus.OK;
    }

    public enum TransferalStatus {
        OK,
        SIMILAR_ACCOUNTS,
        ACCOUNT_NOT_FOUND,
        INVALID_AMOUNT,
        INSUFFICIENT_FUNDS,
        INVALID_ARGUMENTS
    }

    public TransferalStatus transfer(Account sender, int receivingAccountNumber, BigDecimal amount) {
        if (sender == null || amount == null)
            return TransferalStatus.INVALID_ARGUMENTS;

        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            return TransferalStatus.INVALID_AMOUNT;

        if (sender.getBalance().compareTo(amount) < 0)
            return TransferalStatus.INSUFFICIENT_FUNDS;

        Account receiver = accountDAO.getAccountOf(receivingAccountNumber);
        if (receiver == null)
            return TransferalStatus.ACCOUNT_NOT_FOUND;

        if (sender.getNumber() == receiver.getNumber())
            return TransferalStatus.SIMILAR_ACCOUNTS;

        sender.transfer(receiver, amount);
        var transactionDebit = sender.getTransactions().get(sender.getTransactions().size() - 1);
        transactionDAO.save(transactionDebit);

        var transactionCredit = Transaction.create(Transaction.NEW_ID, LocalDateTime.now(), "Transfer", amount, receiver);
        transactionDAO.save(transactionCredit);

        accountDAO.update(sender);
        accountDAO.update(receiver);

        return TransferalStatus.OK;
    }

    public enum DeletionStatus {
        OK,
        INVALID_ARGUMENTS
    }

    public DeletionStatus delete(Account account) {
        if (account == null) {
            return DeletionStatus.INVALID_ARGUMENTS;
        }

        accountDAO.delete(account);

        return DeletionStatus.OK;
    }

    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;


    private AccountService(AccountDAO accountDAO,
                           TransactionDAO transactionDAO) {
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
    }
}
