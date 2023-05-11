package com.main.psoos.controller;

import com.main.psoos.dto.CustomerDTO;
import com.main.psoos.dto.DocumentDTO;
import com.main.psoos.dto.LoginDTO;
import com.main.psoos.dto.MugDTO;
import com.main.psoos.dto.ShirtDTO;
import com.main.psoos.dto.UserDTO;
import com.main.psoos.model.Customer;
import com.main.psoos.model.User;
import com.main.psoos.service.CustomerService;
import com.main.psoos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class LoginController {
    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    List<Object> orders = new ArrayList<>();

    @GetMapping("/getMyName")
    public String getMyName(){
        return "Axel";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model){
        model.addAttribute("isSuccess", true);
        return "index";
    }

    @GetMapping("/accountPage")
    public String accountPage(Model model, User user){
        Customer customer = customerService.getCustomerByName(user.getName());
        model.addAttribute("name", user.getName());
        model.addAttribute("customer", customer);

        return "account";
    }

    @GetMapping("/registrationPage")
    public String registrationPage(){
        return "createAccount";
    }

    @PostMapping("/login")
    public String login(LoginDTO loginDTO, Model model){
        UserDTO userDTO = new UserDTO(loginDTO);
        User user = new User(userDTO);

        User tempUser = userService.loginUser(user);
        boolean isLoginCorrect = false;

        if(tempUser != null){
            isLoginCorrect = true;
        }
        boolean isSuccess = false;

        if(loginDTO.getEmail().equals("")){
            model.addAttribute("emailBlank",true);
        }

        if(isLoginCorrect == true){
            isSuccess = true;
            return accountPage(model, tempUser);
        }

        model.addAttribute("isSuccess",isSuccess);
        return "index";
    }

    @PostMapping({"/createAccount"})
    public String createAccount(CustomerDTO customerDTO, Model model) {
      System.out.println(customerDTO.getCustomerEmail() + customerDTO.getCustomerName());

        boolean isSuccess = true;
        if(customerDTO.getCustomerName().equals("")){
            model.addAttribute("nameBlank",true);
            isSuccess = false;
        }
        if(customerDTO.getCustomerName().equals("")){
            model.addAttribute("nameBlank",true);
            isSuccess = false;
        }

        if(isSuccess == true){
            Customer customer = new Customer(customerDTO);
            UserDTO userDTO = new UserDTO(customerDTO);
            User user = new User(userDTO);
            user.setRole("USER_CLIENT");
            userService.createUser(user);
            customerService.createCustomer(customer);
        }

        return "createAccount";
    }

    @PostMapping("/updateAccount")
    public String updateAccount(CustomerDTO customerDTO, Model model){
        Customer tempCustomer = customerService.getCustomerByName(customerDTO.getCustomerName());
        tempCustomer.setCustomerPhoneNumber(customerDTO.getCustomerPhoneNumber());
        tempCustomer.setCustomerName(customerDTO.getCustomerName());
        customerService.updateCustomerDetails(tempCustomer);
        User user = userService.getUserByName(tempCustomer.getCustomerName());
        model.addAttribute("customer", tempCustomer);

        return "account";
    }

    @GetMapping("/myOrdersPage")
    public String myOrdersPage(){
        return "myOrders";
    }

    public void addMugOrder(MugDTO mugDTO){
        orders.add(mugDTO);
    }
    public void addDocumentOrder(DocumentDTO tempDocument){
        orders.add(tempDocument);
    }
    public void addShirtOrder(ShirtDTO tempShirt){
        orders.add(tempShirt);
    }
}
