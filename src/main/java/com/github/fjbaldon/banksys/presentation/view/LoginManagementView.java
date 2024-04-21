package com.github.fjbaldon.banksys.presentation.view;

import com.github.fjbaldon.banksys.business.model.Login;
import com.github.fjbaldon.banksys.presentation.controller.LoginController;

import javax.swing.*;
import java.awt.event.ItemEvent;

public enum LoginManagementView {

    INSTANCE;
    private final ApplicationView applicationView = ApplicationView.INSTANCE;
    private final LoginController loginController = LoginController.INSTANCE;

    public void show(Login login) {
        applicationView.FRAME.setContentPane(panel);
        applicationView.FRAME.revalidate();
        initComponents(login);
    }

    LoginManagementView() {
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
            usernameField.setEditable(true);
            editUsernameButton.setEnabled(false);
        });

        saveNewCredentialsButton.addActionListener(e -> {
            // todo
        });

        cancelButton.addActionListener(e -> {
            // todo
        });
    }

    private void initComponents(Login login) {
        usernameField.setText("");
        usernameField.setEditable(false);
        oldPasswordField.setText("");
        newPasswordField.setText("");
        confirmNewPasswordField.setText("");
        showPasswordBox.setSelected(false);
    }

    private JPanel panel;
    private JTextField usernameField;
    private JButton editUsernameButton;
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmNewPasswordField;
    private JCheckBox showPasswordBox;
    private JButton saveNewCredentialsButton;
    private JButton cancelButton;
}
