package com.github.fjbaldon.banksys.presentation.view;

import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.presentation.controller.CustomerController;

import javax.swing.*;

public enum CustomerManagementView {

    INSTANCE;
    private final ApplicationView applicationView = ApplicationView.INSTANCE;
    private final CustomerController customerController = CustomerController.INSTANCE;

    public void show(Customer customer) {
        applicationView.FRAME.setContentPane(panel);
        applicationView.FRAME.revalidate();
        initComponents(customer);
    }

    CustomerManagementView() {
        changeCredentialsButton.addActionListener(e -> {
            // todo
        });

        saveNewProfileButton.addActionListener(e -> {
            // todo
        });

        restoreFieldsButton.addActionListener(e -> {
            // todo
        });

        cancelButton.addActionListener(e -> {
            // todo
        });
    }

    private void initComponents(Customer customer) {
        firstnameField.setText(customer.getFirstName());
        lastnameField.setText(customer.getLastName());
        middleInitialField.setText(customer.getMiddleInitial());
        dateOfBirthField.setText(customer.getDateOfBirth().toString());
        emailField.setText(customer.getEmail());
        phoneNumberField.setText(customer.getAddress());
        addressField.setText(customer.getAddress());
    }

    private JPanel panel;
    private JTextField firstnameField;
    private JTextField lastnameField;
    private JTextField middleInitialField;
    private JTextField dateOfBirthField;
    private JTextField emailField;
    private JTextField phoneNumberField;
    private JTextField addressField;
    private JButton saveNewProfileButton;
    private JButton cancelButton;
    private JButton restoreFieldsButton;
    private JButton changeCredentialsButton;
}
