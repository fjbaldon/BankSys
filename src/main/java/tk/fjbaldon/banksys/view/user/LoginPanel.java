package tk.fjbaldon.banksys.view.user;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class LoginPanel {
    public static LoginPanel create() {
        return new LoginPanel();
    }

    public JPanel get() {
        return panel;
    }

    public void update() {
        passwordField.setText("");
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return String.valueOf(passwordField.getPassword());
    }

    public void togglePasswordVisibility() {
        char currentEchoChar = passwordField.getEchoChar();

        if (currentEchoChar == 0)
            passwordField.setEchoChar('*');
        else
            passwordField.setEchoChar((char) 0);
    }

    public void addShowPasswordCheckBoxListener(ItemListener itemListener) {
        showPasswordCheckBox.addItemListener(itemListener);
    }

    public void addLogInButtonListener(ActionListener actionListener) {
        logInButton.addActionListener(actionListener);
    }

    public void addCreateNewUserButtonListener(ActionListener actionListener) {
        createNewUserButton.addActionListener(actionListener);
    }

    public void displaySuccessMessage(String message) {
        JOptionPane.showMessageDialog(panel, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckBox;
    private JButton logInButton;
    private JButton createNewUserButton;

    private LoginPanel() {
    }
}
