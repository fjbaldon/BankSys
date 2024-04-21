package com.github.fjbaldon.banksys.presentation.view;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.presentation.controller.AccountController;

import javax.swing.*;

public enum AccountCreationView {

    INSTANCE;
    private final ApplicationView applicationView = ApplicationView.INSTANCE;
    private final AccountController accountController = AccountController.INSTANCE;

    public void show(Account account) {
        applicationView.FRAME.setContentPane(panel);
        applicationView.FRAME.revalidate();
        initComponents();
    }

    AccountCreationView() {
        generateButton.addActionListener(e -> {
            // todo
        });

        createAccountButton.addActionListener(e -> {
            // todo
        });

        backButton.addActionListener(e -> {
            // todo
        });

        accountTypeBox.addItemListener(e -> {
            // todo
        });
    }

    private void initComponents() {
        accountNumberField.setText("");
        accountNumberField.setEditable(false);
        accountTypeBox.setEditable(false);
    }

    private JPanel panel;
    private JTextField accountNumberField;
    private JComboBox<String> accountTypeBox;
    private JButton generateButton;
    private JButton createAccountButton;
    private JButton backButton;
}
