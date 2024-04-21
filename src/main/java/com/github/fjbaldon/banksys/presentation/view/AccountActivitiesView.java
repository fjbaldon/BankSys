package com.github.fjbaldon.banksys.presentation.view;

import com.github.fjbaldon.banksys.business.model.Account;

import javax.swing.*;

public enum AccountActivitiesView {

    INSTANCE;
    private final ApplicationView applicationView = ApplicationView.INSTANCE;

    public void show(Account account) {
        applicationView.FRAME.setContentPane(panel);
        applicationView.FRAME.revalidate();
        initComponents(account);
    }

    AccountActivitiesView() {
        backButton.addActionListener(e -> {
            // todo
        });

        makeADepositButton.addActionListener(e -> {
            // todo
        });

        makeAWithdrawalButton.addActionListener(e -> {
            // todo
        });

        transferFundsButton.addActionListener(e -> {
            // todo
        });

        transactionHistoryButton.addActionListener(e -> {
            // todo
        });

        manageAccountButton.addActionListener(e -> {
            // todo
        });
    }

    private void initComponents(Account account) {
        accountNumberField.setText(account.getAccountNumber());
        accountNumberField.setEditable(false);
        balanceField.setText(account.getBalance().toString());
        balanceField.setEditable(false);
    }

    private JPanel panel;
    private JTextField accountNumberField;
    private JTextField balanceField;
    private JButton backButton;
    private JButton makeADepositButton;
    private JButton makeAWithdrawalButton;
    private JButton transferFundsButton;
    private JButton transactionHistoryButton;
    private JButton manageAccountButton;
}
