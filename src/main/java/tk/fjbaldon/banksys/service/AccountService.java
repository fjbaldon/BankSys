package tk.fjbaldon.banksys.service;

import tk.fjbaldon.banksys.model.Account;
import tk.fjbaldon.banksys.model.Transaction;
import tk.fjbaldon.banksys.model.User;
import tk.fjbaldon.banksys.repository.AccountRepository;
import tk.fjbaldon.banksys.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The AccountService class provides business logic for managing user accounts and performing
 * financial transactions. It communicates with the AccountRepository and TransactionRepository
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
    public static AccountService create(AccountRepository accountRepository,
                                        TransactionRepository transactionRepository) {
        return new AccountService(accountRepository, transactionRepository);
    }

    public List<Account> listAccountsOf(User user) {
        return (user == null) ? new ArrayList<>() : accountRepository.getAccountsOf(user);
    }

    public enum RegistrationStatus {
        OK,
        ACCOUNT_NUMBER_TAKEN,
        INVALID_ACCOUNT
    }

    public RegistrationStatus register(Account account) {
        if (account == null)
            return RegistrationStatus.INVALID_ACCOUNT;

        if (accountRepository.getAccountOf(account.getNumber()) != null)
            return RegistrationStatus.ACCOUNT_NUMBER_TAKEN;

        accountRepository.save(account);

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

        accountRepository.update(account);
        var transaction = account.getTransactions().get(account.getTransactions().size() - 1);
        transactionRepository.save(transaction);

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

        accountRepository.update(account);
        var transaction = account.getTransactions().get(account.getTransactions().size() - 1);
        transactionRepository.save(transaction);

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

        Account receiver = accountRepository.getAccountOf(receivingAccountNumber);
        if (receiver == null)
            return TransferalStatus.ACCOUNT_NOT_FOUND;

        if (sender.getNumber() == receiver.getNumber())
            return TransferalStatus.SIMILAR_ACCOUNTS;

        sender.transfer(receiver, amount);
        var transactionDebit = sender.getTransactions().get(sender.getTransactions().size() - 1);
        transactionRepository.save(transactionDebit);

        var transactionCredit = Transaction.create(Transaction.NEW_ID, LocalDateTime.now(), "Transfer", amount, receiver);
        transactionRepository.save(transactionCredit);

        accountRepository.update(sender);
        accountRepository.update(receiver);

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

        accountRepository.delete(account);

        return DeletionStatus.OK;
    }

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;


    private AccountService(AccountRepository accountRepository,
                           TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }
}
