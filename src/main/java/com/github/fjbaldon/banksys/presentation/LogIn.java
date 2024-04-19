package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.business.model.Login;
import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

public final class LogIn {

    public void clearLogInInfo() {
        usernameField.setText("");
        passwordField.setText("");
        showPasswordBox.setSelected(false);
    }

    public LogIn(BankSys bankSys) {
        Objects.requireNonNull(bankSys);

        showPasswordBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
                passwordField.setEchoChar((char) 0);
            else if (e.getStateChange() == ItemEvent.DESELECTED)
                passwordField.setEchoChar('*');
        });

        logInButton.addActionListener(e -> {
            try {
                String username = usernameField.getText();
                String password = Arrays.toString(passwordField.getPassword());

                Login login = bankSys.getLoginService().authenticate(username, password);

                if (login == null) {
                    JOptionPane.showMessageDialog(bankSys, "Invalid username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JPanel cards = (JPanel) bankSys.getContentPane();
                ((CardLayout) cards.getLayout()).show(cards, PanelNames.ACCOUNTS);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        createNewProfileButton.addActionListener(e -> {
            JPanel cards = (JPanel) bankSys.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.CUSTOMER_CREATION);
        });
    }

    public JPanel panel;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordBox;
    private JButton logInButton;
    private JButton createNewProfileButton;
}
