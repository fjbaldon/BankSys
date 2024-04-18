package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class CustomerCreation1 {

    public void show() {
        frame.setContentPane(panel);
        frame.revalidate();
    }

    public CustomerCreation1(JFrame frame) {
        this.frame = Objects.requireNonNull(frame);

        signUpButton.addActionListener(e -> {
            // TODO
        });

        cancelButton.addActionListener(e -> {
            // TODO
            JPanel cards = (JPanel) frame.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.CUSTOMER_LOGIN);
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
    private JButton signUpButton;
    private JButton cancelButton;
}
