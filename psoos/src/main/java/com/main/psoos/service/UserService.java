package com.main.psoos.service;

import com.main.psoos.model.User;

import java.util.List;

public interface UserService {
    User createUser (User user);

    User loginUser(User user);

    User getUserByName(String name);

    List<User> getAllActiveUsers();
}
