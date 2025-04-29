package com.p001.todolist.repository;
import java.util.List;
import com.p001.todolist.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Long> {
 List<Login> findByUsername(String username);
}