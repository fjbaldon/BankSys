package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Objects;

public final class CustomerCreation {

    public Customer getToBeCreated() {
        return toBeCreated;
    }

    public CustomerCreation(BankSys bankSys, LoginCreation loginCreation, LogIn logIn) {
        Objects.requireNonNull(bankSys);

        nextButton.addActionListener(e -> {
            String fn = firstnameField.getText();
            String ln = lastnameField.getText();
            String mi = middleInitialField.getText();
            String dob = dateOfBirthField.getText();
            String em = emailField.getText();
            String pn = phoneNumberField.getText();
            String a = addressField.getText();

            toBeCreated = new Customer.Builder()
                    .firstName(fn)
                    .lastName(ln)
                    .middleInitial(mi)
                    .dateOfBirth(LocalDate.parse(dob))
                    .email(em)
                    .phoneNumber(pn)
                    .address(a)
                    .build();

            loginCreation.setToBeCreated(toBeCreated); // to be used by LoginCreation

            JPanel cards = (JPanel) bankSys.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.LOGIN_CREATION);
        });

        cancelButton.addActionListener(e -> {
            logIn.clearLogInInfo();

            JPanel cards = (JPanel) bankSys.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.LOGIN);
        });
    }

    public JPanel panel;

    private Customer toBeCreated;

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
