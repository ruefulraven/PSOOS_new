package com.main.psoos.service.impl;

import com.main.psoos.model.User;
import com.main.psoos.repository.UsersRepository;
import com.main.psoos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public User createUser(User user) {
        return usersRepository.save(user);
    }

    @Override
    public User loginUser(User user) {
        User tempUser = usersRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword());
        return tempUser;
    }

    @Override
    public User getUserByName(String name) {
        User tempUser = usersRepository.findByName(name);
        return tempUser;
    }
}
