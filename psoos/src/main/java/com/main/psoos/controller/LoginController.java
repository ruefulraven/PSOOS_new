package com.main.psoos.controller;

import com.main.psoos.dto.LoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @GetMapping("/getMyName")
    public String getMyName(){
        return "Axel";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO){
        String emailAddressSaved = "asd@gmail.com";
        String passwordSaved = "asdasd";
        boolean isLoginCorrect = false;

        if(emailAddressSaved.equals(loginDTO.getEmailAddress()) && passwordSaved.equals(loginDTO.getPassword())){
            isLoginCorrect = true;
        }

        if(isLoginCorrect == true){
            return "korek login";
        }else{
            return "inkorek login";
        }

    }
}
