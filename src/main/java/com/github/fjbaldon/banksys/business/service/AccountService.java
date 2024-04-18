package com.github.fjbaldon.banksys.business.service;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.data.dao.AccountDAO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Objects;

public final class AccountService {

    public Account createAccount(String accountNumber, Account.AccountType accountType, BigDecimal balance,
                                 BigDecimal interestRate, Long customerId) throws SQLException {
        Account account = new Account.Builder()
                .accountNumber(accountNumber)
                .accountType(accountType)
                .balance(balance)
                .interestRate(interestRate)
                .customerId(customerId)
                .build();
        accountDAO.createAccount(account);
        return account;
    }

    public Account getAccountById(Long accountId) throws SQLException {
        Account account = accountDAO.getAccountById(accountId);
        return account;
    }

    public void updateAccount(Account account) throws SQLException {
        accountDAO.updateAccount(account);
    }

    public void deleteAccount(Account account) throws SQLException {
        accountDAO.deleteAccount(account);
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = Objects.requireNonNull(accountDAO);
    }

    private final AccountDAO accountDAO;
}