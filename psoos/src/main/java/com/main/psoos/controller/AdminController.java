package com.main.psoos.controller;

import com.main.psoos.dto.CustomerDTO;
import com.main.psoos.dto.UserDTO;
import com.main.psoos.model.Customer;
import com.main.psoos.model.User;
import com.main.psoos.service.CustomerService;
import com.main.psoos.service.DocumentService;
import com.main.psoos.service.MugService;
import com.main.psoos.service.OrderService;
import com.main.psoos.service.ShirtService;
import com.main.psoos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @Autowired
    DocumentService documentService;

    @Autowired
    OrderService orderService;

    @Autowired
    MugService mugService;

    @Autowired
    ShirtService shirtService;

    @GetMapping("/createAdmin")
    public String createAdmin(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerName("Sample Admin");
        customerDTO.setCustomerEmail("admin@gmail.com");
        customerDTO.setCustomerPhoneNumber("093605041");
        customerDTO.setCustomerHomeAddress("Sample Address");

        Customer customer = new Customer(customerDTO);
        UserDTO userDTO = new UserDTO(customerDTO);
        User user = new User(userDTO);
        user.setPassword("admin");
        user.setRole("USER_ADMIN");
        userService.createUser(user);
        customerService.createCustomer(customer);
        return "true";
    }
}
