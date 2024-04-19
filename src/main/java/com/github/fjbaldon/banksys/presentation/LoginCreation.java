package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

public class LoginCreation {

    public void setToBeCreated(Customer toBeCreated) {
        this.toBeCreated = Objects.requireNonNull(toBeCreated);
    }

    public LoginCreation(BankSys bankSys, LogIn logIn) {
        Objects.requireNonNull(bankSys);

        showPasswordBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                passwordField.setEchoChar((char) 0);
                confirmPasswordField.setEchoChar((char) 0);
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                passwordField.setEchoChar('*');
                confirmPasswordField.setEchoChar('*');
            }
        });

        signUpButton.addActionListener(e -> {
            // TODO
            try {
                String username = usernameField.getText();
                String password = Arrays.toString(passwordField.getPassword());
                String confirmPassword = Arrays.toString(confirmPasswordField.getPassword());

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(bankSys, "Passwords do not match.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (bankSys.getLoginService().usernameExists(username)) {
                    JOptionPane.showMessageDialog(bankSys, "Username already exists.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (toBeCreated == null) // should be set by CustomerCreation
                    throw new RuntimeException();

                Customer created = bankSys.getCustomerService().createCustomer(
                        toBeCreated.firstName(),
                        toBeCreated.lastName(),
                        toBeCreated.middleInitial(),
                        toBeCreated.dateOfBirth(),
                        toBeCreated.email(),
                        toBeCreated.phoneNumber(),
                        toBeCreated.address()
                );

                bankSys.getLoginService().createLogin(
                        username, password, created
                );

                JOptionPane.showMessageDialog(bankSys, "Profile successfully created.", "Sign Up Success", JOptionPane.INFORMATION_MESSAGE);
                JPanel cards = (JPanel) bankSys.getContentPane();
                ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNTS);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        cancelButton.addActionListener(e -> {
            logIn.clearLogInInfo();

            JPanel cards = (JPanel) bankSys.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.LOGIN);
        });
    }

    public JPanel panel;

    private Customer toBeCreated;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordBox;
    private JPasswordField confirmPasswordField;
    private JButton signUpButton;
    private JButton cancelButton;
}
