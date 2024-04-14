package com.github.fjbaldon.banksys.presentation.view.user;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class CreationPanel {
    public static CreationPanel create() {
        return new CreationPanel();
    }

    public JPanel get() {
        return panel;
    }

    public void update() {
        usernameField.setText("");
        passwordField.setText("");
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return String.valueOf(passwordField.getPassword());
    }

    public void togglePasswordVisibility() {
        char currentEchoChar = passwordField.getEchoChar();

        if (currentEchoChar == 0)
            passwordField.setEchoChar('*');
        else
            passwordField.setEchoChar((char) 0);
    }

    public void addShowPasswordCheckBoxListener(ItemListener itemListener) {
        showPasswordCheckBox.addItemListener(itemListener);
    }

    public void addCreateUserButtonListener(ActionListener actionListener) {
        createUserButton.addActionListener(actionListener);
    }

    public void addBackToLogInButtonListener(ActionListener actionListener) {
        backToLoginButton.addActionListener(actionListener);
    }

    public void displaySuccessMessage(String message) {
        JOptionPane.showMessageDialog(panel, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckBox;
    private JButton createUserButton;
    private JButton backToLoginButton;

    private CreationPanel() {
    }
}
