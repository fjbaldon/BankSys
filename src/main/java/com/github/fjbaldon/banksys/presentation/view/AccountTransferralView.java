package com.github.fjbaldon.banksys.presentation.view;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.presentation.controller.AccountController;

import javax.swing.*;

public enum AccountTransferralView {

    INSTANCE;
    private final ApplicationView applicationView = ApplicationView.INSTANCE;
    private final AccountController accountController = AccountController.INSTANCE;

    public void show(Account account) {
        applicationView.FRAME.setContentPane(panel);
        applicationView.FRAME.revalidate();
        initComponents(account);
    }

    AccountTransferralView() {
        backButton.addActionListener(e -> {
            // todo
        });

        transferFundsButton.addActionListener(e -> {
            // todo
        });
    }

    private void initComponents(Account account) {
        accountNumberField.setText(account.getAccountNumber());
        accountNumberField.setEditable(false);
        balanceField.setText(account.getBalance().toString());
        balanceField.setEditable(false);
        amountField.setText("");
        receivingAccountNumberField.setText("");
    }

    private JPanel panel;
    private JTextField accountNumberField;
    private JTextField balanceField;
    private JTextField amountField;
    private JTextField receivingAccountNumberField;
    private JButton backButton;
    private JButton transferFundsButton;
}
