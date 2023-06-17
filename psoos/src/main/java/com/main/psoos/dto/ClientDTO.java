package com.main.psoos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private Integer userId;
    private String name;
    private String userName;
    private String password;
    private boolean isDeleted;

    private CustomerDTO customerDTO;
}
