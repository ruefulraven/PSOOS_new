package com.main.psoos.repository;

import com.main.psoos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {

    User findByUserNameAndPassword(String username, String password);

    User findByName(String username);
}
