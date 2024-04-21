package com.github.fjbaldon.banksys.presentation.view;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.presentation.controller.AccountController;

import javax.swing.*;

public enum AccountDepositView {

    INSTANCE;
    private final ApplicationView applicationView = ApplicationView.INSTANCE;
    private final AccountController accountController = AccountController.INSTANCE;

    public void show(Account account) {
        applicationView.FRAME.setContentPane(panel);
        applicationView.FRAME.revalidate();
        initComponents(account);
    }

    AccountDepositView() {
        backButton.addActionListener(e -> {
            // todo
        });

        depositFundsButton.addActionListener(e -> {
            // todo
        });
    }

    private void initComponents(Account account) {
        accountNumberField.setText(account.getAccountNumber());
        accountNumberField.setEditable(false);
        balanceField.setText(account.getBalance().toString());
        balanceField.setEditable(false);
        amountField.setText("");
    }

    private JPanel panel;
    private JTextField accountNumberField;
    private JTextField balanceField;
    private JTextField amountField;
    private JButton depositFundsButton;
    private JButton backButton;
}
