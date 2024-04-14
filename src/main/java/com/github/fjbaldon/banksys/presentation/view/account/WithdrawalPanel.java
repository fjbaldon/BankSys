package com.github.fjbaldon.banksys.presentation.view.account;

import com.github.fjbaldon.banksys.business.model.Account;

import javax.swing.*;
import java.awt.event.ActionListener;

public class WithdrawalPanel {
    public static WithdrawalPanel create() {
        return new WithdrawalPanel();
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
        amountField.setText("");
    }

    public String getAmount() {
        return amountField.getText();
    }

    public void addBackToActivitiesButtonListener(ActionListener actionListener) {
        backToActivitiesButton.addActionListener(actionListener);
    }

    public void addWithdrawFundsButtonListener(ActionListener actionListener) {
        withdrawFundsButton.addActionListener(actionListener);
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
    private JButton backToActivitiesButton;
    private JTextField balanceField;
    private JTextField amountField;
    private JButton withdrawFundsButton;

    private WithdrawalPanel() {
    }
}
