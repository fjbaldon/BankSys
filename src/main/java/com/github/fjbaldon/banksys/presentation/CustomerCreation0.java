package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Objects;

public class CustomerCreation0 {

    public void show() {
        frame.setContentPane(panel);
        frame.revalidate();
    }

    public CustomerCreation0(JFrame frame) {
        this.frame = Objects.requireNonNull(frame);

        showPasswordBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                passwordField.setEchoChar((char) 0);
                confirmPasswordField.setEchoChar((char) 0);
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                passwordField.setEchoChar('*');
                passwordField.setEchoChar('*');
            }
        });

        nextButton.addActionListener(e -> {
            JPanel cards = (JPanel) frame.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.CUSTOMER_CREATION_1);
        });

        cancelButton.addActionListener(e -> {
            // TODO
            JPanel cards = (JPanel) frame.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.CUSTOMER_LOGIN);
        });
    }

    private final JFrame frame;

    public JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordBox;
    private JPasswordField confirmPasswordField;
    private JButton nextButton;
    private JButton cancelButton;
}
