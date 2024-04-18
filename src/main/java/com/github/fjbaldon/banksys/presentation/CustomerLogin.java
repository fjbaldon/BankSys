package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.presentation.util.PanelNames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Objects;

public class CustomerLogin {

    public void show() {
        frame.setContentPane(panel);
        frame.revalidate();
    }

    public CustomerLogin(JFrame frame) {
        this.frame = Objects.requireNonNull(frame);

        showPasswordBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
                passwordField.setEchoChar((char) 0);
            else if (e.getStateChange() == ItemEvent.DESELECTED)
                passwordField.setEchoChar('*');
        });

        logInButton.addActionListener(e -> {
            // TODO
            JPanel cards = (JPanel) frame.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.CUSTOMER_ACCOUNTS);
        });

        createNewProfileButton.addActionListener(e -> {
            JPanel cards = (JPanel) frame.getContentPane();
            ((CardLayout) cards.getLayout()).show(cards, PanelNames.CUSTOMER_CREATION_0);
        });
    }

    private final JFrame frame;

    public JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordBox;
    private JButton logInButton;
    private JButton createNewProfileButton;
}
