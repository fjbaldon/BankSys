package com.github.fjbaldon.banksys.presentation.view;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.presentation.controller.AccountController;

import javax.swing.*;

public enum AccountManagementView {

    INSTANCE;
    private final ApplicationView applicationView = ApplicationView.INSTANCE;
    private final AccountController accountController = AccountController.INSTANCE;

    public void show(Account account) {
        applicationView.FRAME.setContentPane(panel);
        applicationView.FRAME.revalidate();
        initComponents(account);
    }

    AccountManagementView() {
        backButton.addActionListener(e -> {
            // todo
        });

        deleteAccountButton.addActionListener(e -> {
            // todo
        });
    }

    private void initComponents(Account account) {
        accountNumberField.setText(account.getAccountNumber());
        accountNumberField.setEditable(false);
        balanceField.setText(account.getBalance().toString());
        balanceField.setEditable(false);
        confirmationField.setText("");
    }

    private JPanel panel;
    private JTextField accountNumberField;
    private JTextField balanceField;
    private JTextField confirmationField;
    private JButton backButton;
    private JButton deleteAccountButton;
}
