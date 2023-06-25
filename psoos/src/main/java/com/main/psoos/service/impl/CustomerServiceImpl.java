
package com.main.psoos.service.impl;

import com.main.psoos.model.Customer;
import com.main.psoos.repository.CustomerRepository;
import com.main.psoos.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerByName(String name){
        Customer tempCustomer = customerRepository.findByCustomerName(name);
        return tempCustomer;
    }

    @Override
    public Customer updateCustomerDetails(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customerRepository.findByCustomerId(id);
    }
}