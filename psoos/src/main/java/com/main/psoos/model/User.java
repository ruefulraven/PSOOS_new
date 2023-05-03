package com.main.psoos.model;

import com.main.psoos.dto.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "user_id")
    private Integer userId;
    @Column( name = "name")
    private String name;
    @Column(name = "user_name" )
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;

    public User (UserDTO userDTO){
        this.name = userDTO.getName();
        this.password = userDTO.getPassword();
        this.userName = userDTO.getUserName();
    }
}
