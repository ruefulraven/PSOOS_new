package com.main.psoos.controller;

import com.main.psoos.dto.LoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class LoginController {

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

        if(emailAddressSaved.equals(loginDTO.getEmail()) && passwordSaved.equals(loginDTO.getPassword())){
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
}
