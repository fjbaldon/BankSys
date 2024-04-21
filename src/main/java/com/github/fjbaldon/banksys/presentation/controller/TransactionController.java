package com.github.fjbaldon.banksys.presentation.controller;

import com.github.fjbaldon.banksys.business.service.LoginService;

public enum TransactionController {

    INSTANCE;
    private final LoginService loginService = LoginService.INSTANCE;

}
