package com.github.fjbaldon.banksys.business.service;

import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.data.dao.CustomerDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public enum CustomerService {

    INSTANCE;
    private final CustomerDAO customerDAO = CustomerDAO.INSTANCE;

    public void createCustomer(String firstName, String lastName, String middleInitial, LocalDate dateOfBirth,
                                   String email, String phoneNumber, String address) {
            Customer customer = new Customer(
                    null,
                    firstName,
                    lastName,
                    middleInitial,
                    dateOfBirth,
                    email,
                    phoneNumber,
                    address,
                    null,
                    null
            );
            customerDAO.createCustomer(customer);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerDAO.getCustomerByEmail(email);
    }

    public void updateCustomer(Customer customer) {
        customerDAO.updateCustomer(customer);
    }

    public void deleteCustomer(Customer customer) {
        customerDAO.deleteCustomer(customer);
    }
}
