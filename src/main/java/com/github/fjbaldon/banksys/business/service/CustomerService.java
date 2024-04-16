package com.github.fjbaldon.banksys.business.service;

import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.data.dao.CustomerDAO;

public final class CustomerService {
    public static CustomerService create(CustomerDAO customerDAO) {
        return new CustomerService(customerDAO);
    }

    public enum AuthenticationStatus {
        OK,
        INVALID_CREDENTIALS,
        INVALID_USER
    }

    public AuthenticationStatus authenticate(Customer customer) {
        if (customer == null)
            return AuthenticationStatus.INVALID_USER;

        var storedUser = customerDAO.getUserOf(customer.getName());

        if (storedUser == null || !storedUser.getPassword().equals(customer.getPassword()))
            return AuthenticationStatus.INVALID_CREDENTIALS;

        return AuthenticationStatus.OK;
    }

    public enum RegistrationStatus {
        OK,
        USERNAME_TAKEN,
        INVALID_ACCOUNT,
    }

    public RegistrationStatus register(Customer customer) {
        if (customer == null)
            return RegistrationStatus.INVALID_ACCOUNT;

        if (customerDAO.getUserOf(customer.getName()) != null)
            return RegistrationStatus.USERNAME_TAKEN;

        customerDAO.save(customer);

        return RegistrationStatus.OK;
    }

    private final CustomerDAO customerDAO;

    private CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
}
