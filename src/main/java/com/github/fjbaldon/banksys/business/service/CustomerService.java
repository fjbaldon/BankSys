package com.github.fjbaldon.banksys.business.service;

import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.data.dao.CustomerDAO;

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
                    null,
                    null
            );
            customerDAO.createCustomer(customer);
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerDAO.getCustomerById(id);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerDAO.getCustomerByEmail(email);
    }

    public Optional<Customer> getCustomerByPhoneNumber(String phoneNumber) {
        return customerDAO.getCustomerByPhoneNumber(phoneNumber);
    }

    public Optional<Customer> getCustomerByAccountNumber(String accountNumber) {
        return customerDAO.getCustomerByAccountNumber(accountNumber);
    }

    public void updateCustomer(Customer customer) {
        customerDAO.updateCustomer(customer);
    }

    public void deleteCustomerById(Long id) {
        customerDAO.deleteCustomerById(id);
    }
}
