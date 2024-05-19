package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.business.model.Account;
import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.business.model.Login;
import com.github.fjbaldon.banksys.business.model.Model;
import com.github.fjbaldon.banksys.business.service.AccountService;
import com.github.fjbaldon.banksys.business.service.CustomerService;
import com.github.fjbaldon.banksys.business.service.LoginService;
import com.github.fjbaldon.banksys.business.service.TransactionService;
import com.github.fjbaldon.banksys.business.service.exception.AccountServiceException;
import com.github.fjbaldon.banksys.business.service.exception.LoginServiceException;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum LoginManageVC implements ApplicationPanel {

    INSTANCE;
    private final Application application = Application.INSTANCE;
    private final LoginService loginService = LoginService.INSTANCE;
    private final AccountService accountService = AccountService.INSTANCE;
    private final CustomerService customerService = CustomerService.INSTANCE;
    private final TransactionService transactionService = TransactionService.INSTANCE;

    @Override
    public void show(JFrame frame, Model optional) {
        if (!(optional instanceof Customer))
            throw new IllegalArgumentException();

        frame.setContentPane(panel);
        frame.revalidate();
        prepare((Customer) optional);
    }

    LoginManageVC() {
        deleteProfileButton.setVerifyInputWhenFocusTarget(false);
        deleteProfileButton.addActionListener(e -> {
            List<Account> accounts = accountService.getAccountsByCustomerId(customer.getCustomerId());
            if (!accounts.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "This profile still holds account(s). " +
                        "It cannot be deleted until all accounts are cleared.", "Profile Deletion Failure", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirmDelete = JOptionPane.showConfirmDialog(
                    panel,
                    "Are you sure you want to delete this profile? This action cannot be undone.",
                    "Confirm Profile Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirmDelete != JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(panel, "Account deletion canceled.", "Profile Deletion Canceled", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Login login = loginService.getLoginsByCustomerId(customer.getCustomerId()).getFirst();
            try {
                loginService.authenticate(login.getUsername(), Arrays.toString(oldPasswordField.getPassword()));
            } catch (LoginServiceException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid old password for the current logged profile.", "Profile Deletion Failure", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (Account account : accounts)
                transactionService.deleteTransactionsByAccountId(account.getAccountId());
            accountService.deleteAccountsByCustomerId(customer.getCustomerId());
            loginService.deleteLoginsByCustomerId(customer.getCustomerId());
            customerService.deleteCustomerById(customer.getCustomerId());
            JOptionPane.showMessageDialog(panel, "Profile deleted successfully", "Profile Deletion Success", JOptionPane.INFORMATION_MESSAGE);
            application.showView(Application.ApplicationPanels.LOGIN, null);
        });

        showPasswordBox.setVerifyInputWhenFocusTarget(false);
        showPasswordBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                oldPasswordField.setEchoChar((char) 0);
                newPasswordField.setEchoChar((char) 0);
                confirmNewPasswordField.setEchoChar((char) 0);
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                oldPasswordField.setEchoChar('*');
                newPasswordField.setEchoChar('*');
                confirmNewPasswordField.setEchoChar('*');
            }
        });

        saveNewCredentialsButton.addActionListener(e -> {
            String un = usernameField.getText().trim();
            String opw = Arrays.toString(oldPasswordField.getPassword());
            String npw = Arrays.toString(newPasswordField.getPassword());

            try {
                loginService.authenticate(un, opw);
            } catch (LoginServiceException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid old password for the current logged profile.", "Profile Update Failure", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Login login = loginService.getLoginsByCustomerId(customer.getCustomerId()).getFirst();
            if (login == null)
                throw new IllegalStateException();
            login.setUsername(un);
            loginService.updateLogin(login, npw);
            JOptionPane.showMessageDialog(panel, "Login information successfully updated!", "Login Update Successful", JOptionPane.INFORMATION_MESSAGE);
            application.showView(Application.ApplicationPanels.CUSTOMER_ACCOUNTS, customer);
        });

        backButton.setVerifyInputWhenFocusTarget(false);
        backButton.addActionListener(e -> application.showView(Application.ApplicationPanels.CUSTOMER_MANAGE, customer));

        usernameField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                String username = textField.getText();

                if (username.isBlank()) {
                    JOptionPane.showMessageDialog(panel, "Please enter your Username.", "Empty Username", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                if (username.length() < 6) {
                    JOptionPane.showMessageDialog(panel, "Username must be at least 6 characters long.", "Invalid Username Length", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                return true;
            }
        });

        oldPasswordField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JPasswordField passwordField = (JPasswordField) input;
                char[] password = passwordField.getPassword();

                if (password.length == 0) {
                    JOptionPane.showMessageDialog(panel, "Please enter your Password.", "Empty Password", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                if (password.length < 8) {
                    JOptionPane.showMessageDialog(panel, "Password must be at least 8 characters long.", "Invalid Password Length", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                return true;
            }
        });

        newPasswordField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JPasswordField passwordField = (JPasswordField) input;
                char[] password = passwordField.getPassword();

                if (password.length == 0) {
                    JOptionPane.showMessageDialog(panel, "Please enter your Password.", "Empty Password", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                if (password.length < 8) {
                    JOptionPane.showMessageDialog(panel, "Password must be at least 8 characters long.", "Invalid Password Length", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                return true;
            }
        });

        confirmNewPasswordField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JPasswordField passwordField = (JPasswordField) input;
                char[] password = passwordField.getPassword();

                if (password.length == 0) {
                    JOptionPane.showMessageDialog(panel, "Please enter your Password.", "Empty Password", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                if (password.length < 8) {
                    JOptionPane.showMessageDialog(panel, "Password must be at least 8 characters long.", "Invalid Password Length", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                return true;
            }
        });
    }

    private void prepare(Customer customer) {
        this.customer = customer;

        Login login = loginService.getLoginsByCustomerId(customer.getCustomerId()).getFirst();
        if (login == null)
            throw new IllegalStateException();
        usernameField.setText(login.getUsername());
        oldPasswordField.setText("");
        newPasswordField.setText("");
        confirmNewPasswordField.setText("");
        showPasswordBox.setSelected(false);
    }

    private Customer customer;

    private JPanel panel;
    private JTextField usernameField;
    private JButton deleteProfileButton;
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmNewPasswordField;
    private JCheckBox showPasswordBox;
    private JButton saveNewCredentialsButton;
    private JButton backButton;
}
