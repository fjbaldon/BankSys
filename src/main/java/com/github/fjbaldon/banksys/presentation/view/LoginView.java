package com.github.fjbaldon.banksys.presentation.view;

import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.presentation.controller.LoginController;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.Optional;

public enum LoginView {

    INSTANCE;
    private final ApplicationView applicationView = ApplicationView.INSTANCE;
    private final LoginController loginController = LoginController.INSTANCE;

    public void show() {
        applicationView.FRAME.setContentPane(panel);
        applicationView.FRAME.revalidate();
        initComponents();
    }

    LoginView() {
        showPasswordBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
                passwordField.setEchoChar((char) 0);
            else if (e.getStateChange() == ItemEvent.DESELECTED)
                passwordField.setEchoChar('*');
        });

        logInButton.addActionListener(e -> {
            String un = usernameField.getText();
            String pw = Arrays.toString(passwordField.getPassword());

            Optional<Customer> c = loginController.handleLoginAuth(un, pw);
            if (c.isEmpty())
                JOptionPane.showMessageDialog(panel, "Invalid username or password.", "Log In", JOptionPane.ERROR_MESSAGE);
            else
                applicationView.CUSTOMER.show(c.get());
        });

        createNewProfileButton.addActionListener(e -> {
            applicationView.CUSTOMER_CREATION.show();
        });
    }

    private void initComponents() {
        usernameField.setText("");
        passwordField.setText("");
        showPasswordBox.setSelected(false);
    }

    private JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordBox;
    private JButton logInButton;
    private JButton createNewProfileButton;
}
