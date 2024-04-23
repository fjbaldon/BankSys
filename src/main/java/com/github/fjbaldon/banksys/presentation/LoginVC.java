package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.business.model.Model;
import com.github.fjbaldon.banksys.business.service.LoginService;
import com.github.fjbaldon.banksys.business.service.exception.LoginServiceException;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.Optional;

public enum LoginVC implements ApplicationPanel {

    INSTANCE;
    private final Application application = Application.INSTANCE;
    private final LoginService loginService = LoginService.INSTANCE;

    @Override
    public void show(JFrame frame, Model optional) {
        frame.setContentPane(panel);
        frame.revalidate();
        prepare();
    }

    LoginVC() {
        showPasswordBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
                passwordField.setEchoChar((char) 0);
            else if (e.getStateChange() == ItemEvent.DESELECTED)
                passwordField.setEchoChar('*');
        });

        logInButton.addActionListener(e -> {
            String un = usernameField.getText();
            String pw = Arrays.toString(passwordField.getPassword());

            try {
                Optional<Customer> c = loginService.authenticate(un, pw);
                c.ifPresent(customer -> application.showView(Application.ApplicationPanels.CUSTOMER_ACCOUNTS, customer));
            } catch (LoginServiceException ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Log In", JOptionPane.ERROR_MESSAGE);
            }
        });

        createNewProfileButton.addActionListener(e -> application.showView(Application.ApplicationPanels.CUSTOMER_CREATE, null));
    }

    private void prepare() {
        usernameField.setText("");
        passwordField.setText("");
        showPasswordBox.setSelected(false);
    }

    private JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordBox;
    private JButton logInButton;
    private JButton createNewProfileButton;
}
