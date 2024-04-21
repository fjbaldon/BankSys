package com.github.fjbaldon.banksys.presentation.controller;

import com.github.fjbaldon.banksys.business.service.CustomerService;

import java.time.LocalDate;

public enum CustomerController {

    INSTANCE;
    private final CustomerService customerService = CustomerService.INSTANCE;

    public void handleCreateCustomer(String firstName, String lastName, String middleInitial, LocalDate dateOfBirth,
                                     String email, String phoneNumber, String address) {
        customerService.createCustomer(firstName, lastName, middleInitial, dateOfBirth, email, phoneNumber, address);
    }
}
