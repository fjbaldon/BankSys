package com.github.fjbaldon.banksys.presentation.view;

import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.presentation.controller.CustomerController;
import com.github.fjbaldon.banksys.presentation.controller.LoginController;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.Objects;

public enum LoginCreationView {

    INSTANCE;
    private final ApplicationView applicationView = ApplicationView.INSTANCE;
    private final LoginController loginController = LoginController.INSTANCE;
    private final CustomerController customerController = CustomerController.INSTANCE;

    public void show(Customer customer) {
        Objects.requireNonNull(customer);

        applicationView.FRAME.setContentPane(panel);
        applicationView.FRAME.revalidate();
        initComponents(customer);
    }

    LoginCreationView() {
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
            // todo
            String un = usernameField.getText();
            String pw = Arrays.toString(passwordField.getPassword());
        });

        cancelButton.addActionListener(e -> {
            // todo
        });
    }

    private void initComponents(Customer customer) {
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        showPasswordBox.setSelected(false);
    }

    private JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JCheckBox showPasswordBox;
    private JButton signUpButton;
    private JButton cancelButton;
}
