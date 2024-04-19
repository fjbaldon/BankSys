package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.business.service.AccountService;
import com.github.fjbaldon.banksys.business.service.CustomerService;
import com.github.fjbaldon.banksys.business.service.LoginService;
import com.github.fjbaldon.banksys.business.service.TransactionService;
import com.github.fjbaldon.banksys.data.connection.ConnectionManager;
import com.github.fjbaldon.banksys.data.dao.AccountDAO;
import com.github.fjbaldon.banksys.data.dao.CustomerDAO;
import com.github.fjbaldon.banksys.data.dao.LoginDAO;
import com.github.fjbaldon.banksys.data.dao.TransactionDAO;
import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Objects;

public final class BankSys extends JFrame {

    public LoginService getLoginService() {
        return loginService;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public AccountService accountService() {
        return accountService;
    }

    public TransactionService transactionService() {
        return transactionService;
    }

    public BankSys() {
        JPanel cards = new JPanel(new CardLayout());
        setContentPane(cards);
        setTitle("BankSys - Banking System");
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("./icon.png"))).getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(800, 600);
        setVisible(true);

        try {
            loginDAO = new LoginDAO(ConnectionManager.getConnection());
            customerDAO = new CustomerDAO(ConnectionManager.getConnection());
            accountDAO = new AccountDAO(ConnectionManager.getConnection());
            transactionDAO = new TransactionDAO(ConnectionManager.getConnection());

            loginService = new LoginService(loginDAO);
            customerService = new CustomerService(customerDAO);
            accountService = new AccountService(accountDAO);
            transactionService = new TransactionService(transactionDAO);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        LogIn logIn = new LogIn(this);
        LoginCreation loginCreation = new LoginCreation(this, logIn);
        CustomerCreation customerCreation = new CustomerCreation(this, loginCreation, logIn);
        Accounts accounts = new Accounts(this, logIn);
        CustomerManagement customerManagement = new CustomerManagement(this);
        LoginManagement loginManagement = new LoginManagement(this);
        AccountCreation accountCreation = new AccountCreation(this);
        AccountActivities accountActivities = new AccountActivities(this);
        AccountDeposit accountDeposit = new AccountDeposit(this);
        AccountWithdrawal accountWithdrawal = new AccountWithdrawal(this);
        AccountTransferral accountTransferral = new AccountTransferral(this);
        AccountHistory accountHistory = new AccountHistory(this);
        AccountManagement accountManagement = new AccountManagement(this);

        cards.add(logIn.panel, PanelNames.LOGIN);
        cards.add(loginCreation.panel, PanelNames.LOGIN_CREATION);
        cards.add(customerCreation.panel, PanelNames.CUSTOMER_CREATION);
        cards.add(accounts.panel, PanelNames.ACCOUNTS);
        cards.add(customerManagement.panel, PanelNames.CUSTOMER_MANAGEMENT);
        cards.add(loginManagement.panel, PanelNames.LOGIN_MANAGEMENT);
        cards.add(accountCreation.panel, PanelNames.ACCOUNT_CREATION);
        cards.add(accountActivities.panel, PanelNames.ACCOUNT_ACTIVITIES);
        cards.add(accountDeposit.panel, PanelNames.ACCOUNT_DEPOSIT);
        cards.add(accountWithdrawal.panel, PanelNames.ACCOUNT_WITHDRAWAL);
        cards.add(accountTransferral.panel, PanelNames.ACCOUNT_TRANSFERRAL);
        cards.add(accountHistory.panel, PanelNames.ACCOUNT_HISTORY);
        cards.add(accountManagement.panel, PanelNames.ACCOUNT_MANAGEMENT);

        ((CardLayout) cards.getLayout()).show(cards, "loginPanel");
    }

    private final LoginDAO loginDAO;
    private final CustomerDAO customerDAO;
    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;

    private final LoginService loginService;
    private final CustomerService customerService;
    private final AccountService accountService;
    private final TransactionService transactionService;
}