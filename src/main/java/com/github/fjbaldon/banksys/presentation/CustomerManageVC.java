package com.github.fjbaldon.banksys.presentation;

import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.business.model.Model;
import com.github.fjbaldon.banksys.business.service.CustomerService;
import org.apache.commons.validator.routines.EmailValidator;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

public enum CustomerManageVC implements ApplicationPanel {

    INSTANCE;
    private final Application application = Application.INSTANCE;
    private final CustomerService customerService = CustomerService.INSTANCE;

    @Override
    public void show(JFrame frame, Model optional) {
        if (!(optional instanceof Customer))
            throw new IllegalArgumentException();

        frame.setContentPane(panel);
        frame.revalidate();
        prepare((Customer) optional);
    }

    CustomerManageVC() {
        changeCredentialsButton.addActionListener(e -> application.showView(Application.ApplicationPanels.LOGIN_MANAGE, customer));

        saveNewProfileButton.addActionListener(e -> {
            customer.setFirstName(firstnameField.getText());
            customer.setLastName(lastnameField.getText());
            customer.setMiddleInitial(middleInitialField.getText());
            customer.setDateOfBirth(LocalDate.parse(dateOfBirthField.getText()));
            customer.setEmail(emailField.getText());
            customer.setPhoneNumber(phoneNumberField.getText());
            customer.setAddress(addressField.getText());

            customerService.updateCustomer(customer);
            JOptionPane.showMessageDialog(panel, "Profile information successfully updated!", "Profile Update Successful", JOptionPane.INFORMATION_MESSAGE);
        });

        restoreFieldsButton.setVerifyInputWhenFocusTarget(false);
        restoreFieldsButton.addActionListener(e -> prepare(customer));

        cancelButton.setVerifyInputWhenFocusTarget(false);
        cancelButton.addActionListener(e -> application.showView(Application.ApplicationPanels.CUSTOMER_ACCOUNTS, customer));

        firstnameField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                String firstName = textField.getText().trim();

                if (firstName.isBlank()) {
                    JOptionPane.showMessageDialog(panel, "Please enter your First Name.", "Empty Name", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                boolean isAlphabetic = firstName.matches("[a-zA-Z ]+");
                if (!isAlphabetic) {
                    JOptionPane.showMessageDialog(panel, "First Name can only contain alphabets and spaces.", "Invalid Name", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                if (firstName.length() < 2 || firstName.length() > 50) {
                    JOptionPane.showMessageDialog(panel, "First Name must be between 2 and 50 characters.", "Invalid Name Length", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                return true;
            }
        });

        lastnameField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                String lastName = textField.getText().trim();

                if (lastName.isBlank()) {
                    JOptionPane.showMessageDialog(panel, "Please enter your Last Name.", "Empty Name", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                boolean isAlphabeticWithSymbols = lastName.matches("[a-zA-Z -’]+");
                if (!isAlphabeticWithSymbols) {
                    JOptionPane.showMessageDialog(panel, "Last Name can only contain alphabets, hyphens, apostrophes, and spaces.", "Invalid Name", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                if (lastName.length() < 2 || lastName.length() > 50) {
                    JOptionPane.showMessageDialog(panel, "Last Name must be between 1 and 50 characters.", "Invalid Name Length", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                return true;
            }
        });

        middleInitialField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                String middleInitial = textField.getText().trim();

                if (middleInitial.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Please enter your Middle Initial.", "Empty Initial", JOptionPane.WARNING_MESSAGE);
                    return true;
                }

                if (middleInitial.length() != 1) {
                    JOptionPane.showMessageDialog(panel, "Middle Initial can only be a single character.", "Invalid Initial", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                if (!Character.isAlphabetic(middleInitial.charAt(0))) {
                    JOptionPane.showMessageDialog(panel, "Middle Initial can only be an alphabet.", "Invalid Initial", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                return true;
            }
        });

        dateOfBirthField.setInputVerifier(new InputVerifier() {
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                String dobString = textField.getText().trim();

                if (dobString.isBlank()) {
                    JOptionPane.showMessageDialog(panel, "Please enter your Date of Birth.", "Empty Date", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                if (!dobString.matches("\\d{4}-[01]\\d-[0-3]\\d")) {
                    JOptionPane.showMessageDialog(panel, "Invalid Date of Birth format. Use YYYY-MM-DD (e.g., 2024-04-21).", "Invalid Format", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                try {
                    Date date = dateFormat.parse(dobString);
                    LocalDate localDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());

                    if (localDate.getYear() < 1900 || localDate.getYear() > 2100) {
                        JOptionPane.showMessageDialog(panel, "Invalid year. Please enter a year between 1900 and 2100.", "Invalid Year", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }

                    return true;
                } catch (ParseException | DateTimeException e) {
                    JOptionPane.showMessageDialog(panel, "Invalid Date of Birth. Please enter a valid date.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        });

        emailField.setInputVerifier(new InputVerifier() {
            private final EmailValidator validator = EmailValidator.getInstance();

            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                String email = textField.getText().trim();

                if (email.isBlank()) {
                    JOptionPane.showMessageDialog(panel, "Please enter your Email Address.", "Empty Email", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                if (!validator.isValid(email)) {
                    JOptionPane.showMessageDialog(panel, "Invalid Email Address. Please enter a valid email.", "Invalid Email", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                return true;
            }
        });

        phoneNumberField.setInputVerifier(new InputVerifier() {
            private final Pattern phonePattern = Pattern.compile("^\\+(?:[0-9]\\)?){6,14}$");

            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                String phoneNumber = textField.getText().trim();

                if (phoneNumber.isBlank()) {
                    JOptionPane.showMessageDialog(panel, "Please enter your Phone Number.", "Empty Phone Number", JOptionPane.WARNING_MESSAGE);
                    return true;
                }

                if (!phonePattern.matcher(phoneNumber).matches()) {
                    JOptionPane.showMessageDialog(panel, "Invalid Phone Number format. Use + followed by digits (e.g., +1234567890).", "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                return true;
            }
        });

        addressField.setInputVerifier(new InputVerifier() {
            private final Pattern addressPattern = Pattern.compile("^\\d+ [\\w\\s,.-]+$");

            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                String address = textField.getText().trim();

                if (address.isBlank()) {
                    JOptionPane.showMessageDialog(panel, "Please enter your Address.", "Empty Address", JOptionPane.ERROR_MESSAGE);
                    return true;
                }

                if (!addressPattern.matcher(address).matches()) {
                    JOptionPane.showMessageDialog(panel, "Invalid Address format. Enter a number followed by street name (e.g., 123 Main St. ).", "Invalid Address", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                return true;
            }
        });
    }

    private void prepare(Customer customer) {
        this.customer = customer;

        firstnameField.setText(customer.getFirstName());
        lastnameField.setText(customer.getLastName());
        middleInitialField.setText(customer.getMiddleInitial());
        dateOfBirthField.setText(customer.getDateOfBirth().toString());
        emailField.setText(customer.getEmail());
        phoneNumberField.setText(customer.getPhoneNumber());
        addressField.setText(customer.getAddress());
    }

    private Customer customer;

    private JPanel panel;
    private JTextField firstnameField;
    private JTextField lastnameField;
    private JTextField middleInitialField;
    private JTextField dateOfBirthField;
    private JTextField emailField;
    private JTextField phoneNumberField;
    private JTextField addressField;
    private JButton saveNewProfileButton;
    private JButton cancelButton;
    private JButton restoreFieldsButton;
    private JButton changeCredentialsButton;
}
