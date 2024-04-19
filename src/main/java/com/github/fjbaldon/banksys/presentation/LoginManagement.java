package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Objects;

public class LoginManagement {

    public void show() {
        bankSys.setContentPane(panel);
        bankSys.revalidate();
    }

    public LoginManagement(BankSys bankSys) {
        this.bankSys = Objects.requireNonNull(bankSys);

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
            JPanel cards = (JPanel) bankSys.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNTS);
        });
    }

    private final BankSys bankSys;

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
