package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Objects;

public class CustomerManagement1 {

    public void show() {
        frame.setContentPane(panel);
        frame.revalidate();
    }

    public CustomerManagement1(JFrame frame) {
        this.frame = Objects.requireNonNull(frame);

        showPasswordBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                oldPasswordField.setEchoChar((char) 0);
                newPasswordField.setEchoChar((char) 0);
                confirmNewPasswordField.setEchoChar((char) 0);
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                oldPasswordField.setEchoChar('*');
                newPasswordField.setEchoChar('*');
                newPasswordField.setEchoChar('*');
            }
        });

        editUsernameButton.addActionListener(e -> {
            // TODO
        });

        saveNewCredentialsButton.addActionListener(e -> {
            // TODO
        });

        cancelButton.addActionListener(e -> {
            JPanel cards = (JPanel) frame.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.CUSTOMER_ACCOUNTS);
        });
    }

    private final JFrame frame;

    public JPanel panel;
    private JTextField usernameField;
    private JPasswordField newPasswordField;
    private JCheckBox showPasswordBox;
    private JPasswordField confirmNewPasswordField;
    private JButton saveNewCredentialsButton;
    private JButton cancelButton;
    private JPasswordField oldPasswordField;
    private JButton editUsernameButton;
}
