package com.github.fjbaldon.banksys.business.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.business.model.Login;
import com.github.fjbaldon.banksys.business.service.exception.LoginServiceException;
import com.github.fjbaldon.banksys.data.dao.CustomerDAO;
import com.github.fjbaldon.banksys.data.dao.LoginDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public enum LoginService {

    INSTANCE;
    private static final Logger log = LoggerFactory.getLogger(LoginService.class);
    private final LoginDAO loginDAO = LoginDAO.INSTANCE;
    private final CustomerDAO customerDAO = CustomerDAO.INSTANCE;

    public Optional<Customer> authenticate(String username, String password) throws LoginServiceException {
        Optional<Login> login = getLoginByUsername(username);

        if (login.isEmpty()) {
            throw new LoginServiceException("Invalid username or password.");
        }

        if (!isPasswordSameAsHash(password, login.get().getPasswordHash())) {
            throw new LoginServiceException("Invalid username or password.");
        }

        return Optional.of(customerDAO.getCustomerById(login.get().getCustomerId())
                .orElseThrow(() -> new LoginServiceException("Customer not found for login.")));
    }

    public void createLogin(String username, String password, Customer customer) {
        Login login = new Login(
                null,
                username,
                hashLoginPassword(password),
                null,
                null,
                customer.getCustomerId()
        );
        loginDAO.createLogin(login);
    }

    public Optional<Login> getLoginById(Long loginId) {
        return loginDAO.getLoginById(loginId);
    }

    public Optional<Login> getLoginByUsername(String username) {
        return loginDAO.getLoginByUsername(username);
    }

    public List<Login> getLoginsByCustomerId(Long customerId) {
        return loginDAO.getLoginsByCustomerId(customerId);
    }

    public void updateLogin(Login login, String newPassword) {
        login.setPasswordHash(hashLoginPassword(newPassword));
        loginDAO.updateLogin(login);
    }

    public void deleteLoginById(Long id) {
        loginDAO.deleteLoginById(id);
    }

    public void deleteLoginsByCustomerId(Long customerId) {
        loginDAO.deleteLoginsByCustomerId(customerId);
    }

    private String hashLoginPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    private boolean isPasswordSameAsHash(String enteredPassword, String storedPasswordHash) {
        return BCrypt.verifyer().verify(enteredPassword.toCharArray(), storedPasswordHash).verified;
    }
}