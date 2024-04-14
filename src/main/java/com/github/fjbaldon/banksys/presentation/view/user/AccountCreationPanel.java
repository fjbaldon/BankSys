package com.github.fjbaldon.banksys.presentation.view.user;

import com.github.fjbaldon.banksys.business.model.User;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Random;

public class AccountCreationPanel {
    public static AccountCreationPanel create() {
        return new AccountCreationPanel();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JPanel get() {
        if (user == null)
            throw new RuntimeException("FATAL_ERR");

        return panel;
    }

    public void update() {
        generateAccountNumber();
    }

    public String getAccountNumber() {
        return accountNumberField.getText();
    }

    public void generateAccountNumber() {
        var random = new Random();

        var accountNumber = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            int digit = random.nextInt(10);
            accountNumber.append(digit);
        }

        accountNumberField.setText(accountNumber.toString());
    }

    public void addGenerateButtonListener(ActionListener actionListener) {
        generateButton.addActionListener(actionListener);
    }

    public void addCreateAccountButtonListener(ActionListener actionListener) {
        createAccountButton.addActionListener(actionListener);
    }

    public void addBackToAccountsButtonListener(ActionListener actionListener) {
        backToAccountsButton.addActionListener(actionListener);
    }

    public void displaySuccessMessage(String message) {
        JOptionPane.showMessageDialog(panel, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private User user;
    private JPanel panel;
    private JTextField accountNumberField;
    private JButton generateButton;
    private JButton createAccountButton;
    private JButton backToAccountsButton;

    private AccountCreationPanel() {
    }
}
