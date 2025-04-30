package com.p001.todolist.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.p001.todolist.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
 Optional<User> findByUsernameAndPassword(String username, String password);
}
