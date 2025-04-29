package com.p001.todolist.controller;

import java.util.List;
import com.p001.todolist.model.Login;
import com.p001.todolist.repository.LoginRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

 private final LoginRepository loginRepository;

 public LoginController(LoginRepository loginRepository) {
  this.loginRepository = loginRepository;
 }

 @GetMapping("/")
 public List<Login> getAllLogins() {
  return loginRepository.findAll();
 }

 @PostMapping
 public List<Login> getUserDetails(@RequestBody Login login) {
  return loginRepository.findByUsername(login.getUsername());
 }

 @PostMapping("/add")
 public Login createLogin(@RequestBody Login login) {
  return loginRepository.save(login);
 }

 @GetMapping("/test")
 public String testApi() {
  return "API is working!";
 }
}