package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class CustomerManagement0 {

    public void show() {
        frame.setContentPane(panel);
        frame.revalidate();
    }

    public CustomerManagement0(JFrame frame) {
        this.frame = Objects.requireNonNull(frame);

        changeCredentialsButton.addActionListener(e -> {
            JPanel cards = (JPanel) frame.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.CUSTOMER_MANAGEMENT_1);
        });

        saveNewProfileButton.addActionListener(e -> {
            // TODO
        });

        restoreFieldsButton.addActionListener(e -> {
            // TODO
        });

        cancelButton.addActionListener(e -> {
            JPanel cards = (JPanel) frame.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.CUSTOMER_ACCOUNTS);
        });
    }

    private final JFrame frame;

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
