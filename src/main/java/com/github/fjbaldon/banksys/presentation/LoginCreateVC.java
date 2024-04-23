package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.business.model.Login;
import com.github.fjbaldon.banksys.business.model.Model;
import com.github.fjbaldon.banksys.business.service.CustomerService;
import com.github.fjbaldon.banksys.business.service.LoginService;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.Optional;

public enum LoginCreateVC implements ApplicationPanel {

    INSTANCE;
    private final Application application = Application.INSTANCE;
    private final LoginService loginService = LoginService.INSTANCE;
    private final CustomerService customerService = CustomerService.INSTANCE;

    @Override
    public void show(JFrame frame, Model optional) {
        if (!(optional instanceof Customer))
            throw new IllegalArgumentException();

        frame.setContentPane(panel);
        frame.revalidate();
        prepare((Customer) optional);
    }

    LoginCreateVC() {
        showPasswordBox.setVerifyInputWhenFocusTarget(false);
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
            String un = usernameField.getText();
            String pw = Arrays.toString(passwordField.getPassword());
            String cpw = Arrays.toString(confirmPasswordField.getPassword());

            if (un.isBlank() || pw.isBlank() || cpw.isBlank()) {
                JOptionPane.showMessageDialog(panel, "Please fill up all the required fields.", "Missing Fields", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!cpw.equals(pw)) {
                JOptionPane.showMessageDialog(panel, "Passwords do not match. Please try again.", "Password Mismatch", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (customer == null)
                throw new NullPointerException();

            Optional<Login> loginModel = loginService.getLoginByUsername(un);
            if (loginModel.isPresent()) {
                JOptionPane.showMessageDialog(panel, "Username already exists. Please choose a different username and try again.", "Username Taken", JOptionPane.ERROR_MESSAGE);
                return;
            }

            customerService.createCustomer(
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getMiddleInitial(),
                    customer.getDateOfBirth(),
                    customer.getEmail(),
                    customer.getPhoneNumber(),
                    customer.getAddress()
            );

            Optional<Customer> customerModel = customerService.getCustomerByEmail(customer.getEmail());
            if (customerModel.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "This may indicate a problem in the system. Please contact customer support for assistance",
                        "Profile Creation Failure", JOptionPane.ERROR_MESSAGE);
                return;
            }

            customer = null;
            loginService.createLogin(un, pw, customerModel.get());
            JOptionPane.showMessageDialog(panel, "Signup successful! Welcome!", "Success", JOptionPane.INFORMATION_MESSAGE);
            application.showView(Application.ApplicationPanels.CUSTOMER_ACCOUNTS, customerModel.get());
        });

        cancelButton.setVerifyInputWhenFocusTarget(false);
        cancelButton.addActionListener(e -> application.showView(Application.ApplicationPanels.LOGIN, null));

        usernameField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                String username = textField.getText();

                if (username.isBlank()) {
                    JOptionPane.showMessageDialog(panel, "Please enter your Username.", "Empty Username", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                if (username.length() < 6) {
                    JOptionPane.showMessageDialog(panel, "Username must be at least 6 characters long.", "Invalid Username Length", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                return true;
            }
        });

        passwordField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JPasswordField passwordField = (JPasswordField) input;
                char[] password = passwordField.getPassword();

                if (password.length == 0) {
                    JOptionPane.showMessageDialog(panel, "Please enter your Password.", "Empty Password", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                if (password.length < 8) {
                    JOptionPane.showMessageDialog(panel, "Password must be at least 8 characters long.", "Invalid Password Length", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                return true;
            }
        });

        confirmPasswordField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JPasswordField confirmField = (JPasswordField) input;
                char[] confirmPassword = confirmField.getPassword();

                if (confirmPassword.length == 0) {
                    JOptionPane.showMessageDialog(panel, "Please confirm your Password.", "Empty Confirm Password", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                return true;
            }
        });
    }

    private void prepare(Customer customer) {
        this.customer = customer;

        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        showPasswordBox.setSelected(false);
    }

    private Customer customer;

    private JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JCheckBox showPasswordBox;
    private JButton signUpButton;
    private JButton cancelButton;
}
