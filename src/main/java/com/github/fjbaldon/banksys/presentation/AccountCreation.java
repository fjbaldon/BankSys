package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AccountCreation {
    public void show() {
        frame.setContentPane(panel);
        frame.revalidate();
    }

    public AccountCreation(JFrame frame) {
        this.frame = Objects.requireNonNull(frame);

        generateButton.addActionListener(e -> {
            // TODO
        });

        createAccountButton.addActionListener(e -> {
            // TODO
        });

        backButton.addActionListener(e -> {
            JPanel cards = (JPanel) frame.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.CUSTOMER_ACCOUNTS);
        });
    }

    private final JFrame frame;

    public JPanel panel;
    private JTextField accountNumberField;
    private JComboBox accountTypeBox;
    private JButton generateButton;
    private JButton createAccountButton;
    private JButton backButton;
}
