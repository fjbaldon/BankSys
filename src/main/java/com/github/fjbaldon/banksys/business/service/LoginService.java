package com.github.fjbaldon.banksys.business.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.business.model.Login;
import com.github.fjbaldon.banksys.data.dao.LoginDAO;

import java.sql.SQLException;
import java.util.Objects;

public final class LoginService {

    public Login authenticate(String username, String password) throws SQLException {
        Login login = loginDAO.getLoginByUsername(username);
        if (login != null && verifyPassword(password, login.passwordHash()))
            return login;
        else
            return null;
    }

    public boolean usernameExists(String username) throws SQLException {
        return loginDAO.getLoginByUsername(username) != null;
    }

    public Login createLogin(String username, String password, Customer customer) throws SQLException {
        Login newLogin = new Login.Builder()
                .username(username)
                .passwordHash(hashPassword(password))
                .customerId(customer.customerId())
                .build();
        loginDAO.createLogin(newLogin);
        return newLogin;
    }

    public void updateLogin(Login login, String newPassword) throws SQLException {
        String hashedPassword = hashPassword(newPassword);
        Login newLogin = new Login.Builder()
                .loginId(login.loginId())
                .username(login.username())
                .passwordHash(hashedPassword)
                .customerId(login.customerId())
                .build();
        loginDAO.updateLogin(newLogin);
    }

    public void deleteLogin(Login login) throws SQLException {
        loginDAO.deleteLogin(login);
    }

    private boolean verifyPassword(String enteredPassword, String storedPasswordHash) {
        return BCrypt.verifyer().verify(enteredPassword.toCharArray(), storedPasswordHash).verified;
    }

    private String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public LoginService(LoginDAO loginDAO) {
        this.loginDAO = Objects.requireNonNull(loginDAO);
    }

    private final LoginDAO loginDAO;
}