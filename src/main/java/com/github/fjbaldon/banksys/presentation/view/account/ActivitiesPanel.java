package com.github.fjbaldon.banksys.presentation.view.account;

import com.github.fjbaldon.banksys.business.model.Account;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ActivitiesPanel {
    public static ActivitiesPanel create() {
        return new ActivitiesPanel();
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public JPanel get() {
        if (account == null)
            throw new RuntimeException("FATAL_ERR");

        return panel;
    }

    public void update() {
        accountNumberField.setText(String.valueOf(account.getNumber()));
        balanceField.setText(String.valueOf(account.getBalance()));
    }

    public void addBackToAccountsButtonListener(ActionListener actionListener) {
        backToAccountsButton.addActionListener(actionListener);
    }

    public void addDepositButtonListener(ActionListener actionListener) {
        depositButton.addActionListener(actionListener);
    }

    public void addWithdrawalButtonListener(ActionListener actionListener) {
        withdrawalButton.addActionListener(actionListener);
    }

    public void addTransferalButtonListener(ActionListener actionListener) {
        transferalButton.addActionListener(actionListener);
    }

    public void addHistoryButtonListener(ActionListener actionListener) {
        historyButton.addActionListener(actionListener);
    }

    public void addManageButtonListener(ActionListener actionListener) {
        manageButton.addActionListener(actionListener);
    }

    public void displaySuccessMessage(String message) {
        JOptionPane.showMessageDialog(panel, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private Account account;
    private JPanel panel;
    private JTextField accountNumberField;
    private JTextField balanceField;
    private JButton backToAccountsButton;
    private JButton depositButton;
    private JButton withdrawalButton;
    private JButton transferalButton;
    private JButton historyButton;
    private JButton manageButton;

    private ActivitiesPanel() {
    }
}
