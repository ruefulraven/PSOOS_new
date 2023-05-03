package com.main.psoos.service;

import com.main.psoos.model.Customer;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer getCustomerByName(String name);
    Customer updateCustomerDetails(Customer customer);

}