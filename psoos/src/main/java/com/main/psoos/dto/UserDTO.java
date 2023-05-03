package com.main.psoos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Integer userId;
    private String name;
    private String userName;
    private String password;

    public UserDTO(CustomerDTO customerDTO){
        this.name = customerDTO.getCustomerName();
        this.password = customerDTO.getPassword();
        this.userName = customerDTO.getCustomerEmail();
    }

    public UserDTO(LoginDTO loginDTO){
        this.password = loginDTO.getPassword();
        this.userName = loginDTO.getEmail();
    }
}
