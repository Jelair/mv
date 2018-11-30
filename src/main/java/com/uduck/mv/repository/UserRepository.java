package com.uduck.mv.repository;

import com.uduck.mv.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUsername(String username);
}
