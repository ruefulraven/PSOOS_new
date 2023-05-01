package com.main.psoos.controller;

import com.main.psoos.dto.CustomerDTO;
import com.main.psoos.dto.LoginDTO;
import com.main.psoos.model.Customer;
import com.main.psoos.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.PrintStream;


@Controller
public class LoginController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/getMyName")
    public String getMyName(){
        return "Axel";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model){
        model.addAttribute("isSuccess", true);
        return "index";
    }

    @GetMapping("/registrationPage")
    public String registrationPage(){
        return "createAccount";
    }

    @PostMapping("/login")
    public String login(LoginDTO loginDTO, Model model){
        String emailAddressSaved = "asd@gmail.com";
        String passwordSaved = "asdasd";
        boolean isLoginCorrect = false;

        if(emailAddressSaved.equals(loginDTO.getEmail())
                && passwordSaved.equals(loginDTO.getPassword())){
            isLoginCorrect = true;
        }
        boolean isSuccess = false;

        if(isLoginCorrect == true){
            isSuccess = true;
            return "home";
        }

        model.addAttribute("isSuccess",isSuccess);
        return "index";
    }

    @PostMapping({"/createAccount"})
    public String createAccount(CustomerDTO customerDTO, Model model) {
      System.out.println(customerDTO.getCustomerEmail() + customerDTO.getCustomerName());

        Customer customer = new Customer(customerDTO);
        this.customerService.createCustomer(customer);
        model.addAttribute("isSuccess", true);
        return "createAccount";
    }
}
