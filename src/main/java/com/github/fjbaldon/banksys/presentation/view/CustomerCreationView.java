package com.github.fjbaldon.banksys.presentation.view;

import com.github.fjbaldon.banksys.business.model.Customer;
import org.apache.commons.validator.routines.EmailValidator;

import javax.swing.*;
import java.time.LocalDate;

public enum CustomerCreationView {

    INSTANCE;
    private final ApplicationView applicationView = ApplicationView.INSTANCE;

    public void show() {
        applicationView.FRAME.setContentPane(panel);
        applicationView.FRAME.revalidate();
        initComponents();
    }

    CustomerCreationView() {
        nextButton.addActionListener(e -> {
            // todo
            String fn = firstnameField.getText();
            String ln = lastnameField.getText();
            String mi = middleInitialField.getText();
            String dob = dateOfBirthField.getText();
            String em = emailField.getText();
            String pn = phoneNumberField.getText();
            String a = addressField.getText();

            Customer c = new Customer(null, fn, ln, mi, LocalDate.parse(dob), em, pn, a, null, null);
        });

        cancelButton.addActionListener(e -> {
            // todo
        });
    }

    private void initComponents() {
        firstnameField.setText("");
        lastnameField.setText("");
        middleInitialField.setText("");
        dateOfBirthField.setText("");
        emailField.setText("");
        phoneNumberField.setText("");
        addressField.setText("");

        emailField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                String em = ((JTextField) input).getText();
                return (EmailValidator.getInstance().isValid(em));
            }
        });
    }

    private JPanel panel;
    private JTextField firstnameField;
    private JTextField lastnameField;
    private JTextField middleInitialField;
    private JTextField dateOfBirthField;
    private JTextField emailField;
    private JTextField phoneNumberField;
    private JTextField addressField;
    private JButton nextButton;
    private JButton cancelButton;
}
