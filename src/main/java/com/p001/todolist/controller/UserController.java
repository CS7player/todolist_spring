package com.p001.todolist.controller;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
   // Save the user to the database
   User savedUser = userRepository.save(user);

   // Prepare the response data
   response.put("status", true);
   response.put("data", savedUser);

   return ResponseEntity.ok(response); // Return the saved user with 200 OK status
  } catch (DataIntegrityViolationException ex) {
   // Handle unique constraint violation (e.g., duplicate username)
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
   // User found, return a success message
   response.put("status", true);
   response.put("msg", "Login successful");
   return ResponseEntity.ok(response);
  } else {
   // User not found, return an error message
   response.put("status", false);
   response.put("msg", "Invalid username or password");
   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }
 }
}
