package com.github.fjbaldon.banksys.business.service;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.data.dao.AccountDAO;

import java.math.BigDecimal;
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
                customerId
        );
        accountDAO.createAccount(account);
    }

    public Optional<Account> getAccountByNumber(String accountNumber) {
        return accountDAO.getAccountByNumber(accountNumber);
    }

    public void updateAccount(Account account) {
        accountDAO.updateAccount(account);
    }

    public void deleteAccount(Account account) {
        accountDAO.deleteAccount(account);
    }
}