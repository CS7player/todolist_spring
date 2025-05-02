package com.p001.todolist.controller;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p001.todolist.model.User;
import com.p001.todolist.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

 private final UserRepository userRepository;

 public UserController(UserRepository userRepository) {
  this.userRepository = userRepository;
 }

 @PostMapping("/add")
 public ResponseEntity<HashMap<String, Object>> createUser(@RequestBody User user) {
  HashMap<String, Object> response = new HashMap<>();
  try {
   User savedUser = userRepository.save(user);
   response.put("status", true);
   response.put("data", savedUser);
   return ResponseEntity.ok(response);
  } catch (DataIntegrityViolationException ex) {
   response.put("status", false);
   response.put("error", "Username already exists, please choose another one.");
   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }
 }

 @PostMapping("/login")
 public ResponseEntity<HashMap<String, Object>> login(@RequestBody User user) {
  Optional<User> existingUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
  HashMap<String, Object> response = new HashMap<>();
  if (existingUser.isPresent()) {
   response.put("status", true);
   response.put("msg", "Login successful");
   response.put("data", existingUser);
   return ResponseEntity.ok(response);
  } else {
   response.put("status", false);
   response.put("msg", "Invalid username or password");
   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }
 }
}
