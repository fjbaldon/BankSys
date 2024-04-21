package com.github.fjbaldon.banksys.presentation.controller;

import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.business.model.Login;
import com.github.fjbaldon.banksys.business.service.LoginService;

import java.util.Optional;

public enum LoginController {

    INSTANCE;
    private final LoginService loginService = LoginService.INSTANCE;

    public Optional<Customer> handleLoginAuth(String username, String password) {
        Optional<Login> login = loginService.getLoginByUsername(username);

        if (login.isEmpty())
            return Optional.empty();

        return loginService.authenticate(login.get(), password);
    }
}
