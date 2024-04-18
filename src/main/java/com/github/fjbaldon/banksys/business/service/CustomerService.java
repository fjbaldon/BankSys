package com.github.fjbaldon.banksys.business.service;

import com.github.fjbaldon.banksys.business.model.Customer;
import com.github.fjbaldon.banksys.data.dao.CustomerDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

public final class CustomerService {

    public Customer createCustomer(String firstName, String lastName, String middleInitial, LocalDate dateOfBirth,
                                   String email, String phoneNumber, String address) throws SQLException {
        Customer customer = new Customer.Builder()
                .firstName(firstName)
                .lastName(lastName)
                .middleInitial(middleInitial)
                .dateOfBirth(dateOfBirth)
                .email(email)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();
        return customerDAO.createCustomer(customer);
    }

    public Customer getCustomerById(Long customerId) throws SQLException {
        return customerDAO.getCustomerById(customerId);
    }

    public void updateCustomer(Customer customer) throws SQLException {
        customerDAO.updateCustomer(customer);
    }

    public void deleteCustomer(Customer customer) throws SQLException {
        customerDAO.deleteCustomer(customer);
    }

    private final CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = Objects.requireNonNull(customerDAO);
    }
}
