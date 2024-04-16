package com.github.fjbaldon.banksys.presentation.view.user;

import com.github.fjbaldon.banksys.BankSys;
import com.github.fjbaldon.banksys.business.model.Customer;

/**
 * The UserView class provides methods for displaying different panels related to user interactions.
 * It acts as a controller for the UI, coordinating the display of panels for login, user creation,
 * account overview, and account creation.
 *
 * Note: Error handling is minimal in this example for brevity. In a real-world application,
 * robust error handling and logging should be implemented.
 *
 * @author Francis John Baldon
 * @version 1.0
 * @since December 2023
 */
public class UserView {
    public static UserView create(BankSys bankSys,
                                  LoginPanel loginPanel,
                                  CreationPanel creationPanel,
                                  AccountsPanel accountsPanel,
                                  AccountCreationPanel accountCreationPanel) {
        return new UserView(bankSys, loginPanel, creationPanel, accountsPanel, accountCreationPanel);
    }

    public LoginPanel getLogin() {
        return loginPanel;
    }

    public CreationPanel getCreation() {
        return creationPanel;
    }

    public AccountsPanel getAccounts() {
        return accountsPanel;
    }

    public AccountCreationPanel getAccountCreation() {
        return accountCreationPanel;
    }

    public void showLogin() {
        loginPanel.update();

        bankSys.setContentPane(loginPanel.get());
        bankSys.revalidate();
    }

    public void showCreation() {
        creationPanel.update();

        bankSys.setContentPane(creationPanel.get());
        bankSys.revalidate();
    }

    public void showAccounts(Customer customer) {
        accountsPanel.setUser(customer);
        accountsPanel.update();

        bankSys.setContentPane(accountsPanel.get());
        bankSys.revalidate();
    }

    public void showAccountCreation(Customer customer) {
        accountCreationPanel.setUser(customer);
        accountCreationPanel.update();

        bankSys.setContentPane(accountCreationPanel.get());
        bankSys.revalidate();
    }

    private final BankSys bankSys;
    private final LoginPanel loginPanel;
    private final CreationPanel creationPanel;
    private final AccountsPanel accountsPanel;
    private final AccountCreationPanel accountCreationPanel;

    private UserView(BankSys bankSys,
                     LoginPanel loginPanel,
                     CreationPanel creationPanel,
                     AccountsPanel accountsPanel,
                     AccountCreationPanel accountCreationPanel) {
        this.bankSys = bankSys;
        this.loginPanel = loginPanel;
        this.creationPanel = creationPanel;
        this.accountsPanel = accountsPanel;
        this.accountCreationPanel = accountCreationPanel;
    }
}
