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

public final class BankSys {

    public BankSys() {
        frame.setTitle("BankSys - Banking System");
        frame.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("./icon.png"))).getImage());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(cards);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);
        frame.setVisible(true);

        cards.add(new CustomerLogin(frame).panel, PanelNames.CUSTOMER_LOGIN);
        cards.add(new CustomerCreation0(frame).panel, PanelNames.CUSTOMER_CREATION_0);
        cards.add(new CustomerCreation1(frame).panel, PanelNames.CUSTOMER_CREATION_1);
        cards.add(new CustomerAccounts(frame).panel, PanelNames.CUSTOMER_ACCOUNTS);
        cards.add(new CustomerManagement0(frame).panel, PanelNames.CUSTOMER_MANAGEMENT_0);
        cards.add(new CustomerManagement1(frame).panel, PanelNames.CUSTOMER_MANAGEMENT_1);
        cards.add(new AccountCreation(frame).panel, PanelNames.ACCOUNT_CREATION);
        cards.add(new AccountActivities(frame).panel, PanelNames.ACCOUNT_ACTIVITIES);
        cards.add(new AccountDeposit(frame).panel, PanelNames.ACCOUNT_DEPOSIT);
        cards.add(new AccountWithdrawal(frame).panel, PanelNames.ACCOUNT_WITHDRAWAL);
        cards.add(new AccountTransferral(frame).panel, PanelNames.ACCOUNT_TRANSFERRAL);
        cards.add(new AccountHistory(frame).panel, PanelNames.ACCOUNT_HISTORY);
        cards.add(new AccountManagement(frame).panel, PanelNames.ACCOUNT_MANAGEMENT);

        ((CardLayout) cards.getLayout()).show(cards, "loginPanel");

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
    }

    private final JFrame frame = new JFrame();
    private final JPanel cards = new JPanel(new CardLayout());

    private final LoginDAO loginDAO;
    private final CustomerDAO customerDAO;
    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;

    private final LoginService loginService;
    private final CustomerService customerService;
    private final AccountService accountService;
    private final TransactionService transactionService;
}