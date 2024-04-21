package com.github.fjbaldon.banksys.business.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.business.model.Login;
import com.github.fjbaldon.banksys.data.dao.CustomerDAO;
import com.github.fjbaldon.banksys.data.dao.LoginDAO;

import java.util.Optional;

public enum LoginService {

    INSTANCE;
    private final LoginDAO loginDAO = LoginDAO.INSTANCE;
    private final CustomerDAO customerDAO = CustomerDAO.INSTANCE;

    public Optional<Customer> authenticate(Login login, String password) {
        if (verifyLoginPassword(password, login.getPasswordHash()))
            return customerDAO.getCustomerById(login.getCustomerId());
        return Optional.empty();
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

    public Optional<Login> getLoginByUsername(String username) {
        return loginDAO.getLoginByUsername(username);
    }

    public void updateLogin(Login login, String newPassword) {
        login.setPasswordHash(hashLoginPassword(newPassword));
        loginDAO.updateLogin(login);
    }

    public void deleteLogin(Login login) {
        loginDAO.deleteLogin(login);
    }

    private String hashLoginPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    private boolean verifyLoginPassword(String enteredPassword, String storedPasswordHash) {
        return BCrypt.verifyer().verify(enteredPassword.toCharArray(), storedPasswordHash).verified;
    }
}