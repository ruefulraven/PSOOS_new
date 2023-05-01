package com.main.psoos.dto;

public class UserDTO {

    private Integer userId;
    private String name;
    private String userName;
    private String password;

    public UserDTO(CustomerDTO customerDTO){
        this.name = customerDTO.getCustomerName();
        this.password = customerDTO.getPassword();
    }
}
