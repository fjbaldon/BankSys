package com.github.fjbaldon.banksys.business.service;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.data.dao.AccountDAO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public enum AccountService {

    INSTANCE;
    private final AccountDAO accountDAO = AccountDAO.INSTANCE;

    public void createAccount(String accountNumber, Account.AccountType accountType, BigDecimal balance,
                              BigDecimal interestRate, Long customerId) {
        Account account = new Account(
                null,
                accountNumber,
                accountType,
                balance,
                interestRate,
                null,
                null,
                customerId,
                null
        );
        accountDAO.createAccount(account);
    }

    public Optional<Account> getAccountById(Long id) {
        return accountDAO.getAccountById(id);
    }

    public Optional<Account> getAccountByNumber(String accountNumber) {
        return accountDAO.getAccountByAccountNumber(accountNumber);
    }

    public List<Account> getAccountsByCustomerId(Long customerId) {
        return accountDAO.getAccountsByCustomerId(customerId);
    }

    public void updateAccount(Account account) {
        accountDAO.updateAccount(account);
    }

    public void deleteAccountById(Long id) {
        accountDAO.deleteAccountById(id);
    }

    public void deleteAccountsByCustomerId(Long customerId) {
        accountDAO.deleteAccountsByCustomerId(customerId);
    }
}