package com.github.fjbaldon.banksys.presentation.view;

import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.presentation.controller.CustomerController;

import javax.swing.*;
import java.util.Objects;

public enum CustomerView {

    INSTANCE;
    private final ApplicationView applicationView = ApplicationView.INSTANCE;
    private final CustomerController customerController = CustomerController.INSTANCE;

    public void show(Customer customer) {
        Objects.requireNonNull(customer);

        applicationView.FRAME.setContentPane(panel);
        applicationView.FRAME.revalidate();
        initComponents(customer);
    }

    CustomerView() {
        manageProfileButton.addActionListener(e -> {
            // todo
        });
        
        logOutButton.addActionListener(e -> {
            // todo
        });
        
        openAccountButton.addActionListener(e -> {
            // todo
        });

        createNewAccountButton.addActionListener(e -> {
            // todo
        });

        accountsTable.getSelectionModel().addListSelectionListener(e -> {
            // todo
        });
    }

    private void initComponents(Customer customer) {
        customerField.setText(String.format("%s %s. %s", customer.getFirstName(), customer.getMiddleInitial(), customer.getLastName()));
        customerField.setEditable(false);
        accountField.setEditable(false);
    }

    private JPanel panel;
    private JButton manageProfileButton;
    private JButton logOutButton;
    private JButton openAccountButton;
    private JButton createNewAccountButton;
    private JTable accountsTable;
    private JTextField customerField;
    private JTextField accountField;
}
