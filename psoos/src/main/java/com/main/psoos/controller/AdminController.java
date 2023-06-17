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

    @GetMapping("/createAdminDesigner")
    public String createAdminDesigner(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerName("Sample Designer");
        customerDTO.setCustomerEmail("adminDesigner@gmail.com");
        customerDTO.setCustomerPhoneNumber("093605041");
        customerDTO.setCustomerHomeAddress("Sample Address");

        Customer customer = new Customer(customerDTO);
        UserDTO userDTO = new UserDTO(customerDTO);
        User user = new User(userDTO);
        user.setPassword("admin");
        user.setRole("USER_DESIGNER");
        userService.createUser(user);
        customerService.createCustomer(customer);
        return "true";
    }

    @GetMapping("/createAdminWorker")
    public String createAdminWorker(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerName("Worker 1");
        customerDTO.setCustomerEmail("worker1@gmail.com");
        customerDTO.setCustomerPhoneNumber("093605041");
        customerDTO.setCustomerHomeAddress("Sample Address");

        Customer customer = new Customer(customerDTO);
        UserDTO userDTO = new UserDTO(customerDTO);
        User user = new User(userDTO);
        user.setPassword("admin");
        user.setRole("USER_WORKER");
        userService.createUser(user);
        customerService.createCustomer(customer);
        return "true";
    }

    @GetMapping("/createAdminWorker2")
    public String createAdminWorker2(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerName("Worker 2");
        customerDTO.setCustomerEmail("worker2@gmail.com");
        customerDTO.setCustomerPhoneNumber("093605041");
        customerDTO.setCustomerHomeAddress("Sample Address");

        Customer customer = new Customer(customerDTO);
        UserDTO userDTO = new UserDTO(customerDTO);
        User user = new User(userDTO);
        user.setPassword("admin");
        user.setRole("USER_WORKER");
        userService.createUser(user);
        customerService.createCustomer(customer);
        return "true";
    }

    @GetMapping("/createAdminWorker3")
    public String createAdminWorker3(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerName("Worker 3");
        customerDTO.setCustomerEmail("worker3@gmail.com");
        customerDTO.setCustomerPhoneNumber("093605041");
        customerDTO.setCustomerHomeAddress("Sample Address");

        Customer customer = new Customer(customerDTO);
        UserDTO userDTO = new UserDTO(customerDTO);
        User user = new User(userDTO);
        user.setPassword("admin");
        user.setRole("USER_WORKER");
        userService.createUser(user);
        customerService.createCustomer(customer);
        return "true";
    }
}
