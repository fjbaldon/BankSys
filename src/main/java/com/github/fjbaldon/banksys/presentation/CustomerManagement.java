package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class CustomerManagement {

    public void show() {
        bankSys.setContentPane(panel);
        bankSys.revalidate();
    }

    public CustomerManagement(BankSys bankSys) {
        this.bankSys = Objects.requireNonNull(bankSys);

        changeCredentialsButton.addActionListener(e -> {
            JPanel cards = (JPanel) bankSys.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.LOGIN_MANAGEMENT);
        });

        saveNewProfileButton.addActionListener(e -> {
            // TODO
        });

        restoreFieldsButton.addActionListener(e -> {
            // TODO
        });

        cancelButton.addActionListener(e -> {
            JPanel cards = (JPanel) bankSys.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNTS);
        });
    }

    private final BankSys bankSys;

    public JPanel panel;
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
